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

package io.github.atholton.sigmath.latex;

import java.beans.JavaBean;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.SwingContainer;

import org.scilab.forge.jlatexmath.*;

import io.github.atholton.sigmath.util.Strings;

@JavaBean(defaultProperty = "UI", description = "A label that displays LaTeX icons")
@SwingContainer(false)
public class TeXLabel extends JLabel implements TeXComponentProperties {
    // backslashes '\' and whitespace ' '
    public static final Pattern BACKSPACE_STOPS = Pattern.compile("(?:\\s?\\\\)|\\s+");

    private TeXFormula formula;
    private String texText;

    private int style;

    private float size;

    public TeXLabel(String latex) {
        this(latex, DEFAULT_STYLE, TeXIcon.defaultSize);
    }

    public TeXLabel(String latex, int style) {
        this(latex, style, TeXIcon.defaultSize);
    }

    public TeXLabel(String latex, float size) {
        this(latex, DEFAULT_STYLE, size);
    }

    public TeXLabel(String latex, int style, float size) {
        super();
        this.texText = latex;
        this.formula = new TeXFormula(latex);
        this.style = style;
        this.size = size;
        setIcon(formula.createTeXIcon(style, size));
    }

    @Override
    public int getTeXStyle() {
        return style;
    }
    
    @Override
    public void setTeXStyle(int style) {
        int old = this.style;
        this.style = style;

        firePropertyChange("teXStyle", old, style);

        repaint();
    }

    @Override
    public float getTeXSize() {
        return size;
    }

    @Override
    public void setTeXSize(float size) {
        float old = this.size;
        this.size = size;

        firePropertyChange("teXSize", old, size);

        revalidate();
    }

    @Override
    public void setTeX(String latex) {
        String old = texText;
        this.texText = latex;
        formula.setLaTeX(latex);

        firePropertyChange("teX", old, texText);

        setIcon(formula.createTeXIcon(style, size));
    }

    public TeXComponentProperties append(String latex) {
        String old = texText;
        this.texText = texText + latex;
        formula.append(latex);

        firePropertyChange("teX", old, texText);

        setIcon(formula.createTeXIcon(style, size));

        return this;
    }

    public TeXComponentProperties insert(int position, String latex) {
        if(position < 0 || position > texText.length()) {
            throw new IndexOutOfBoundsException(position);
        }

        if(position == texText.length()) {
            // append is less expensive than set, so avoid
            // setting if we can.
            return append(latex);
        }

        setTeX(Strings.insertString(texText, latex, position));
        return this;
    }

    public TeXComponentProperties backspace() {
        setTeX(Strings.backspaceUntilMatches(texText, BACKSPACE_STOPS));
        return this;
    }

    // public TeXFormula getTeX() {
    //     return formula;
    // }
}
