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
        logo.setBackground(Color.WHITE);
        logo.setAlignmentY(TOP_ALIGNMENT);
        logo.setBorder(new EmptyBorder(0, 50, 0, 50));
        logo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = MainMenu.get();
                RecentTopicsMenu home = RecentTopicsMenu.get();
                menu.add(home);
            }
        });

        topics = new JButton("Topics");
        topics.setFont(new Font("Sans Serif", Font.BOLD, 60));
        topics.setAlignmentY(TOP_ALIGNMENT);
        topics.setBorder(new EmptyBorder(0, 50, 0, 50));
        topics.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = MainMenu.get();
                menu.add(AllTopicsMenu.get());
            }
            
        });

        settings = new JButton("Settings");
        settings.setFont(new Font("Sans Serif", Font.BOLD, 60));
        settings.setAlignmentY(TOP_ALIGNMENT);
        settings.setBorder(new EmptyBorder(0, 50, 0, 50));
        settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = MainMenu.get();
                //super sick transition :thumbs up:
                menu.add(SettingsMenu.get());
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
