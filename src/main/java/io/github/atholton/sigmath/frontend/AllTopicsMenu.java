package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.topics.*;

import java.awt.*;
import java.util.ArrayList;

public class AllTopicsMenu extends JPanel{
    private ArrayList<RecentTopicsButton> topics;
    
    public AllTopicsMenu() {
        topics = new ArrayList<RecentTopicsButton>();

        addTopic(new PolynomialDerivative(), "Ur stinky... - Albert Einstein");
        for (RecentTopicsButton t : topics) {
            add(t);
        }
    }

    public void addTopic(Topic t, String topicString) {
        RecentTopicsButton b = new RecentTopicsButton(t, topicString);
        topics.add(b);
    }
}
