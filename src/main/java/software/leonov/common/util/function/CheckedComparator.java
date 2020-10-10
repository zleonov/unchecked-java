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
     * @param o1 the first argument
     * @param o2 the second argument
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than
     *         the second
     * @throws Exception if an error occurs
     */
    public int compare(T o1, T o2) throws Exception;

    /**
     * Returns a composed comparator which tries {@code this} comparator, followed by the {@code other} comparator if this
     * comparator returns a zero result.
     * <p>
     * The returned comparator is serializable if the specified comparator is also serializable.
     *
     * @param other the other comparator to be used when {@code this} comparator determines that two objects are equal
     * @return a composed comparator which tries {@code this} comparator, followed by the {@code other} comparator if this
     *         comparator returns a zero result
     */
    @SuppressWarnings("unchecked")
    public default CheckedComparator<T> thenComparing(final CheckedComparator<? super T> other) {
        Objects.requireNonNull(other, "other == null");
        return (CheckedComparator<T> & Serializable) (c1, c2) -> {
            int res = compare(c1, c2);
            return (res != 0) ? res : other.compare(c1, c2);
        };
    }

    /*
     * Is there any point in having the additional reversed, thenComparingXXXX, static reverseOrder, static naturalOrder,
     * etc., methods to match the functionality of the Comparator interface?
     */

    /**
     * Returns a {@link Comparator} which delegates to the underlying {@link CheckedComparator},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param comparator the specified checked comparator
     * @return a {@link Comparator} which delegates to the underlying {@link CheckedComparator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
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
