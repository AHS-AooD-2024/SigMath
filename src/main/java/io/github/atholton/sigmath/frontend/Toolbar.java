package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.File;

import io.github.atholton.sigmath.user.UserSettings;
import io.github.atholton.sigmath.user.UserStats;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class Toolbar extends Menu {
    private JButton topics, settings, logo;
    private Font lexend;
    public JLabel user;
    private static Toolbar instance;

    public static Toolbar get()
    {
        if (instance == null) instance = new Toolbar();
        return instance;
    }

    private Toolbar() {
        try {
            lexend = Font.createFont(Font.TRUETYPE_FONT, new File("Lexend-VariableFont_wght.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lexend);
            //lexend.decode(TOOL_TIP_TEXT_KEY);
        }
        catch (IOException | FontFormatException e) {
            System.out.println("LEXEND NOT REGISTERED");
        }

        //logo should redirect to recent Topics
        logo = new JButton("SigΣath");
        logo.setFont(lexend);
        logo.setBackground(new Color(201, 218, 248));
        logo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = MainMenu.get();
                RecentTopicsMenu home = RecentTopicsMenu.get();
                menu.remove(menu.getComponent(1));
                menu.add(home, BorderLayout.CENTER);
                menu.repaint();
                menu.revalidate();
            }
        });

        topics = new JButton();
        //settings.setFont(new Font("Sans Serif", Font.BOLD, 60));
        ImageIcon topicsIcon = new ImageIcon(getClass().getResource("topics.png"));
        topicsIcon = new ImageIcon(topicsIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        topics.setIcon(topicsIcon);
        topics.setBackground(new Color(201, 218, 248));
        topics.addActionListener(new ActionListener() {
            private JPanel previous;

            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = MainMenu.get();
                JPanel currentMenu = (JPanel) menu.getComponent(1);
                menu.remove(menu.getComponent(1));
                if (currentMenu.getClass() == AllTopicsMenu.class)
                {
                    menu.add(previous);
                }
                else
                {
                    menu.add(AllTopicsMenu.get(), BorderLayout.CENTER);
                }
                previous = currentMenu;
                menu.repaint();
                menu.revalidate();
            }
            
        });

        settings = new JButton();
        //settings.setFont(new Font("Sans Serif", Font.BOLD, 60));
        ImageIcon settingsIcon = new ImageIcon(getClass().getResource("settings.png"));
        settingsIcon = new ImageIcon(settingsIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH));
        settings.setIcon(settingsIcon);
        settings.setBackground(new Color(201, 218, 248));
        settings.addActionListener(new ActionListener() {
            private JPanel previous;

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("butt");
                MainMenu menu = MainMenu.get();
                //super sick transition :thumbs up:
                JPanel currentMenu = (JPanel) menu.getComponent(1);
                menu.remove(menu.getComponent(1));
                if (currentMenu.getClass() == AllTopicsMenu.class)
                {
                    menu.add(previous);
                }
                else
                {
                    menu.add(SettingsMenu.get(), BorderLayout.CENTER);
                }
                previous = currentMenu;
                menu.repaint();
                menu.revalidate();
            }
            
        });

        //gridBag
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(30, 50, 30, 50);
        makeComponent(topics);
        makeComponent(logo);
        c.gridwidth = GridBagConstraints.REMAINDER;
        makeComponent(settings);
        c.gridwidth = 1;
        c.insets = new Insets(0, 10, 0, 0);
        c.weightx = 0;
        
        System.out.println(UserStats.get().name);
        user = new JLabel("Profile: " + UserStats.get().name);
        user.setFont(new Font("Sans Serif", Font.BOLD, 20));
        makeComponent(user);

        originalFont.put(logo, new Font("Sans Serif", Font.BOLD, 100));

        setBackground(new Color(201, 218, 248));
    }

    @Override
    public void updateFontSizes()
    {
        for(Map.Entry<Component, Font> entry : originalFont.entrySet())
        {
            entry.getKey().setFont(entry.getValue().deriveFont(UserSettings.get().getToolBarSize() / 100.0f * entry.getValue().getSize2D()));
        }
        revalidate();
    }

    public void alignItems(int gap) {
        logo.setBorder(new EmptyBorder(10, gap, 0, gap));
        topics.setBorder(new EmptyBorder(10, gap, 0, gap));
        settings.setBorder(new EmptyBorder(10, gap, 0, gap));
    }
}
