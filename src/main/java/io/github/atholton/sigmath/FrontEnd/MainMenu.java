package io.github.atholton.sigmath.FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel{

    public MainMenu() {
        
        setLayout(new BorderLayout());
        add(new Toolbar(), BorderLayout.NORTH);
        add(new RecentTopicsMenu());
        
    }

    
}
