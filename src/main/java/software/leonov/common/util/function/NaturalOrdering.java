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

import java.io.Serializable;
import java.util.Objects;

class NaturalOrdering<T> implements CheckedComparator<Comparable<Object>>, Serializable {

    private static final long serialVersionUID = 6308659430944415484L;

    private static final NaturalOrdering<Comparable<Object>> INSTANCE = new NaturalOrdering<>();

    static NaturalOrdering<Comparable<Object>> getInstance() {
        return INSTANCE;
    }

    private NaturalOrdering() {
    }

    @Override
    public int compare(final Comparable<Object> left, final Comparable<Object> right) throws Exception {
        Objects.requireNonNull(left, "left == null");
        Objects.requireNonNull(right, "right == null");
        return left.compareTo(right);
    }

    // singleton
    private Object readResolve() {
        return NaturalOrdering.getInstance();
    }

    @Override
    public CheckedComparator<Comparable<Object>> reversed() {
        return ReversedNaturalOrdering.getInstance();
    }

}