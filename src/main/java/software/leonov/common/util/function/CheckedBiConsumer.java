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

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Mirror of the {@link BiConsumer} interface whose {@code accept(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBiConsumer<T, U> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first argument
     * @param u the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final U u) throws Exception;

    /**
     * Returns a composed consumer that performs, in sequence, this operation followed by the {@code after} operation. If
     * performing either operation throws an exception, it is relayed to the caller of the composed operation. If performing
     * this operation throws an exception, the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed consumer that performs in sequence this operation followed by the {@code after} operation
     */
    public default CheckedBiConsumer<T, U> andThen(final CheckedBiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after, "after == null");
        return (t, u) -> {
            accept(t, u);
            after.accept(t, u);
        };
    }

    /**
     * Returns a {@link BiConsumer} which delegates to the underlying {@link CheckedBiConsumer},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return a {@link BiConsumer} which delegates to the underlying {@link CheckedBiConsumer},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> BiConsumer<T, U> unchecked(final CheckedBiConsumer<? super T, ? super U> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }
}
