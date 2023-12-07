/*
 * Copyright (C) 2023 Zhenya Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.leonov.common.util.function;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Mirror of the {@link BiPredicate} interface whose {@code test(T, U)} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedBiPredicate<T, U> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first argument
     * @param u the second argument
     * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
     * @throws Exception if an error occurs
     */
    public boolean test(T t, U u) throws Exception;

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code false}, then the {@code other} predicate is not
     * evaluated.
     * <p>
     * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this
     * predicate throws an exception, the {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this predicate
     * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the {@code other}
     *         predicate
     */
    public default CheckedBiPredicate<T, U> and(final CheckedBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other, "other == null");
        return (t, u) -> test(t, u) && other.test(t, u);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    public default CheckedBiPredicate<T, U> negate() {
        return (t, u) -> !test(t, u);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. When
     * evaluating the composed predicate, if this predicate is {@code true}, then the {@code other} predicate is not
     * evaluated.
     * <p>
     * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this
     * predicate throws an exception, the {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this predicate
     * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the {@code other}
     *         predicate
     */
    public default CheckedBiPredicate<T, U> or(final CheckedBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other, "other == null");
        return (t, u) -> test(t, u) || other.test(t, u);
    }

    /**
     * Returns a {@link BiPredicate} which delegates to the underlying {@link CheckedBiPredicate},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param predicate the underlying checked predicate
     * @return a {@link BiPredicate} which delegates to the underlying {@link CheckedBiPredicate},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T, U> BiPredicate<T, U> unchecked(final CheckedBiPredicate<? super T, ? super U> predicate) {
        Objects.requireNonNull(predicate, "predicate == null");
        return (t, u) -> {
            try {
                return predicate.test(t, u);
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}
