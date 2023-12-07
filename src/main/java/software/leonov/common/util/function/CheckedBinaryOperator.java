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
import java.util.function.BinaryOperator;

/**
 * Mirror of the {@link BinaryOperator} interface whose {@code apply(T, T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBinaryOperator<T> extends CheckedBiFunction<T, T, T> {

    /**
     * Returns a {@link CheckedBinaryOperator} which returns the lesser of two elements according to the specified
     * {@code CheckedComparator}.
     *
     * @param comparator a {@code CheckedComparator} for comparing the two values
     * @return a {@code CheckedBinaryOperator} which returns the lesser of its operands, according to the supplied
     *         {@code CheckedComparator}
     */
    public static <T> CheckedBinaryOperator<T> minBy(final CheckedComparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator == null");
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }

    /**
     * Returns a {@link CheckedBinaryOperator} which returns the greater of two elements according to the specified
     * {@code Comparator}.
     *
     * @param comparator a {@code CheckedComparator} for comparing the two values
     * @return a {@code CheckedBinaryOperator} which returns the greater of its operands, according to the supplied
     *         {@code CheckedComparator}
     */
    public static <T> CheckedBinaryOperator<T> maxBy(final CheckedComparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator == null");
        return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
    }

    /**
     * Returns a {@link BinaryOperator} which delegates to the underlying {@link CheckedBinaryOperator},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link BinaryOperator} which delegates to the underlying {@link CheckedBinaryOperator},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> BinaryOperator<T> unchecked(final CheckedBinaryOperator<T> operator) {
        Objects.requireNonNull(operator, "operator == null");
        return (t, u) -> {
            try {
                return operator.apply(t, u);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}
