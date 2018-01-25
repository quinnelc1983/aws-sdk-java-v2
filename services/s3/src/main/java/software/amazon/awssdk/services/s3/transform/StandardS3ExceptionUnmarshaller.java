/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package software.amazon.awssdk.services.s3.transform;

import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.runtime.transform.StandardErrorUnmarshaller;

/**
 * The unmarshaller used to read S3 exceptions when no more-specific exception unmarshaller is found. This is the S3 equivalent
 * of {@link StandardErrorUnmarshaller}.
 */
public final class StandardS3ExceptionUnmarshaller extends S3ExceptionUnmarshaller {
    public StandardS3ExceptionUnmarshaller(Class<? extends SdkServiceException> exceptionClass) {
        super(exceptionClass, null);
    }
}
