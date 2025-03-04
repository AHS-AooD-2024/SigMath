package io.github.atholton.sigmath.equationtree;

public class ShuntingYardDemo {
    public static void main(String[] args) {
        final ShuntingYardParser parser = new ShuntingYardParser();
        final String input = "2 + 4 + 6 + (24 + 6)";


        ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();
        parseTree.simplify();
        parseTree.print();
    }
}