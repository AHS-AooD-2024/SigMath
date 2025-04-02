package io.github.atholton.sigmath.frontend;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.scilab.forge.jlatexmath.TeXFont;
import org.scilab.forge.jlatexmath.TeXFormula;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.latex.TeXComponentProperties;
import io.github.atholton.sigmath.latex.TeXLabel;

public class ProblemTeX extends JPanel implements TeXComponentProperties, SwingConstants {
    private TeXLabel teXLabel;
    private JLabel pretext;
    private JLabel posttext;

    private ASTNode question;

    public ProblemTeX(int teXStyle, float teXSize) {
        super();

        teXLabel = new TeXLabel("", teXStyle, teXSize);
        teXLabel.setHorizontalAlignment(CENTER);

        pretext = new JLabel();
        
        posttext = new JLabel();

        // should check for is right to left
        pretext.setHorizontalAlignment(RIGHT);
        posttext.setHorizontalAlignment(LEFT);

        add(pretext);
        add(teXLabel);
        add(posttext);
    }

    public ProblemTeX() {
        this(TeXComponentProperties.DEFAULT_STYLE, 12.0f);
    }

    public String getPretext() {
        return pretext.getText();
    }
    
    public void setPretext(String pretext) {
        this.pretext.setText(pretext);;
    }
    
    public String getPosttext() {
        return posttext.getText();
    }
    
    public void setPosttext(String posttext) {
        this.posttext.setText(posttext);;
    }

    public void setQuestion(String question) {
        setQuestion0(ShuntingYardParser.get().convertInfixNotationToAST(question));
    }

    public void setQuestion(ASTNode ast) {
        setQuestion0(ASTNode.copy(ast));
    }

    private void setQuestion0(ASTNode ast) {
        this.question = ast;
        if(ast == null) {
            setTeX("null");
        } else {
            setTeX(ast.convertToLatex());
        }
    }

    public ASTNode getQuestionAST() {
        return ASTNode.copy(question);
    }

    public String getQuestion() {
        return question.toInfix();
    }

    @Override
    public int getTeXStyle() {
        return teXLabel.getTeXStyle();
    }

    @Override
    public void setTeXStyle(int style) {
        teXLabel.setTeXStyle(style);
    }

    @Override
    public float getTeXSize() {
        return teXLabel.getTeXSize();
    }

    @Override
    public void setTeXSize(float size) {
        teXLabel.setTeXSize(size);
    }

    @Override
    public void setTeX(String latex) {
        teXLabel.setTeX(latex);
    }

    @Override
    public void setFont(Font font) {
        if(teXLabel != null) {
            teXLabel.setFont(font);
            teXLabel.setTeXSize(font.getSize2D());
        }
        if(pretext != null) pretext.setFont(font);
        if(posttext != null) posttext.setFont(font);
        super.setFont(font);
    }
}
