package io.github.atholton.sigmath;
import java.util.Random;

import io.github.atholton.sigmath.topics.Topic;

public class QuestionGenerator {
    private Topic topic;
    private Random random;
    private int index;
    private double proficiencyLevel;

    public QuestionGenerator(Topic topic, int index, double proficiencyLevel) {
        this.topic = topic;
        this.index = index;
        this.proficiencyLevel = proficiencyLevel;
        this.random = new Random();
    }

    public double IncrementProficiency() {
        proficiencyLevel += 0.2; // Get the proficiency level of user and increment it by 0.2 (5 questions with each formula) 
        return proficiencyLevel; 
    }

    public int IncrementIndex() {
        index = (int) (proficiencyLevel);
        return index;
    } 

    public String generateQuestion() {
        String formula = topic.getFormula(index); // Retrieve a formula for the selected topic
        String formattedQuestion = replaceVariables(formula); // Replace variables with numbers
        return new String(formattedQuestion);
    }

    private String replaceVariables(String equation) {
        return equation.replaceAll("a", String.valueOf(random.nextInt(100) + 1))  // Replace 'a' with random number
                       .replaceAll("b", String.valueOf(random.nextInt(100) + 1)); // Replace 'b' with random number
    }
}

