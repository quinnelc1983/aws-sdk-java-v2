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

package software.amazon.awssdk.awscore.client.builder;

import static software.amazon.awssdk.awscore.client.config.AwsClientOption.DEFAULTS_MODE;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import software.amazon.awssdk.annotations.SdkProtectedApi;
import software.amazon.awssdk.annotations.SdkTestInternalApi;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.awscore.client.config.AwsAdvancedClientOption;
import software.amazon.awssdk.awscore.client.config.AwsClientOption;
import software.amazon.awssdk.awscore.defaultsmode.DefaultsMode;
import software.amazon.awssdk.awscore.endpoint.DefaultServiceEndpointBuilder;
import software.amazon.awssdk.awscore.endpoint.DualstackEnabledProvider;
import software.amazon.awssdk.awscore.endpoint.FipsEnabledProvider;
import software.amazon.awssdk.awscore.eventstream.EventStreamInitialRequestInterceptor;
import software.amazon.awssdk.awscore.interceptor.HelpfulUnknownHostExceptionInterceptor;
import software.amazon.awssdk.awscore.interceptor.TraceIdExecutionInterceptor;
import software.amazon.awssdk.awscore.internal.defaultsmode.AutoDefaultsModeDiscovery;
import software.amazon.awssdk.awscore.internal.defaultsmode.DefaultsModeConfiguration;
import software.amazon.awssdk.awscore.internal.defaultsmode.DefaultsModeResolver;
import software.amazon.awssdk.awscore.retry.AwsRetryPolicy;
import software.amazon.awssdk.core.client.builder.SdkDefaultClientBuilder;
import software.amazon.awssdk.core.client.config.SdkAdvancedClientOption;
import software.amazon.awssdk.core.client.config.SdkClientConfiguration;
import software.amazon.awssdk.core.client.config.SdkClientOption;
import software.amazon.awssdk.core.interceptor.ExecutionInterceptor;
import software.amazon.awssdk.core.retry.RetryMode;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.ServiceMetadata;
import software.amazon.awssdk.regions.ServiceMetadataAdvancedOption;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;
import software.amazon.awssdk.utils.AttributeMap;
import software.amazon.awssdk.utils.CollectionUtils;
import software.amazon.awssdk.utils.Logger;
import software.amazon.awssdk.utils.Pair;
import software.amazon.awssdk.utils.StringUtils;

/**
 * An SDK-internal implementation of the methods in {@link AwsClientBuilder}, {@link AwsAsyncClientBuilder} and
 * {@link AwsSyncClientBuilder}. This implements all methods required by those interfaces, allowing service-specific builders to
 * just
 * implement the configuration they wish to add.
 *
 * <p>By implementing both the sync and async interface's methods, service-specific builders can share code between their sync
 * and
 * async variants without needing one to extend the other. Note: This only defines the methods in the sync and async builder
 * interfaces. It does not implement the interfaces themselves. This is because the sync and async client builder interfaces both
 * require a type-constrained parameter for use in fluent chaining, and a generic type parameter conflict is introduced into the
 * class hierarchy by this interface extending the builder interfaces themselves.</p>
 *
 * <p>Like all {@link AwsClientBuilder}s, this class is not thread safe.</p>
 *
 * @param <BuilderT> The type of builder, for chaining.
 * @param <ClientT> The type of client generated by this builder.
 */
