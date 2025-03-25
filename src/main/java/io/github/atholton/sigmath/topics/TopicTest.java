package io.github.atholton.sigmath.topics;

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

        final ShuntingYardParser parser = ShuntingYardParser.get();
        final String input = "(x + 2)^2";


        final ASTNode parseTree = parser.convertInfixNotationToAST(input);
        parseTree.printTree();

        ASTNode answer = PolynomialDerivative.returnAnswer(parseTree);
        //answer.simplify();
        answer.printTree();
    }
}
