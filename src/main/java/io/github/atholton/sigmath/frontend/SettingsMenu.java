package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.github.atholton.sigmath.user.UserSettings;

import java.awt.*;

public class SettingsMenu extends JPanel{
    private static SettingsMenu instance;
    private JLabel fontSizeLabel, toolBarSizeLabel;
    private JSlider fontSize, toolBarSize;
    private JButton saveButton;
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
        fontSize = new JSlider(50, 200, UserSettings.get().getFontSize());
        
        fontSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = fontSize.getValue();
                UserSettings.get().setFontSize(num);
                //TODO: update states for all singletons and have ProblemsMenu set font to this size

            }
        });
        toolBarSizeLabel = new JLabel("Font Size");
        toolBarSize = new JSlider(50, 200, UserSettings.get().getToolBarSize());
        
        toolBarSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                int num = toolBarSize.getValue();
                UserSettings.get().setToolBarSize(num);
                Toolbar.updateSize();
            }
        });

        saveButton = new JButton("Save");


        layout = new GridBagLayout();
        setLayout(layout);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addComponent(fontSizeLabel);
        addComponent(fontSize);
        addComponent(saveButton);
    }

    public static SettingsMenu get() {
        if(instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }
}
