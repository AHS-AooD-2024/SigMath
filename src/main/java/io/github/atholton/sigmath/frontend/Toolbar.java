package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel{
    private JButton topics, settings, logo;

    public Toolbar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //logo should redirect to recent Topics
        logo = new JButton("SigΣath");
        logo.setFont(new Font("Sans Serif", Font.BOLD, 60));
        logo.setBackground(new Color(201, 218, 248));
        logo.setAlignmentY(TOP_ALIGNMENT);
        logo.setBorder(new EmptyBorder(0, 50, 0, 50));
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
        ImageIcon topicsIcon = new ImageIcon(getClass().getResource("tempTopics.jpg"));
        topicsIcon = new ImageIcon(topicsIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        topics.setIcon(topicsIcon);
        topics.setBackground(new Color(201, 218, 248));
        topics.setAlignmentY(TOP_ALIGNMENT);
        topics.setBorder(new EmptyBorder(0, 50, 0, 50));
        topics.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = MainMenu.get();
                menu.remove(menu.getComponent(1));
                menu.add(AllTopicsMenu.get(), BorderLayout.CENTER);
                menu.repaint();
                menu.revalidate();
            }
            
        });

        settings = new JButton();
        //settings.setFont(new Font("Sans Serif", Font.BOLD, 60));
        ImageIcon settingsIcon = new ImageIcon(getClass().getResource("tempsettings.jpg"));
        settingsIcon = new ImageIcon(settingsIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        settings.setIcon(settingsIcon);
        settings.setBackground(new Color(201, 218, 248));
        settings.setAlignmentY(TOP_ALIGNMENT);
        settings.setBorder(new EmptyBorder(0, 50, 0, 50));
        settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("butt");
                MainMenu menu = MainMenu.get();
                //super sick transition :thumbs up:
                menu.remove(menu.getComponent(1));
                menu.add(SettingsMenu.get(), BorderLayout.CENTER);
                menu.repaint();
                menu.revalidate();
            }
            
        });

        add(topics);
        add(logo);
        add(settings);

        setBackground(new Color(201, 218, 248));
    }

    public void alignItems(int gap) {
        logo.setBorder(new EmptyBorder(10, gap, 0, gap));
        topics.setBorder(new EmptyBorder(10, gap, 0, gap));
        settings.setBorder(new EmptyBorder(10, gap, 0, gap));
    }
}
