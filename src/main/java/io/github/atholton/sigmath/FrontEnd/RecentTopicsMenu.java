package io.github.atholton.sigmath.FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RecentTopicsMenu extends JPanel{
    ArrayList<TopicsButton> topics;
    
    public RecentTopicsMenu() {
        topics = new ArrayList<TopicsButton>();

        addTopic("Ur stinky... - Albert Einstein");
        addTopic("fool me three times I punch your face");
        for (TopicsButton t : topics) {
            add(t);
        }
    }

    public void addTopic(String topicString) {
        TopicsButton b = new TopicsButton(topicString);
        topics.add(b);
    }
}
