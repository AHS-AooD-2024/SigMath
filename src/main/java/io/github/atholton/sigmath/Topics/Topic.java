package io.github.atholton.sigmath.Topics;
import java.util.ArrayList;

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
     * @param listIndex
     * @return answer to the question in tree format
     */
    public abstract Node returnAnswer(Node question, int listIndex);

    /**
     * Returns the answer to a given question in String format using the desired formula
     * 
     * @param question
     * @param listIndex
     * @return answer to the question in tree format
     */
    public abstract Node returnAnswer(String question, int listIndex);
}
