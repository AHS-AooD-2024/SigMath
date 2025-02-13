/**
 * Copyright 2025 Sigmath Creators and Contributers 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.atholton.sigmath.symbols;

public final class Algebra {
    private Algebra() {
        throw new AssertionError("Do not instantiate class " + Algebra.class);
    }

    public static final char infinity = '∞';

    public static final char plus   = '+';
    public static final char minus  = '-';
    public static final char times  = '×';
    public static final char divide = '÷';

    public static final char equals = '=';
    public static final char approximately = '≈';
    public static final char notEquals = '≠';
    public static final char proportional = '∝';

    public static final char implies = '⇒';
    public static final char iff = '⇔';

    public static final char gte = '≥';
    public static final char gt  = '>';
    public static final char lte = '≤';
    public static final char lt  = '<';

    public static final char sqrt = '√';
}
