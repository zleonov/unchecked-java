package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

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
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link BinaryOperator} which delegates to the underlying {@link CheckedBinaryOperator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> BinaryOperator<T> evalUnchecked(final CheckedBinaryOperator<T> operator) {
        Objects.requireNonNull(operator, "operator == null");
        return (t, u) -> {
            try {
                return operator.apply(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
