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

public class SettingsMenu extends Menu {
    private static SettingsMenu instance;
    private JLabel fontSizeLabel, toolBarSizeLabel;
    private JSlider fontSize, toolBarSize;
    private JButton saveButton;

    private SettingsMenu() {
        super();
        fontSizeLabel = new JLabel("Font Size");
        fontSize = new JSlider(50, 200, UserSettings.get().getFontSize());
        
        fontSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = fontSize.getValue();
                UserSettings.get().setFontSize(num);
                if (!fontSize.getValueIsAdjusting()) Application.resizeFonts();

            }
        });
        toolBarSizeLabel = new JLabel("Logo Font Size");
        toolBarSize = new JSlider(50, 200, UserSettings.get().getToolBarSize());
        
        toolBarSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = toolBarSize.getValue();
                UserSettings.get().setToolBarSize(num);
                if(!toolBarSize.getValueIsAdjusting()) Application.resizeFonts();
            }
        });

        saveButton = new JButton("Save as Default");
        saveButton.setBackground(new Color(201, 218, 248));
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().path = "DEFAULT";
                UserStats.get().save();
                JOptionPane.showMessageDialog(Application.get(), "Saved Default Profile to: " + UserStats.get().name);
                Application.resizeFonts();
            }
            
        });

        JButton saveB = new JButton("Save");
        saveB.setBackground(new Color(201, 218, 248));
        saveB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().path = UserStats.get().name;
                UserStats.get().save();
                JOptionPane.showMessageDialog(Application.get(), "Saved Current Profile to: " + UserStats.get().name);
            }
            
        });

        JTextField setName = new JTextField();
        setName.setToolTipText("Set Profile Name");
        setName.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().name = setName.getText();
                JOptionPane.showMessageDialog(Application.get(), "Set Profile Name to: " + UserStats.get().name);
            }
            
        });

        layout = new GridBagLayout();
        setLayout(layout);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        c.insets = new Insets(10, 50, 10, 50);
        c.gridwidth = GridBagConstraints.REMAINDER;
        makeComponent(fontSizeLabel);
        originalFont.put(fontSizeLabel, new Font("Sans Serif", Font.PLAIN, 30));
        makeComponent(fontSize);
        makeComponent(toolBarSizeLabel);
        originalFont.put(toolBarSizeLabel, new Font("Sans Serif", Font.PLAIN, 30));
        makeComponent(toolBarSize);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        makeComponent(saveButton);
        originalFont.put(saveButton, new Font("Sans Serif", Font.PLAIN, 30));
        makeComponent(saveB);
        originalFont.put(saveB, new Font("Sans Serif", Font.PLAIN, 30));
        c.insets = new Insets(10, 10, 10, 10);
        c.weightx = 0.5;
        makeComponent(new JLabel("Set Profile Name: ", 0));
        c.weightx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        makeComponent(setName);
    }
    
    public static SettingsMenu get() {
        if(instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }
}
