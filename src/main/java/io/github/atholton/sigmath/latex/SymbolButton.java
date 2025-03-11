package io.github.atholton.sigmath.latex;

import java.beans.JavaBean;

import javax.swing.JButton;

import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

@JavaBean()
public class SymbolButton extends JButton implements TeXComponentProperties {
    private TeXFormula formula;
    private String texText;

    private int style;

    private float size;

    public SymbolButton(String latex, int style) {
        this(latex, style, TeXIcon.defaultSize);
    }
    
    public SymbolButton(String latex, float size) {
        this(latex, DEFAULT_STYLE, size);
    }

    public SymbolButton(String latex, int style, float size) {
        super();
        texText = latex;
        formula = new TeXFormula(latex);
        setIcon(formula.createTeXIcon(style, size));
        setActionCommand(texText);
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
        setActionCommand(texText);
    }
}
