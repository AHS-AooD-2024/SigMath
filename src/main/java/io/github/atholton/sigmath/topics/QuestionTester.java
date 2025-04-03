package io.github.atholton.sigmath.topics;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;

public class QuestionTester {
    public static boolean testEquations(ASTNode eq1, ASTNode eq2)
    {
        eq1.simplify();
        eq2.simplify();
        ASTNode.initializeFuncs();
        for (int i = 0; i < 100; i++)
        {
            Map<String, Double> variableNumbers = new HashMap<>();
            double answer1, answer2;
            try {
                answer1 = getVal(eq1, variableNumbers);
            } catch (Exception e) {
                answer1 = Double.MIN_VALUE;
            }
            try {
                answer2 = getVal(eq2, variableNumbers);
            } catch (Exception e) {
                answer2 = Double.MIN_VALUE;
            }
            answer1 = Math.round(answer1 * 100) / 100.0;
            answer2 = Math.round(answer2 * 100) / 100.0;
            if (answer1 != answer2)
            {
                return false;
            }
        }
        return true;
    }
   
    /**
     * Return true if two given equations simplify to the same result.
     * This function uses a copy of each argument, so they will be unaffected
     * by this function. More formally, this function returns true if and only 
     * if the following segment holds true.
     * <pre>
     * a.simplify();
     * b.simplify();
     * equals(a, b);
     * </pre>
     * 
     * @param a The first equation.
     * @param b The second equation.
     * @return {@code true} if both simplify to the same result 
     * (as specified by {@link QuestionTester#equals(ASTNode, ASTNode)}).
     */
    public static boolean equivalent(ASTNode a, ASTNode b) {
        if(a == b) return true;

        ASTNode copyA = ASTNode.copy(a);
        copyA.simplify();
        ASTNode copyB = ASTNode.copy(b);
        copyB.simplify();

        return equals(copyA, copyB);
    }

    public static boolean equals(ASTNode a, ASTNode b) {
        return Objects.equals(a, b);
    }

    public static double getVal(ASTNode equation, Map<String, Double> varNums) throws Exception
    {
        switch(equation.type)
        {
            case FUNCTION:
            return ASTNode.computeFunction.get(equation.getValue()).apply(
                getVal(equation.getLeftASTNode(), varNums)
            );
            case NUMBER:
                return Double.parseDouble(equation.getValue());
            case OPERATOR:
                return ASTNode.computeOperator.get(equation.getValue()).apply(
                    getVal(equation.getLeftASTNode(), varNums), getVal(equation.getRightASTNode(), varNums)
                );
            case VARIABLE:
                varNums.putIfAbsent(equation.getValue(), (Math.random() * 1000));
                return varNums.get(equation.getValue());
            default:
                System.err.println("WHAT TF");
                return 0;
        }
    }
    public static void main(String[] args) {
        String equation = "y^2";
        String equation2 = "y + 2";
        ASTNode eq = ShuntingYardParser.get().convertInfixNotationToAST(equation);
        ASTNode eq2 = ShuntingYardParser.get().convertInfixNotationToAST(equation2);
        System.out.println(testEquations(eq, eq2));
    }
}
