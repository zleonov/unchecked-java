package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.IntBinaryOperator;

/**
 * Mirror of the {@link IntBinaryOperator} interface whose {@code applyAsInt(int, int)} method can throw a
 * checked exception.
 */
@FunctionalInterface
public interface CheckedIntBinaryOperator {

    /**
     * Applies this operator to the given operands.
     * 
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(int left, int right) throws Exception;

    /**
     * Returns an {@link IntBinaryOperator} which delegates to the underlying {@link CheckedIntBinaryOperator},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return an {@link IntBinaryOperator} which delegates to the underlying {@link CheckedIntBinaryOperator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static IntBinaryOperator evalUnchecked(final CheckedIntBinaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return (l, r) -> {
            try {
                return operator.applyAsInt(l, r);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
