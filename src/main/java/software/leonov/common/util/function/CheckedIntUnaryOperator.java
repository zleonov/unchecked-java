package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.IntUnaryOperator;

/**
 * Mirror of the {@link IntUnaryOperator} interface whose {@code applyAsInt(int)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedIntUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the the operand
     * @return the operator result
     * @throws Exception if an error occurs
     */
    public int applyAsInt(final int operand) throws Exception;

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input, and then applies this
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     *
     * @see #andThen(CheckedIntUnaryOperator)
     */
    default CheckedIntUnaryOperator compose(final CheckedIntUnaryOperator before) {
        Objects.requireNonNull(before, "before == null");
        return v -> applyAsInt(before.applyAsInt(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input, and then applies the {@code after}
     * operator to the result. If evaluation of either operator throws an exception, it is relayed to the caller of the
     * composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     *
     * @see #compose(CheckedIntUnaryOperator)
     */
    default CheckedIntUnaryOperator andThen(final CheckedIntUnaryOperator after) {
        Objects.requireNonNull(after, "after == null");
        return t -> after.applyAsInt(applyAsInt(t));
    }

    /**
     * Returns an {@link IntUnaryOperator} which delegates to the underlying {@link CheckedIntUnaryOperator},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return an {@link IntUnaryOperator} which delegates to the underlying {@link CheckedIntUnaryOperator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static IntUnaryOperator evalUnchecked(final CheckedIntUnaryOperator operator) {
        Objects.requireNonNull(operator, "operator == null");
        return d -> {
            try {
                return operator.applyAsInt(d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}