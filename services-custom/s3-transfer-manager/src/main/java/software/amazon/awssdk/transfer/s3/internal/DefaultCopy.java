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

package software.amazon.awssdk.transfer.s3.internal;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.transfer.s3.CompletedCopy;
import software.amazon.awssdk.transfer.s3.Copy;
import software.amazon.awssdk.transfer.s3.progress.TransferProgress;
import software.amazon.awssdk.utils.ToString;
import software.amazon.awssdk.utils.Validate;

@SdkInternalApi
public final class DefaultCopy implements Copy {

    private final CompletableFuture<CompletedCopy> completionFuture;
    private final TransferProgress progress;

    DefaultCopy(CompletableFuture<CompletedCopy> completionFuture, TransferProgress progress) {
        this.completionFuture = Validate.paramNotNull(completionFuture, "completionFuture");
        this.progress = Validate.paramNotNull(progress, "progress");
    }

    @Override
    public CompletableFuture<CompletedCopy> completionFuture() {
        return completionFuture;
    }

    @Override
    public CompletableFuture<TransferProgress> progress() {
        return CompletableFuture.completedFuture(progress);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultCopy that = (DefaultCopy) o;

        if (!Objects.equals(completionFuture, that.completionFuture)) {
            return false;
        }
        return Objects.equals(progress, that.progress);
    }

    @Override
    public int hashCode() {
        int result = completionFuture != null ? completionFuture.hashCode() : 0;
        result = 31 * result + (progress != null ? progress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToString.builder("DefaultCopy")
                       .add("completionFuture", completionFuture)
                       .add("progress", progress)
                       .build();
    }
}
