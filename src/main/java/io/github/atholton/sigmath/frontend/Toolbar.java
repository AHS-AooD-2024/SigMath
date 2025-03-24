package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.File;

import io.github.atholton.sigmath.user.UserSettings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Toolbar extends JPanel{
    private JButton topics, settings, logo;
    private Font lexend;
    private static Toolbar instance;

    public static Toolbar get()
    {
        if (instance == null) instance = new Toolbar();
        return instance;
    }
    public static void updateSize()
    {
        UserSettings settings = UserSettings.get();
        int size = settings.getToolBarSize();
        Toolbar bar = Toolbar.get();
        bar.logo.setFont(bar.logo.getFont().deriveFont((size/100.0f * bar.logo.getFont().getSize())));
    }

    private void makeComponent(Component comp,
                               GridBagLayout gridbag,
                               GridBagConstraints c) {
        gridbag.setConstraints(comp, c);
        add(comp);
        revalidate();
    }

    private Toolbar() {
        try {
            lexend = Font.createFont(Font.TRUETYPE_FONT, new File("Lexend-VariableFont_wght.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lexend);
            //lexend.decode(TOOL_TIP_TEXT_KEY);
        }
        catch (IOException | FontFormatException e) {

        }
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

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
        ImageIcon topicsIcon = new ImageIcon(getClass().getResource("tempTopics.jpg"));
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
        settingsIcon = new ImageIcon(settingsIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
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
