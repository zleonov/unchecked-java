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
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the underlying checked supplier
     * @return a {@link Supplier} which delegates to the underlying {@link CheckedSupplier},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> Supplier<T> unchecked(final CheckedSupplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return () -> {
            try {
                return supplier.get();
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

    /**
     * Returns the result of calling {@link #get()} on the specified {@code CheckedSupplier}
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param supplier the specified checked supplier
     * @return the result of calling {@link #get()} on the specified {@code CheckedSupplier}
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static <T> T uncheckedGet(final CheckedSupplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier == null");
        return unchecked(supplier).get();
    }

}
