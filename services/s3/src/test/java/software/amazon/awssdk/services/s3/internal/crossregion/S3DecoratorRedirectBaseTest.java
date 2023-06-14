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

package software.amazon.awssdk.services.s3.internal.crossregion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.endpoints.EndpointProvider;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3ServiceClientConfiguration;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointProvider;
import software.amazon.awssdk.services.s3.internal.crossregion.endpointprovider.BucketEndpointProvider;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

public abstract class S3DecoratorRedirectBaseTest {

    protected static final String CROSS_REGION_BUCKET = "anyBucket";
    public static final String X_AMZ_BUCKET_REGION = "x-amz-bucket-region";
    protected static final String CROSS_REGION = Region.EU_CENTRAL_1.toString();
    protected static final String CHANGED_CROSS_REGION = "us-west-1";

    protected static final List<S3Object> S3_OBJECTS = Collections.singletonList(S3Object.builder().key("keyObject").build());

    protected static final S3ServiceClientConfiguration ENDPOINT_CONFIGURED =
        S3ServiceClientConfiguration.builder().endpointProvider(S3EndpointProvider.defaultProvider()).build();

    @Test
    void decoratorAttemptsToRetryWithRegionNameInErrorResponse() throws Throwable {
        stubServiceClientConfiguration();
        stubClientAPICallWithFirstRedirectThenSuccessWithRegionInErrorResponse();
        // Assert retrieved listObject
        ListObjectsResponse listObjectsResponse = firstApiCallToService();
        assertThat(listObjectsResponse.contents()).isEqualTo(S3_OBJECTS);

        ArgumentCaptor<ListObjectsRequest> requestArgumentCaptor = ArgumentCaptor.forClass(ListObjectsRequest.class);
        verifyTheApiServiceCall(2, requestArgumentCaptor);

        assertThat(requestArgumentCaptor.getAllValues().get(0).overrideConfiguration()).isNotPresent();
        verifyTheEndPointProviderOverridden(1,requestArgumentCaptor, CROSS_REGION);

        verifyHeadBucketServiceCall(0);
    }

    @Test
    void decoratorUsesCache_when_CrossRegionAlreadyPresent() throws Throwable {
        stubServiceClientConfiguration();
        stubRedirectSuccessSuccess();

        ListObjectsResponse listObjectsResponse = firstApiCallToService();
        assertThat(listObjectsResponse.contents()).isEqualTo(S3_OBJECTS);

        ListObjectsResponse listObjectsResponseSecondCall = firstApiCallToService();
        assertThat(listObjectsResponseSecondCall.contents()).isEqualTo(S3_OBJECTS);

        ArgumentCaptor<ListObjectsRequest> requestArgumentCaptor = ArgumentCaptor.forClass(ListObjectsRequest.class);
        verifyTheApiServiceCall(3, requestArgumentCaptor);

        assertThat(requestArgumentCaptor.getAllValues().get(0).overrideConfiguration()).isNotPresent();
        verifyTheEndPointProviderOverridden(1,requestArgumentCaptor, CROSS_REGION);
        verifyTheEndPointProviderOverridden(2,requestArgumentCaptor, CROSS_REGION);
    }

    /**
     * Call is redirected to actual end point
     * The redirected call fails because of incorrect parameters passed
     * This exception should be reported correctly
     */
    @Test
    void apiCallFailure_when_CallFailsAfterRedirection() {
        stubServiceClientConfiguration();
        stubRedirectThenError();
        assertThatExceptionOfType(S3Exception.class)
            .isThrownBy(
                () -> firstApiCallToService())
            .withMessage("Invalid id (Service: S3, Status Code: 400, Request ID: 1, "
                         + "Extended Request ID: A1)");
        verifyHeadBucketServiceCall(0);
    }

    @Test
    void headBucketCalled_when_RedirectDoesNotHasRegionName() throws Throwable {
        stubServiceClientConfiguration();
        stubRedirectWithNoRegionAndThenSuccess();
        stubHeadBucketRedirect();
        ListObjectsResponse listObjectsResponse = firstApiCallToService();
        assertThat(listObjectsResponse.contents()).isEqualTo(S3_OBJECTS);

        ArgumentCaptor<ListObjectsRequest> requestArgumentCaptor = ArgumentCaptor.forClass(ListObjectsRequest.class);
        verifyTheApiServiceCall(2, requestArgumentCaptor);

        assertThat(requestArgumentCaptor.getAllValues().get(0).overrideConfiguration()).isNotPresent();
        verifyTheEndPointProviderOverridden(1,requestArgumentCaptor, CROSS_REGION);
        verifyHeadBucketServiceCall(1);
    }

