package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.topics.*;

import java.awt.*;
import java.util.ArrayList;

public class AllTopicsMenu extends JPanel{
    private ArrayList<TopicsButton> topics;
    private static AllTopicsMenu instance;
    
    private AllTopicsMenu() {
        topics = new ArrayList<TopicsButton>();

        addTopic(PolynomialDerivative.get(), "Ur stinky... - Albert Einstein");
        for (TopicsButton t : topics) {
            add(t);
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
