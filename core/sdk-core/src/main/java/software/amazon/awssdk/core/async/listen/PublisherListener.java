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

package software.amazon.awssdk.core.async.listen;


import static software.amazon.awssdk.utils.FunctionalUtils.runAndLogError;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.annotations.SdkProtectedApi;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.utils.FunctionalUtils.UnsafeRunnable;
import software.amazon.awssdk.utils.Validate;

/**
 * Listener interface that invokes callbacks associated with this {@link Publisher} and any resulting {@link Subscriber}.
 *
 * @see AsyncResponseTransformerListener
 * @see SubscriberListener
 */
public interface PublisherListener<T> extends SubscriberListener<T> {
    /**
     * Invoked after {@link Publisher#subscribe(Subscriber)}
     */
    default void publisherSubscribe(Subscriber<? super T> subscriber) {
    }

    /**
     * Wrap this {@link SdkPublisher} with a new one that will notify a {@link PublisherListener} of important events occurring.
     */
    static <T> SdkPublisher<T> wrap(SdkPublisher<T> delegate, PublisherListener<T> listener) {
        return new NotifyingPublisher<>(delegate, listener);
    }

    @SdkProtectedApi
    final class NotifyingPublisher<T> implements SdkPublisher<T> {
        private static final Logger log = LoggerFactory.getLogger(NotifyingPublisher.class);

        private final SdkPublisher<T> delegate;
        private final PublisherListener<T> listener;

        NotifyingPublisher(SdkPublisher<T> delegate,
                           PublisherListener<T> listener) {
            this.delegate = Validate.notNull(delegate, "delegate");
            this.listener = Validate.notNull(listener, "listener");
        }

        @Override
        public void subscribe(Subscriber<? super T> s) {
            delegate.subscribe(SubscriberListener.wrap(s, listener));
            invoke(() -> listener.publisherSubscribe(s), "publisherSubscribe");
        }

        static void invoke(UnsafeRunnable runnable, String callbackName) {
            runAndLogError(log, callbackName + " callback failed. This exception will be dropped.", runnable);
        }
    }
}
