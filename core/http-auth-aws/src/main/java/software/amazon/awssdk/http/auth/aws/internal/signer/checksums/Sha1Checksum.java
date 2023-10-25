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

package software.amazon.awssdk.http.auth.aws.internal.signer.checksums;

import java.security.MessageDigest;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.http.auth.aws.internal.signer.util.DigestAlgorithm;

/**
 * Implementation of {@link SdkChecksum} to calculate an Sha-1 checksum.
 */
@SdkInternalApi
public class Sha1Checksum implements SdkChecksum {

    private MessageDigest digest;

    private MessageDigest digestLastMarked;

    public Sha1Checksum() {
        this.digest = getDigest();
    }

    @Override
    public void update(int b) {
        digest.update((byte) b);
    }

    @Override
    public void update(byte[] b, int off, int len) {
        digest.update(b, off, len);
    }

    @Override
    public long getValue() {
        throw new UnsupportedOperationException("Use getChecksumBytes() instead.");
    }

    @Override
    public void reset() {
        digest = (digestLastMarked == null)
                 // This is necessary so that should there be a reset without a
                 // preceding mark, the Sha-1 would still be computed correctly.
                 ? getDigest()
                 : cloneFrom(digestLastMarked);
    }

    private MessageDigest getDigest() {
        return DigestAlgorithm.SHA1.getDigest();
    }

    @Override
    public byte[] getChecksumBytes() {
        return digest.digest();
    }

    @Override
    public void mark(int readLimit) {
        digestLastMarked = cloneFrom(digest);
    }

    private MessageDigest cloneFrom(MessageDigest from) {
        try {
            return (MessageDigest) from.clone();
        } catch (CloneNotSupportedException e) { // should never occur
            throw new IllegalStateException("unexpected", e);
        }
    }
}
