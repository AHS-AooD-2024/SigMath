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

    public ProblemsMenu(Topic t) {
        questionGenerator = new QuestionGenerator(t, 0, 0);
        problemText = new JLabel();

        inputBox = new DualTeXField();
        add(inputBox);
    }
}
