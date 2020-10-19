package software.leonov.common.util.function;

import java.util.Objects;

/**
 * Static utility methods for working with {@link Exception}s.
 * 
 * @author Zhenya Leonov
 */
final class Exceptions {

    private Exceptions() {
    }

    /**
     * Propagates the specified {@code Exception} as if it is always an instance of {@code RuntimeException} without
     * wrapping it in a {@code RuntimeException}.
     * 
     * The main advantage this method has over {@link Throwables#propagate(Throwable)} is not filling up the stack trace
     * with unnecessary bloat that comes from wrapping a checked exception in a {@code RuntimeException}.
     * <p>
     * For example:
     * 
     * <pre>
     * T doSomething() { // does not throw a checked exception
     *     try {
     *         return someMethodThatCouldThrowAnything();
     *     } catch (final IKnowWhatToDoWithThisException e) {
     *         ...
     *     } catch (final Exception e) {
     *         throw evalUnchecked(e);
     *     }
     * }
     * </pre>
     * 
     * <b>Warning:</b> This method breaks Java's exception handling idiom and can lead to horrible errors when misused. See
     * <a target="_blank" href="https://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html">Unchecked
     * Exceptions — The Controversy</a> for further discussion. It is only safe to use if you ensure the caller will catch
     * all possible checked exceptions that could occur. If in doubt <b>do not use</b>.
     * 
     * @param e the specified throwable
     * @return this method does not return - the return type is only for your convenience to make the compiler happy
     * @throws the specified throwable - always
     */
    @SuppressWarnings("unchecked")
    public static <E extends Exception> RuntimeException uncheckedException(final Exception e) throws E {
        Objects.requireNonNull(e, "e == null");
        throw (E) e;
    }

}
