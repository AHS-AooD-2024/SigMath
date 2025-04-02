package io.github.atholton.sigmath.latex;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter.Highlight;

import org.scilab.forge.jlatexmath.ParseException;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.util.FilterKeyListener;
import io.github.atholton.sigmath.util.LazyLogger;

/**
 * A LaTeX input field that uses two components: a {@link JTextField}
 *  and a {@link TeXLabel}.
 */
public class DualTeXField extends JPanel {
    private static LazyLogger logger = new LazyLogger("TeXInput");

    private JTextField input;
    private TeXLabel output;

    private ASTNode ast;

    private DocumentListener labelUpdater;
    private KeyListener parenthesisSurrounder;
    private static final char[] surrounds = {
        '(', ')', '{', '}', '[', ']', '|', '|'
    };

    private ShuntingYardParser parser = ShuntingYardParser.get();

    private DualTeXField(LayoutManager layout, int columns) {
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
                String text = input.getText();
                ast = parser.convertInfixNotationToAST(text);
                ASTNode.printTree(ast);
                ASTNode.printInfix(ast);
                String texify = TeXComponentProperties.texify(ast);
                try {
                    output.setTeX(texify);
                } catch (ParseException e) {
                    logger.get().logp(Level.SEVERE, getClass().getName(), "#texify0", e, () -> "Illegal LaTeX generated:\n" + texify + "\nFrom input:\n" + text);
                }
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
        
        output = new TeXLabel("Aloha");

        input.addKeyListener(FilterKeyListener.disallows('\\'));

        add(output);
        add(input);
    }

    public DualTeXField(int columns) {
        this(null, columns);
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layout);
    }

    public DualTeXField() {
        this(0);
    }

    @Override
    public Component add(Component comp) {
        if(comp instanceof SymbolButton) {

        }
        return super.add(comp);
    }
    public ASTNode getEquation()
    {
        String text = input.getText();
        return parser.convertInfixNotationToAST(text);
    }
    /**
     * Gets an equation tree root that represents the input. The returned
     * node will be its own copy; you are free to mutate it without affecting the
     * TeXField or future {@link #getAstNode()} calls in any way.
     * 
     * @return An {@link ASTNode} representing the input expression.
     */
    public ASTNode getAstNode() {
        return ASTNode.copy(ast);
    }
    public void clearInput()
    {
        input.setText("");
    }
}
