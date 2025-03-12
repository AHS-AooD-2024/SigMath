package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {
    public static void main(String[] args) {
        final ShuntingYardParser parser = new ShuntingYardParser();
        final String input = "x * sin(x) + 3 * sin(x)";


        ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.simplify();
        parseTree.printInfix();
    }
}