@SdkProtectedApi
public abstract class AwsDefaultClientBuilder<BuilderT extends AwsClientBuilder<BuilderT, ClientT>, ClientT>
    extends SdkDefaultClientBuilder<BuilderT, ClientT>
    implements AwsClientBuilder<BuilderT, ClientT> {
    private static final Logger log = Logger.loggerFor(AwsClientBuilder.class);
    private static final String DEFAULT_ENDPOINT_PROTOCOL = "https";
    private static final String[] FIPS_SEARCH = {"fips-", "-fips"};
    private static final String[] FIPS_REPLACE = {"", ""};

    private final AutoDefaultsModeDiscovery autoDefaultsModeDiscovery;

    protected AwsDefaultClientBuilder() {
        super();
        autoDefaultsModeDiscovery = new AutoDefaultsModeDiscovery();
    }

    @SdkTestInternalApi
    AwsDefaultClientBuilder(SdkHttpClient.Builder defaultHttpClientBuilder,
                            SdkAsyncHttpClient.Builder defaultAsyncHttpClientFactory,
                            AutoDefaultsModeDiscovery autoDefaultsModeDiscovery) {
        super(defaultHttpClientBuilder, defaultAsyncHttpClientFactory);
        this.autoDefaultsModeDiscovery = autoDefaultsModeDiscovery;
    }

    /**
     * Implemented by child classes to define the endpoint prefix used when communicating with AWS. This constitutes the first
     * part of the URL in the DNS name for the service. Eg. in the endpoint "dynamodb.amazonaws.com", this is the "dynamodb".
     *
     * <p>For standard services, this should match the "endpointPrefix" field in the AWS model.</p>
     */
    protected abstract String serviceEndpointPrefix();

    /**
     * Implemented by child classes to define the signing-name that should be used when signing requests when communicating with
     * AWS.
     */
    protected abstract String signingName();

    /**
     * Implemented by child classes to define the service name used to identify the request in things like metrics.
     */
    protected abstract String serviceName();

    @Override
    protected final AttributeMap childHttpConfig() {
        return serviceHttpConfig();
    }

    /**
     * Return HTTP related defaults with the following chain of priorities.
     * <ol>
     * <li>Service-Specific Defaults</li>
     * <li>Defaults vended by {@link DefaultsMode}</li>
     * </ol>
     */
    @Override
    protected final AttributeMap childHttpConfig(SdkClientConfiguration configuration) {
        AttributeMap attributeMap = serviceHttpConfig();
        return mergeSmartHttpDefaults(configuration, attributeMap);
    }

    /**
     * Optionally overridden by child classes to define service-specific HTTP configuration defaults.
     */
    protected AttributeMap serviceHttpConfig() {
        return AttributeMap.empty();
    }

    @Override
    protected final SdkClientConfiguration mergeChildDefaults(SdkClientConfiguration configuration) {
        SdkClientConfiguration config = mergeServiceDefaults(configuration);
        config = config.merge(c -> c.option(AwsAdvancedClientOption.ENABLE_DEFAULT_REGION_DETECTION, true)
                                    .option(SdkAdvancedClientOption.DISABLE_HOST_PREFIX_INJECTION, false)
                                    .option(AwsClientOption.SERVICE_SIGNING_NAME, signingName())
                                    .option(SdkClientOption.SERVICE_NAME, serviceName())
                                    .option(AwsClientOption.ENDPOINT_PREFIX, serviceEndpointPrefix()));
        return mergeInternalDefaults(config);
    }

    /**
     * Optionally overridden by child classes to define service-specific default configuration.
     */
    protected SdkClientConfiguration mergeServiceDefaults(SdkClientConfiguration configuration) {
        return configuration;
    }

    /**
     * Optionally overridden by child classes to define internal default configuration.
     */
    protected SdkClientConfiguration mergeInternalDefaults(SdkClientConfiguration configuration) {
        return configuration;
    }

    /**
     * Return a client configuration object, populated with the following chain of priorities.
     * <ol>
     *     <li>Defaults vended from {@link DefaultsMode} </li>
     *     <li>AWS Global Defaults</li>
     * </ol>
     */
    @Override
    protected final SdkClientConfiguration finalizeChildConfiguration(SdkClientConfiguration configuration) {
        configuration = finalizeServiceConfiguration(configuration);

        configuration = configuration.toBuilder()
                                     .option(AwsClientOption.AWS_REGION, resolveRegion(configuration))
                                     .option(AwsClientOption.DUALSTACK_ENDPOINT_ENABLED,
                                             resolveDualstackEndpointEnabled(configuration))
                                     .option(AwsClientOption.FIPS_ENDPOINT_ENABLED, resolveFipsEndpointEnabled(configuration))
                                     .build();

        configuration = mergeSmartDefaults(configuration);

        return configuration.toBuilder()
                            .option(AwsClientOption.CREDENTIALS_PROVIDER, resolveCredentials(configuration))
                            .option(SdkClientOption.ENDPOINT, resolveEndpoint(configuration))
                            .option(SdkClientOption.EXECUTION_INTERCEPTORS, addAwsInterceptors(configuration))
                            .option(AwsClientOption.SIGNING_REGION, resolveSigningRegion(configuration))
                            .option(SdkClientOption.RETRY_POLICY, resolveAwsRetryPolicy(configuration))
                            .build();
    }

    private SdkClientConfiguration mergeSmartDefaults(SdkClientConfiguration configuration) {
        DefaultsMode defaultsMode = resolveDefaultsMode(configuration);
        AttributeMap defaultConfig = DefaultsModeConfiguration.defaultConfig(defaultsMode);
        return configuration.toBuilder()
                            .option(DEFAULTS_MODE, defaultsMode)
                            .build()
                            .merge(c -> c.option(SdkClientOption.DEFAULT_RETRY_MODE,
                                                 defaultConfig.get(SdkClientOption.DEFAULT_RETRY_MODE))
                                         .option(ServiceMetadataAdvancedOption.DEFAULT_S3_US_EAST_1_REGIONAL_ENDPOINT,
                                                 defaultConfig.get(
                                                     ServiceMetadataAdvancedOption.DEFAULT_S3_US_EAST_1_REGIONAL_ENDPOINT)));
    }

    /**
     * Optionally overridden by child classes to derive service-specific configuration from the default-applied configuration.
     */
    protected SdkClientConfiguration finalizeServiceConfiguration(SdkClientConfiguration configuration) {
        return configuration;
    }

    /**
     * Merged the HTTP defaults specified for each {@link DefaultsMode}
     */
    private AttributeMap mergeSmartHttpDefaults(SdkClientConfiguration configuration, AttributeMap attributeMap) {
        DefaultsMode defaultsMode = configuration.option(DEFAULTS_MODE);
        return attributeMap.merge(DefaultsModeConfiguration.defaultHttpConfig(defaultsMode));
    }

    /**
     * Resolve the signing region from the default-applied configuration.
     */
    private Region resolveSigningRegion(SdkClientConfiguration config) {
        return ServiceMetadata.of(serviceEndpointPrefix())
                              .signingRegion(config.option(AwsClientOption.AWS_REGION));
    }

    /**
     * Resolve the endpoint from the default-applied configuration.
     */
    private URI resolveEndpoint(SdkClientConfiguration config) {
        return Optional.ofNullable(config.option(SdkClientOption.ENDPOINT))
                       .orElseGet(() -> endpointFromConfig(config));
    }

    private URI endpointFromConfig(SdkClientConfiguration config) {
        return new DefaultServiceEndpointBuilder(serviceEndpointPrefix(), DEFAULT_ENDPOINT_PROTOCOL)
            .withRegion(config.option(AwsClientOption.AWS_REGION))
            .withProfileFile(() -> config.option(SdkClientOption.PROFILE_FILE))
            .withProfileName(config.option(SdkClientOption.PROFILE_NAME))
            .putAdvancedOption(ServiceMetadataAdvancedOption.DEFAULT_S3_US_EAST_1_REGIONAL_ENDPOINT,
                               config.option(ServiceMetadataAdvancedOption.DEFAULT_S3_US_EAST_1_REGIONAL_ENDPOINT))
            .withDualstackEnabled(config.option(AwsClientOption.DUALSTACK_ENDPOINT_ENABLED))
            .withFipsEnabled(config.option(AwsClientOption.FIPS_ENDPOINT_ENABLED))
            .getServiceEndpoint();
    }

    /**
     * Resolve the region that should be used based on the customer's configuration.
     */
    private Region resolveRegion(SdkClientConfiguration config) {
        return config.option(AwsClientOption.AWS_REGION) != null
               ? config.option(AwsClientOption.AWS_REGION)
               : regionFromDefaultProvider(config);
    }

    /**
     * Load the region from the default region provider if enabled.
     */
    private Region regionFromDefaultProvider(SdkClientConfiguration config) {
        Boolean defaultRegionDetectionEnabled = config.option(AwsAdvancedClientOption.ENABLE_DEFAULT_REGION_DETECTION);
        if (defaultRegionDetectionEnabled != null && !defaultRegionDetectionEnabled) {
            throw new IllegalStateException("No region was configured, and use-region-provider-chain was disabled.");
        }

        ProfileFile profileFile = config.option(SdkClientOption.PROFILE_FILE);
        String profileName = config.option(SdkClientOption.PROFILE_NAME);
        return DefaultAwsRegionProviderChain.builder()
                                            .profileFile(() -> profileFile)
                                            .profileName(profileName)
                                            .build()
                                            .getRegion();
    }

    private DefaultsMode resolveDefaultsMode(SdkClientConfiguration config) {
        DefaultsMode defaultsMode =
            config.option(AwsClientOption.DEFAULTS_MODE) != null ?
            config.option(AwsClientOption.DEFAULTS_MODE) :
            DefaultsModeResolver.create()
                                .profileFile(() -> config.option(SdkClientOption.PROFILE_FILE))
                                .profileName(config.option(SdkClientOption.PROFILE_NAME))
                                .resolve();

        if (defaultsMode == DefaultsMode.AUTO) {
            defaultsMode = autoDefaultsModeDiscovery.discover(config.option(AwsClientOption.AWS_REGION));
            DefaultsMode finalDefaultsMode = defaultsMode;
            log.debug(() -> String.format("Resolved %s client's AUTO configuration mode to %s", serviceName(),
                      finalDefaultsMode));
        }

        return defaultsMode;
    }

    /**
     * Resolve whether a dualstack endpoint should be used for this client.
     */
    private Boolean resolveDualstackEndpointEnabled(SdkClientConfiguration config) {
        return config.option(AwsClientOption.DUALSTACK_ENDPOINT_ENABLED) != null
               ? config.option(AwsClientOption.DUALSTACK_ENDPOINT_ENABLED)
               : resolveUseDualstackFromDefaultProvider(config);
    }

    /**
     * Load the dualstack endpoint setting from the default provider logic.
     */
    private Boolean resolveUseDualstackFromDefaultProvider(SdkClientConfiguration config) {
        ProfileFile profileFile = config.option(SdkClientOption.PROFILE_FILE);
        String profileName = config.option(SdkClientOption.PROFILE_NAME);
        return DualstackEnabledProvider.builder()
                                       .profileFile(() -> profileFile)
                                       .profileName(profileName)
                                       .build()
                                       .isDualstackEnabled()
                                       .orElse(null);
    }

    /**
     * Resolve whether a dualstack endpoint should be used for this client.
     */
    private Boolean resolveFipsEndpointEnabled(SdkClientConfiguration config) {
        return config.option(AwsClientOption.FIPS_ENDPOINT_ENABLED) != null
               ? config.option(AwsClientOption.FIPS_ENDPOINT_ENABLED)
               : resolveUseFipsFromDefaultProvider(config);
    }

    /**
     * Load the dualstack endpoint setting from the default provider logic.
     */
    private Boolean resolveUseFipsFromDefaultProvider(SdkClientConfiguration config) {
        ProfileFile profileFile = config.option(SdkClientOption.PROFILE_FILE);
        String profileName = config.option(SdkClientOption.PROFILE_NAME);
        return FipsEnabledProvider.builder()
                                  .profileFile(() -> profileFile)
                                  .profileName(profileName)
                                  .build()
                                  .isFipsEnabled()
                                  .orElse(null);
    }

    /**
     * Resolve the credentials that should be used based on the customer's configuration.
     */
    private AwsCredentialsProvider resolveCredentials(SdkClientConfiguration config) {
        return config.option(AwsClientOption.CREDENTIALS_PROVIDER) != null
               ? config.option(AwsClientOption.CREDENTIALS_PROVIDER)
               : DefaultCredentialsProvider.builder()
                                           .profileFile(config.option(SdkClientOption.PROFILE_FILE))
                                           .profileName(config.option(SdkClientOption.PROFILE_NAME))
                                           .build();
    }

    private RetryPolicy resolveAwsRetryPolicy(SdkClientConfiguration config) {
        RetryPolicy policy = config.option(SdkClientOption.RETRY_POLICY);

        if (policy != null) {
            if (policy.additionalRetryConditionsAllowed()) {
                return AwsRetryPolicy.addRetryConditions(policy);
            } else {
                return policy;
            }
        }

        RetryMode retryMode = RetryMode.resolver()
                                       .profileFile(() -> config.option(SdkClientOption.PROFILE_FILE))
                                       .profileName(config.option(SdkClientOption.PROFILE_NAME))
                                       .defaultRetryMode(config.option(SdkClientOption.DEFAULT_RETRY_MODE))
                                       .resolve();
        return AwsRetryPolicy.forRetryMode(retryMode);
    }

    @Override
    public final BuilderT region(Region region) {
        Region regionToSet = region;
        Boolean fipsEnabled = null;

        if (region != null) {
            Pair<Region, Optional<Boolean>> transformedRegion = transformFipsPseudoRegionIfNecessary(region);
            regionToSet = transformedRegion.left();
            fipsEnabled = transformedRegion.right().orElse(null);
        }

        clientConfiguration.option(AwsClientOption.AWS_REGION, regionToSet);
        clientConfiguration.option(AwsClientOption.FIPS_ENDPOINT_ENABLED, fipsEnabled);
        return thisBuilder();
    }

    public final void setRegion(Region region) {
        region(region);
    }

    @Override
    public BuilderT dualstackEnabled(Boolean dualstackEndpointEnabled) {
        clientConfiguration.option(AwsClientOption.DUALSTACK_ENDPOINT_ENABLED, dualstackEndpointEnabled);
        return thisBuilder();
    }

    public final void setDualstackEnabled(Boolean dualstackEndpointEnabled) {
        dualstackEnabled(dualstackEndpointEnabled);
    }

    @Override
    public BuilderT fipsEnabled(Boolean dualstackEndpointEnabled) {
        clientConfiguration.option(AwsClientOption.FIPS_ENDPOINT_ENABLED, dualstackEndpointEnabled);
        return thisBuilder();
    }

    public final void setFipsEnabled(Boolean fipsEndpointEnabled) {
        fipsEnabled(fipsEndpointEnabled);
    }

    @Override
    public final BuilderT credentialsProvider(AwsCredentialsProvider credentialsProvider) {
        clientConfiguration.option(AwsClientOption.CREDENTIALS_PROVIDER, credentialsProvider);
        return thisBuilder();
    }

    public final void setCredentialsProvider(AwsCredentialsProvider credentialsProvider) {
        credentialsProvider(credentialsProvider);
    }

    private List<ExecutionInterceptor> addAwsInterceptors(SdkClientConfiguration config) {
        List<ExecutionInterceptor> interceptors = awsInterceptors();
        interceptors = CollectionUtils.mergeLists(interceptors, config.option(SdkClientOption.EXECUTION_INTERCEPTORS));
        return interceptors;
    }

    private List<ExecutionInterceptor> awsInterceptors() {
        return Arrays.asList(new HelpfulUnknownHostExceptionInterceptor(),
                             new EventStreamInitialRequestInterceptor(),
                             new TraceIdExecutionInterceptor());
    }

    @Override
    public final BuilderT defaultsMode(DefaultsMode defaultsMode) {
        clientConfiguration.option(DEFAULTS_MODE, defaultsMode);
        return thisBuilder();
    }

    public final void setDefaultsMode(DefaultsMode defaultsMode) {
        defaultsMode(defaultsMode);
    }

    /**
     * If the region is a FIPS pseudo region (contains "fips"), this method returns a pair of values, the left side being the
     * region with the "fips" string removed, and the right being {@code true}. Otherwise, the region is returned
     * unchanged, and the right will be empty.
     */
    private static Pair<Region, Optional<Boolean>> transformFipsPseudoRegionIfNecessary(Region region) {
        String id = region.id();
        String newId = StringUtils.replaceEach(id, FIPS_SEARCH, FIPS_REPLACE);
        if (!newId.equals(id)) {
            log.info(() -> String.format("Replacing input region %s with %s and setting fipsEnabled to true", id, newId));
            return Pair.of(Region.of(newId), Optional.of(true));
        }

        return Pair.of(region, Optional.empty());
    }
}
