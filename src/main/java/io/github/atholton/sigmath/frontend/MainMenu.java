package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    private static MainMenu instance;

    private MainMenu() {
        
        setLayout(new BorderLayout());
        add(Toolbar.get(), BorderLayout.NORTH);
        add(RecentTopicsMenu.get());
    }
    public static MainMenu get()
    {
        if (instance == null) instance = new MainMenu();
        return instance;
    }
    public void addWithTransition(Component component)
    {
        add(component);
    }
}
