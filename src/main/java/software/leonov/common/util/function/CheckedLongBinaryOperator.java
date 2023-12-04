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
import java.util.function.LongBinaryOperator;

/**
 * Mirror of the {@link LongBinaryOperator} interface whose {@code applyAsLong(long, long)} method can throw a
 * checked exception.
 */
@FunctionalInterface
public interface CheckedLongBinaryOperator {

    /**
     * Applies this operator to the given operands.
     * 
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(long left, long right) throws Exception;

    /**
     * Returns a {@link LongBinaryOperator} which delegates to the underlying {@link CheckedLongBinaryOperator},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link LongBinaryOperator} which delegates to the underlying {@link CheckedLongBinaryOperator},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongBinaryOperator evalUnchecked(final CheckedLongBinaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return (l, r) -> {
            try {
                return operator.applyAsLong(l, r);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
