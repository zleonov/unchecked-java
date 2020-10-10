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
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link LongBinaryOperator} which delegates to the underlying {@link CheckedLongBinaryOperator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static LongBinaryOperator unchecked(final CheckedLongBinaryOperator operator) {
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
