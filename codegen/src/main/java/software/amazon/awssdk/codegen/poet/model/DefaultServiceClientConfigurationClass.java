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

package software.amazon.awssdk.codegen.poet.model;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.awscore.client.config.AwsClientOption;
import software.amazon.awssdk.codegen.model.intermediate.IntermediateModel;
import software.amazon.awssdk.codegen.poet.ClassSpec;
import software.amazon.awssdk.codegen.poet.PoetUtils;
import software.amazon.awssdk.core.ServiceClientConfiguration;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.client.config.SdkClientConfiguration;

public class DefaultServiceClientConfigurationClass implements ClassSpec {

    private final ClassName defaultClientMetadataClassName;

    public DefaultServiceClientConfigurationClass(IntermediateModel model) {
        String basePackage = model.getMetadata().getFullInternalPackageName();
        this.defaultClientMetadataClassName = ClassName.get(basePackage, "DefaultServiceClientConfiguration");
    }

    @Override
    public TypeSpec poetSpec() {
        return PoetUtils.createClassBuilder(defaultClientMetadataClassName)
                        .addSuperinterface(ServiceClientConfiguration.class)
            .addMethod(constructor())
            .addMethod(regionMethod())
            .addMethod(clientOverrideConfigMethod())
            .addModifiers(PUBLIC)
            .addAnnotation(SdkInternalApi.class)
            .addField(regionField())
            .addField(clientOverrideConfigField())
            .build();
    }

    @Override
    public ClassName className() {
        return defaultClientMetadataClassName;
    }

    public MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                         .addModifiers(PUBLIC)
                         .addParameter(SdkClientConfiguration.class, "clientConfiguration")
                         .addParameter(ClientOverrideConfiguration.class, "clientOverrideConfiguration")
                         .addStatement("this.region = clientConfiguration.option($T.AWS_REGION).toString()",
                                       AwsClientOption.class)
                         .addStatement("this.overrideConfiguration = clientOverrideConfiguration")
                         .build();
    }

    public MethodSpec regionMethod() {
        return MethodSpec.methodBuilder("region")
                         .addModifiers(PUBLIC)
                         .returns(String.class)
                         .addStatement("return this.region")
                         .build();
    }

    public FieldSpec regionField() {
        return FieldSpec.builder(ClassName.get(String.class), "region")
                        .addModifiers(PRIVATE, FINAL)
                        .build();
    }

    public MethodSpec clientOverrideConfigMethod() {
        return MethodSpec.methodBuilder("overrideConfiguration")
            .addModifiers(PUBLIC)
            .returns(ClientOverrideConfiguration.class)
            .addStatement("return this.overrideConfiguration")
                         .build();
    }

    public FieldSpec clientOverrideConfigField() {
        return FieldSpec.builder(ClassName.get(ClientOverrideConfiguration.class), "overrideConfiguration")
                        .addModifiers(PRIVATE, FINAL)
                        .build();
    }
}
