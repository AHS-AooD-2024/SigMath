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

import io.github.atholton.sigmath.latex.TeXComponentProperties;
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
        final String test     = "(zz) (sqrt (asqrt(b) c (ff(G)) )(d)) sqrth;";
        final String expected = "(zz) (root {aroot{b} c (ff(G)) }(d)) root{}h;";
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

    @Test
    public void testReverse() {
        final String test = "abcdef g12345";
        final String expt = "54321g fedcba";
        final String actl = Strings.reversed(test);
        assertEquals(expt, actl);
    }

    @Test
    public void testReplaceBackwardWithInsides() {
        // test main function usage
        final String test     = "(zz) ( (a(b)sqrt c (ff(G)) )sqrt(d)) (h)sqrt;";
        final String expected = "(zz) ( {a{b}root c (ff(G)) }root(d)) {h}root;";
        String a = Strings.replaceBeforeWithInsides(
            test, "sqrt", "root", 
            "(", "{", 
            ")", "}"
        );
        System.out.println(a);
        System.out.println(expected);
        assertEquals(expected, a);
        
        // test insertion of parenthesis
        final String testb     = "asqrt b";
        final String expectedb = "a{}root b";
        String b = Strings.replaceBeforeWithInsides(
            testb, "sqrt", "root", 
            "(", "{", 
            ")", "}"
        );
        assertEquals(expectedb, b);

    }

    @Test
    public void testReplaceBothSides() {
        String test = "(34) / (a) + b";
        String expt = "\\frac{34}  {a} + b";
        String a = Strings.replaceWithInsides(
            test, "/", "/", 
            "(", "{", 
            ")", "}"
        );
        a = Strings.replaceBeforeWithInsides(
            a, "/", "", 
            "(", "\\frac{", 
            ")", "}"
        );

        assertEquals(expt, a);
    }

    @Test
    public void testGrabReplace() {
        String test = "x^4 + x^2+3 y^(6)";
        String expt = "x^{4} + x^{2+3} y^{6}";
        String a = Strings.replaceWithInsides(
            test, "^", "^",
             "(", "{",
             ")", "}",
             " "
            );

        assertEquals(expt, a);
    }

    @Test
    public void courtesyOfAbhay() {
        String test = "a ((x)/(5)) b";
        String expt = "a  (  \\frac{ x }{ 5 }  )  b";
        
        String a = TeXComponentProperties.texify(test);

        assertEquals(expt, a);
    }

    @Test
    public void recursiveFrac() {
        String test = "2/2";
        String expt = "\\frac{2}{2}";

        String a = TeXComponentProperties.texify(test);

        assertEquals(expt, a);

        test = "2/2/2";
        expt = "\\frac{\\frac{2}{2}}{2}";

        a = TeXComponentProperties.texify(test);

        assertEquals(expt, a);

        test = "2/2/2/2";
        expt = "\\frac{\\frac{\\frac{2}{2}}{2}}{2}";

        a = TeXComponentProperties.texify(test);

        assertEquals(expt, a);
    }
}
