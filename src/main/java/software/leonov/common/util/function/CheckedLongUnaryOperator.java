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
import java.util.function.LongUnaryOperator;

/**
 * Mirror of the {@link LongUnaryOperator} interface whose {@code applyAsLong(long)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedLongUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the the operand
     * @return the operator result
     * @throws Exception if an error occurs
     */
    public long applyAsLong(final long operand) throws Exception;

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input, and then applies this
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     *
     * @see #andThen(CheckedLongUnaryOperator)
     */
    default CheckedLongUnaryOperator compose(final CheckedLongUnaryOperator before) {
        Objects.requireNonNull(before, "before == null");
        return v -> applyAsLong(before.applyAsLong(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input, and then applies the {@code after}
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     *
     * @see #compose(CheckedLongUnaryOperator)
     */
    default CheckedLongUnaryOperator andThen(final CheckedLongUnaryOperator after) {
        Objects.requireNonNull(after, "after == null");
        return t -> after.applyAsLong(applyAsLong(t));
    }

    /**
     * Returns a {@link LongUnaryOperator} which delegates to the underlying {@link CheckedLongUnaryOperator},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link LongUnaryOperator} which delegates to the underlying {@link CheckedLongUnaryOperator},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongUnaryOperator evalUnchecked(final CheckedLongUnaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return d -> {
            try {
                return operator.applyAsLong(d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}