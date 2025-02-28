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

        deriveConstants(tempQuestion);
        reformatX(tempQuestion);   

        if (qL == null || qR == null) {
            //Do nothing
        }
        else if (!(tempQuestion.getLeftASTNode().getValue().equals("x") && tempQuestion.getValue().equals("^"))) {
            returnAnswer(tempQuestion.getLeftASTNode());
            returnAnswer(tempQuestion.getRightASTNode());
        }
        else {
            return solve(tempQuestion);
        }
        return tempQuestion;
    }
    
    @Override
    public ASTNode solve(ASTNode question) {
        ASTNode tempQuestion = question;
        ASTNode qL = tempQuestion.getLeftASTNode();
        ASTNode qR = tempQuestion.getRightASTNode();

        qL.multByConstant(Double.parseDouble(qR.getValue()));
        qR.setValue(Double.parseDouble(qR.getValue()) - 1);

        return tempQuestion;
    }

    /**
     * Reformats all x's to be x^1 to be processed by returnAnswer
     * @param question question to process x's in
     */
    public void reformatX(ASTNode question) {
        ASTNode tempQuestion = question;
        ASTNode qL = tempQuestion.getLeftASTNode();
        ASTNode qR = tempQuestion.getRightASTNode(); 
        
        if (qR == null || !(qR.getValue().equals("x"))) {//first condition used to short-circuit
            if (qL != null)
                reformatX(qL);
            if (qR != null)
                reformatX(qR);
        }
        else {
            qR.addExponent(1);
        }
    }

    /**
     * Derives constants which can't be reached by returnAnswer
     * @param question question to derive constants in
     */
    public void deriveConstants(ASTNode question) {
        ASTNode tempQuestion = question;
        ASTNode qL = tempQuestion.getLeftASTNode();
        ASTNode qR = tempQuestion.getRightASTNode(); 
        try {
            Double.parseDouble(qR.getValue());
            if (tempQuestion.getValue().equals("+"))
                qR.setValue(0);
        }
        catch (Exception e) {
            if (qL != null)
                deriveConstants(qL);
            if (qR != null)
                deriveConstants(qR);
        }

        try {
            Double.parseDouble(qL.getValue());
            if (tempQuestion.getValue().equals("+"))
                qL.setValue(0);
        }
        catch (Exception e) {
            if (qL != null)
                deriveConstants(qL);
            if (qR != null)
                deriveConstants(qR);
        }
    }
    
}