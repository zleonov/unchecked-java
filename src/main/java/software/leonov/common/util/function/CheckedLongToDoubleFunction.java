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
import java.util.function.LongToDoubleFunction;

/**
 * Mirror of the {@link LongToDoubleFunction} interface whose {@code applyAsDouble(long)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedLongToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(final long value) throws Exception;

    /**
     * Returns a {@link LongToDoubleFunction} which delegates to the underlying {@link CheckedLongToDoubleFunction},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link LongToDoubleFunction} which delegates to the underlying {@link CheckedLongToDoubleFunction},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongToDoubleFunction unchecked(final CheckedLongToDoubleFunction function) {
        Objects.requireNonNull(function, "function == null");
        return d -> {
            try {
                return function.applyAsDouble(d);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}