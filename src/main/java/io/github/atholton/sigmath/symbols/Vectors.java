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

public final class Vectors {
    private Vectors() {
        throw new AssertionError("Do not instantiate class " + Greek.class);
    }

    public static final char dot = '⋅';
    public static final char cross = '×';
    
    public static final char rightArrow = '→';

    public static final char magnitude = '∥';

    public static final char nabla = '∇';
    public static final char dabba = '∂';
}
