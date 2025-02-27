package io.github.atholton.sigmath.Topics;

import java.util.ArrayList;
import java.util.Collection;

import io.github.atholton.sigmath.EquationTree.ASTNode;
import io.github.atholton.sigmath.EquationTree.BaseOperator;
import io.github.atholton.sigmath.EquationTree.Operator;
import io.github.atholton.sigmath.EquationTree.ShuntingYardParser;

public class TopicTest {
    public static void main(String[] args) {
        // Define our basic operators for arithmetic.
        final Collection<Operator> operators = new ArrayList<>();
        operators.add(new BaseOperator("^", true, 4));
        operators.add(new BaseOperator("*", false, 3));
        operators.add(new BaseOperator("/", false, 3));
        operators.add(new BaseOperator("+", false, 2));
        operators.add(new BaseOperator("-", false, 2));

        final ShuntingYardParser parser = new ShuntingYardParser(operators);
        Topic d = new PolynomialDerivative();
        final String input = "2 * x ^ 2 + 3";


        final ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.print();

        ASTNode answer = d.returnAnswer(parseTree);
        answer.print();
    }
}
