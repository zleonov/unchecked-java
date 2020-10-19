package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ToDoubleFunction;

/**
 * Mirror of the {@link ToDoubleFunction} interface whose {@code applyAsDouble(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedToDoubleFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public double applyAsDouble(final T t) throws Exception;

    /**
     * Returns a {@link ToDoubleFunction} which delegates to the underlying {@link CheckedToDoubleFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link ToDoubleFunction} which delegates to the underlying {@link CheckedToDoubleFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ToDoubleFunction<T> evalUnchecked(final CheckedToDoubleFunction<? super T> function) {
        Objects.requireNonNull(function, "function == null");
        return t -> {
            try {
                return function.applyAsDouble(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}