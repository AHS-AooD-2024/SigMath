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

        questionGenerator = new QuestionGenerator(t);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        //problemText at top, percentage on left, submit and input box in the middle, get help on right.
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 0.6;
        problemText = new JLabel("SAMPLE TEXT");
        problemText.setFont(new Font("Sans Serif", Font.BOLD, 60));
        problemText.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        problemText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        makeComponent(problemText, layout, c);  

        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(100, 20, 100, 20);
        percentageText = new JLabel("0%");
        makeComponent(percentageText, layout, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridheight = 1;
        c.weightx = 6;
        c.gridx = 1;
        c.weighty = 1;
        inputBox = new DualTeXField();
        makeComponent(inputBox, layout, c);

        c.weighty = 0.5;
        c.insets = new Insets(30, 100, 30, 100);
        submitButton = new JButton("Submit");
        makeComponent(submitButton, layout, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.weighty = 1;
        c.weightx = 1;
        c.gridheight = 2;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridwidth = GridBagConstraints.REMAINDER;
        getHelpButton = new JButton("HELP");
        makeComponent(getHelpButton, layout, c);

        problemText.setText(questionGenerator.generateQuestion());
    }
}
