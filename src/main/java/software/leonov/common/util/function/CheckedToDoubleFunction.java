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
import java.util.function.ToDoubleFunction;

/**
 * Mirror of the {@link ToDoubleFunction} interface whose {@code applyAsDouble(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedToDoubleFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(final T t) throws Exception;

    /**
     * Returns a {@link ToDoubleFunction} which delegates to the underlying {@link CheckedToDoubleFunction},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToDoubleFunction} which delegates to the underlying {@link CheckedToDoubleFunction},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ToDoubleFunction<T> unchecked(final CheckedToDoubleFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return t -> {
            try {
                return function.applyAsDouble(t);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}