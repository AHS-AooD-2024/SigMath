package io.github.atholton.sigmath.latex;

import java.awt.LayoutManager;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import io.github.atholton.sigmath.symbols.Algebra;
import io.github.atholton.sigmath.util.FilterKeyListener;
import io.github.atholton.sigmath.util.Strings;

/**
 * A LaTeX input field that uses two components: a {@link JTextField}
 *  and a {@link TeXLabel}.
 */
public class DualTeXField extends JPanel {
    public JTextField input;
    private TeXLabel output;

    private DocumentListener labelUpdater;

    public DualTeXField(LayoutManager layout, int columns) {
        super(layout);

        input = new HintTextField("Type your answer here");
        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        input.setFont(font1);


        labelUpdater = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                texify0();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                texify0();
            }

            private void texify0() {
                String texify = texify(input.getText());
                output.setTeX(texify);
                System.out.println(texify);
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        input.getDocument().addDocumentListener(labelUpdater);
        
        output = new TeXLabel("Aloha");

        input.addKeyListener(FilterKeyListener.disallows('\\'));

        add(output);
        add(input);
    }

    public DualTeXField() {
        this(null, 0);
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layout);
    }

    private static String texify(String str) {
        str = Strings.replaceWithInsides(
            str, "sqrt", "\\sqrt",
            "(", "{",
            ")", "}"
            );


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

        str = str.replace("inf", " \\inf ");

        str = str.replace("sin", " \\sin ");
        str = str.replace("cos", " \\cos ");
        str = str.replace("tan", " \\tan ");

        str = str.replace("sec", " \\sec ");
        str = str.replace("csc", " \\csc ");
        str = str.replace("cot", " \\cot ");

        str = str.replace("*", " \\cdot ");

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
        
        str = Strings.replaceWithInsides(
            str, "^", "^", 
            "(", "{", 
            ")", "}",
            " "
        );


        return str;
    }
}
