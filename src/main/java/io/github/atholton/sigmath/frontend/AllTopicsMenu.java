package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.topics.*;
import io.github.atholton.sigmath.user.UserSettings;

import java.awt.*;
import java.util.ArrayList;

public class AllTopicsMenu extends Menu {
    private ArrayList<TopicsButton> topics;
    private static AllTopicsMenu instance;
    
    private AllTopicsMenu() {
        topics = new ArrayList<TopicsButton>();

        addTopic(PolynomialDerivative.get(), "Polynomial Derivatives");
        for (TopicsButton t : topics) {
            add(t);
        }
    }
    public void addTopic(Topic t, String topicString) {
        TopicsButton b = new TopicsButton(t, topicString);
        topics.add(b);
        originalFont.put(b, new Font("Sans Serif", Font.PLAIN, 30));
    }
    public static AllTopicsMenu get()
    {
        if (instance == null) instance = new AllTopicsMenu();
        return instance;
    }
}
