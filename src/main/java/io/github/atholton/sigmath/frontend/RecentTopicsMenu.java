package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.github.atholton.sigmath.topics.*;
import io.github.atholton.sigmath.user.UserSettings;

import java.util.ArrayList;

public class RecentTopicsMenu extends Menu{
    private static RecentTopicsMenu instance;
    private ArrayList<TopicsButton> topics;
    JButton redirect;

    public void makeComponent(Component comp, int index) {
        //should be like
        //0 1 2
        //3 4 5
        //where each number is a recent topics
        if (index % 3 == 2)
        {
            c.gridwidth = GridBagConstraints.REMAINDER;
        }
        else
        {
            c.gridwidth = 1;
        }
        layout.setConstraints(comp, c);
        add(comp);
        revalidate();
    }

    private RecentTopicsMenu() {
        super();
        topics = new ArrayList<TopicsButton>();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(100, 100, 100, 100);
        for (int i = 0; i < topics.size(); i++)
        {
            makeComponent(topics.get(i), i);
        }
        if (topics.size() == 0)
        {
            redirect = new JButton("GOTO: All Topics");
            originalFont.put(redirect, new Font("Sans Serif", Font.PLAIN, 80));
            redirect.setBackground(new Color(201, 218, 248));
            redirect.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    MainMenu menu = MainMenu.get();
                    JPanel main = (JPanel)menu.getComponent(1);
                    menu.remove(main);

                    menu.add(AllTopicsMenu.get());
                    menu.revalidate();
                    menu.repaint();
                }
            });
            makeComponent(redirect, 0);
        }
    }
    public static RecentTopicsMenu get() {
        if (instance == null) instance = new RecentTopicsMenu();
        return instance;
    }

    public void addTopic(Topic t, String topicString) {
        TopicsButton b = new TopicsButton(t, topicString);
        originalFont.put(b, new Font("Sans Serif", Font.PLAIN, 30));
        topics.add(b);
        if (redirect != null)
        {
            remove(redirect);
            redirect = null;
        }
    }
    public void addTopic(TopicsButton b)
    {
        topics.add(b);
        makeComponent(b, topics.size() - 1);
        updateFontSizes();
        revalidate();
        originalFont.put(b, new Font("Sans Serif", Font.PLAIN, 30));
    }

    public ArrayList<TopicsButton> getTopics() {
        return topics;
    }
}
