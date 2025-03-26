package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.github.atholton.sigmath.user.UserSettings;
import io.github.atholton.sigmath.user.UserStats;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SettingsMenu extends JPanel{
    private static SettingsMenu instance;
    private JLabel fontSizeLabel, toolBarSizeLabel;
    private JSlider fontSize, toolBarSize;
    private JButton saveButton;
    private GridBagLayout layout;
    private GridBagConstraints c;

    private void addComponent(Component comp)
    {
        layout.setConstraints(comp, c);
        add(comp);
        revalidate();
    }
    private SettingsMenu() {
        super();
        fontSizeLabel = new JLabel("Font Size");
        fontSize = new JSlider(50, 200, UserSettings.get().getFontSize());
        
        fontSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = fontSize.getValue();
                UserSettings.get().setFontSize(num);
                //TODO: update states for all singletons and have ProblemsMenu set font to this size
                update();

            }
        });
        toolBarSizeLabel = new JLabel("Font Size");
        toolBarSize = new JSlider(50, 200, UserSettings.get().getToolBarSize());
        
        toolBarSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = toolBarSize.getValue();
                UserSettings.get().setToolBarSize(num);
                Toolbar.updateSize();
                update();
            }
        });

        saveButton = new JButton("Save as Default");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().path = "DEFAULT";
                UserStats.get().save();
            }
            
        });

        JButton saveB = new JButton("Save");
        saveB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().path = UserStats.get().name;
                UserStats.get().save();
            }
            
        });

        JTextField setName = new JTextField();
        setName.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().name = setName.getText();
            }
            
        });

        layout = new GridBagLayout();
        setLayout(layout);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        c.insets = new Insets(20, 50, 20, 50);
        c.gridwidth = GridBagConstraints.REMAINDER;
        addComponent(fontSizeLabel);
        addComponent(fontSize);
        addComponent(toolBarSizeLabel);
        addComponent(toolBarSize);
        c.gridwidth = 1;
        addComponent(saveButton);
        addComponent(saveB);
        addComponent(setName);
    }
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
                        JOptionPane.showMessageDialog(Application.get(), "Set profile to: " + p.name);
                    }
                });
                add(profileButton);
            }
        }
        public static Profiles get()
        {
            if (instance == null) instance = SettingsMenu.get().new Profiles();
            return instance;
        }
    }
    public static void update()
    {

    }

    public static SettingsMenu get() {
        if(instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }
}
