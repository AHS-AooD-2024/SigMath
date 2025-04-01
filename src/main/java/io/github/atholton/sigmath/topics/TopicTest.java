package io.github.atholton.sigmath.topics;

import java.util.ArrayList;
import java.util.Collection;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.BaseOperator;
import io.github.atholton.sigmath.equationtree.Operator;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;

public class TopicTest {
    public static void main(String[] args) {
        final ShuntingYardParser parser = ShuntingYardParser.get();
        Topic d = PolynomialDerivative.get();
        final String input = "x^2 + 2*x";


        final ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();

        Derivative.derive(parseTree);
        parseTree.print();
    }
}
