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

package software.amazon.awssdk.custom.s3.transfer;

import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.testutils.service.S3BucketUtils.temporaryBucketName;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.custom.s3.transfer.util.ChecksumUtils;
import software.amazon.awssdk.custom.s3.transfer.internal.S3CrtAsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.testutils.RandomTempFile;

public class S3TransferManagerUploadIntegrationTest extends S3IntegrationTestBase {
    private static final String TEST_BUCKET = temporaryBucketName(S3TransferManagerUploadIntegrationTest.class);
    private static final String TEST_KEY = "8mib_file.dat";
    private static final int OBJ_SIZE = 8 * 1024 * 1024;

    private static RandomTempFile testFile;
    private static S3CrtAsyncClient s3Crt;
    private static S3TransferManager tm;

    @BeforeClass
    public static void setUp() throws Exception {
        S3IntegrationTestBase.setUp();
        createBucket(TEST_BUCKET);

        testFile = new RandomTempFile(TEST_KEY, OBJ_SIZE);

        s3Crt = S3CrtAsyncClient.builder()
                .credentialsProvider(CREDENTIALS_PROVIDER_CHAIN)
                .region(DEFAULT_REGION)
                .build();

        tm = S3TransferManager.builder()
                .s3CrtClient(s3Crt)
                .build();
    }

    @AfterClass
    public static void teardown() throws IOException {
        tm.close();
        s3Crt.close();
        Files.delete(testFile.toPath());
        deleteBucketAndAllContents(TEST_BUCKET);
    }

    @Test
    public void upload_fileSentCorrectly() throws IOException, NoSuchAlgorithmException {
        Upload upload = tm.upload(UploadRequest.builder()
                .bucket(TEST_BUCKET)
                .key(TEST_KEY)
                .source(testFile.toPath())
                .build());

        upload.completionFuture().join();

        ResponseInputStream<GetObjectResponse> obj = s3.getObject(r -> r.bucket(TEST_BUCKET).key(TEST_KEY),
                ResponseTransformer.toInputStream());

        assertThat(ChecksumUtils.computeCheckSum(Files.newInputStream(testFile.toPath())))
                .isEqualTo(ChecksumUtils.computeCheckSum(obj));
    }
}
