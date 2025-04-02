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

    @Override
    public void set()
    {
        set(this);
    }
    
    private PolynomialDerivative() {
        formulaList.add("c1*x^n1");
        formulaList.add("c1*x^n1 + c2*x^n2");
        formulaList.add("(c1 + c2*x^n1)*x^n2");
        formulaList.add("(x^n1 + c1 * x^n2)/x");
        formulaList.add("x^(1-n1)");
        formulaList.add("c1*x^n1 + c2*x^n2 + c3*x^n3");
    }

    public ASTNode returnAnswer(ASTNode question) {
        Derivative.derive(question);
        return question;
    }
}