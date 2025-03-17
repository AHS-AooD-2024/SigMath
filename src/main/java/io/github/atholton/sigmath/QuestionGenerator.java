package io.github.atholton.sigmath;
import java.util.Random;

import io.github.atholton.sigmath.topics.Topic;
import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.topics.PolynomialDerivative;

public class QuestionGenerator {
    private Topic topic;
    private Random random;
    private int index;

    public QuestionGenerator(Topic topic, int index) {
        this.topic = topic;
        this.index = index;
        this.random = new Random();
    }

    public String generateQuestion() {
        String formula = topic.getFormula(index); // Retrieve a formula for the selected topic
        String formattedQuestion = replaceVariables(formula); // Replace variables with numbers
        return new String(formattedQuestion);
    }

    private String replaceVariables(String equation) {
        return equation.replaceAll("a", String.valueOf(random.nextInt(20) + 1))  // Replace 'a' with random number
                       .replaceAll("b", String.valueOf(random.nextInt(20) + 1)) // Replace 'b' with random number
                       .replaceAll("c", String.valueOf(random.nextInt(20) + 1))
                       .replaceAll("d", String.valueOf(random.nextInt(20) + 1))
                       .replaceAll("e", String.valueOf(random.nextInt(20) + 1))
                       .replaceAll("n", String.valueOf(random.nextInt(5) + 1));//this is a horrendous line btw
    }
    public static void main(String[] args) {
        QuestionGenerator gen = new QuestionGenerator(new PolynomialDerivative(), 0);
        String eq = gen.generateQuestion();
        System.out.println(eq);
        ASTNode equation = ShuntingYardParser.get().convertInfixNotationToAST(eq);
        equation.print();
        (new PolynomialDerivative()).returnAnswer(equation);
        equation.print();
    }
}

