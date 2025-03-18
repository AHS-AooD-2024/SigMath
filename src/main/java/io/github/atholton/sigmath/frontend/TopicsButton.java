package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.github.atholton.sigmath.topics.*;

public class TopicsButton extends JButton implements ActionListener{
    private Topic topic;

    public TopicsButton(Topic topic, String topicString) {
        super(topicString);
        this.topic = topic;
        addActionListener(this);
    }
    
    public Topic getTopic() {
        return topic;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other.getClass() != getClass()) return false;

        TopicsButton o = (TopicsButton)other;
        return o.topic.equals(topic);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainMenu menu = MainMenu.get();
        menu.remove(menu.getComponent(1));
        menu.add(new ProblemsMenu(topic));
        menu.revalidate();
        menu.repaint();
    }
}
