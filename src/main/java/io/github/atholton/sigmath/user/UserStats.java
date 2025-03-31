package io.github.atholton.sigmath.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        //add all other topics
        allTopics = new ArrayList<>();
        allTopics.add(PolynomialDerivative.get());
        //add settings
        settings = UserSettings.get(); //pointer so dynamic
        //get name
        name = "User";
        path = "DEFAULT";
    }
    private static void tryRead(String path) throws Exception
    {
        FileInputStream file = new FileInputStream("data/" + path + ".dat");
        ObjectInputStream in = new ObjectInputStream(file);

        UserStats read = (UserStats)in.readObject();
        set(read);
        setStuff(read);
        
        in.close();
    }
    private static void setStuff(UserStats read)
    {
        UserSettings.set(read.settings);
        //assign the topics their topics from file, which would hold proficiency
        for (Topic t : read.allTopics)
        {
            t.set();
        }
    }
    public static UserStats get()
    {
        if (instance == null)
        {
            try {
                tryRead("DEFAULT");
            }
            catch (Exception e)
            {
                System.out.println("new profile");
                instance = new UserStats();
            }
        }
        return instance;
    }
    
    public static void set(UserStats stats)
    {
        UserStats.instance = stats;
    }
    public static void set(String path)
    {
        try {
            tryRead(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
