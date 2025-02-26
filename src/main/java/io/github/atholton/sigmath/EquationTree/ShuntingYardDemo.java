package io.github.atholton.sigmath.EquationTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class ShuntingYardDemo {

    /***
     * Evaluates the calculation encoded in the given abstract syntax tree.
     * This method uses recursion to keep things clean. If you needed to
     * evaluate a very deep tree you might need to rewrite this method to use
     * depth first search and evaluate the tree using an explicit stack.
     *
     * @param tree The {@link ASTNode} to evaluate.
     *
     * @return The result of the computation.
     */
    private static double evaluateAST(ASTNode tree) {
        switch(tree.getValue()) {
            case "^":
                return Math.pow(evaluateAST(tree.getLeftASTNode()),
                        evaluateAST(tree.getRightASTNode()));
            case "*":
                return evaluateAST(tree.getLeftASTNode()) * evaluateAST(tree.
                        getRightASTNode());
            case "/":
                return evaluateAST(tree.getLeftASTNode()) / evaluateAST(tree.
                        getRightASTNode());
            case "+":
                return evaluateAST(tree.getLeftASTNode()) + evaluateAST(tree.
                        getRightASTNode());
            case "-":
                return evaluateAST(tree.getLeftASTNode()) - evaluateAST(tree.
                        getRightASTNode());
            default:
                return Double.valueOf(tree.getValue());
        }
    }

    public static void main(String[] args) {
        final ShuntingYardParser parser = new ShuntingYardParser();
        final String input = "3 + 4 * 2 ÷ ( 1 - 5 ) ^ 2";


        final ASTNode parseTree = parser.convertInfixNotationToAST(input);
        //System.out.println(parser.convertInfixNotationToRPN(input));
        parseTree.print();
    }
}