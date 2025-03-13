package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel{
    private static MainMenu instance;
    
    private MainMenu() {
        
        setLayout(new BorderLayout());
        add(new Toolbar(), BorderLayout.NORTH);
        add(new RecentTopicsMenu());
    }

    public static MainMenu get() {
        if(instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }
}
