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

    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other.getClass() != getClass()) return false;

        RecentTopicsButton o = (RecentTopicsButton)other;
        return o.topic.equals(topic);
    }
}
