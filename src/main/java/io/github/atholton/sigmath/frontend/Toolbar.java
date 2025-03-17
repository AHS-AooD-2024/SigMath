package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel{
    private JButton topics, settings, logo;

    private void makeComponent(Component comp,
                               GridBagLayout gridbag,
                               GridBagConstraints c) {
        gridbag.setConstraints(comp, c);
        add(comp);
        revalidate();
    }

    public Toolbar() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

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
                menu.remove(menu.getComponent(1));
                menu.add(home, BorderLayout.CENTER);
                menu.repaint();
                menu.revalidate();
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
                menu.remove(menu.getComponent(1));
                menu.add(AllTopicsMenu.get(), BorderLayout.CENTER);
                menu.repaint();
                menu.revalidate();
            }
            
        });

        settings = new JButton("Settings");
        settings.setFont(new Font("Sans Serif", Font.BOLD, 60));
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

        //gridBag
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        makeComponent(topics, (GridBagLayout)getLayout(), c);
        makeComponent(logo, (GridBagLayout)getLayout(), c);
        makeComponent(settings, (GridBagLayout)getLayout(), c);

        setBackground(new Color(201, 218, 248));
    }

    public void alignItems(int gap) {
        logo.setBorder(new EmptyBorder(10, gap, 0, gap));
        topics.setBorder(new EmptyBorder(10, gap, 0, gap));
        settings.setBorder(new EmptyBorder(10, gap, 0, gap));
    }
}
