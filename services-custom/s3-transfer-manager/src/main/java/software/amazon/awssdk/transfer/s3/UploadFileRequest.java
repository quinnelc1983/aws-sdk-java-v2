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

package software.amazon.awssdk.transfer.s3;

import static software.amazon.awssdk.utils.Validate.paramNotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import software.amazon.awssdk.annotations.NotThreadSafe;
import software.amazon.awssdk.annotations.SdkPreviewApi;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.ToString;
import software.amazon.awssdk.utils.Validate;
import software.amazon.awssdk.utils.builder.CopyableBuilder;
import software.amazon.awssdk.utils.builder.ToCopyableBuilder;

/**
 * Upload a local file to an object in S3. For non-file-based uploads, you may use {@link UploadRequest} instead.
 *
 * @see S3TransferManager#uploadFile(UploadFileRequest)
 */
@SdkPublicApi
@SdkPreviewApi
public final class UploadFileRequest
    implements TransferObjectRequest,
               ToCopyableBuilder<UploadFileRequest.Builder, UploadFileRequest> {

    private final PutObjectRequest putObjectRequest;
    private final Path source;
    private final TransferRequestOverrideConfiguration configuration;

    private UploadFileRequest(DefaultBuilder builder) {
        this.putObjectRequest = paramNotNull(builder.putObjectRequest, "putObjectRequest");
        this.source = paramNotNull(builder.source, "source");
        this.configuration = builder.configuration;
    }

    /**
     * @return The {@link PutObjectRequest} request that should be used for the upload
     */
    public PutObjectRequest putObjectRequest() {
        return putObjectRequest;
    }

    /**
     * The {@link Path} containing data to send to the service.
     *
     * @return the request body
     */
    public Path source() {
        return source;
    }

    /**
     * @return the optional override configuration
     * @see Builder#overrideConfiguration(TransferRequestOverrideConfiguration)
     */
    @Override
    public Optional<TransferRequestOverrideConfiguration> overrideConfiguration() {
        return Optional.ofNullable(configuration);
    }

    /**
     * Create a builder that can be used to create a {@link UploadFileRequest}.
     *
     * @see S3TransferManager#uploadFile(UploadFileRequest)
     */
    public static Builder builder() {
        return new DefaultBuilder();
    }

    public static Class<? extends Builder> serializableBuilderClass() {
        return DefaultBuilder.class;
    }
    
    @Override
    public Builder toBuilder() {
        return new DefaultBuilder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadFileRequest that = (UploadFileRequest) o;

        if (!Objects.equals(putObjectRequest, that.putObjectRequest)) {
            return false;
        }
        if (!Objects.equals(source, that.source)) {
            return false;
        }
        return Objects.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        int result = putObjectRequest != null ? putObjectRequest.hashCode() : 0;
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToString.builder("UploadFileRequest")
                       .add("putObjectRequest", putObjectRequest)
                       .add("source", source)
                       .add("configuration", configuration)
                       .build();
    }

    /**
     * A builder for a {@link UploadFileRequest}, created with {@link #builder()}
     */
    @SdkPublicApi
    @NotThreadSafe
    public interface Builder extends CopyableBuilder<Builder, UploadFileRequest> {

        /**
         * The {@link Path} to file containing data to send to the service. File will be read entirely and may be read
         * multiple times in the event of a retry. If the file does not exist or the current user does not have
         * access to read it then an exception will be thrown.
         *
         * @param source the source path
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        Builder source(Path source);

        /**
         * The file containing data to send to the service. File will be read entirely and may be read
         * multiple times in the event of a retry. If the file does not exist or the current user does not have
         * access to read it then an exception will be thrown.
         *
         * @param source the source path
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        default Builder source(File source) {
            Validate.paramNotNull(source, "source");
            return this.source(source.toPath());
        }

        /**
         * Configure the {@link PutObjectRequest} that should be used for the upload
         *
         * @param putObjectRequest the putObjectRequest
         * @return Returns a reference to this object so that method calls can be chained together.
         * @see #putObjectRequest(Consumer)
         */
        Builder putObjectRequest(PutObjectRequest putObjectRequest);

        /**
         * Configure the {@link PutObjectRequest} that should be used for the upload
         *
         * <p>
         * This is a convenience method that creates an instance of the {@link PutObjectRequest} builder avoiding the
         * need to create one manually via {@link PutObjectRequest#builder()}.
         *
         * @param putObjectRequestBuilder the putObjectRequest consumer builder
         * @return Returns a reference to this object so that method calls can be chained together.
         * @see #putObjectRequest(PutObjectRequest)
         */
        default Builder putObjectRequest(Consumer<PutObjectRequest.Builder> putObjectRequestBuilder) {
            return putObjectRequest(PutObjectRequest.builder()
                                                    .applyMutation(putObjectRequestBuilder)
                                                    .build());
        }

        /**
         * Add an optional request override configuration.
         *
         * @param configuration The override configuration.
         * @return This builder for method chaining.
         */
        Builder overrideConfiguration(TransferRequestOverrideConfiguration configuration);

        /**
         * Similar to {@link #overrideConfiguration(TransferRequestOverrideConfiguration)}, but takes a lambda to configure a new
         * {@link TransferRequestOverrideConfiguration.Builder}. This removes the need to call {@link
         * TransferRequestOverrideConfiguration#builder()} and {@link TransferRequestOverrideConfiguration.Builder#build()}.
         *
         * @param configurationBuilder the upload configuration
         * @return this builder for method chaining.
         * @see #overrideConfiguration(TransferRequestOverrideConfiguration)
         */
        default Builder overrideConfiguration(Consumer<TransferRequestOverrideConfiguration.Builder> configurationBuilder) {
            Validate.paramNotNull(configurationBuilder, "configurationBuilder");
            return overrideConfiguration(TransferRequestOverrideConfiguration.builder()
                                                                             .applyMutation(configurationBuilder)
                                                                             .build());
        }
    }

    private static class DefaultBuilder implements Builder {
        private PutObjectRequest putObjectRequest;
        private Path source;
        private TransferRequestOverrideConfiguration configuration;

        private DefaultBuilder() {
        }

        private DefaultBuilder(UploadFileRequest uploadFileRequest) {
            this.source = uploadFileRequest.source;
            this.putObjectRequest = uploadFileRequest.putObjectRequest;
            this.configuration = uploadFileRequest.configuration;
        }

        @Override
        public Builder source(Path source) {
            this.source = Validate.paramNotNull(source, "source");
            return this;
        }

        public Path getSource() {
            return source;
        }

        public void setSource(Path source) {
            source(source);
        }

        @Override
        public Builder putObjectRequest(PutObjectRequest putObjectRequest) {
            this.putObjectRequest = putObjectRequest;
            return this;
        }

        public PutObjectRequest getPutObjectRequest() {
            return putObjectRequest;
        }

        public void setPutObjectRequest(PutObjectRequest putObjectRequest) {
            putObjectRequest(putObjectRequest);
        }

        @Override
        public Builder overrideConfiguration(TransferRequestOverrideConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

        public void setOverrideConfiguration(TransferRequestOverrideConfiguration configuration) {
            overrideConfiguration(configuration);
        }

        public TransferRequestOverrideConfiguration getOverrideConfiguration() {
            return configuration;
        }

        @Override
        public UploadFileRequest build() {
            return new UploadFileRequest(this);
        }
    }
}
