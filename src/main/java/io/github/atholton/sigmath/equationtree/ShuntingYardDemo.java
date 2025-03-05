package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {
    public static void main(String[] args) {
        final ShuntingYardParser parser = new ShuntingYardParser();
        final String input = "(x + 1) * 2";


        ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();
        parseTree.simplify();
        parseTree.print();
    }
}