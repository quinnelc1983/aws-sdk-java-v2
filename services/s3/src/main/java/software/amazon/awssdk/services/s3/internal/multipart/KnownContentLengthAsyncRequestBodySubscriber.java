/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.services.s3.internal.multipart;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.annotations.SdkTestInternalApi;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.multipart.S3ResumeToken;
import software.amazon.awssdk.utils.Logger;
import software.amazon.awssdk.utils.Pair;

@SdkInternalApi
public class KnownContentLengthAsyncRequestBodySubscriber implements Subscriber<AsyncRequestBody>  {

    private static final Logger log = Logger.loggerFor(KnownContentLengthAsyncRequestBodySubscriber.class);

    /**
     * The number of AsyncRequestBody has been received but yet to be processed
     */
    private final AtomicInteger asyncRequestBodyInFlight = new AtomicInteger(0);
    private final AtomicBoolean failureActionInitiated = new AtomicBoolean(false);
    private final AtomicInteger partNumber = new AtomicInteger(1);
    private final MultipartUploadHelper multipartUploadHelper;
    private final long partSize;
    private final int partCount;
    private final int numExistingParts;
    private final String uploadId;
    private final Collection<CompletableFuture<CompletedPart>> futures = new ConcurrentLinkedQueue<>();
    private final PutObjectRequest putObjectRequest;
    private final CompletableFuture<PutObjectResponse> returnFuture;
    private final Map<Integer, CompletedPart> completedParts;
    private final Map<Integer, CompletedPart> existingParts;
    private Subscription subscription;
    private volatile boolean isDone;
    private volatile boolean isPaused;
    private volatile CompletableFuture<CompleteMultipartUploadResponse> completeMpuFuture;

    KnownContentLengthAsyncRequestBodySubscriber(MpuRequestContext mpuRequestContext,
                                                 CompletableFuture<PutObjectResponse> returnFuture,
                                                 MultipartUploadHelper multipartUploadHelper) {
        this.partSize = mpuRequestContext.partSize();
        this.partCount = determinePartCount(mpuRequestContext.contentLength(), partSize);
        this.putObjectRequest = mpuRequestContext.request().left();
        this.returnFuture = returnFuture;
        this.uploadId = mpuRequestContext.uploadId();
        this.existingParts = mpuRequestContext.existingParts();
        this.numExistingParts = (int) mpuRequestContext.numPartsCompleted();
        this.completedParts = new ConcurrentHashMap<>();
        this.multipartUploadHelper = multipartUploadHelper;
    }

    private int determinePartCount(long contentLength, long partSize) {
        return (int) Math.ceil(contentLength / (double) partSize);
    }

    public S3ResumeToken pause() {
        isPaused = true;

        if (completeMpuFuture != null && completeMpuFuture.isDone()) {
            return null;
        }

        if (completeMpuFuture != null && !completeMpuFuture.isDone()) {
            completeMpuFuture.cancel(true);
        }

        long numPartsCompleted = 0;
        for (CompletableFuture<CompletedPart> cf : futures) {
            if (!cf.isDone()) {
                cf.cancel(true);
            } else {
                numPartsCompleted++;
            }
        }

        return S3ResumeToken.builder()
                            .uploadId(uploadId)
                            .partSize(partSize)
                            .totalNumParts(partCount)
                            .numPartsCompleted(numPartsCompleted + numExistingParts)
                            .build();
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (this.subscription != null) {
            log.warn(() -> "The subscriber has already been subscribed. Cancelling the incoming subscription");
            subscription.cancel();
            return;
        }
        this.subscription = s;
        s.request(1);
        returnFuture.whenComplete((r, t) -> {
            if (t != null) {
                s.cancel();
                if (shouldFailRequest()) {
                    multipartUploadHelper.failRequestsElegantly(futures, t, uploadId, returnFuture, putObjectRequest);
                }
            }
        });
    }

    @Override
    public void onNext(AsyncRequestBody asyncRequestBody) {
        if (isPaused) {
            return;
        }

        if (existingParts.containsKey(partNumber.get())) {
            partNumber.getAndIncrement();
            asyncRequestBody.subscribe(new CancelledSubscriber<>());
            subscription.request(1);
            return;
        }

        asyncRequestBodyInFlight.incrementAndGet();
        UploadPartRequest uploadRequest = SdkPojoConversionUtils.toUploadPartRequest(putObjectRequest,
                                                                                     partNumber.getAndIncrement(),
                                                                                     uploadId);

        Consumer<CompletedPart> completedPartConsumer =
            completedPart -> completedParts.put(completedPart.partNumber(), completedPart);
        multipartUploadHelper.sendIndividualUploadPartRequest(uploadId, completedPartConsumer, futures,
                                                              Pair.of(uploadRequest, asyncRequestBody))
                             .whenComplete((r, t) -> {
                                 if (t != null) {
                                     if (shouldFailRequest()) {
                                         multipartUploadHelper.failRequestsElegantly(futures, t, uploadId, returnFuture,
                                                                                     putObjectRequest);
                                     }
                                 } else {
                                     completeMultipartUploadIfFinished(asyncRequestBodyInFlight.decrementAndGet());
                                 }
                             });
        subscription.request(1);
    }

    private boolean shouldFailRequest() {
        return failureActionInitiated.compareAndSet(false, true) && !isPaused;
    }

    @Override
    public void onError(Throwable t) {
        log.debug(() -> "Received onError ", t);
        if (failureActionInitiated.compareAndSet(false, true)) {
            multipartUploadHelper.failRequestsElegantly(futures, t, uploadId, returnFuture, putObjectRequest);
        }
    }

    @Override
    public void onComplete() {
        log.debug(() -> "Received onComplete()");
        isDone = true;
        if (!isPaused) {
            completeMultipartUploadIfFinished(asyncRequestBodyInFlight.get());
        }
    }

    private void completeMultipartUploadIfFinished(int requestsInFlight) {
        if (isDone && requestsInFlight == 0) {
            CompletedPart[] parts;
            if (existingParts.isEmpty()) {
                parts = completedParts.values().toArray(new CompletedPart[0]);
            } else if (!completedParts.isEmpty()) {
                // List of CompletedParts needs to be in ascending order
                parts = mergeCompletedParts();
            } else {
                parts = existingParts.values().toArray(new CompletedPart[0]);
            }
            completeMpuFuture = multipartUploadHelper.completeMultipartUpload(returnFuture, uploadId, parts,
                                                                              putObjectRequest);
        }
    }

    private CompletedPart[] mergeCompletedParts() {
        CompletedPart[] merged = new CompletedPart[partCount];
        int currPart = 1;
        while (currPart < partCount + 1) {
            CompletedPart completedPart = existingParts.containsKey(currPart) ? existingParts.get(currPart) :
                                          completedParts.get(currPart);
            merged[currPart - 1] = completedPart;
            currPart++;
        }
        return merged;
    }

    @SdkTestInternalApi
    public void setCompleteMpuFuture(CompletableFuture<CompleteMultipartUploadResponse> completeMpuFuture) {
        this.completeMpuFuture = completeMpuFuture;
    }

}
