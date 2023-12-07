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
import java.util.function.ToLongBiFunction;

/**
 * Mirror of the {@link ToLongBiFunction} interface whose {@code applyAsLong(T, U)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedToLongBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(T t, U u) throws Exception;

    /**
     * Returns a {@link ToLongBiFunction} which delegates to the underlying {@link CheckedToLongBiFunction},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToLongBiFunction} which delegates to the underlying {@link CheckedToLongBiFunction},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> ToLongBiFunction<T, U> unchecked(final CheckedToLongBiFunction<? super T, ? super U> function) {
        Objects.requireNonNull(function, "function == null");
        return (t, u) -> {
            try {
                return function.applyAsLong(t, u);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}
