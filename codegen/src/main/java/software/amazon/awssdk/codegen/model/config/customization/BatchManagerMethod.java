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

package software.amazon.awssdk.codegen.model.config.customization;

/**
 * Config required to generate a batchManager method that returns an instance of a BatchManager in addition to any required
 * executors or scheduledExecutors.
 */
public class BatchManagerMethod {

    public static final String METHOD_NAME = "batchManager";

    /** Fqcn of the return type of the operation */
    private String returnType;

    /** Fqcn of the instance type to be created */
    private String instanceType;

    /** indicates whether the client already has an executor. If it does not, create a field for one */
    private boolean hasExecutor;

    /** indicates whether the client already has a scheduledExecutor. If it does not, create a field for one */
    private boolean hasScheduledExecutor;

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public boolean hasExecutor() {
        return hasExecutor;
    }

    public void setHasExecutor(boolean hasExecutor) {
        this.hasExecutor = hasExecutor;
    }

    public boolean hasScheduledExecutor() {
        return hasScheduledExecutor;
    }

    public void setHasScheduledExecutor(boolean hasScheduledExecutor) {
        this.hasScheduledExecutor = hasScheduledExecutor;
    }
}
