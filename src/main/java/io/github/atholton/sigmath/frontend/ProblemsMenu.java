package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.QuestionGenerator;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.topics.Topic;

import java.awt.*;
import java.util.ArrayList;

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
     }

    public ProblemsMenu(Topic t) {
        super(new GridBagLayout());

        layout = (GridBagLayout)this.getLayout();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        problemText = new JLabel("SAMPLE TEXT");
        inputBox = new DualTeXField();
        percentageText = new JLabel("0%");
        submitButton = new JButton("Submit");
        questionGenerator = new QuestionGenerator(t, 0, 0);

        //problemText at top, percentage on left, submit and input box in the middle, get help on right.
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 2;
        makeComponent(problemText, layout, c);

        c.gridheight = 1;
        c.weightx = 1;
        makeComponent(percentageText, layout, c);

        c.weightx = 2;
        makeComponent(inputBox, layout, c);

        c.weightx = 1;
        makeComponent(submitButton, layout, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        makeComponent(getHelpButton, layout, c);
        
        
        add(inputBox);
    }
}
