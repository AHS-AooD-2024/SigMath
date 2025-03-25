package io.github.atholton.sigmath.topics;

import java.util.ArrayList;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;

/**
 * Represents all polynomial derivatives
 * @author Abhay Nagaraj
 */
public class PolynomialDerivative extends Topic {
    private static PolynomialDerivative instance;
    private static final long serialVersionUID = 324090982439L;
    public static PolynomialDerivative get()
    {
        if (instance == null) instance = new PolynomialDerivative();
        return instance;
    }
    public static void set(PolynomialDerivative instance)
    {
        PolynomialDerivative.instance = instance;
    }
    private PolynomialDerivative() {
        super();
        formulaList.add("a*x^n1");
        formulaList.add("a*x^n1 + b*x^n2");
        formulaList.add("a*x^n1 + b*x^n2 + c*x^n3");
    }

    //TODO: broken lol
    public static ASTNode returnAnswer(ASTNode question) {
        //return question;
        deriveConstants(question);
        reformatX(question);
        deriveAll(question);
        return question;
    }

    /**
     * Reformats all x's to be x^1 to be processed by returnAnswer
     * @param question question to process x's in
     */
    protected static void reformatX(ASTNode question) {
        ASTNode qL = question.getLeftASTNode();
        ASTNode qR = question.getRightASTNode(); 
        
        if (qR == null || !(qR.getValue().equals("x"))) {//first condition used to short-circuit
            if (qL != null)
                reformatX(qL);
            if (qR != null)
                reformatX(qR);
        }
        else {
            if (qR.getValue().equals("x") && !question.getValue().equals("^"))
            qR.addExponent(1);
        }

        if (qL == null || !(qL.getValue().equals("x"))) {
            if (qL != null)
                reformatX(qL);
            if (qR != null)
                reformatX(qR);
        }
        else {
            if (qL.getValue().equals("x") && !question.getValue().equals("^"))
            qL.addExponent(1);
        } 
    }
        
        /*
        if (question.getValue().equals("x")) {
            question.addExponent(1);
        }
        */
    

    /**
     * Derives constants which can't be reached by returnAnswer
     * @param question question to derive constants in
     */
    protected static void deriveConstants(ASTNode question) {
        ASTNode qL = question.getLeftASTNode();
        ASTNode qR = question.getRightASTNode(); 
        try {
            Double.parseDouble(qR.getValue());
            if (question.getValue().equals("+"))
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
            if (question.getValue().equals("+"))
                qL.setValue(0);
        }
        catch (Exception e) {
            if (qL != null)
                deriveConstants(qL);
            if (qR != null)
                deriveConstants(qR);
        }
    }
    
    private static ASTNode deriveAll(ASTNode question) {
        ASTNode qL = question.getLeftASTNode();
        ASTNode qR = question.getRightASTNode();

        if (qL == null || qR == null) {
            //Do nothing
        }
        else if (!(/*question.getLeftASTNode().getValue().equals("x") &&*/ question.getValue().equals("^"))) {
            deriveAll(question.getLeftASTNode());
            deriveAll(question.getRightASTNode());
        }
        else {
            ASTNode copiedQuestion = ASTNode.copy(question);
            ASTNode.replaceNode(question, new ASTNode("*", ASTNode.copy(copiedQuestion.getRightASTNode()), copiedQuestion, Type.OPERATOR));

            //question = new ASTNode("*", ASTNode.copy(copiedQuestion.getRightASTNode()), copiedQuestion, Type.OPERATOR);
            qR = question.getRightASTNode();
            qL = question.getLeftASTNode();
            qR.getRightASTNode().setValue(Double.parseDouble(qR.getRightASTNode().getValue()) - 1);
        }
        return question;
    }
}