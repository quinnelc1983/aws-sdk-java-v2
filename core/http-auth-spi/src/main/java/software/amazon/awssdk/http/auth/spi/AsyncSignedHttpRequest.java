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

package software.amazon.awssdk.http.auth.spi;

import java.nio.ByteBuffer;
import org.reactivestreams.Publisher;
import software.amazon.awssdk.annotations.Immutable;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.annotations.ThreadSafe;
import software.amazon.awssdk.http.SdkHttpRequest;
import software.amazon.awssdk.http.auth.spi.internal.DefaultAsyncSignedHttpRequest;
import software.amazon.awssdk.utils.builder.SdkBuilder;

/**
 * Represents a request that has been signed by {@link HttpSigner}.
 ** //TODO:
 */
@SdkPublicApi
@Immutable
@ThreadSafe
public interface AsyncSignedHttpRequest extends SignedHttpRequest<Publisher<ByteBuffer>> {

    /**
     * Get a new builder for creating a {@link SyncSignedHttpRequest}.
     */
    static Builder builder() {
        return new DefaultAsyncSignedHttpRequest.BuilderImpl();
    }

    /**
     * A builder for a {@link SyncSignedHttpRequest}.
     */
    interface Builder extends SignedHttpRequest.Builder<Publisher<ByteBuffer>>, SdkBuilder<Builder, AsyncSignedHttpRequest> {

        /**
         * Set the HTTP request object, without the request body payload.
         */
        @Override
        Builder request(SdkHttpRequest request);

        /**
         * Set the body payload of the request. A payload is optional. By default, the payload will be empty.
         */
        @Override
        Builder payload(Publisher<ByteBuffer> payload);
    }
}
