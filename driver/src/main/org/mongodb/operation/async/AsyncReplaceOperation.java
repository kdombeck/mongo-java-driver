/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.operation.async;

import org.mongodb.Document;
import org.mongodb.Encoder;
import org.mongodb.MongoNamespace;
import org.mongodb.WriteConcern;
import org.mongodb.connection.BufferPool;
import org.mongodb.operation.MongoReplace;
import org.mongodb.operation.protocol.MessageSettings;
import org.mongodb.operation.protocol.MongoReplaceMessage;
import org.mongodb.operation.protocol.MongoRequestMessage;

import java.nio.ByteBuffer;

public class AsyncReplaceOperation<T> extends AsyncWriteOperation {
    private final MongoReplace<T> replace;
    private final Encoder<Document> queryEncoder;
    private final Encoder<T> encoder;

    public AsyncReplaceOperation(final MongoNamespace namespace, final MongoReplace<T> replace, final Encoder<Document> queryEncoder,
                                 final Encoder<T> encoder, final BufferPool<ByteBuffer> bufferPool) {
        super(namespace, bufferPool);
        this.replace = replace;
        this.queryEncoder = queryEncoder;
        this.encoder = encoder;
    }

    @Override
    protected MongoRequestMessage createRequestMessage(final MessageSettings settings) {
        return new MongoReplaceMessage<T>(getNamespace().getFullName(), replace, queryEncoder, encoder, settings);
    }

    @Override
    public MongoReplace<T> getWrite() {
        return replace;
    }

    @Override
    public WriteConcern getWriteConcern() {
        return replace.getWriteConcern();
    }
}
