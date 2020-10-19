package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.Function;

/**
 * Mirror of the {@link Function} interface whose {@code apply(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public R apply(final T t) throws Exception;

    /**
     * Returns a composed function that first applies the {@code before} function to its input, and then applies this
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before} function and then applies this function
     *
     * @see #andThen(CheckedFunction)
     */
    public default <V> CheckedFunction<V, R> compose(final CheckedFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before, "before == null");
        return v -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     *
     * @see #compose(CheckedFunction)
     */
    public default <V> CheckedFunction<T, V> andThen(final CheckedFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after, "after == null");
        return t -> after.apply(apply(t));
    }

    // Any point in having CheckedFunction.identity() method corresponding to Function.identity()?

    /**
     * Returns a {@link Function} which delegates to the underlying {@link CheckedFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link Function} which delegates to the underlying {@link CheckedFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, R> Function<T, R> evalUnchecked(final CheckedFunction<? super T, ? extends R> function) {
        Objects.requireNonNull(function, "function == null");
        return t -> {
            try {
                return function.apply(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}