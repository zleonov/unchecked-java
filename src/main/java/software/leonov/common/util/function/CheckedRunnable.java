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

/**
 * Mirror of the {@link Runnable} interface whose {@code run()} method can throw a checked exception.
 */
@FunctionalInterface
public interface CheckedRunnable {

    /**
     * The general contract of the method {@code run()} is that it may take any action whatsoever.
     * 
     * @throws Exception if an error occurs
     */
    public void run() throws Exception;

    /**
     * Returns a {@link Runnable} which delegates to the underlying {@link CheckedRunnable},
     * {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked.
     * 
     * @param runnable the underlying checked runnable
     * @return a {@link Runnable} which delegates to the underlying {@link CheckedRunnable},
     *         {@link Unchecked#exception(Exception) rethrowing} any checked exceptions as if they were unchecked
     */
    public static Runnable unchecked(final CheckedRunnable runnable) {
        Objects.requireNonNull(runnable, "runnable == null");
        return () -> {
            try {
                runnable.run();
            } catch (final Exception e) {
                throw Unchecked.exception(e);
            }
        };
    }

}