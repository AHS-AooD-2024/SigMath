package io.github.atholton.sigmath.frontend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.topics.QuestionGenerator;
import io.github.atholton.sigmath.topics.QuestionTester;
import io.github.atholton.sigmath.topics.Topic;
import io.github.atholton.sigmath.user.UserSettings;

public class ProblemsMenu extends JPanel{
    private DualTeXField inputBox;
    private JLabel problemText, percentageText;
    private JButton submitButton, getHelpButton;
    private QuestionGenerator questionGenerator;
    private Topic t;
    private GridBagLayout layout;
    private GridBagConstraints c;
    private static double initialSize = 0.4;

    private void makeComponent(Component comp,
                               GridBagLayout gridbag,
                               GridBagConstraints c) {
         comp.setFont(new Font("Sans Serif", Font.PLAIN, (int)(UserSettings.get().getFontSize() * initialSize)));
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
        problemText.setFont(new Font("Sans Serif", Font.BOLD, 40));
        problemText.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        problemText.setHorizontalAlignment(SwingConstants.CENTER);
        makeComponent(problemText, layout, c);  

        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = GridBagConstraints.RELATIVE;
        c.insets = new Insets(180, 20, 350, 0);
        percentageText = new JLabel("Progress:\n50%");
        percentageText.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        percentageText.setHorizontalAlignment(SwingConstants.CENTER);
        percentageText.setOpaque(true);
        percentageText.setBackground(new Color(255,229,153));
        makeComponent(percentageText, layout, c);

        c.insets = new Insets(30, 0, 0, 0);
        c.gridheight = 1;
        c.weightx = 6;
        c.gridx = 1;
        c.weighty = 1;
        inputBox = new DualTeXField();
        makeComponent(inputBox, layout, c);

        c.weighty = 0.1;
        c.insets = new Insets(30, 150, 30, 100);
        submitButton = new JButton("Submit"){
            {
                setSize(150, 75);
                setMaximumSize(getSize());
            }
        };
        submitButton.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        submitButton.addActionListener(new Submit());
        submitButton.setBackground(new Color(201, 218, 248));
        submitButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        submitButton.setOpaque(true);
        makeComponent(submitButton, layout, c);

        c.insets = new Insets(180, 0, 350, 20);
        c.weighty = 1;
        c.weightx = 1;
        c.gridheight = 2;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridwidth = GridBagConstraints.REMAINDER;
        getHelpButton = new JButton("Need Help?");
        getHelpButton.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        getHelpButton.setBackground(new Color(213, 166, 189));
        getHelpButton.setOpaque(true);
        getHelpButton.setBorderPainted(false);
        makeComponent(getHelpButton, layout, c);

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
