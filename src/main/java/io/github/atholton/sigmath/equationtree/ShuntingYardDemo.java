package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {
    public static void main(String[] args) {
        final ShuntingYardParser parser = ShuntingYardParser.get();
        final String input = "6 * (5 * x ^ 5)";


        ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.printInfix();
        parseTree.simplify();
        parseTree.printInfix();
    }
}