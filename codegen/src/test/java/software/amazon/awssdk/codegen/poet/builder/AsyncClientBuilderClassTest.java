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

package software.amazon.awssdk.codegen.poet.builder;

import static software.amazon.awssdk.codegen.poet.ClientTestModels.composedClientJsonServiceModels;
import static software.amazon.awssdk.codegen.poet.ClientTestModels.restJsonServiceModels;
import static software.amazon.awssdk.codegen.poet.builder.BuilderClassTestUtils.validateGeneration;

import org.junit.jupiter.api.Test;

/**
 * Validate AsyncClientBuilderClass generation.
 */
public class AsyncClientBuilderClassTest {

    @Test
    public void asyncClientBuilderClass() {
        validateGeneration(AsyncClientBuilderClass::new, restJsonServiceModels(), "test-async-client-builder-class.java");
    }

    @Test
    public void asyncComposedClientBuilderClass() {
        validateGeneration(AsyncClientBuilderClass::new, composedClientJsonServiceModels(),
                           "test-composed-async-client-builder-class.java");
    }
}
