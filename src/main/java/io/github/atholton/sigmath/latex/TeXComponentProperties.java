package io.github.atholton.sigmath.latex;

import java.beans.BeanProperty;

import org.scilab.forge.jlatexmath.TeXConstants;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.util.Strings;

public interface TeXComponentProperties {

    int DEFAULT_STYLE = TeXConstants.STYLE_DISPLAY;

    /**
     * Gets the style of the displayed LaTeX.
     * 
     * @return An int representing the style of the displayed LaTeX.
     * 
     * @see TeXConstants#STYLE_DISPLAY
     * @see TeXConstants#STYLE_TEXT
     * @see TeXConstants#STYLE_SCRIPT
     * @see TeXConstants#STYLE_SCRIPT_SCRIPT
     */
    int getTeXStyle();
    
    /**
     * Sets the style of the displayed LaTeX.
     * 
     * @param style The style to display the LaTeX.
     * 
     * @see TeXConstants#STYLE_DISPLAY
     * @see TeXConstants#STYLE_TEXT
     * @see TeXConstants#STYLE_SCRIPT
     * @see TeXConstants#STYLE_SCRIPT_SCRIPT
     */
    @BeanProperty(preferred = true, bound = true, visualUpdate = true, description 
        = "The TeX style"
    )
    void setTeXStyle(int style);

    /**
     * Gets the size to display the LaTeX.
     * 
     * @return The font point size of the displayed LaTeX.
     */
    float getTeXSize();

    /**
     * Sets the size of the diplayed LaTeX.
     * 
     * @param size The desired font point size to display the LaTeX.
     */
    @BeanProperty(preferred = true, bound = true, visualUpdate = true, description 
        = "The size of the TeX Icon" 
    )
    void setTeXSize(float size);

    /**
     * Sets this component to display a new LaTeX formula.
     * 
     * @param latex The LaTeX formula to display.
     */
    @BeanProperty(preferred = true, bound = true, visualUpdate = true, description
        = "The LaTeX formula the is displayed"
    )
    void setTeX(String latex);

