package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.topics.*;
import java.util.ArrayList;

public class RecentTopicsMenu extends JPanel{
    private static RecentTopicsMenu instance;
    private ArrayList<RecentTopicsButton> topics;
    private RecentTopicsMenu() {
        topics = new ArrayList<RecentTopicsButton>();

        addTopic(new PolynomialDerivative(), "Ur stinky... - Albert Einstein");
        addTopic(new PolynomialDerivative(), "I'm a frog");
        for (RecentTopicsButton t : topics) {
            add(t);
        }
    }
    public static RecentTopicsMenu get() {
        if (instance == null) instance = new RecentTopicsMenu();
        return instance;
    }

    public void addTopic(Topic t, String topicString) {
        RecentTopicsButton b = new RecentTopicsButton(t, topicString);
        topics.add(b);
    }

    public void addTopicIfAbsent(Topic t, String topicString) {
        
    }
}
