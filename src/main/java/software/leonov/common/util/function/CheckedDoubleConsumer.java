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
import java.util.function.DoubleConsumer;

/**
 * Mirror of the {@link DoubleConsumer} interface whose {@code accept(double)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedDoubleConsumer {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     * @throws Exception if an error occurs
     */
    public void accept(double value) throws Exception;

    /**
     * Returns a composed {@code CheckedDoubleConsumer} that performs, in sequence, this operation followed by the
     * {@code after} operation. If performing either operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing {@code this} operation throws an exception, the {@code after} operation will not be
     * performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code CheckedDoubleConsumer} that performs in sequence this operation followed by the
     *         {@code after} operation
     */
    public default CheckedDoubleConsumer andThen(final CheckedDoubleConsumer after) {
        Objects.requireNonNull(after, "after == null");
        return t -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a {@link DoubleConsumer} which delegates to the underlying {@link CheckedDoubleConsumer},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return a {@link DoubleConsumer} which delegates to the underlying {@link CheckedDoubleConsumer},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleConsumer unchecked(final CheckedDoubleConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return t -> {
            try {
                consumer.accept(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}