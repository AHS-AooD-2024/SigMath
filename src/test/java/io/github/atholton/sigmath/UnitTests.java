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

import java.util.regex.Pattern;

import org.junit.Test;

import io.github.atholton.sigmath.latex.TeXLabel;
import io.github.atholton.sigmath.symbols.Greek;
import io.github.atholton.sigmath.util.Strings;

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
    }

    @Test
    public void backspaceTest() {
        String tex = "2 \\over \\sqrt{3x}";
        String b1 = Strings.backspaceUntilMatches(tex, " ");
        assertEquals("2 \\over", b1);
        String b2 = Strings.backspaceUntilMatches(b1, " ");
        assertEquals("2", b2);
    }

    @Test
    public void testReplaceWithInsides() {
        // test main function usage
        final String test     = "(zz) (sqrt (asqrt(b) c (ff(G)) )(d)) sqrt(h);";
        final String expected = "(zz) (root {aroot{b} c (ff(G)) }(d)) root{h};";
        String a = Strings.replaceWithInsides(
            test, "sqrt", "root", 
            "(", "{", 
            ")", "}"
        );
        // System.out.println(a);
        // System.out.println(expected);
        assertEquals(expected, a);
        
        // test insertion of parenthesis
        final String testb     = "sqrta b";
        final String expectedb = "root{}a b";
        String b = Strings.replaceWithInsides(
            testb, "sqrt", "root", 
            "(", "{", 
            ")", "}"
        );
        assertEquals(expectedb, b);

    }
}
