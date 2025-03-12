package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import io.github.atholton.sigmath.topics.*;

import java.awt.*;
import java.util.ArrayList;

public class SettingsMenu extends JPanel{
    private static SettingsMenu instance;
    private JLabel tempSettingsText;
    private SettingsMenu() {
        tempSettingsText = new JLabel("Settings will go here");
        add(tempSettingsText);
    }

    public static SettingsMenu get() {
        if(instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }
}
