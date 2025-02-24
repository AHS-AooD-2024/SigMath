package io.github.atholton.sigmath.latex;

import java.awt.LayoutManager;
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
    private JTextField input;
    private TeXLabel output;

    private DocumentListener labelUpdater;

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

        input.getDocument().addDocumentListener(labelUpdater);
        
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

        str = str.replace("alpha", "\\alpha");

        str = str.replace('*', Algebra.dotTimes);

        return str;
    }
}
