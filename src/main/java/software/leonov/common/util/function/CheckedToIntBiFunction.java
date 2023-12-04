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
import java.util.function.ToIntBiFunction;

/**
 * Mirror of the {@link ToIntBiFunction} interface whose {@code applyAsInt(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedToIntBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(T t, U u) throws Exception;

    /**
     * Returns a {@link ToIntBiFunction} which delegates to the underlying {@link CheckedToIntBiFunction},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToIntBiFunction} which delegates to the underlying {@link CheckedToIntBiFunction},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> ToIntBiFunction<T, U> evalUnchecked(final CheckedToIntBiFunction<? super T, ? super U> function) {
        Objects.requireNonNull(function, "function == null");
        return (t, u) -> {
            try {
                return function.applyAsInt(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
