package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {
    public static void main(String[] args) {
        final ShuntingYardParser parser = new ShuntingYardParser();
        final String input = "(x + 1)^3";


        ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.simplify();
        parseTree.printTree();
        parseTree.printInfix();
    }
}