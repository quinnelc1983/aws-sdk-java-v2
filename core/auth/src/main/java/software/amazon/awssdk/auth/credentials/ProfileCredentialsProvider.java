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

package software.amazon.awssdk.auth.credentials;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.annotations.SdkTestInternalApi;
import software.amazon.awssdk.auth.credentials.internal.ProfileCredentialsUtils;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.profiles.ProfileFileSupplier;
import software.amazon.awssdk.profiles.ProfileFileSystemSetting;
import software.amazon.awssdk.utils.IoUtils;
import software.amazon.awssdk.utils.SdkAutoCloseable;
import software.amazon.awssdk.utils.ToString;
import software.amazon.awssdk.utils.builder.CopyableBuilder;
import software.amazon.awssdk.utils.builder.ToCopyableBuilder;

/**
 * Credentials provider based on AWS configuration profiles. This loads credentials from a {@link ProfileFile}, allowing you to
 * share multiple sets of AWS security credentials between different tools like the AWS SDK for Java and the AWS CLI.
 *
 * <p>See http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html</p>
 *
 * <p>If this credentials provider is loading assume-role credentials from STS, it should be cleaned up with {@link #close()} if
 * it is no longer being used.</p>
 *
 * @see ProfileFile
 */
@SdkPublicApi
public final class ProfileCredentialsProvider
    implements AwsCredentialsProvider,
               SdkAutoCloseable,
               ToCopyableBuilder<ProfileCredentialsProvider.Builder, ProfileCredentialsProvider> {

    private AwsCredentials credentials;
    private final RuntimeException loadException;
    private final ProfileFileSupplier profileFileSupplier;
    private volatile ProfileFile currentProfileFile;
    private final String profileName;
    private final Supplier<ProfileFile> defaultProfileFileLoader;

    /**
     * @see #builder()
     */
    private ProfileCredentialsProvider(BuilderImpl builder) {
        this.defaultProfileFileLoader = builder.defaultProfileFileLoader;

        RuntimeException thrownException = null;
        String selectedProfileName = null;
        ProfileFileSupplier selectedProfileSupplier = null;

        try {
            selectedProfileName = Optional.ofNullable(builder.profileName)
                                          .orElseGet(ProfileFileSystemSetting.AWS_PROFILE::getStringValueOrThrow);

            selectedProfileSupplier = Optional.ofNullable(builder.profileFileSupplier)
                                              .orElseGet(() -> ProfileFileSupplier
                                                  .fixedProfileFile(builder.defaultProfileFileLoader.get()));

        } catch (RuntimeException e) {
            // If we couldn't load the credentials provider for some reason, save an exception describing why. This exception
            // will only be raised on calls to resolveCredentials. We don't want to raise an exception here because it may be
            // expected (eg. in the default credential chain).
            thrownException = e;
        }

        this.loadException = thrownException;
        this.profileName = selectedProfileName;
        this.profileFileSupplier = selectedProfileSupplier;
    }

    /**
     * Create a {@link ProfileCredentialsProvider} using the {@link ProfileFile#defaultProfileFile()} and default profile name.
     * Use {@link #builder()} for defining a custom {@link ProfileCredentialsProvider}.
     */
    public static ProfileCredentialsProvider create() {
        return builder().build();
    }

    /**
     * Create a {@link ProfileCredentialsProvider} using the given profile name and {@link ProfileFile#defaultProfileFile()}. Use
     * {@link #builder()} for defining a custom {@link ProfileCredentialsProvider}.
     *
     * @param profileName the name of the profile to use from the {@link ProfileFile#defaultProfileFile()}
     */
    public static ProfileCredentialsProvider create(String profileName) {
        return builder().profileName(profileName).build();
    }

    /**
     * Get a builder for creating a custom {@link ProfileCredentialsProvider}.
     */
    public static Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public AwsCredentials resolveCredentials() {
        if (loadException != null) {
            throw loadException;
        }

        ProfileFile cachedOrRefreshedProfileFile = refreshProfileFile();
        if (isNewProfileFile(cachedOrRefreshedProfileFile)) {
            currentProfileFile = cachedOrRefreshedProfileFile;
            handleProfileFileReload(cachedOrRefreshedProfileFile);
        }

        return credentials;
    }

    private void handleProfileFileReload(ProfileFile profileFile) {
        updateCredentials(profileFile, profileName);
    }

    private void updateCredentials(ProfileFile profileFile, String profileName) {
        AwsCredentialsProvider credentialsProvider = createCredentialsProvider(profileFile, profileName);

        this.credentials = credentialsProvider.resolveCredentials();

        IoUtils.closeIfCloseable(credentialsProvider, null);
    }

    private ProfileFile refreshProfileFile() {
        return profileFileSupplier.getProfileFile();
    }

    private boolean isNewProfileFile(ProfileFile profileFile) {
        return currentProfileFile != profileFile;
    }

    @Override
    public String toString() {
        return ToString.builder("ProfileCredentialsProvider")
                       .add("profileName", profileName)
                       .add("profileFileSupplier", profileFileSupplier)
                       .build();
    }

    @Override
    public void close() {
        profileFileSupplier.close();
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    private AwsCredentialsProvider createCredentialsProvider(ProfileFile profileFile, String profileName) {
        // Load the profile and credentials provider
        return profileFile.profile(profileName)
                          .flatMap(p -> new ProfileCredentialsUtils(profileFile, p, profileFile::profile).credentialsProvider())
                          .orElseThrow(() -> {
                              String errorMessage = String.format("Profile file contained no credentials for " +
                                                                  "profile '%s': %s", profileName, profileFile);
                              return SdkClientException.builder().message(errorMessage).build();
                          });
    }

    /**
     * A builder for creating a custom {@link ProfileCredentialsProvider}.
     */
    public interface Builder extends CopyableBuilder<Builder, ProfileCredentialsProvider> {

        /**
         * Define the profile file that should be used by this credentials provider. By default, the
         * {@link ProfileFile#defaultProfileFile()} is used.
         */
        Builder profileFile(ProfileFile profileFile);

        /**
         * Similar to {@link #profileFile(ProfileFile)}, but takes a lambda to configure a new {@link ProfileFile.Builder}. This
         * removes the need to called {@link ProfileFile#builder()} and {@link ProfileFile.Builder#build()}.
         */
        Builder profileFile(Consumer<ProfileFile.Builder> profileFile);

        /**
         * Define the name of the profile that should be used by this credentials provider. By default, the value in
         * {@link ProfileFileSystemSetting#AWS_PROFILE} is used.
         */
        Builder profileName(String profileName);

        /**
         * Define the mechanism for loading profile files.
         *
         * @param profileFileSupplier Supplier interface for generating a ProfileFile instance.
         */
        Builder profileFileSupplier(ProfileFileSupplier profileFileSupplier);

        /**
         * Create a {@link ProfileCredentialsProvider} using the configuration applied to this builder.
         */
        @Override
        ProfileCredentialsProvider build();
    }

    static final class BuilderImpl implements Builder {
        private ProfileFileSupplier profileFileSupplier;
        private String profileName;
        private Supplier<ProfileFile> defaultProfileFileLoader = ProfileFile::defaultProfileFile;

        BuilderImpl() {
        }

        BuilderImpl(ProfileCredentialsProvider provider) {
            this.profileName = provider.profileName;
            this.defaultProfileFileLoader = provider.defaultProfileFileLoader;
            this.profileFileSupplier = provider.profileFileSupplier;
        }

        @Override
        public Builder profileFile(ProfileFile profileFile) {
            ProfileFileSupplier supplier = Objects.nonNull(profileFile) ? () -> profileFile : null;

            return profileFileSupplier(supplier);
        }

        public void setProfileFile(ProfileFile profileFile) {
            profileFile(profileFile);
        }

        @Override
        public Builder profileFile(Consumer<ProfileFile.Builder> profileFile) {
            return profileFile(ProfileFile.builder().applyMutation(profileFile).build());
        }

        @Override
        public Builder profileName(String profileName) {
            this.profileName = profileName;
            return this;
        }

        public void setProfileName(String profileName) {
            profileName(profileName);
        }

        @Override
        public Builder profileFileSupplier(ProfileFileSupplier profileFileSupplier) {
            this.profileFileSupplier = profileFileSupplier;
            return this;
        }

        public void setProfileFileSupplier(ProfileFileSupplier supplier) {
            profileFileSupplier(supplier);
        }

        @Override
        public ProfileCredentialsProvider build() {
            return new ProfileCredentialsProvider(this);
        }

        /**
         * Override the default configuration file to be used when the customer does not explicitly set
         * profileFile(ProfileFile) or profileFileSupplier(supplier);
         * {@link #profileFile(ProfileFile)}. Use of this method is
         * only useful for testing the default behavior.
         */
        @SdkTestInternalApi
        Builder defaultProfileFileLoader(Supplier<ProfileFile> defaultProfileFileLoader) {
            this.defaultProfileFileLoader = defaultProfileFileLoader;
            return this;
        }
    }

}
