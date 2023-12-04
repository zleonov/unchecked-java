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
import java.util.function.ObjDoubleConsumer;

/**
 * Mirror of the {@link ObjDoubleConsumer} interface whose {@code accept(T, double)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedObjDoubleConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first argument
     * @param value the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final double value) throws Exception;

    /**
     * Returns an {@link ObjDoubleConsumer} which delegates to the underlying {@link CheckedObjDoubleConsumer},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return an {@link ObjDoubleConsumer} which delegates to the underlying {@link CheckedObjDoubleConsumer},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ObjDoubleConsumer<T> evalUnchecked(final CheckedObjDoubleConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, d) -> {
            try {
                consumer.accept(t, d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }
}
