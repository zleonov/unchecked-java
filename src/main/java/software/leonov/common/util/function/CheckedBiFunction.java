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
import java.util.function.BiFunction;

/**
 * Mirror of the {@link BiFunction} interface whose {@code apply(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBiFunction<T, U, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first argument
     * @param u the second argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public R apply(final T t, final U u) throws Exception;

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     */
    public default <V> CheckedBiFunction<T, U, V> andThen(final CheckedFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after, "after == null");
        return (t, u) -> after.apply(apply(t, u));
    }

    /**
     * Returns a {@link BiFunction} which delegates to the underlying {@link CheckedBiFunction},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link BiFunction} which delegates to the underlying {@link CheckedBiFunction},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U, R> BiFunction<T, U, R> evalUnchecked(final CheckedBiFunction<? super T, ? super U, ? extends R> function) {
        Objects.requireNonNull(function, "function == null");
        return (t, u) -> {
            try {
                return function.apply(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
