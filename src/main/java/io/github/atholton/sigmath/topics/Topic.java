package io.github.atholton.sigmath.topics;
import java.io.Serializable;
import java.util.ArrayList;

import io.github.atholton.sigmath.equationtree.ASTNode;

/**
 * Represents an individual topic from the curriculum.
 * @author Abhay Nagaraj
 */
//Should topics be singleton? should user stats just have a list of all Topics?
public abstract class Topic implements Serializable{
    protected double proficiencyLevel;

    /**
     * List of sample problems to plug in numbers to
     * might need a formula solver that tells how to solve formula maybe
     */
    protected ArrayList<String> formulaList = new ArrayList<String>();

    public abstract void set();

    /**
     * Gets the proficiency level of the user in this specific topic
     * 
     * @return proficiencyLevel
     */
    public double getProficiency()
    {
        return proficiencyLevel;
    }
    public void setProficiency(double proficiency)
    {
        proficiencyLevel = proficiency;
    }
    
    /**
     * Gets a chosen formula from the formula list
     * 
     * @param index the index value of formulaList
     * @return formulaList.get(index)
     */
    public String getFormula(int index)
    {
        index = Math.min(index, formulaList.size() - 1);
        index = Math.max(index, 0);

        return formulaList.get(index);
    }
    public String getFormula()
    {
        return getFormula((int)(Math.random() * formulaList.size()));
    }

    /**
     * Returns the answer to a given question in tree format using the desired formula
     * 
     * @param question question in node format
     * @return answer to the question in tree format
     */
    public abstract ASTNode returnAnswer(ASTNode question);

    @Override
    //I have no idea if this is right, supposed to be if the class is the same
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other == this || other.getClass() == getClass()) return true;
        return false;
    }
}
