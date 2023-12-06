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
import java.util.function.UnaryOperator;

/**
 * Mirror of the {@link UnaryOperator} interface whose {@code apply(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedUnaryOperator<T> extends CheckedFunction<T, T> {

    /**
     * Returns a {@link UnaryOperator} which delegates to the underlying {@link CheckedUnaryOperator},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link UnaryOperator} which delegates to the underlying {@link CheckedUnaryOperator},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> UnaryOperator<T> unchecked(final CheckedUnaryOperator<T> operator) {
        Objects.requireNonNull(operator, "operator == null");
        return t -> {
            try {
                return operator.apply(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
