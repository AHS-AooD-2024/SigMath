package io.github.atholton.sigmath.topics;
import java.util.ArrayList;

import io.github.atholton.sigmath.equationtree.ASTNode;

/**
 * Represents an individual topic from the curriculum.
 * @author Abhay Nagaraj
 */
public abstract class Topic {
    protected double proficiencyLevel;
    protected ArrayList<String> formulaList = new ArrayList<String>();

    /**
     * Gets the proficiency level of the user in this specific topic
     * 
     * @return proficiencyLevel
     */
    public abstract double getProficiency();
    
    /**
     * Gets a chosen formula from the formula list
     * 
     * @param index the index value of formulaList
     * @return formulaList.get(index)
     */
    public abstract String getFormula(int index);

    /**
     * Returns the answer to a given question in tree format using the desired formula
     * 
     * @param question question in node format
     * @return answer to the question in tree format
     */
    protected abstract ASTNode returnAnswer(ASTNode question);

    /**
     * Solves an individual unit of a topic within a problem
     * 
     * @param question question to answer
     * @return the answert to the question
     */
    protected ASTNode solve(ASTNode question) {return null;}
}
