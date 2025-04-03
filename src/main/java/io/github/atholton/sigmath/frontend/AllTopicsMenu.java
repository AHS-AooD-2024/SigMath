package io.github.atholton.sigmath.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;

import io.github.atholton.sigmath.topics.ChainRuleDerivative;
import io.github.atholton.sigmath.topics.PolynomialDerivative;
import io.github.atholton.sigmath.topics.ProductRuleDerivative;
import io.github.atholton.sigmath.topics.Topic;
import io.github.atholton.sigmath.topics.TrigDerivative;

public class AllTopicsMenu extends Menu {
    private ArrayList<TopicsButton> topics;
    private static AllTopicsMenu instance;
    private GridBagLayout layout;
    
    private AllTopicsMenu() {
        topics = new ArrayList<TopicsButton>();

        addTopic(PolynomialDerivative.get(), "Polynomial Derivatives");
        addTopic(ChainRuleDerivative.get(), "Chain Rule Derivatives");
        addTopic(ProductRuleDerivative.get(), "Product Rule");
        addTopic(TrigDerivative.get(), "Trig Derivatives");
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
