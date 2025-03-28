package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import io.github.atholton.sigmath.topics.*;

public class TopicsButton extends JButton implements ActionListener{
    private Topic topic;
    private String topicString;

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
        JPanel main = (JPanel)menu.getComponent(1);
        menu.remove(main);
        menu.add(new ProblemsMenu(topic));
        menu.revalidate();
        menu.repaint();

        boolean addTopic = true;
        for (TopicsButton t : RecentTopicsMenu.get().getTopics()) {
            if (t.equals(this)) {
                addTopic = false;
                break;
            }
        }
        if (addTopic) {
            ArrayList<TopicsButton> topicsList = RecentTopicsMenu.get().getTopics();
            RecentTopicsMenu.get().addTopic(topic, getText());
            RecentTopicsMenu.get().makeComponent(topicsList.get(topicsList.size() - 1), topicsList.size() - 1);
            RecentTopicsMenu.get().revalidate();
            RecentTopicsMenu.get().updateFontSizes();
            //System.out.println(RecentTopicsMenu.get().getTopics().get(0));
        }
    }
}
