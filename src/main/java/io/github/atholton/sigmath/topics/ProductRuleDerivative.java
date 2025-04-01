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
        super();
        formulaList.add("(a * x) * (b * x ^ n)");//placeholder
    }

    public ASTNode returnAnswer(ASTNode question) {
        Derivative.derive(question);
        return question;
    }
}