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

public final class Geometry {
    private Geometry() {
        throw new AssertionError("Do not instantiate class " + Geometry.class);
    }

    public static final char triangle = '△';
    public static final char angle = '∠';
    public static final char rightAngle = '∟';
    public static final char perpendicular = '⊥';
    public static final char parallel = '∥';
    
    public static final char congruent = '≅';
    public static final char similar = '~';

    public static final char degrees = '°';
}
