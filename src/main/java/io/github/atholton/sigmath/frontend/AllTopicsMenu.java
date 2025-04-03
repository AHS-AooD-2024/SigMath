package io.github.atholton.sigmath.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;

import io.github.atholton.sigmath.topics.PolynomialDerivative;
import io.github.atholton.sigmath.topics.ProductRuleDerivative;
import io.github.atholton.sigmath.topics.Topic;

public class AllTopicsMenu extends Menu {
    private ArrayList<TopicsButton> topics;
    private static AllTopicsMenu instance;
    
    private AllTopicsMenu() {
        topics = new ArrayList<TopicsButton>();

        addTopic(PolynomialDerivative.get(), "Polynomial Derivatives");
        addTopic(ProductRuleDerivative.get(), "Product Rule");
        for (TopicsButton t : topics) {
            add(t);
        }
    }
    public void addTopic(Topic t, String topicString) {
        TopicsButton b = new TopicsButton(t, topicString);
        b.setSize(150, 75);
        b.setMaximumSize(getSize());
        b.setBackground(new Color(255, 255, 255));
        b.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        b.setOpaque(true);
        topics.add(b);
        originalFont.put(b, new Font("Sans Serif", Font.PLAIN, 30));
    }
    public static AllTopicsMenu get()
    {
        if (instance == null) instance = new AllTopicsMenu();
        return instance;
    }
}
