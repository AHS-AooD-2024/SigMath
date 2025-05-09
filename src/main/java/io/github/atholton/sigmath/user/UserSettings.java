package io.github.atholton.sigmath.user;

import java.io.Serializable;

public class UserSettings implements Serializable
{
    private static final long serialVersionUID = -2980982098L;
    private int fontSize, toolBarSize;
    private static UserSettings instance;
    private UserSettings()
    {
        //default
        fontSize = 100;
        toolBarSize = 100;
    }
    public int getFontSize()
    {
        return fontSize;
    }
    public void setFontSize(int size)
    {
        fontSize = size;
    }
    public int getToolBarSize()
    {
        return toolBarSize;
    }
    public void setToolBarSize(int size)
    {
        toolBarSize = size;
    }
    public static UserSettings get()
    {
        if (instance == null) instance = new UserSettings();
        return instance;
    }
    public static void set(UserSettings in)
    {
        instance = in;
    }
}
