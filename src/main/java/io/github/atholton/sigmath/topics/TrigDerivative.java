package io.github.atholton.sigmath.topics;

import io.github.atholton.sigmath.equationtree.ASTNode;

public class TrigDerivative extends Topic
{
    private static TrigDerivative instance;
    private static final long serialVersionUID = -4943983890L;

    public TrigDerivative()
    {
        formulaList.add("sin(c1 * x)");
        formulaList.add("cos(c1 * x)");
        formulaList.add("tan(c1 * x)");
        formulaList.add("sin(c1 * x) * cos(c2 * x)");
        formulaList.add("tan(c1 * x) * sin(c2 * x)");
    }
    @Override
    public void set() {
        set(instance);
    }
    public static void set(TrigDerivative instance)
    {
        TrigDerivative.instance = instance;
    }
    public static TrigDerivative get()
    {
        if (instance == null) instance = new TrigDerivative();
        return instance;
    }

    @Override
    public ASTNode returnAnswer(ASTNode question) {
        Derivative.derive(question);
        return question;
    }
    
}
