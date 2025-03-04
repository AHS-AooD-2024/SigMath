package io.github.atholton.sigmath.latex;

import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter.Highlight;

import io.github.atholton.sigmath.util.FilterKeyListener;
import io.github.atholton.sigmath.util.Strings;

/**
 * A LaTeX input field that uses two components: a {@link JTextField}
 *  and a {@link TeXLabel}.
 */
public class DualTeXField extends JPanel {
    private JTextField input;
    private TeXLabel output;

    private DocumentListener labelUpdater;
    private KeyListener parenthesisSurrounder;
    private static final char[] surrounds = {
        '(', ')', '{', '}', '[', ']'
    };

    public DualTeXField(LayoutManager layout, int columns) {
        super(layout);

        input = new JTextField(columns);
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

        parenthesisSurrounder = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char ch = e.getKeyChar();
                int i;
                for(i = 0; i < surrounds.length; i += 2) {
                    if(ch == surrounds[i]) {
                        break;
                    }
                }
                
                // found one
                if(i < surrounds.length) {
                    Highlight[] highlights = input.getHighlighter().getHighlights();

                    char open = surrounds[i];
                    char close = surrounds[i + 1];
                    try {
                        if(highlights.length <= 0) {
                            // bonus, auto place closers
                            input.getDocument().insertString(input.getCaretPosition(), String.valueOf(new char[]{open, close}), null);
                            input.setCaretPosition(input.getCaretPosition() - 1);
                        } else {
                            runSurrounds(e, open, close, highlights);
                        }
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    } finally {
                        // consume so that the highlighted text is not immediately replaced
                        e.consume();
                    }
                }
            }

            private void runSurrounds(KeyEvent e, char open, char close, Highlight[] highlights) throws BadLocationException {
                for(Highlight h : highlights) {
                    int a = h.getStartOffset();
                    int b = h.getEndOffset();
                        // getDocument is pretty inexpensive, so we are not
                        // going to do any lazy caching or anything
                        input.getDocument().insertString(a, String.valueOf(open), null);
                        input.getDocument().insertString(b + 1, String.valueOf(close), null);
                    

                }
            }
        };

        input.getDocument().addDocumentListener(labelUpdater);
        input.addKeyListener(parenthesisSurrounder);
        
        output = new TeXLabel("");

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

        str = str.replace("ln", " \\ln ");
        str = str.replace("log", " \\log ");

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
}
