package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;
import io.github.atholton.sigmath.topics.*;

public class RecentTopicsButton extends JButton{
    private Topic topic;

    public RecentTopicsButton(Topic topic, String topicString) {
        super(topicString);
        this.topic = topic;
    }
    
    public Topic getTopic() {
        return topic;
    }
}
