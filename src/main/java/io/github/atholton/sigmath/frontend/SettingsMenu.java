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
                if (!fontSize.getValueIsAdjusting()) update();

            }
        });
        toolBarSizeLabel = new JLabel("Logo Font Size");
        toolBarSize = new JSlider(50, 200, UserSettings.get().getToolBarSize());
        
        toolBarSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = toolBarSize.getValue();
                UserSettings.get().setToolBarSize(num);
                if(!toolBarSize.getValueIsAdjusting()) update();
            }
        });

        saveButton = new JButton("Save as Default");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().path = "DEFAULT";
                UserStats.get().save();
                JOptionPane.showMessageDialog(Application.get(), "Saved Default Profile to: " + UserStats.get().name);
            }
            
        });

        JButton saveB = new JButton("Save");
        saveB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserStats.get().path = UserStats.get().name;
                UserStats.get().save();
                JOptionPane.showMessageDialog(Application.get(), "Saved Current Profile to: " + UserStats.get().name);
            }
            
        });

        JTextField setName = new JTextField();
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
        addComponent(fontSizeLabel);
        addComponent(fontSize);
        addComponent(toolBarSizeLabel);
        addComponent(toolBarSize);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        addComponent(saveButton);
        addComponent(saveB);
        addComponent(setName);

        Application.get().pack();
        SettingsMenu.update();
    }
    
    /**
     * General update fonts function for SINGLETON PANELS
     */
    public static void update()
    {
        SettingsMenu menu = SettingsMenu.get();
        UserSettings settings = UserSettings.get();
        menu.fontSize.setValue(settings.getFontSize());
        menu.toolBarSize.setValue(settings.getToolBarSize());

        //update fonts
        menu.fontSizeLabel.setFont(new Font("Sans Serif", Font.PLAIN, settings.getFontSize() / 2));
        menu.toolBarSizeLabel.setFont(new Font("Sans Serif", Font.PLAIN, settings.getFontSize() / 2));

        AllTopicsMenu.updateSizes();
        RecentTopicsMenu.updateSizes();
        Toolbar.updateSize();
    }

    public static SettingsMenu get() {
        if(instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }
}
