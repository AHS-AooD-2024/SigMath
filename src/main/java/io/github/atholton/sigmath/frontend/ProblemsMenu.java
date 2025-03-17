package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.QuestionGenerator;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.topics.Topic;

import java.awt.*;

public class ProblemsMenu extends JPanel{
    private DualTeXField inputBox;
    private JLabel problemText, percentageText;
    private JButton submitButton, getHelpButton;
    private QuestionGenerator questionGenerator;
    private GridBagLayout layout;
    private GridBagConstraints c;

    private void makeComponent(Component comp,
                               GridBagLayout gridbag,
                               GridBagConstraints c) {
         gridbag.setConstraints(comp, c);
         add(comp);
         revalidate();
     }

    public ProblemsMenu(Topic t) {
        super();
        layout = new GridBagLayout();
        setLayout(layout);

        questionGenerator = new QuestionGenerator(t, 0, 0);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        //problemText at top, percentage on left, submit and input box in the middle, get help on right.
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 2;
        problemText = new JLabel("SAMPLE TEXT");
        makeComponent(problemText, layout, c);

        c.gridheight = 1;
        c.weightx = 1;
        percentageText = new JLabel("0%");
        makeComponent(percentageText, layout, c);

        c.weightx = 2;
        inputBox = new DualTeXField();
        makeComponent(inputBox, layout, c);

        c.weightx = 1;
        submitButton = new JButton("Submit");
        makeComponent(submitButton, layout, c);

        c.gridwidth = GridBagConstraints.REMAINDER;
        getHelpButton = new JButton("HELP");
        makeComponent(getHelpButton, layout, c);
    }
}
