package io.github.atholton.sigmath.equationtree;

import java.util.ArrayList;
import java.util.List;

public class Test {
    /*
    public static void main(String[] args) {
        String input = "4 + 5";
        List<String> tokens = new ArrayList<>();

        String build = "";
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c == ' ') continue;
            if (isNumber(c))
            {
                build += String.valueOf(c);
            }
            else
            {
                if (!build.equals(""))
                {
                    tokens.add(build);
                    build = "";
                }
                //if previous isn't an operator, maybe add a * sign in between, b/c 2x or 2sintheta
                tokens.add(String.valueOf(c));
            }
        }
        if (!build.equals(""))
        {
            tokens.add(build);
        }
        System.out.print(tokens);
    }
    private static boolean isNumber(char c)
    {
        return c == '.' || (c >= '0' &&c <= '9');
    }
        */
    public static void main(String[] args) {
        System.out.println(true ^ false);
        System.out.println(true ^ true);
        System.out.println(false ^ false);
    }
}
