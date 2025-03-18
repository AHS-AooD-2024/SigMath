package io.github.atholton.sigmath.topics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the statistics of the user, ie their profiecency level in all topics
 * Should be saved and maybe have profiles or smth
 */
public class UserStats implements Serializable{
    private List<Topic> allTopics;
    private String name;
    private static UserStats instance;
    private UserStats()
    {
        allTopics = new ArrayList<>();
        //look for file with default. ex) default.osufile idk
        //else make a new one
        try {
            //get file, then read all the topics
            //assign the topics their topics from file, which would hold proficiency
            //assign name as well
        } catch (Exception e) {
            allTopics.add(PolynomialDerivative.get());
            //add all other topics
            //get name
        }
    }
    public static UserStats get()
    {
        if (instance == null) instance = new UserStats();
        return instance;
    }
    public static void save()
    {
        //write instance to file
    }
}
