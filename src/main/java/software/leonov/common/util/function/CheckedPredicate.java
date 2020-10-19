package software.leonov.common.util.function;

import static software.leonov.common.util.function.Exceptions.uncheckedException;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Mirror of the {@link Predicate} interface whose {@code test(T)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedPredicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     * @throws Exception if an error occurs
     */
    public boolean test(T t) throws Exception;

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code false}, then the {@code other} predicate is not
     * evaluated.
     * <p>
     * Any exceptions thrown during evaluation of either predicate are relayed to the caller. If evaluation of this
     * predicate throws an exception, the {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this predicate
     * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the {@code other}
     *         predicate
     */
    public default CheckedPredicate<T> and(final CheckedPredicate<? super T> other) {
        Objects.requireNonNull(other, "other == null");
        return t -> test(t) && other.test(t);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    public default CheckedPredicate<T> negate() {
        return t -> !test(t);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code true}, then the {@code other} predicate is not
     * evaluated.
     * <p>
     * Any exceptions thrown during evaluation of either predicate are relayed to the caller. If evaluation of this
     * predicate throws an exception, the {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this predicate
     * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the {@code other}
     *         predicate
     */
    public default CheckedPredicate<T> or(final CheckedPredicate<? super T> other) {
        Objects.requireNonNull(other, "other == null");
        return t -> test(t) || other.test(t);
    }

    /**
     * Returns a {@link Predicate} which delegates to the underlying {@link CheckedPredicate},
     * {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param predicate the underlying checked predicate
     * @return a {@link Predicate} which delegates to the underlying {@link CheckedPredicate},
     *         {@link Exceptions#uncheckedException(Throwable) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> Predicate<T> evalUnchecked(final CheckedPredicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate == null");
        return t -> {
            try {
                return predicate.test(t);
            } catch (final Exception e) {
                throw uncheckedException(e);
            }
        };
    }

    // Any point in having CheckedPredicate.isEqual(Object) corresponding to Predicate.isEqual(Object)?

}
