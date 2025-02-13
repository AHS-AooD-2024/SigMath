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

package io.github.atholton.sigmath;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTests {
    
    /**
     * Assert that uppercase alpha is not the same thing as uppercase A.
     * They look the same in most fonts, but they might not always.
     * If this passes, it is likely that the other Greek letters that
     * look like Anglic/Latin letters are also different code-points  
     */
    @Test
    public void alphaIsNotA() {
        assertFalse(Greek.ALPHA == 'A');
        System.out.println(Greek.alphabet);
    }
}
