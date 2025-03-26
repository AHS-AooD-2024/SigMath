package io.github.atholton.sigmath.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import io.github.atholton.sigmath.user.UserStats;

public class Profiles extends JPanel {
    private static Profiles instance;
    private List<UserStats> profiles;
    private Profiles()
    {
        File folder = new File("data/");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                FileInputStream file;
                try {
                    file = new FileInputStream(listOfFiles[i]);
                    ObjectInputStream in = new ObjectInputStream(file);
                    profiles.add((UserStats) in.readObject());
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (UserStats profile : profiles)
        {
            JButton profileButton = new JButton(profile.name);
            profileButton.addActionListener(new ActionListener() {
                public UserStats p = profile;
                @Override
                public void actionPerformed(ActionEvent e) {
                    UserStats.set(p);
                }
            });
            add(profileButton);
        }
    }
    public static Profiles get()
    {
        if (instance == null) instance = new Profiles();
        return instance;
    }
}