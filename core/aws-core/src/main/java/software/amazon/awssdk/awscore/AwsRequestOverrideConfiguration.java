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

package software.amazon.awssdk.awscore;

import java.util.Objects;
import java.util.Optional;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.CredentialUtils;
import software.amazon.awssdk.core.RequestOverrideConfiguration;
import software.amazon.awssdk.identity.spi.AwsCredentialsIdentity;
import software.amazon.awssdk.identity.spi.IdentityProvider;
import software.amazon.awssdk.utils.builder.SdkBuilder;

/**
 * Request-specific configuration overrides for AWS service clients.
 */
@SdkPublicApi
public final class AwsRequestOverrideConfiguration extends RequestOverrideConfiguration {
    private final AwsCredentialsProvider credentialsProvider;

    private AwsRequestOverrideConfiguration(Builder builder) {
        super(builder);
        this.credentialsProvider = builder.credentialsProvider();
    }

    /**
     * Create a {@link AwsRequestOverrideConfiguration} from the provided {@link RequestOverrideConfiguration}.
     *
     * Given null, this will return null. Given a {@code AwsRequestOverrideConfiguration} this will return the input. Given
     * any other {@code RequestOverrideConfiguration} this will return a new {@code AwsRequestOverrideConfiguration} with all
     * the common attributes from the input copied into the result.
     */
    public static AwsRequestOverrideConfiguration from(RequestOverrideConfiguration configuration) {
        if (configuration == null) {
            return null;
        }

        if (configuration instanceof AwsRequestOverrideConfiguration) {
            return (AwsRequestOverrideConfiguration) configuration;
        }

        return new AwsRequestOverrideConfiguration.BuilderImpl(configuration).build();
    }

    /**
     * The optional {@link AwsCredentialsProvider} that will provide credentials to be used to authenticate this request.
     *
     * @return The optional {@link AwsCredentialsProvider}.
     */
    // TODO: Note, this method is called from generated public classes, when endpoint discover is involved.
    public Optional<AwsCredentialsProvider> credentialsProvider() {
        return Optional.ofNullable(credentialsProvider);
    }

    // TODO: Cannot change the return type of {@link #credentialsProvider()} so creating another method returning the same
    //       object but of new super type.
    // TODO: As mentioned below, another option is to save reference of IdentityProvider<? extends AwsCredentialsProvider> and
    //       convert in above method. Either ways, need 2 methods to return the 2 different types.
    /**
     * The optional {@link IdentityProvider<? extends AwsCredentialsIdentity>} that will provide credentials to be used to
     * authenticate this request.
     *
     * @return The optional {@link IdentityProvider<? extends AwsCredentialsIdentity>}.
     */
    public Optional<IdentityProvider<? extends AwsCredentialsIdentity>> credentialsIdentityProvider() {
        return Optional.ofNullable(credentialsProvider);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AwsRequestOverrideConfiguration that = (AwsRequestOverrideConfiguration) o;
        return Objects.equals(credentialsProvider, that.credentialsProvider);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + super.hashCode();
        hashCode = 31 * hashCode + Objects.hashCode(credentialsProvider);
        return hashCode;
    }

    public interface Builder extends RequestOverrideConfiguration.Builder<Builder>,
                                     SdkBuilder<Builder, AwsRequestOverrideConfiguration> {
        /**
         * Set the optional {@link AwsCredentialsProvider} that will provide credentials to be used to authenticate this request.
         *
         * @param credentialsProvider The {@link AwsCredentialsProvider}.
         * @return This object for chaining.
         */
        default Builder credentialsProvider(AwsCredentialsProvider credentialsProvider) {
            return credentialsProvider((IdentityProvider<AwsCredentialsIdentity>) credentialsProvider);
        }

        /**
         * Set the optional {@link IdentityProvider<? extends AwsCredentialsIdentity>} that will provide credentials to be used
         * to authenticate this request.
         *
         * @param credentialsProvider The {@link IdentityProvider<? extends AwsCredentialsIdentity>}.
         * @return This object for chaining.
         */
        default Builder credentialsProvider(IdentityProvider<? extends AwsCredentialsIdentity> credentialsProvider) {
            throw new UnsupportedOperationException();
        }

        /**
         * Return the optional {@link AwsCredentialsProvider} that will provide credentials to be used to authenticate this
         * request.
         *
         * @return The optional {@link AwsCredentialsProvider}.
         */
        AwsCredentialsProvider credentialsProvider();

        @Override
        AwsRequestOverrideConfiguration build();
    }

    private static final class BuilderImpl extends RequestOverrideConfiguration.BuilderImpl<Builder> implements Builder {

        private AwsCredentialsProvider awsCredentialsProvider;


        private BuilderImpl() {
        }

        private BuilderImpl(RequestOverrideConfiguration requestOverrideConfiguration) {
            super(requestOverrideConfiguration);
        }

        private BuilderImpl(AwsRequestOverrideConfiguration awsRequestOverrideConfig) {
            super(awsRequestOverrideConfig);
            this.awsCredentialsProvider = awsRequestOverrideConfig.credentialsProvider;
        }

        @Override
        public Builder credentialsProvider(AwsCredentialsProvider credentialsProvider) {
            // TODO: Another option:
            //       credentialsProvider((IdentityProvider<? extends AwsCredentialsIdentity>) credentialsProvider);
            //       But that would lead to potentially converting a AwsCredentialsProvider to another AwsCredentialsProvider
            //       to fulfil AwsRequestOverrideConfiguration.credentialsProvider() to return AwsCredentialsProvider type.
            this.awsCredentialsProvider = credentialsProvider;
            return this;
        }

        @Override
        public Builder credentialsProvider(IdentityProvider<? extends AwsCredentialsIdentity> credentialsProvider) {
            this.awsCredentialsProvider = CredentialUtils.toCredentialsProvider(credentialsProvider);
            return this;
        }

        @Override
        public AwsCredentialsProvider credentialsProvider() {
            return awsCredentialsProvider;
        }

        @Override
        public AwsRequestOverrideConfiguration build() {
            return new AwsRequestOverrideConfiguration(this);
        }
    }
}
