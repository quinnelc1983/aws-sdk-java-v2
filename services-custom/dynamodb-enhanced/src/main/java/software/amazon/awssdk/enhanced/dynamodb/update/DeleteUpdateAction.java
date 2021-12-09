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

package software.amazon.awssdk.enhanced.dynamodb.update;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.utils.Validate;

/**
 * A representation of a single {@link UpdateExpression} DELETE action.
 * <p>
 * At a minimum, this action must contain a path string referencing the attribute that should be acted upon and a value string
 * referencing the value (subset) to be removed from the attribute. The value should be substituted with tokens using the
 * ':value_token' syntax and values associated with the token must be explicitly added to the expressionValues map.
 * Consult the DynamoDB UpdateExpression documentation for details on this action.
 * <p>
 * Optionally, attribute names can be substituted with tokens using the '#name_token' syntax. If tokens are used in the
 * expression then the names associated with those tokens must be explicitly added to the expressionNames map
 * that is also stored on this object.
 * <p>
 * Example:-
 * <pre>
 * {@code
 * DeleteUpdateAction deleteAction = DeleteUpdateAction.builder()
 *                                                     .path("#a")
 *                                                     .value(":b")
 *                                                     .putExpressionName("#a", "attributeA")
 *                                                     .putExpressionValue(":b", myAttributeValue)
 *                                                     .build();
 * }
 * </pre>
 */
@SdkPublicApi
public final class DeleteUpdateAction implements UpdateAction {

    private final String path;
    private final String value;
    private final Map<String, String> expressionNames;
    private final Map<String, AttributeValue> expressionValues;

    private DeleteUpdateAction(Builder builder) {
        this.path = Validate.paramNotNull(builder.path, "path");
        this.value = Validate.paramNotNull(builder.value, "value");
        this.expressionValues = unmodifiableMap(Validate.paramNotNull(builder.expressionValues, "expressionValues"));
        this.expressionNames = unmodifiableMap(builder.expressionNames != null ? builder.expressionNames : new HashMap<>());
    }

    /**
     * Constructs a new builder for {@link DeleteUpdateAction}.
     *
     * @return a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public String path() {
        return path;
    }

    public String value() {
        return value;
    }

    public Map<String, String> expressionNames() {
        return expressionNames;
    }

    public Map<String, AttributeValue> expressionValues() {
        return expressionValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeleteUpdateAction that = (DeleteUpdateAction) o;

        if (path != null ? ! path.equals(that.path) : that.path != null) {
            return false;
        }
        if (value != null ? ! value.equals(that.value) : that.value != null) {
            return false;
        }
        if (expressionValues != null ? ! expressionValues.equals(that.expressionValues) :
            that.expressionValues != null) {
            return false;
        }
        return expressionNames != null ? expressionNames.equals(that.expressionNames) : that.expressionNames == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (expressionValues != null ? expressionValues.hashCode() : 0);
        result = 31 * result + (expressionNames != null ? expressionNames.hashCode() : 0);
        return result;
    }

    /**
     * A builder for {@link DeleteUpdateAction}
     */
    public static final class Builder {

        private String path;
        private String value;
        private Map<String, String> expressionNames;
        private Map<String, AttributeValue> expressionValues;

        /**
         * A string expression representing the attribute to be acted upon
         */
        public Builder path(String path) {
            this.path = path;
            return this;
        }

        /**
         * A string expression representing the value used in the action. The value must be represented as an
         * expression attribute value token.
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the 'expression values' token map that maps from value references (expression attribute values) to
         * DynamoDB AttributeValues, overriding any existing values.
         * The value reference should always start with ':' (colon).
         *
         * @see #putExpressionValue(String, AttributeValue)
         */
        public Builder expressionValues(Map<String, AttributeValue> expressionValues) {
            this.expressionValues = expressionValues == null ? null : new HashMap<>(expressionValues);
            return this;
        }

        /**
         * Adds a single element to the 'expression values' token map.
         *
         * @see #expressionValues(Map)
         */
        public Builder putExpressionValue(String key, AttributeValue value) {
            if (this.expressionValues == null) {
                this.expressionValues = new HashMap<>();
            }

            this.expressionValues.put(key, value);
            return this;
        }

        /**
         * Sets the optional 'expression names' token map, overriding any existing values. Use if the attribute
         * references in the path expression are token ('expression attribute names') prepended with the
         * '#' (pound) sign. It should map from token name to real attribute name.
         *
         * @see #putExpressionName(String, String)
         */
        public Builder expressionNames(Map<String, String> expressionNames) {
            this.expressionNames = expressionNames == null ? null : new HashMap<>(expressionNames);
            return this;
        }

        /**
         * Adds a single element to the optional 'expression names' token map.
         *
         * @see #expressionNames(Map)
         */
        public Builder putExpressionName(String key, String value) {
            if (this.expressionNames == null) {
                this.expressionNames = new HashMap<>();
            }
            this.expressionNames.put(key, value);
            return this;
        }

        /**
         * Builds an {@link DeleteUpdateAction} based on the values stored in this builder.
         */
        public DeleteUpdateAction build() {
            return new DeleteUpdateAction(this);
        }
    }
}
