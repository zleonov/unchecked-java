package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Mirror of the {@link BiFunction} interface whose {@code apply(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBiFunction<T, U, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first argument
     * @param u the second argument
     * @return the function result
     * @throws Exception if an error occurs
     */
    public R apply(final T t, final U u) throws Exception;

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     */
    public default <V> CheckedBiFunction<T, U, V> andThen(final CheckedFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after, "after == null");
        return (t, u) -> after.apply(apply(t, u));
    }

    /**
     * Returns a {@link BiFunction} which delegates to the underlying {@link CheckedBiFunction},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param function the underlying checked function
     * @return a {@link BiFunction} which delegates to the underlying {@link CheckedBiFunction},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U, R> BiFunction<T, U, R> unchecked(final CheckedBiFunction<? super T, ? super U, ? extends R> function) {
        Objects.requireNonNull(function, "function == null");
        return (t, u) -> {
            try {
                return function.apply(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}
