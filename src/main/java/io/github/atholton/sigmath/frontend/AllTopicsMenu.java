package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.topics.*;
import io.github.atholton.sigmath.user.UserSettings;

import java.awt.*;
import java.util.ArrayList;

public class AllTopicsMenu extends JPanel{
    private static double initialSize = 0.3;
    private ArrayList<TopicsButton> topics;
    private static AllTopicsMenu instance;
    
    private AllTopicsMenu() {
        topics = new ArrayList<TopicsButton>();

        addTopic(PolynomialDerivative.get(), "Polynomial Derivatives");
        for (TopicsButton t : topics) {
            add(t);
        }
        Application.get().pack();
    }
    public static void updateSizes()
    {
        AllTopicsMenu menu = AllTopicsMenu.get();
        UserSettings settings = UserSettings.get();
        for (TopicsButton topic : menu.topics)
        {
            topic.setFont(new Font("Sans Serif", Font.PLAIN, (int)(settings.getFontSize() * initialSize)));
        }
    }
    public void addTopic(Topic t, String topicString) {
        TopicsButton b = new TopicsButton(t, topicString);
        topics.add(b);
    }
    public static AllTopicsMenu get()
    {
        if (instance == null) instance = new AllTopicsMenu();
        return instance;
    }
}
