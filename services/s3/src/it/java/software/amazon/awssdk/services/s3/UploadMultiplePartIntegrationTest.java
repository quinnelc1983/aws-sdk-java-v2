/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package software.amazon.awssdk.services.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.testutils.service.S3BucketUtils.temporaryBucketName;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListMultipartUploadsResponse;
import software.amazon.awssdk.services.s3.model.MultipartUpload;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

public class UploadMultiplePartIntegrationTest extends S3IntegrationTestBase {

    private static final String BUCKET = temporaryBucketName(UploadMultiplePartIntegrationTest.class);
    private static final String CONTENT = RandomStringUtils.randomAscii(1000);

    @BeforeClass
    public static void setupFixture() {
        createBucket(BUCKET);
    }

    @AfterClass
    public static void tearDownFixture() {
        deleteBucketAndAllContents(BUCKET);
    }

    @Test
    public void uploadMultiplePart_complete() throws Exception {
        String key = "uploadMultiplePart_complete";

        // 1. Initiate multipartUpload request
        String uploadId = initiateMultipartUpload(key);

        int partCount = 1;
        List<String> contentsToUpload = new ArrayList<>();

        // 2. Upload each part
        List<UploadPartResponse> uploadPartResponses = uploadParts(key, uploadId, partCount, contentsToUpload);

        List<CompletedPart> completedParts = new ArrayList<>();

        for (int i = 0; i < uploadPartResponses.size(); i++) {
            int partNumber = i + 1;
            UploadPartResponse response = uploadPartResponses.get(i);
            completedParts.add(CompletedPart.builder().eTag(response.eTag()).partNumber(partNumber).build());
        }

        // 3. Complete multipart upload
        CompleteMultipartUploadResponse completeMultipartUploadResponse =
            s3.completeMultipartUpload(b -> b.bucket(BUCKET)
                                             .key(key)
                                             .uploadId(uploadId)
                                             .multipartUpload(CompletedMultipartUpload.builder()
                                                                                      .parts(completedParts)
                                                                                      .build()).build());

        assertThat(completeMultipartUploadResponse).isNotNull();
        verifyMultipartUploadResult(key, contentsToUpload);
    }

    @Test
    public void uploadMultiplePart_abort() {
        String key = "uploadMultiplePart_abort";

        // 1. Initiate multipartUpload request
        String uploadId = initiateMultipartUpload(key);
        int partCount = 3;

        // 2. Upload each part
        List<String> contentsToUpload = new ArrayList<>();
        List<UploadPartResponse> uploadPartResponses = uploadParts(key, uploadId, partCount, contentsToUpload);

        // 3. abort the multipart upload
        AbortMultipartUploadResponse abortMultipartUploadResponse =
            s3.abortMultipartUpload(b -> b.bucket(BUCKET).key(key).uploadId(uploadId));

        // Verify no in-progress multipart uploads
        ListMultipartUploadsResponse listMultipartUploadsResponse = s3.listMultipartUploads(b -> b.bucket(BUCKET));

        List<MultipartUpload> uploads = listMultipartUploadsResponse.uploads();

        assertThat(uploads).isEmpty();
    }

    private void verifyMultipartUploadResult(String key, List<String> contentsToUpload) throws Exception {
        ResponseBytes<GetObjectResponse> objectAsBytes = s3.getObject(b -> b.bucket(BUCKET).key(key),
                                                                      ResponseTransformer.toBytes());
        String appendedString = String.join("", contentsToUpload);
        assertThat(objectAsBytes.asUtf8String()).isEqualTo(appendedString);
    }

    private List<UploadPartResponse> uploadParts(String key, String uploadId, int partCount, List<String> contentsToUpload) {
        List<UploadPartResponse> uploadPartResponses = new ArrayList<>();

        for (int i = 0; i < partCount; i++) {
            int partNumber = i + 1;
            contentsToUpload.add(CONTENT);
            UploadPartRequest uploadPartRequest = UploadPartRequest.builder().bucket(BUCKET).key(key)
                                                                   .uploadId(uploadId)
                                                                   .partNumber(partNumber)
                                                                   .build();

            uploadPartResponses.add(s3.uploadPart(uploadPartRequest, RequestBody.fromString(CONTENT)));
        }
        return uploadPartResponses;
    }

    private String initiateMultipartUpload(String key) {
        return s3.createMultipartUpload(b -> b.bucket(BUCKET).key(key)).uploadId();
    }
}
