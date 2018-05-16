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

package software.amazon.awssdk.auth.signer.internal;

import software.amazon.awssdk.auth.signer.SignerConstants;
import software.amazon.awssdk.http.SdkHttpFullRequest;

/**
 * Parameters that are used for computing a AWS 4 signature for a request.
 */
public final class Aws4SignerRequestParams {

    /**
     * The signing algorithm to be used for computing the signature.
     */
    private final String signingAlgorithm = SignerConstants.AWS4_SIGNING_ALGORITHM;

    /**
     * The HTTP request to be signed.
     */
    private final SdkHttpFullRequest.Builder httpRequest;

    /**
     * The datetime in milliseconds for which the signature needs to be
     * computed.
     */
    private final long signingDateTimeMilli;

    /**
     * The scope of the signature.
     */
    private final String scope;

    /**
     * The AWS region to be used for computing the signature.
     */
    private final String regionName;

    /**
     * The name of the AWS service.
     */
    private final String serviceName;

    /**
     * UTC formatted version of the signing time stamp.
     */
    private final String formattedSigningDateTime;

    /**
     * UTC Formatted Signing date with time stamp stripped.
     */
    private final String formattedSigningDate;

    /**
     * Generates an instance of AWS4signerRequestParams that holds the parameters used for computing a AWS 4 signature
     * for a request.
     */
    public Aws4SignerRequestParams(SdkHttpFullRequest.Builder httpRequest, AwsSignerParams signerParams) {
        this.httpRequest = httpRequest;
        this.signingDateTimeMilli = getSigningDate(signerParams.getTimeOffset());
        this.formattedSigningDate = Aws4SignerUtils.formatDateStamp(signingDateTimeMilli);
        this.serviceName = signerParams.getSigningName();
        this.regionName = signerParams.getRegion().value();
        this.scope = generateScope(formattedSigningDate, this.serviceName, regionName);
        this.formattedSigningDateTime = Aws4SignerUtils.formatTimestamp(signingDateTimeMilli);
    }

    /**
     * Returns the signing date from the request.
     */
    private long getSigningDate(Integer timeOffset) {
        if (timeOffset == null) {
            return System.currentTimeMillis();
        } else {
            return System.currentTimeMillis() - timeOffset * 1000L;
        }
    }

    /**
     * Returns the scope to be used for the signing.
     */
    private String generateScope(String dateStamp, String serviceName, String regionName) {
        return dateStamp + "/" + regionName + "/" + serviceName + "/" + SignerConstants.AWS4_TERMINATOR;
    }

    /**
     * Returns the HTTP request to be signed.
     */
    public SdkHttpFullRequest.Builder httpRequest() {
        return httpRequest;
    }

    /**
     * Returns the scope of the signing.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Returns the formatted date and time of the signing date in UTC zone.
     */
    public String getFormattedSigningDateTime() {
        return formattedSigningDateTime;
    }

    /**
     * Returns the signing date time in millis for which the signature needs to
     * be computed.
     */
    public long getSigningDateTimeMilli() {
        return signingDateTimeMilli;
    }

    /**
     * Returns the AWS region name to be used while computing the signature.
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Returns the AWS Service name to be used while computing the signature.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Returns the formatted date in UTC zone of the signing date.
     */
    public String getFormattedSigningDate() {
        return formattedSigningDate;
    }

    /**
     * Returns the signing algorithm used for computing the signature.
     */
    public String getSigningAlgorithm() {
        return signingAlgorithm;
    }
}