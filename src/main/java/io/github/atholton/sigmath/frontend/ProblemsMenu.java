package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.topics.QuestionGenerator;
import io.github.atholton.sigmath.topics.QuestionTester;
import io.github.atholton.sigmath.topics.Topic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProblemsMenu extends JPanel{
    private DualTeXField inputBox;
    private JLabel problemText, percentageText;
    private JButton submitButton, getHelpButton;
    private QuestionGenerator questionGenerator;
    private Topic t;
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
        this.t = t;

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
        percentageText.setOpaque(true);
        percentageText.setBackground(new Color(255, 0, 255));
        makeComponent(percentageText, layout, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridheight = 1;
        c.weightx = 6;
        c.gridx = 1;
        c.weighty = 1;
        inputBox = new DualTeXField();
        makeComponent(inputBox, layout, c);

        c.weighty = 0.2;
        c.insets = new Insets(30, 300, 30, 100);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new Submit());
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
    class Submit implements ActionListener
    {
        private int numGuesses = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            String userAnswer = inputBox.input.getText();
            boolean right = false;
            try
            {
                ASTNode userEquation = ShuntingYardParser.get().convertInfixNotationToAST(userAnswer);
                ASTNode answer = ShuntingYardParser.get().convertInfixNotationToAST(problemText.getText());
                t.returnAnswer(answer);
                right = QuestionTester.testEquations(userEquation, answer);
            }
            catch(Exception exception)
            {
                System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\nMY LEGGGGGGGGGGGGG");
            }
            finally
            {
                JOptionPane.showMessageDialog(Application.get(), right ? "RIGHT ANSWER":"WRONG ANSWER");
                if (right || numGuesses > 3)
                {
                    if (right) t.setProficiency(t.getProficiency() + 0.1);

                    problemText.setText(questionGenerator.generateQuestion());
                    numGuesses = 0;
                }
                else
                {
                    numGuesses++;
                }
            }
        }

    }
}
