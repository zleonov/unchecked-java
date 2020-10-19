package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.IntSupplier;

/**
 * Mirror of the {@link IntSupplier} interface whose {@code getAsInt()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedIntSupplier {

    /**
     * Returns a integer result.
     *
     * @return a integer result
     * @throws Exception if an error occurs
     */
    public int getAsInt() throws Exception;

    /**
     * Returns an {@link IntSupplier} which delegates to the underlying {@link CheckedIntSupplier},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked int-supplier
     * @return an {@link IntSupplier} which delegates to the underlying {@link CheckedIntSupplier},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static IntSupplier evalUnchecked(final CheckedIntSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.getAsInt();
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #getAsInt()} on the specified {@code CheckedIntSupplier}
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #getAsInt()} on the specified {@code CheckedSupplier}
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static int uncheckedGet(final CheckedIntSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return evalUnchecked(supplier).getAsInt();
    }

}
