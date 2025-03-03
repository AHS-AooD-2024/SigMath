package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {

    /**
     * Attempts to math the equation :)
     * @param tree The {@link ASTNode} to evaluate.
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
        final String input = "1 / 2 + 3 ^ (2+1)";


        final ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();
        System.out.println(parseTree.convertToLatex());
    }
}