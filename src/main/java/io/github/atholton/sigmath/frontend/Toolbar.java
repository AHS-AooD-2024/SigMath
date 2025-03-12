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
        logo.setAlignmentY(TOP_ALIGNMENT);
        logo.setBorder(new EmptyBorder(0, 50, 0, 50));

        topics = new JButton("Topics");
        topics.setFont(new Font("Sans Serif", Font.BOLD, 60));
        topics.setAlignmentY(TOP_ALIGNMENT);
        topics.setBorder(new EmptyBorder(0, 50, 0, 50));
        topics.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Application app = Application.get();
                app.add(AllTopicsMenu.get());
            }
            
        });

        settings = new JButton("Settings");
        settings.setFont(new Font("Sans Serif", Font.BOLD, 60));
        settings.setAlignmentY(TOP_ALIGNMENT);
        settings.setBorder(new EmptyBorder(0, 50, 0, 50));
        settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Application app = Application.get();
                app.add(SettingsMenu.get());
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
