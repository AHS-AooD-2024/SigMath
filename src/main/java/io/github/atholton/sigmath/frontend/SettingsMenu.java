package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.github.atholton.sigmath.topics.*;
import io.github.atholton.sigmath.user.UserSettings;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class SettingsMenu extends JPanel implements Serializable{
    private static SettingsMenu instance;
    private JLabel fontSizeLabel;
    private JSlider fontSize;
    private GridBagLayout layout;
    private GridBagConstraints c;
    
    private void addComponent(Component comp)
    {
        layout.setConstraints(comp, c);
        add(comp);
        revalidate();
    }
    private SettingsMenu() {
        super();
        fontSizeLabel = new JLabel("Font Size");
        fontSize = new JSlider(1, 100);
        
        fontSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = fontSize.getValue();
                UserSettings.get().setFontSize(num);
            }
        });
        layout = new GridBagLayout();
        setLayout(layout);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addComponent(fontSizeLabel);
        addComponent(fontSize);
    }

    public static SettingsMenu get() {
        if(instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }
}
