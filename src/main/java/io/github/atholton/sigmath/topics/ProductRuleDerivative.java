package io.github.atholton.sigmath.topics;

import java.util.ArrayList;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;

/**
 * Represents all polynomial derivatives
 * @author Abhay Nagaraj
 */
public class ProductRuleDerivative extends Topic {
    private static ProductRuleDerivative instance;
    private static final long serialVersionUID = 324090982439L;
    public static ProductRuleDerivative get()
    {
        if (instance == null) instance = new ProductRuleDerivative();
        return instance;
    }
    public static void set(ProductRuleDerivative instance)
    {
        ProductRuleDerivative.instance = instance;
    }
    @Override
    public void set()
    {
        set(this);
    }
    private ProductRuleDerivative() {
        formulaList.add("(c1 * x) * (c2 * x)");
        formulaList.add("(c1 * x) * (c2 * x ^ n1)");
        formulaList.add("(c1 * x ^ n1) * (c2 * x ^ n2)");
    }

    public ASTNode returnAnswer(ASTNode question) {
        Derivative.derive(question);
        return question;
    }
}