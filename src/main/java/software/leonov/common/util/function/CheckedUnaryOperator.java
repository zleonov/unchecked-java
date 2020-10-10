package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Mirror of the {@link UnaryOperator} interface whose {@code apply(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedUnaryOperator<T> extends CheckedFunction<T, T> {

    /**
     * Returns a {@link UnaryOperator} which delegates to the underlying {@link CheckedUnaryOperator},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param operator the underlying checked operator
     * @return a {@link UnaryOperator} which delegates to the underlying {@link CheckedUnaryOperator},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> UnaryOperator<T> unchecked(final CheckedUnaryOperator<T> operator) {
        Objects.requireNonNull(operator, "operator == null");
        return t -> {
            try {
                return operator.apply(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
