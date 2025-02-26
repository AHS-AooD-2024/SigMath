package io.github.atholton.sigmath.Topics;

import java.util.ArrayList;

import io.github.atholton.sigmath.EquationTree.ASTNode;

/**
 * Represents all polynomial derivatives
 * @author Abhay Nagaraj
 */
public class PolynomialDerivative extends Topic{
    public PolynomialDerivative() {
        super();
        formulaList.add("a*x^n");
        formulaList.add("a*x^n + b*x^n-1");
        formulaList.add("a*x^n + b*x^n-1 + c*x^n-2");
    }

    @Override
    public String getFormula(int index) {
        return formulaList.get(index);
    }

    @Override
    public double getProficiency() {
        return proficiencyLevel;
    }

    @Override
    public ASTNode returnAnswer(ASTNode question) {
        ASTNode tempQuestion = question;
        ASTNode qL = tempQuestion.getLeftASTNode();
        ASTNode qR = tempQuestion.getRightASTNode();

        reformat(question);

        if (qL == null || qR == null) {
            //Do nothing
        }
        else if (!(question.getLeftASTNode().getValue().equals("x") && question.getValue().equals("^"))) {
            returnAnswer(question.getLeftASTNode());
            returnAnswer(question.getRightASTNode());
        }
        else {
            return solve(question);
        }
        return tempQuestion;
    }
    
    @Override
    public ASTNode solve(ASTNode question) {//ASK NATHAN: how would I modify the tree to multiply the x value by a constant?
        ASTNode tempQuestion = question;
        ASTNode qL = tempQuestion.getLeftASTNode();
        ASTNode qR = tempQuestion.getRightASTNode();

        qL.multByConstant(Double.parseDouble(qR.getValue()));
        qR.setValue(Double.parseDouble(qR.getValue()) - 1);

        return tempQuestion;
    }

    public void reformat(ASTNode question) {
        ASTNode tempQuestion = question;
        ASTNode qL = tempQuestion.getLeftASTNode();
        ASTNode qR = tempQuestion.getRightASTNode(); 
        
        if (qR == null || !(qR.getValue().equals("x"))) {
            if (qL != null)
                reformat(qL);
            if (qR != null)
                reformat(qR);
        }
        else {
            qR.addExponent(1);
        }
    }
    
}