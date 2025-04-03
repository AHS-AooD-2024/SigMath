package io.github.atholton.sigmath.frontend;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.latex.HintTextField;
import io.github.atholton.sigmath.topics.QuestionGenerator;
import io.github.atholton.sigmath.topics.QuestionTester;
import io.github.atholton.sigmath.topics.Topic;

public class ProblemsMenu extends Menu {
    private DualTeXField inputBox;
    private ProgressText percentageText;
    // private ProblemText problemText;
    private ProblemTeX problemText;
    private JButton submitButton, getHelpButton;
    private URI uri;
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
        problemText = new ProblemTeX();
        originalFont.put(problemText, new Font("Sans Serif", Font.BOLD, 40));
        problemText.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // problemText.setHorizontalAlignment(SwingConstants.CENTER);
        makeComponent(problemText);  

        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 0.8;
        c.weighty = 1;
        c.gridx = GridBagConstraints.RELATIVE;
        //c.insets = new Insets(180, 20, 350, 0);
        c.insets = new Insets(100, 100, 100, 100);
        percentageText = new ProgressText(t);
        originalFont.put(percentageText, new Font("Sans Serif", Font.PLAIN, 40));
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
        //uri = new URI("http://www.youtube.com/watch?v=qzW6mgfY5X4");
        
        String myURL = "https://www.youtube.com/playlist?list=PL_oayj0GNI_ryLSlaGd-ug5qACQvAXD43";
        try {
            URL url = new URL(myURL);
            String nullFragment = null;
            this.uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), nullFragment);
            System.out.println("URI " + this.uri.toString() + " is OK");
        } catch (MalformedURLException e) {
            System.out.println("URL " + myURL + " is a malformed URL");
        } catch (URISyntaxException e) {
            System.out.println("URI " + myURL + " is a malformed URL");
        }

        getHelpButton.addActionListener(new Help());
        getHelpButton.setOpaque(true);
        getHelpButton.setBorderPainted(false);
        makeComponent(getHelpButton);

        problemText.setPretext("Derive ");
        problemText.setPosttext(" in terms of x.");
        problemText.setQuestion(questionGenerator.generateQuestion());
        updateFontSizes();
    }

    class Submit implements ActionListener
    {
        private int numGuesses = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            ASTNode userAnswer = inputBox.getAstNode();
            ASTNode answer = ShuntingYardParser.get().convertInfixNotationToAST(problemText.getQuestion());
            boolean right = false;
            try
            {
                answer = t.returnAnswer(answer);
                right = QuestionTester.testEquations(userAnswer, answer);
            }
            catch(Exception exception)
            {
                System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\nMY LEGGGGGGGGGGGGG");
            }
            finally
            {
                JOptionPane.showMessageDialog(Application.get(), right ? "RIGHT ANSWER":"WRONG ANSWER: " + (3 - numGuesses) + " CHANCES LEFT.");
                if (right || numGuesses >= 3)
                {                    
                    if (right && t.getProficiency() <= t.getFormulaList().size() - 1) t.setProficiency(t.getProficiency() + 0.5);
                    else JOptionPane.showMessageDialog(Application.get(), "Right Answer is: " + answer.toInfix());
                    percentageText.updateText();
                    problemText.setQuestion(questionGenerator.generateQuestion());
                    numGuesses = 0;
                    inputBox.clearInput();
                }
                else
                {
                    numGuesses++;
                }
            }
        }

    }

    class Help implements ActionListener
    {
        @Override
      
        public void actionPerformed(ActionEvent e){
        open(uri);
        }   
    

    }

    private static void open(URI uri) 
    {
        if (Desktop.isDesktopSupported()) 
        {
            try 
            {
                Desktop.getDesktop().browse(uri);
            }
            catch (IOException e) 
            {
                System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\nMY A**SSSSSS (swwy)");
            }
        }
        else
        { /* TODO: error handling [or not (seeyuhh)] */ }
    }
}


