package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Mirror of the {@link BiConsumer} interface whose {@code accept(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBiConsumer<T, U> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first argument
     * @param u the second argument
     * @throws Exception if an error occurs
     */
    public void accept(final T t, final U u) throws Exception;

    /**
     * Returns a composed consumer that performs, in sequence, this operation followed by the {@code after} operation. If
     * performing either operation throws an exception, it is relayed to the caller of the composed operation. If performing
     * this operation throws an exception, the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed consumer that performs in sequence this operation followed by the {@code after} operation
     */
    public default CheckedBiConsumer<T, U> andThen(final CheckedBiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after, "after == null");
        return (t, u) -> {
            accept(t, u);
            after.accept(t, u);
        };
    }

    /**
     * Returns a {@link BiConsumer} which delegates to the underlying {@link CheckedBiConsumer},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param consumer the underlying checked consumer
     * @return a {@link BiConsumer} which delegates to the underlying {@link CheckedBiConsumer},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> BiConsumer<T, U> unchecked(final CheckedBiConsumer<? super T, ? super U> consumer) {
        Objects.requireNonNull(consumer, "consumer == null");
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }
}