    /**
     * Convert a mathematical string to a LaTeX string to display an expression.
     * 
     * @param str The expression to display
     * @return A string of valid LaTeX that displays the expression.
     */
    static String texify(String str) {
        // This is kind of a hack, but it works REALLY well, so this
        // is the way we do it now. The alternative would be to allow
        // out replace function to grab from a pattern of characters
        // and yeah no I am not doing that.
        // The space padding gives the grab function something to stop 
        // at for parenthesis
        str = str.replace("(", " ( ");
        str = str.replace(")", " ) ");
        str = str.replace("[", " [ ");
        str = str.replace("]", " ] ");
        str = str.replace("{", " \\{ ");
        str = str.replace("}", " \\} ");
        // pipes are kinda parens for abs 
        str = str.replace("|", " | "); 

        str = str.replace(") /", ")/");
        str = str.replace("/ (", "/(");

        // NOTE: ***ALL*** simple replaces MUST have spaces to pad the 
        // replacement, as otherwise the tex commands can merge with
        // other text and get really mess.
            
        // unfortunately, some letters contain "eta" inside of them,
        // so to get around this we run the replace here and test
        // the other replaces with the backslash
        str = str.replace("eta", " \\eta ");
        
        str = str.replace("alpha", " \\alpha ");
        str = str.replace("Alpha", " \\Alpha ");
        str = str.replace("b \\eta ", " \\beta ");
        str = str.replace("B \\eta ", " \\beta ");
        str = str.replace("gamma", " \\gamma ");
        str = str.replace("Gamma", " \\Gamma ");
        str = str.replace("delta", " \\delta ");
        str = str.replace("Delta", " \\Delta ");
        str = str.replace("epsilon", " \\epsilon ");
        str = str.replace("epsilon", " \\Epsilon ");
        str = str.replace("z \\eta ", " \\zeta ");
        str = str.replace("Z \\eta ", " \\Zeta ");
        str = str.replace("th \\eta ", " \\theta ");
        str = str.replace("Th \\eta ", " \\Theta ");
        str = str.replace("Eta", " \\Eta ");
        str = str.replace("iota", " \\iota ");
        str = str.replace("Iota", " \\Iota ");
        str = str.replace("kappa", " \\kappa ");
        str = str.replace("Kappa", " \\Kappa ");
        str = str.replace("lambda", " \\lambda ");
        str = str.replace("Lambda", " \\Lambda ");
        str = str.replace("mu", " \\mu ");
        str = str.replace("Mu", " \\Mu ");
        str = str.replace("nu", " \\nu ");
        str = str.replace("Nu", " \\Nu ");
        str = str.replace("xi", " \\xu ");
        str = str.replace("Xi", " \\Xu ");
        str = str.replace("omicron", " \\omicron ");
        str = str.replace("Omicron", " \\Omicron ");
        str = str.replace("pi", " \\pi ");
        str = str.replace("Pi", " \\Pi ");
        str = str.replace("rho", " \\rho ");
        str = str.replace("Rho", " \\Rho ");
        str = str.replace("sigma", " \\sigma ");
        str = str.replace("Sigma", " \\Sigma ");
        str = str.replace("tau", " \\tau ");
        str = str.replace("Tau", " \\Tau ");
        str = str.replace("upsilon", " \\upsilon ");
        str = str.replace("Upsilon", " \\Upsilon ");
        str = str.replace("phi", " \\phi ");
        str = str.replace("Phi", " \\Phi ");
        str = str.replace("chi", " \\chi ");
        str = str.replace("Chi", " \\Chi ");
        str = str.replace("psi", " \\psi ");
        str = str.replace("Psi", " \\Psi ");
        str = str.replace("omega", " \\omega ");
        str = str.replace("Omega", " \\Omega ");

        str = str.replace("nabla", " \\nabla ");
        str = str.replace("dabba", " \\partial ");
        str = str.replace("partial", " \\partial ");
    
        str = str.replace("inf", " \\infty ");
    
        str = str.replace("sin", " \\sin ");
        str = str.replace("cos", " \\cos ");
        str = str.replace("tan", " \\tan ");
        
        str = str.replace("sec", " \\sec ");
        str = str.replace("csc", " \\csc ");
        str = str.replace("cot", " \\cot ");
        
        str = str.replace("arcsin", " \\arcsin ");
        str = str.replace("arccos", " \\arccos ");
        str = str.replace("arctan", " \\arctan ");
        
        str = str.replace("arcsec", " \\arcsec ");
        str = str.replace("arccsc", " \\arccsc ");
        str = str.replace("arccot", " \\arccot ");

        str = str.replace("sinh", " \\sinh ");
        str = str.replace("cosh", " \\cosh ");
        str = str.replace("tanh", " \\tanh ");
        str = str.replace("coth", " \\coth ");
        
        str = str.replace("ln", " \\ln ");
        str = str.replace("log", " \\log ");
    
        str = str.replace("*", " \\times ");
        str = str.replace("dot", " \\cdot ");
    
        str = str.replace(" +- ", " \\pm ");
        str = str.replace("<=", " \\leq ");
        str = str.replace(">=", " \\geq ");
        str = str.replace(" !=", " \\neq ");
        str = str.replace("=/=", " \\neq ");

        str = str.replace("...", " \\ldots ");

        str = Strings.replaceWithInsides(
            str, "sqrt", "\\sqrt",
            "(", "{",
            ")", "}"
            );
        str = Strings.replaceWithInsides(
            str, "cbrt", "\\sqrt[3]",
            "(", "{",
            ")", "}"
            );

        str = Strings.replaceWithInsides(
            str, "ceil", "",
            "(", "\\lceil",
            ")", "\\rceil"
            );
        str = Strings.replaceWithInsides(
            str, "floor", "",
            "(", "\\lfloor",
            ")", "\\rfloor"
            );

        str = Strings.replaceWithInsides(
            str, "abs", "",
            "(", "|",
            ")", "|"
            );
    
    
        str = Strings.replaceWithInsides(
            str, "/", "/", 
            "(", "{", 
            ")", "}",
            " "
        );
        str = Strings.replaceBeforeWithInsides(
            str, "/", "", 
            "(", "\\frac{", 
            ")", "}",
            " "
        );
        
    
        // caret for superscript, underscore for subscript
        str = Strings.replaceWithInsides(
            str, "^", "^", 
            "(", "{", 
            ")", "}",
            " "
        );
    
        str = Strings.replaceWithInsides(
            str, "_", "_", 
            "(", "{", 
            ")", "}",
            " "
        );
    
    
        return str;
    }

    // FIXME: the ast idea of texifying has some...interesting interperetations.
    // some highlights include
    // "a + " => "(+a)"
    // "a/b - c" => "\frac{a}{-1} \times c"
    static String texify(ASTNode root) {
        return root.convertToLatex(); // well that was easy lol
    }
}