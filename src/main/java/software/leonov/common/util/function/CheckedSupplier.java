package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Mirror of the {@link Supplier} interface whose {@code get()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

    /**
     * Returns a result.
     *
     * @return a result
     * @throws Exception if an error occurs
     */
    public T get() throws Exception;

    /**
     * Returns a {@link Supplier} which delegates to the underlying {@link CheckedSupplier},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked supplier
     * @return a {@link Supplier} which delegates to the underlying {@link CheckedSupplier},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> Supplier<T> evalUnchecked(final CheckedSupplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.get();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #get()} on the specified {@code CheckedSupplier}
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #get()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> T uncheckedGet(final CheckedSupplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return evalUnchecked(supplier).get();
    }

}
