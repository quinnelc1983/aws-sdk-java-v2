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

package software.amazon.awssdk.core;

import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;

/**
 * Interface to expose service client settings to the user, e.g., region, ClientOverrideConfiguration
 */
@SdkPublicApi
public interface ServiceClientConfiguration {

    /**
     *
     * @return The configured region of the SdkClient
     */
    String region();

    /**
     *
     * @return The ClientOverrideConfiguration of the SdkClient. If this is not set, an ClientOverrideConfiguration object will
     * still be returned, with empty fields
     */
    ClientOverrideConfiguration overrideConfiguration();
}
