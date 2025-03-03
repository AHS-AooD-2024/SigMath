package io.github.atholton.sigmath.Topics;

import java.util.ArrayList;
import java.util.Collection;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.BaseOperator;
import io.github.atholton.sigmath.equationtree.Operator;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;

public class TopicTest {
    public static void main(String[] args) {
        // Define our basic operators for arithmetic.
        final Collection<Operator> operators = new ArrayList<>();
        operators.add(new BaseOperator("^", true, 4));
        operators.add(new BaseOperator("*", false, 3));
        operators.add(new BaseOperator("/", false, 3));
        operators.add(new BaseOperator("+", false, 2));
        operators.add(new BaseOperator("-", false, 2));

        final ShuntingYardParser parser = new ShuntingYardParser();
        Topic d = new PolynomialDerivative();
        final String input = "4 * x ^ (0.5)";


        final ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();

        ASTNode answer = d.returnAnswer(parseTree);
        answer.print();
    }
}
