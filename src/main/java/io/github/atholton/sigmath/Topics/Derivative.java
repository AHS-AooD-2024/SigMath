package io.github.atholton.sigmath.Topics;

import java.util.ArrayList;

/**
 * Represents all derivative methods
 * @author Abhay Nagaraj
 */
public class Derivative extends Topic{
    public Derivative() {
        proficiencyLevel = 1;
        formulaList = new ArrayList<String>();
    }

    /**
     * Returns the answer to any derivative
     * 
     * @param question question to answer
     * @param listIndex index of the desired formula
     * @return the derivative of question
     */
    @Override
    public ASTNode returnAnswer(ASTNode question, int listIndex) {
        return null;
    }
}
