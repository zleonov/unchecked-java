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
 * Static utility methods for working with {@link Exception}s.
 * 
 * @author Zhenya Leonov
 */
public final class Exceptions {

    private Exceptions() {
    }

    /**
     * Propagates the specified {@code Exception} as if it is an instance of {@code RuntimeException} without the additional
     * bloat to the stack trace which would result from wrapping it in a {@code RuntimeException}.
     * <p>
     * For example:
     * 
     * <pre>
     * try {
     *     return someMethodThatCouldThrowAnything();
     * } catch (final IKnowWhatToDoWithThisException e) {
     *     ...
     * } catch (final Exception e) {
     *     throw uncheckedException(e); // propagate without wrapping in a RuntimeException 
     * }
     * </pre>
     * 
     * <b>Warning:</b> This method circumvents Java's exception handling mechanism and can lead to horrible errors when
     * misused. It is explicitly designed for cases where you are sure the caller will handle all possible exceptions thrown
     * by this method. See Unchecked Java's
     * <a target="_blank" href="https://github.com/zleonov/unchecked-java/wiki/Safety-Guide">Safety Guide</a> before using
     * this method. If in doubt <b>do not use</b>.
     * 
     * @param <E> the type of exception to rethrow as unchecked
     * @param e   the specified exception
     * @return this method does not return - the return type is only for your convenience to make the compiler happy
     * @throws E always as an unchecked exception
     */
    @SuppressWarnings("unchecked")
    public static <E extends Exception> RuntimeException uncheckedException(final Exception e) throws E {
        Objects.requireNonNull(e, "e == null");
        throw (E) e;
    }

}
