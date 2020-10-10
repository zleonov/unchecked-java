package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.ObjDoubleConsumer;

/**
 * Mirror of the {@link ObjDoubleConsumer} interface whose {@code accept(T, double)} method can throw a checked
 * exception.
 */
@FunctionalInterface
public interface CheckedObjDoubleConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first argument
     * @param value the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final double value) throws Exception;

    /**
     * Returns an {@link ObjDoubleConsumer} which delegates to the underlying {@link CheckedObjDoubleConsumer},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return an {@link ObjDoubleConsumer} which delegates to the underlying {@link CheckedObjDoubleConsumer},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> ObjDoubleConsumer<T> unchecked(final CheckedObjDoubleConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, d) -> {
            try {
                consumer.accept(t, d);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }
}
