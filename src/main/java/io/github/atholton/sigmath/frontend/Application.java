package io.github.atholton.sigmath.frontend;

import javax.swing.*;

import org.scilab.forge.jlatexmath.TeXIcon;

import io.github.atholton.sigmath.Sigmath;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.latex.TeXLabel;


public class Application extends JFrame implements Runnable
{
    private static Application instance;
    public static void main(String[] args) {
        Application sigmath = get();
        SwingUtilities.invokeLater(sigmath);
    }

    private Application() {
        super("SigMath");
    }

    @Override
    public void run() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //MainMenu mm = new MainMenu();
        //AllTopicsMenu m = AllTopicsMenu.get();
        add(new ProblemsMenu());
        //add(mm);
        //add(m);

        pack();
    }
    public static Application get()
    {
        if (instance == null) instance = new Application();
        return instance;
    }
}
