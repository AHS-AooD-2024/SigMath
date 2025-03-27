package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;

import io.github.atholton.sigmath.topics.*;
import io.github.atholton.sigmath.user.UserSettings;

import java.util.ArrayList;

public class RecentTopicsMenu extends JPanel{
    private static RecentTopicsMenu instance;
    private static double initialSize = 0.3;
    private ArrayList<TopicsButton> topics;
    private GridBagConstraints c;

    private void makeComponent(Component comp, GridBagLayout gridbag, GridBagConstraints c, int index) {
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
        gridbag.setConstraints(comp, c);
        add(comp);
        revalidate();
    }

    private RecentTopicsMenu() {
        super();
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        topics = new ArrayList<TopicsButton>();

        addTopic(PolynomialDerivative.get(), "Polynomial Derivatives");
        
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 10, 10, 10);
        for (int i = 0; i < topics.size(); i++)
        {
            makeComponent(topics.get(i), (GridBagLayout)getLayout(), c, i);
        }

        Application.get().pack();
    }
    public static void updateSizes()
    {
        RecentTopicsMenu menu = RecentTopicsMenu.get();
        UserSettings settings = UserSettings.get();
        for (TopicsButton topic : menu.topics)
        {
            topic.setFont(new Font("Sans Serif", Font.PLAIN, (int)(settings.getFontSize() * initialSize)));
        }
    }
    public static RecentTopicsMenu get() {
        if (instance == null) instance = new RecentTopicsMenu();
        return instance;
    }

    public void addTopic(Topic t, String topicString) {
        TopicsButton b = new TopicsButton(t, topicString);
        topics.add(b);
    }

    public void addTopicIfAbsent(Topic t, String topicString) {
        //only add topic if absent, and if size is less than 6, supposed to be recent, not all
    }
}
