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
import java.util.function.DoubleBinaryOperator;

/**
 * Mirror of the {@link DoubleBinaryOperator} interface whose {@code applyAsDouble(double, double)} method can throw a
 * checked exception.
 */
@FunctionalInterface
public interface CheckedDoubleBinaryOperator {

    /**
     * Applies this operator to the given operands.
     * 
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(double left, double right) throws Exception;

    /**
     * Returns a {@link DoubleBinaryOperator} which delegates to the underlying {@link CheckedDoubleBinaryOperator},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link DoubleBinaryOperator} which delegates to the underlying {@link CheckedDoubleBinaryOperator},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleBinaryOperator unchecked(final CheckedDoubleBinaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return (l, r) -> {
            try {
                return operator.applyAsDouble(l, r);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}
