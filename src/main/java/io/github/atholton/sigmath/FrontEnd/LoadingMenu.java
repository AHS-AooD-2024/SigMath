package io.github.atholton.sigmath.frontend;

import javax.swing.*;
import java.awt.*;

public class LoadingMenu extends JPanel{
    private JLabel logo;
    public LoadingMenu() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logo = new JLabel("SigΣath");
        logo.setFont(new Font("Sans Serif", Font.BOLD, 300));
        add(logo);

        setBackground(new Color(201, 218, 248));
    }
}
