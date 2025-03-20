package io.github.atholton.sigmath.topics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the statistics of the user, ie their profiecency level in all topics
 * Should be saved and maybe have profiles or smth
 */
public class UserStats implements Serializable {
    private static final long serialVersionUID = -539438982350009L;
    private List<Topic> allTopics;
    String name, path; //will look for default path only
    private static UserStats instance;

    private UserStats()
    {
        
        //look for file with default. ex) default.osufile idk
        //else make a new one
        try {
            //get file, then read all the topics
            FileInputStream file = new FileInputStream("DEFAULT.dat");
            ObjectInputStream in = new ObjectInputStream(file);

            UserStats read = (UserStats)in.readObject();
            instance = read;
            //assign the topics their topics from file, which would hold proficiency
            for (Topic t : instance.allTopics)
            {
                if (t instanceof PolynomialDerivative)
                {
                    PolynomialDerivative.set((PolynomialDerivative)t);
                }
                //horrendous code
            }
            //assign name as well

            in.close();
        } catch (Exception e) {
            //add all other topics
            allTopics = new ArrayList<>();
            allTopics.add(PolynomialDerivative.get());
            //get name
            name = "User";
            path = "DEFAULT";
        }
    }
    
    private UserStats(String tempPath)
    {
        
        //look for file with default. ex) default.osufile idk
        //else make a new one
        try {
            //get file, then read all the topics
            FileInputStream file = new FileInputStream(tempPath + ".dat");
            ObjectInputStream in = new ObjectInputStream(file);

            UserStats read = (UserStats)in.readObject();
            instance = read;
            //assign the topics their topics from file, which would hold proficiency
            for (Topic t : instance.allTopics)
            {
                if (t instanceof PolynomialDerivative)
                {
                    PolynomialDerivative.set((PolynomialDerivative)t);
                }
                //horrendous code
            }
            in.close();
        } catch (Exception e) {
            //add all other topics
            allTopics = new ArrayList<>();
            allTopics.add(PolynomialDerivative.get());
            //get name
            name = "User";
            path = "DEFAULT";
        }
    }
        
    public static UserStats get()
    {
        if (instance == null) instance = new UserStats();
        return instance;
    }
    
    public static void set(String path)
    {
        instance = new UserStats(path);
    }

    public void save()
    {
        //write instance to file
        try
        {   
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(get().path + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(file);
            
            // Method for serialization of object
            out.writeObject(get());
            
            out.close();
            file.close();
            
            System.out.println("SAVED");

        }
        
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
    public static void main(String[] args) {
        UserStats player = UserStats.get();
        //PolynomialDerivative topic = PolynomialDerivative.get();
        //topic.setProficiency(3);
        //player.save();
        System.out.println(PolynomialDerivative.get().getProficiency());
    }
}
