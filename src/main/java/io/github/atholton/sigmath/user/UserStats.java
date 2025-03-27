package io.github.atholton.sigmath.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import io.github.atholton.sigmath.topics.PolynomialDerivative;
import io.github.atholton.sigmath.topics.Topic;

/**
 * Contains all the statistics of the user, ie their profiecency level in all topics
 * Should be saved and maybe have profiles or smth
 */
public class UserStats implements Serializable {
    private static final long serialVersionUID = -539438982350009L;
    private List<Topic> allTopics;
    private UserSettings settings;
    public String name, path; //will look for default path only
    private static UserStats instance;

    private UserStats()
    {
        
        this("DEFAULT");
    }
    
    private UserStats(String tempPath)
    {
        
        //look for file with default. ex) default.osufile idk
        //else make a new one
        try {
            //get file, then read all the topics
            FileInputStream file = new FileInputStream("data/" + tempPath + ".dat");
            ObjectInputStream in = new ObjectInputStream(file);

            UserStats read = (UserStats)in.readObject();
            System.out.println("IN USER STATS: " + read.name + "    \n" + read.path);
            allTopics = read.allTopics;
            settings = read.settings;
            name = read.name;
            path = read.path;

            setStuff(read);
            
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            //add all other topics
            allTopics = new ArrayList<>();
            allTopics.add(PolynomialDerivative.get());
            //add settings
            settings = UserSettings.get(); //pointer so dynamic
            //get name
            name = "User";
            path = "DEFAULT";
        }
    }
    private static void setStuff(UserStats read)
    {
        UserSettings.set(read.settings);
        //assign the topics their topics from file, which would hold proficiency
        for (Topic t : read.allTopics)
        {
            if (t instanceof PolynomialDerivative)
            {
                PolynomialDerivative.set((PolynomialDerivative)t);
            }
            //horrendous code
        }
    }
    public static UserStats get()
    {
        if (instance == null) instance = new UserStats();
        return instance;
    }
    
    public static void set(String path)
    {
        UserStats.instance = new UserStats(path);
    }

    public void save()
    {
        //write instance to file
        try
        {   
            //Saving of object in a file
            File dir = new File("data/");
            if (!dir.exists()) dir.mkdirs();
            File temp = new File("data/" + get().path + ".dat");
            if (temp.exists())
                temp.delete();
            FileOutputStream file = new FileOutputStream("data/" + get().path + ".dat");
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
        //System.out.println(PolynomialDerivative.get().getProficiency());
    }
}
