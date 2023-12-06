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

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Mirror of the {@link Comparator} interface whose {@code compare(T, T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedComparator<T> {

    /**
     * Compares its two arguments for order.
     *
     * @param left  the first argument
     * @param right the second argument
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than
     *         the second
     * @throws Exception if an error occurs
     */
    public int compare(final T left, final T right) throws Exception;

    /**
     * Returns a comparator that applies the specified function to each element of {@code type T} and compares the results
     * according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified function is also serializable.
     *
     * @param <T>      the type of element to be compared
     * @param <U>      the type of the result returned by the function
     * @param function the function to apply
     * @return a comparator that applies the specified function to each element of {@code type T} and compares the results
     *         according to their <i>natural ordering</i>
     */
    @SuppressWarnings("unchecked")
    public static <T, U extends Comparable<? super U>> CheckedComparator<T> comparing(final CheckedFunction<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "function == null");
        return (CheckedComparator<T> & Serializable) (left, right) -> function.apply(left).compareTo(function.apply(right));
    }

    /**
     * Returns a comparator that uses the specified comparator to compare the results of applying the specified function to
     * each element of {@code type T}.
     * <p>
     * The returned comparator is serializable if both the function and the specified comparator are serializable.
     *
     * @param <T>        the type of element to be compared
     * @param <U>        the type of the result returned by the function
     * @param function   the function to apply
     * @param comparator the comparator to use to compare elements of {@code type U}
     * @return a comparator that uses the specified comparator to compare the results of applying the specified function to
     *         each element of {@code type T}
     */
    @SuppressWarnings("unchecked")
    public static <T, U> CheckedComparator<T> comparing(final CheckedFunction<? super T, ? extends U> function, CheckedComparator<? super U> comparator) {
        Objects.requireNonNull(function, "function == null");
        Objects.requireNonNull(comparator, "comparator == null");
        return (CheckedComparator<T> & Serializable) (left, right) -> comparator.compare(function.apply(left), function.apply(right));
    }

    /**
     * Returns a comparator that applies the specified function to each element of {@code type T} and compares the
     * {@code double} results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified function is also serializable.
     *
     * @param <T>      the type of element to be compared
     * @param function the function to apply
     * @return a comparator that applies the specified function to each element of {@code type T} and compares the
     *         {@code double} results according to their <i>natural ordering</i>
     */
    @SuppressWarnings("unchecked")
    public static <T> CheckedComparator<T> comparingDouble(final CheckedToDoubleFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return (CheckedComparator<T> & Serializable) (left, right) -> Double.compare(function.applyAsDouble(left), function.applyAsDouble(right));
    }

    /**
     * Returns a comparator that applies the specified function to each element of {@code type T} and compares the
     * {@code int} results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified function is also serializable.
     *
     * @param <T>      the type of element to be compared
     * @param function the function to apply
     * @return a comparator that applies the specified function to each element of {@code type T} and compares the
     *         {@code int} results according to their <i>natural ordering</i>
     */
    @SuppressWarnings("unchecked")
    public static <T> CheckedComparator<T> comparingInt(final CheckedToIntFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return (CheckedComparator<T> & Serializable) (left, right) -> Integer.compare(function.applyAsInt(left), function.applyAsInt(right));
    }

    /**
     * Returns a comparator that applies the specified function to each element of {@code type T} and compares the
     * {@code long} results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified function is also serializable.
     *
     * @param <T>      the type of element to be compared
     * @param function the function to apply
     * @return a comparator that applies the specified function to each element of {@code type T} and compares the
     *         {@code long} results according to their <i>natural ordering</i>
     */
    @SuppressWarnings("unchecked")
    public static <T> CheckedComparator<T> comparingLong(final CheckedToLongFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return (CheckedComparator<T> & Serializable) (left, right) -> Long.compare(function.applyAsLong(left), function.applyAsLong(right));
    }

    /**
     * Returns a comparator that compares values according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable and does not accept {@code null} values.
     *
     * @param <T> the type of element to be compared
     * @return a comparator that compares values according to their <i>natural ordering</i>
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> CheckedComparator<T> naturalOrder() {
        return (CheckedComparator<T>) NaturalOrdering.getInstance();
    }

    /**
     * Returns a comparator that considers {@code null} values to be less than non-{@code null} values. If both are
     * non-{@code null}, the specified comparator is used to determine the order. If the specified comparator is
     * {@code null}, then the returned comparator considers all non-{@code null} values to be equal.
     * <p>
     * The returned comparator is serializable if the specified comparator is serializable.
     *
     * @param <T>        the type of the elements to be compared
     * @param comparator the comparator to use for comparing non-{@code null} values
     * @return a comparator that considers {@code null} values to be less than non-{@code null} values
     */
    public static <T> CheckedComparator<T> nullsFirst(final CheckedComparator<? super T> comparator) {
        return Nulls.first(comparator);
    }

    /**
     * Returns a comparator that considers {@code null} values to be greater than non-{@code null} values. If both are
     * non-{@code null}, the specified comparator is used to determine the order. If the specified comparator is
     * {@code null}, then the returned comparator considers all non-{@code null} values to be equal.
     * <p>
     * The returned comparator is serializable if the specified comparator is serializable.
     *
     * @param <T>        the type of the elements to be compared
     * @param comparator the comparator to use for comparing non-{@code null} values
     * @return a comparator that considers {@code null} values to be greater than non-{@code null} values
     */
    public static <T> CheckedComparator<T> nullsLast(final CheckedComparator<? super T> comparator) {
        return Nulls.last(comparator);
    }

    /**
     * Returns a comparator that compares values according to the reverse of their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable and does not accept {@code null} values.
     *
     * @param <T> the {@link Comparable} type of element to be compared
     * @return a comparator that compares values according to the reverse of their <i>natural ordering</i>
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> CheckedComparator<T> reverseOrder() {
        return (CheckedComparator<T>) ReversedNaturalOrdering.getInstance();
    }

    /**
     * Returns a comparator that has the reverse ordering of this comparator.
     *
     * @return a comparator that has the reverse ordering of this comparator
     */
    default CheckedComparator<T> reversed() {
        return new Reversed<T>(this);
    }

    /**
     * Returns a composed comparator which uses {@code this} comparator, followed by the {@code other} comparator if this
     * comparator returns a zero result.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     *
     * @param other the other comparator to be used when {@code this} comparator determines that two objects are equal
     * @return a composed comparator which uses {@code this} comparator, followed by the {@code other} comparator if this
     *         comparator returns a zero result
     */
    @SuppressWarnings("unchecked")
    default CheckedComparator<T> thenComparing(final CheckedComparator<? super T> other) {
        Objects.requireNonNull(other, "other == null");
        return (CheckedComparator<T> & Serializable) (left, right) -> {
            int res = compare(left, right);
            return (res != 0) ? res : other.compare(left, right);
        };
    }

    /**
     * Returns a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     * specified function before comparing the results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     *
     * @param function the function to apply
     * @return a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     *         specified function before comparing the results according to their <i>natural ordering</i>
     */
    default <U extends Comparable<? super U>> CheckedComparator<T> thenComparing(CheckedFunction<? super T, ? extends U> function) {
        return thenComparing(comparing(function));
    }

    /**
     * Returns a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     * specified function before using the {@code other} comparator.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     * 
     * @param function the function to apply
     * @param other    the comparator to use after applying the function
     * @return a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     *         specified function before using the {@code other} comparator
     */
    default <U> CheckedComparator<T> thenComparing(final CheckedFunction<? super T, ? extends U> function, final CheckedComparator<? super U> other) {
        return thenComparing(comparing(function, other));
    }

    /**
     * Returns a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     * specified function before comparing the {@code double} results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     *
     * @param function the function to apply
     * @return a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     *         specified function before comparing the {@code double} results according to their <i>natural ordering</i>
     */
    default CheckedComparator<T> thenComparingDouble(CheckedToDoubleFunction<? super T> function) {
        return thenComparing(comparingDouble(function));
    }

    /**
     * Returns a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     * specified function before comparing the {@code int} results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     * 
     * @param function the function to apply
     * @return a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     *         specified function before comparing the {@code int} results according to their <i>natural ordering</i>
     */
    default CheckedComparator<T> thenComparingInt(final CheckedToIntFunction<? super T> function) {
        return thenComparing(comparingInt(function));
    }

    /**
     * Returns a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     * specified function before comparing the {@code long} results according to their <i>natural ordering</i>.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     * 
     * @param function the function to apply
     * @return a composed comparator which uses {@code this} comparator first, and if the result is zero, applies the
     *         specified function before comparing the {@code long} results according to their <i>natural ordering</i>
     */
    default CheckedComparator<T> thenComparingLong(final CheckedToLongFunction<? super T> function) {
        return thenComparing(comparingLong(function));
    }

    /**
     * Returns a {@link Comparator} which delegates to the underlying {@link CheckedComparator},
     * {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param comparator the specified checked comparator
     * @return a {@link Comparator} which delegates to the underlying {@link CheckedComparator},
     *         {@link Exceptions#uncheckedException(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> Comparator<T> unchecked(final CheckedComparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator == null");
        return (left, right) -> {
            try {
                return comparator.compare(left, right);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
