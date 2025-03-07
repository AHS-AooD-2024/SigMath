package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {
    public static void main(String[] args) {
        final ShuntingYardParser parser = new ShuntingYardParser();
        final String input = "2 * (x + 1)";


        ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();
        parseTree.simplify();
        parseTree.print();
    }
}