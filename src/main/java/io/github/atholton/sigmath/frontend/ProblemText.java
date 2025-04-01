package io.github.atholton.sigmath.frontend;

import javax.swing.JLabel;

import io.github.atholton.sigmath.equationtree.ASTNode;

public class ProblemText extends JLabel
{
    private String question;
    public ProblemText()
    {
        super();
    }
    public void setText(String start, String question, String end)
    {
        setText(start + question + end);
        this.question = question;
    }
    public String getQuestion()
    {
        return question;
    }
}
