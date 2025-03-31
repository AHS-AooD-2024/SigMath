package io.github.atholton.sigmath.topics;

import io.github.atholton.sigmath.equationtree.ASTNode;

public class TrigDerivative extends Topic
{
    private static TrigDerivative instance;
    private static final long serialVersionUID = -4943983890L;

    public TrigDerivative()
    {
        super();
        formulaList.add("sin(x)");
        formulaList.add("cos(x)");
        formulaList.add("tan(x)");
    }
    @Override
    public void set() {
        set(instance);
    }
    public static void set(TrigDerivative instance)
    {
        TrigDerivative.instance = instance;
    }
    public static void get()
    {
        if (instance == null) instance = new TrigDerivative();
    }

    @Override
    public ASTNode returnAnswer(ASTNode question) {
        Derivative.derive(question);
        return question;
    }
    
}
