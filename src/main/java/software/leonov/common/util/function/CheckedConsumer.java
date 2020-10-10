package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Mirror of the {@link Consumer} interface whose {@code accept(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws Exception if an error occurs
     */
    public void accept(T t) throws Exception;

    /**
     * Returns a composed consumer that performs, in sequence, this operation followed by the {@code after} operation. If
     * performing either operation throws an exception, it is relayed to the caller of the composed operation. If performing
     * {@code this} operation throws an exception, the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed consumer that performs in sequence this operation followed by the {@code after} operation
     */
    public default CheckedConsumer<T> andThen(final CheckedConsumer<? super T> after) {
        Objects.requireNonNull(after, "after == null");
        return t -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a {@link Consumer} which delegates to the underlying {@link CheckedConsumer},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return a {@link Consumer} which delegates to the underlying {@link CheckedConsumer},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> Consumer<T> unchecked(final CheckedConsumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return t -> {
            try {
                consumer.accept(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

}