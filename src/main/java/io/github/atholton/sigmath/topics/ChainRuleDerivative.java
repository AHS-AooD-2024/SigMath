package io.github.atholton.sigmath.topics;

import java.util.ArrayList;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;

/**
 * Represents all polynomial derivatives
 * @author Abhay Nagaraj
 */
public class ChainRuleDerivative extends Topic {
    private static ChainRuleDerivative instance;
    private static final long serialVersionUID = 324090982439L;
    public static ChainRuleDerivative get()
    {
        if (instance == null) instance = new ChainRuleDerivative();
        return instance;
    }
    public static void set(ChainRuleDerivative instance)
    {
        ChainRuleDerivative.instance = instance;
    }

    @Override
    public void set()
    {
        set(this);
    }
    
    private ChainRuleDerivative() {
        formulaList.add("(x + c1) ^ n1");
        formulaList.add("(x + c1) ^ n1 + (c2 * x + c3) ^ n2");
        formulaList.add("(c1 * x ^ n1 + c2 * x ^ n2) ^ n3");
    }

    public ASTNode returnAnswer(ASTNode question) {
        Derivative.derive(question);
        return question;
    }
}