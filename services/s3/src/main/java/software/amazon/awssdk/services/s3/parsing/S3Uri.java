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

package software.amazon.awssdk.services.s3.parsing;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import software.amazon.awssdk.annotations.Immutable;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.utils.builder.CopyableBuilder;
import software.amazon.awssdk.utils.builder.ToCopyableBuilder;

/**
 * Object that represents a parsed S3 URI. Can be used to easily retrieve the bucket, key, region, style, and query parameters
 * of the URI. Only basic bucket endpoints are supported, i.e., path-style and virtual-hosted style URLs. Encoded buckets, keys,
 * and query parameters will be returned decoded.
 */
@Immutable
@SdkPublicApi
public final class S3Uri implements ToCopyableBuilder<S3Uri.Builder, S3Uri> {

    private final URI uri;
    private final String bucket;
    private final String key;
    private final Region region;
    private final boolean isPathStyle;
    private final Map<String, List<String>> queryParams;

    private S3Uri(Builder builder) {
        this.uri = builder.uri;
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.region = builder.region;
        this.isPathStyle = builder.isPathStyle;
        this.queryParams = builder.queryParams;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns the original URI that was used to instantiate the {@link S3Uri}
     */
    public URI uri() {
        return uri;
    }

    /**
     * Returns the bucket specified in the URI. Returns null if no bucket is specified.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * Returns the key specified in the URI. Returns null if no key is specified.
     */
    public String key() {
        return key;
    }

    /**
     * Returns the region specified in the URI. Returns null if no region is specified, i.e., global endpoint.
     */
    public Region region() {
        return region;
    }

    /**
     * Returns true if the URI is path-style, false if the URI is virtual-hosted style.
     */
    public boolean isPathStyle() {
        return isPathStyle;
    }

    /**
     * Returns a map of the query parameters specified in the URI. Returns an empty map if no queries are specified.
     */
    public Map<String, List<String>> queryParams() {
        return Collections.unmodifiableMap(queryParams);
    }

    /**
     * Returns the list of values for a specified query parameter. A {@link SdkClientException} is thrown if the URI does not
     * contain the specified query parameter.
     */
    public List<String> queryParamValues(String key) {
        List<String> queryValues = queryParams.get(key);
        if (queryValues == null) {
            throw SdkClientException.create("The URI does not contain the specified query parameter.");
        }
        List<String> queryValuesCopy = Arrays.asList(new String[queryValues.size()]);
        Collections.copy(queryValuesCopy, queryValues);
        return queryValuesCopy;
    }

    /**
     * Returns the value for the specified query parameter. If there are multiple values for the query parameter, the first
     * value is returned. A {@link SdkClientException} is thrown if the URI does not contain the specified query parameter.
     */
    public String queryParamValue(String key) {
        if (queryParams().get(key) == null) {
            throw SdkClientException.create("The URI does not contain the specified query parameter.");
        }
        return queryParams.get(key).get(0);
    }

    @Override
    public String toString() {
        return uri.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        S3Uri s3Uri = (S3Uri) o;
        return Objects.equals(uri, s3Uri.uri)
               && Objects.equals(bucket, s3Uri.bucket)
               && Objects.equals(key, s3Uri.key)
               && Objects.equals(region, s3Uri.region)
               && Objects.equals(queryParams, s3Uri.queryParams);
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (bucket != null ? bucket.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (queryParams != null ? queryParams.hashCode() : 0);
        return result;
    }

    /**
     * A builder for creating a {@link S3Uri}
     */
    public static final class Builder implements CopyableBuilder<Builder, S3Uri> {
        private URI uri;
        private String bucket;
        private String key;
        private Region region;
        private boolean isPathStyle;
        private Map<String, List<String>> queryParams;

        private Builder() {
        }

        private Builder(S3Uri s3Uri) {
            this.uri = s3Uri.uri;
            this.bucket = s3Uri.bucket;
            this.key = s3Uri.key;
            this.region = s3Uri.region;
            this.isPathStyle = s3Uri.isPathStyle;
            this.queryParams = s3Uri.queryParams;
        }

        /**
         * Configure the URI
         */
        public Builder uri(URI uri) {
            this.uri = uri;
            return this;
        }

        /**
         * Configure the bucket
         */
        public Builder bucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        /**
         * Configure the key
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Configure the region
         */
        public Builder region(Region region) {
            this.region = region;
            return this;
        }

        /**
         * Configure the path style flag
         */
        public Builder isPathStyle(boolean isPathStyle) {
            this.isPathStyle = isPathStyle;
            return this;
        }

        /**
         * Configure the map of query parameters
         */
        public Builder queryParams(Map<String, List<String>> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        @Override
        public S3Uri build() {
            return new S3Uri(this);
        }
    }

}
