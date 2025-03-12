package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import org.scilab.forge.jlatexmath.TeXIcon;

import io.github.atholton.sigmath.Sigmath;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.latex.TeXLabel;

public class Application extends JFrame implements Runnable{
    public static void main(String[] args) {
        Application sigmath = new Application();
        SwingUtilities.invokeLater(sigmath);
    }

    public Application() {
        super("SigMath");
    }

    @Override
    public void run() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //MainMenu mm = new MainMenu();
        AllTopicsMenu m = new AllTopicsMenu();
        //add(mm);
        add(m);

        pack();
    }
}
