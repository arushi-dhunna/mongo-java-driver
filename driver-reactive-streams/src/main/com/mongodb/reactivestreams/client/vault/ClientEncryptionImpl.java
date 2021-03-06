/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.reactivestreams.client.vault;

import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.model.vault.EncryptOptions;
import com.mongodb.internal.async.client.vault.AsyncClientEncryption;
import com.mongodb.reactivestreams.client.internal.Publishers;
import org.bson.BsonBinary;
import org.bson.BsonValue;
import org.reactivestreams.Publisher;

import static com.mongodb.assertions.Assertions.notNull;

class ClientEncryptionImpl implements ClientEncryption {
    private final AsyncClientEncryption wrapped;

    ClientEncryptionImpl(final AsyncClientEncryption wrapped) {
        this.wrapped = notNull("wrapped", wrapped);
    }

    @Override
    public Publisher<BsonBinary> createDataKey(final String kmsProvider) {
        return createDataKey(kmsProvider, new DataKeyOptions());
    }

    @Override
    public Publisher<BsonBinary> createDataKey(final String kmsProvider, final DataKeyOptions dataKeyOptions) {
        return Publishers.publish(
                callback -> wrapped.createDataKey(kmsProvider, dataKeyOptions, callback));
    }

    @Override
    public Publisher<BsonBinary> encrypt(final BsonValue value, final EncryptOptions options) {
        return Publishers.publish(
                callback -> wrapped.encrypt(value, options, callback));
    }

    @Override
    public Publisher<BsonValue> decrypt(final BsonBinary value) {
        return Publishers.publish(
                callback -> wrapped.decrypt(value, callback));
    }

    @Override
    public void close() {
        wrapped.close();
    }
}
