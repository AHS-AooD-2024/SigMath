package io.github.atholton.sigmath.topics;
import java.util.Random;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;

public class QuestionGenerator {
    private Topic topic;
    private Random random;

    public QuestionGenerator(Topic topic) {
        this.topic = topic;
        this.random = new Random();
    }

    public String generateQuestion() {
        String formula = topic.getFormula((int)topic.getProficiency()); // Retrieve a formula for the selected topic
        String formattedQuestion = replaceVariables(formula); // Replace variables with numbers
        return new String(formattedQuestion);
    }

    private String replaceVariables(String equation) {

        return equation.replace("a", String.valueOf(random.nextInt(20) + 1))  // Replace 'a' with random number
                       .replace("b", String.valueOf(random.nextInt(20) + 1)) // Replace 'b' with random number
                       .replace("c", String.valueOf(random.nextInt(20) + 1))
                       .replace("d", String.valueOf(random.nextInt(20) + 1))
                       .replace("e", String.valueOf(random.nextInt(20) + 1))
                       .replace("n1", String.valueOf(random.nextInt(5) + 1))
                       .replace("n2", String.valueOf(random.nextInt(5) + 1))
                       .replace("n3", String.valueOf(random.nextInt(5) + 1));//this is a horrendous line btw
    }
    public static void main(String[] args) {
        QuestionGenerator gen = new QuestionGenerator(PolynomialDerivative.get());
        String eq = gen.generateQuestion();
        System.out.println(eq);
        ASTNode equation = ShuntingYardParser.get().convertInfixNotationToAST(eq);
        equation.print();
        Derivative.derive(equation);
        equation.print();
        equation.simplify();
        System.out.println();
        equation.print();
    }
        
}

