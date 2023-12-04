/*
 * Copyright (C) 2023 Zhenya Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ObjLongConsumer;

/**
 * Mirror of the {@link ObjLongConsumer} interface whose {@code accept(T, long)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedObjLongConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first argument
     * @param value the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final long value) throws Exception;

    /**
     * Returns an {@link ObjLongConsumer} which delegates to the underlying {@link CheckedObjLongConsumer},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return an {@link ObjLongConsumer} which delegates to the underlying {@link CheckedObjLongConsumer},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ObjLongConsumer<T> evalUnchecked(final CheckedObjLongConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, l) -> {
            try {
                consumer.accept(t, l);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }
}