    @Test
    void headBucketCalledAndCached__when_RedirectDoesNotHasRegionName() throws Throwable {
        stubServiceClientConfiguration();
        stubRedirectWithNoRegionAndThenSuccess();
        stubHeadBucketRedirect();
        ListObjectsResponse listObjectsResponse = firstApiCallToService();
        assertThat(listObjectsResponse.contents()).isEqualTo(S3_OBJECTS);

        ArgumentCaptor<ListObjectsRequest> preCacheCaptor = ArgumentCaptor.forClass(ListObjectsRequest.class);
        verifyTheApiServiceCall(2, preCacheCaptor);
        // We need to get the BucketEndpointProvider in order to update the cache
        verifyTheEndPointProviderOverridden(1,preCacheCaptor, CROSS_REGION);
        listObjectsResponse = firstApiCallToService();
        assertThat(listObjectsResponse.contents()).isEqualTo(S3_OBJECTS);
        // We need to captor again so that we get the args used in second API Call
        ArgumentCaptor<ListObjectsRequest> overAllPostCacheCaptor = ArgumentCaptor.forClass(ListObjectsRequest.class);
        verifyTheApiServiceCall(3, overAllPostCacheCaptor);
        assertThat(overAllPostCacheCaptor.getAllValues().get(0).overrideConfiguration()).isNotPresent();
        verifyTheEndPointProviderOverridden(1,overAllPostCacheCaptor, CROSS_REGION);
        verifyTheEndPointProviderOverridden(2,overAllPostCacheCaptor, CROSS_REGION);
        verifyHeadBucketServiceCall(1);
    }

    @Test
    void requestsAreNotOverridden_when_NoBucketInRequest() throws Throwable {
        stubServiceClientConfiguration();
        stubApiWithNoBucketField();
        stubHeadBucketRedirect();
        assertThatExceptionOfType(S3Exception.class)
            .isThrownBy(
                () -> noBucketCallToService())
            .withMessage("Redirect (Service: S3, Status Code: 301, Request ID: 1, "
                         + "Extended Request ID: A1)");
        ArgumentCaptor<ListBucketsRequest> requestArgumentCaptor = ArgumentCaptor.forClass(ListBucketsRequest.class);
        verifyHeadBucketServiceCall(0);
        verifyNoBucketApiCall(1, requestArgumentCaptor);
        assertThat(requestArgumentCaptor.getAllValues().get(0).overrideConfiguration()).isNotPresent();
    }

    protected abstract void verifyNoBucketApiCall(int i, ArgumentCaptor<ListBucketsRequest> requestArgumentCaptor);

    protected abstract ListBucketsResponse noBucketCallToService() throws Throwable;

    protected abstract void stubApiWithNoBucketField();

    protected abstract void stubHeadBucketRedirect();

    protected abstract void stubRedirectWithNoRegionAndThenSuccess();

    protected abstract void stubRedirectThenError();

    protected abstract void stubRedirectSuccessSuccess();

    protected AwsServiceException redirectException(int statusCode, String region, String errorCode, String errorMessage) {
        SdkHttpFullResponse.Builder sdkHttpFullResponseBuilder = SdkHttpFullResponse.builder();
        if (region != null) {
            sdkHttpFullResponseBuilder.appendHeader(X_AMZ_BUCKET_REGION, region);
        }
        return S3Exception.builder()
                          .statusCode(statusCode)
                          .requestId("1")
                          .extendedRequestId("A1")
                          .awsErrorDetails(AwsErrorDetails.builder()
                                                          .errorMessage(errorMessage != null ? errorMessage : null)
                                                          .sdkHttpResponse(sdkHttpFullResponseBuilder.build())
                                                          .errorCode(errorCode)
                                                          .serviceName("S3")
                                                          .build())
                          .build();
    }

    void verifyTheEndPointProviderOverridden(int attempt, ArgumentCaptor<ListObjectsRequest> requestArgumentCaptor,
                                             String region) throws Exception {
        EndpointProvider overridenEndpointProvider =
            requestArgumentCaptor.getAllValues().get(attempt).overrideConfiguration().get().endpointProvider().get();
        assertThat(overridenEndpointProvider).isInstanceOf(BucketEndpointProvider.class);
        assertThat(((S3EndpointProvider) overridenEndpointProvider)
                       .resolveEndpoint(e -> e.region(Region.US_WEST_2).bucket(CROSS_REGION_BUCKET).build()).get().url().getHost())
            .isEqualTo("s3." + region + ".amazonaws.com");
    }

    protected abstract ListObjectsResponse firstApiCallToService() throws Throwable;

    protected abstract void verifyTheApiServiceCall(int times, ArgumentCaptor<ListObjectsRequest> requestArgumentCaptor) ;
    protected abstract void verifyHeadBucketServiceCall(int times) ;

    protected abstract void stubServiceClientConfiguration();

    protected abstract void stubClientAPICallWithFirstRedirectThenSuccessWithRegionInErrorResponse();
}
