package io.github.atholton.sigmath.frontend;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.topics.QuestionGenerator;
import io.github.atholton.sigmath.topics.QuestionTester;
import io.github.atholton.sigmath.topics.Topic;
import io.github.atholton.sigmath.user.UserSettings;

public class ProblemsMenu extends Menu {
    private DualTeXField inputBox;
    private JLabel problemText, percentageText;
    private JButton submitButton, getHelpButton;
    private QuestionGenerator questionGenerator;
    private Topic t;
    private GridBagLayout layout;

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
        c.weighty = 0.7;
        problemText = new JLabel("PROBLEM TEXT");
        problemText.setFont(new Font("Sans Serif", Font.BOLD, (int)(40.0 * UserSettings.get().getFontSize() / 100.0)));
        problemText.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        problemText.setHorizontalAlignment(SwingConstants.CENTER);
        makeComponent(problemText);  

        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 0.8;
        c.weighty = 1;
        c.gridx = GridBagConstraints.RELATIVE;
        //c.insets = new Insets(180, 20, 350, 0);
        c.insets = new Insets(100, 100, 100, 100);
        percentageText = new JLabel("Progress:\n50%");
        percentageText.setFont(new Font("Sans Serif", Font.PLAIN, (int)(40.0 * UserSettings.get().getFontSize() / 100.0)));
        percentageText.setHorizontalAlignment(SwingConstants.CENTER);
        percentageText.setOpaque(true);
        percentageText.setBackground(new Color(255,229,153));
        makeComponent(percentageText);

        c.insets = new Insets(30, 0, 0, 0);
        c.gridheight = 1;
        c.weightx = 6;
        c.gridx = 1;
        c.weighty = 1;
        inputBox = new DualTeXField();
        makeComponent(inputBox);

        c.weighty = 0.1;
        c.insets = new Insets(30, 150, 30, 100);
        submitButton = new JButton("Submit"){
            {
                setSize(150, 75);
                setMaximumSize(getSize());
            }
        };
        originalFont.put(submitButton, new Font("Sans Serif", Font.PLAIN, 40));
        submitButton.addActionListener(new Submit());
        submitButton.setBackground(new Color(201, 218, 248));
        submitButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        submitButton.setOpaque(true);
        makeComponent(submitButton);

        //c.insets = new Insets(180, 0, 350, 20);
        c.insets = new Insets(100, 100, 100, 100);
        c.weighty = 1;
        c.weightx = 0.8;
        c.gridheight = 2;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridwidth = GridBagConstraints.REMAINDER;
        getHelpButton = new JButton("Need Help?");
        originalFont.put(getHelpButton, new Font("Sans Serif", Font.PLAIN, 40));
        getHelpButton.setBackground(new Color(213, 166, 189));
        getHelpButton.setOpaque(true);
        getHelpButton.setBorderPainted(false);
        makeComponent(getHelpButton);

        problemText.setText("Derive y = " + questionGenerator.generateQuestion() + " in terms of x.");
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
                answer = t.returnAnswer(answer);
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
