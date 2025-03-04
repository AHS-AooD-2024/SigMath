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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseListener;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.scilab.forge.jlatexmath.TeXFormula;

import io.github.atholton.sigmath.symbols.Algebra;
import io.github.atholton.sigmath.util.FilterKeyListener;
import io.github.atholton.sigmath.util.FocusOnClickLitener;

/**
 * @deprecated Eventually, hopefully, this will work. But it doesn't right now.
 */
@Deprecated
public class TeXField extends JTextField {
    private static final class SplitterDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offs, str + " ", a);
        }
    }
    
    private static final String SQRT_TOKEN = "__s_q_r_t";
    private static final String MULT_TOKEN = String.valueOf(Algebra.times);

    // global filter can be used by everyone
    private static final FilterKeyListener DISALLOWS = FilterKeyListener.disallows('\\');

    // the width in ens, allows for half of an em in width
    private int widthEns;

    private TeXFormula formula;
    private String rawText;

    // private Caret caret;
    private MouseListener focusOnClick;

    public TeXField(int widthEms) {
        this(widthEms, true);
    }

    public TeXField(int width, boolean isEms) {
        super();
        if(width < 0) throw new IllegalArgumentException("width cannot be negative");
        this.widthEns = width;
        if(isEms) {
            widthEns <<= 1;
        }

        rawText = "";

        // caret = new Caret(0, 1000, getForeground());

        focusOnClick = new FocusOnClickLitener(this);
        addMouseListener(focusOnClick);

        formula = new TeXFormula();
        addKeyListener(DISALLOWS);

        setBorder(new LineBorder(getForeground()));
        super.setOpaque(false);
    }

    public TeXField() {
        this(10);
    }

    @Override
    protected Document createDefaultModel() {
        return new SplitterDocument();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics gg = g.create();
        
        int w = getWidth();
        int h = getHeight();
        
        gg.setColor(getBackground());
        gg.fillRect(0, 0, w, h);
        
        Font font = getFont();
        
        String text;
        
        text = processText();
        
        formula.setLaTeX(text);
        Image image = formula.createBufferedImage(font.getStyle(), font.getSize2D(), getForeground(), getBackground());

        gg.drawImage(image, 0, 0, null);

        gg.dispose();
    }

    private String processText() {
        String text;

        rawText = getText();
        // System.out.println(rawText);

        rawText = rawText.replace("s q r t", SQRT_TOKEN);
        text = rawText.replace(SQRT_TOKEN, "\\sqrt{}");

        setText(rawText);

        return text;
    }

    @Override
    public Dimension getPreferredSize() {
        if(isPreferredSizeSet()) return super.getPreferredSize();

        return getCalculatedSize();
    }

    @Override
    public Dimension getMinimumSize() {
        if(isMinimumSizeSet()) return super.getMinimumSize();

        return getCalculatedSize();
    }

    private Dimension getCalculatedSize() {
        Font font = getFont();
        float fontSize = font.getSize2D();
        Dimension dim = new Dimension();
        Insets insets = getInsets();
        dim.height = font.getSize() + insets.bottom + insets.top;
        dim.width = (int) (fontSize * widthEns) + insets.left + insets.right;
        return dim;
    }

    // public Caret getCaret() {
    //     return caret;
    // }
}
