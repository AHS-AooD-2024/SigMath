package io.github.atholton.sigmath.frontend;

import javax.swing.JLabel;

import io.github.atholton.sigmath.topics.Topic;
import io.github.atholton.sigmath.user.UserStats;

public class ProgressText extends JLabel
{
    private Topic topic;
    public ProgressText(Topic topic)
    {
        super();
        this.topic = topic;
        updateText();
    }
    public void updateText()
    {
        double percentage = topic.getProficiency();
        setText("Progress:\n" + (int)(100 * percentage / 5) + "%");
    }
}
