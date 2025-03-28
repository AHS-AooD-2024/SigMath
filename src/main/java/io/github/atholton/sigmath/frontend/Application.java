package io.github.atholton.sigmath.frontend;

import java.awt.Dimension;

import javax.swing.*;

import org.scilab.forge.jlatexmath.TeXIcon;

import io.github.atholton.sigmath.Sigmath;
import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.latex.TeXLabel;
import io.github.atholton.sigmath.user.UserSettings;
import io.github.atholton.sigmath.user.UserStats;


public class Application extends JFrame implements Runnable
{
    private static Application instance;
    public static Menu[] resizableMenus;
    public static void main(String[] args) {
        Application sigmath = get();
        SwingUtilities.invokeLater(sigmath);
    }

    private Application() {
        super("SigMath");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1000, 700));
        UserStats.get();
        resizableMenus = new Menu[]{AllTopicsMenu.get(), RecentTopicsMenu.get(), SettingsMenu.get(), Toolbar.get()};
        resizeFonts();
    }

    @Override
    public void run() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //LoadingMenu lm = new LoadingMenu();
        MainMenu mm = MainMenu.get();
        //AllTopicsMenu m = AllTopicsMenu.get();
        add(mm);
        //add(lm);

        pack();
    }
    public static void resizeFonts()
    {
        for (Menu menu : resizableMenus)
        {
            menu.updateFontSizes();
        }
    }
    public static Application get()
    {
        if (instance == null) instance = new Application();
        return instance;
    }
}
