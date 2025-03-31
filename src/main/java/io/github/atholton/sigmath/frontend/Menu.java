package io.github.atholton.sigmath.frontend;

import javax.swing.JPanel;

import io.github.atholton.sigmath.user.UserSettings;

import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;

public abstract class Menu extends JPanel
{
    protected GridBagLayout layout;
    protected GridBagConstraints c;
    protected Map<Component, Font> originalFont;
    
    protected Menu()
    {
        super();
        originalFont = new HashMap<>();
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);
    }

    protected void makeComponent(Component component)
    {
        add(component, c);
        revalidate();
    }

    public void updateFontSizes()
    {
        for(Map.Entry<Component, Font> entry : originalFont.entrySet())
        {
            entry.getKey().setFont(entry.getValue().deriveFont(UserSettings.get().getFontSize() / 100.0f * entry.getValue().getSize2D()));
        }
        revalidate();
    }
}
