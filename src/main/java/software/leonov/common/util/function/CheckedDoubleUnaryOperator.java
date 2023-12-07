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
import java.util.function.DoubleUnaryOperator;

/**
 * Mirror of the {@link DoubleUnaryOperator} interface whose {@code applyAsDouble(double)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedDoubleUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the the operand
     * @return the operator result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(final double operand) throws Exception;

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input, and then applies this
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     *
     * @see #andThen(CheckedDoubleUnaryOperator)
     */
    default CheckedDoubleUnaryOperator compose(final CheckedDoubleUnaryOperator before) {
        Objects.requireNonNull(before, "before == null");
        return v -> applyAsDouble(before.applyAsDouble(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input, and then applies the {@code after}
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     *
     * @see #compose(CheckedDoubleUnaryOperator)
     */
    default CheckedDoubleUnaryOperator andThen(final CheckedDoubleUnaryOperator after) {
        Objects.requireNonNull(after, "after == null");
        return t -> after.applyAsDouble(applyAsDouble(t));
    }

    /**
     * Returns a {@link DoubleUnaryOperator} which delegates to the underlying {@link CheckedDoubleUnaryOperator},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link DoubleUnaryOperator} which delegates to the underlying {@link CheckedDoubleUnaryOperator},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleUnaryOperator unchecked(final CheckedDoubleUnaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return d -> {
            try {
                return operator.applyAsDouble(d);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}