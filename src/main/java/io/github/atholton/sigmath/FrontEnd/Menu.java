package io.github.atholton.sigmath.FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Menu extends JPanel{
    ArrayList<JButton> topics = new ArrayList<JButton>();

    public Menu() {
        setLayout(new BorderLayout());
        add(new Toolbar(), BorderLayout.NORTH);
    }

    public void addTopic() {
        
    }
}
