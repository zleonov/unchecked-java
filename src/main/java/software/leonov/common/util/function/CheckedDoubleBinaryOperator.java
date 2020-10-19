package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

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
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link DoubleBinaryOperator} which delegates to the underlying {@link CheckedDoubleBinaryOperator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static DoubleBinaryOperator evalUnchecked(final CheckedDoubleBinaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return (l, r) -> {
            try {
                return operator.applyAsDouble(l, r);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
