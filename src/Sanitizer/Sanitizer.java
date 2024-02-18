package Sanitizer;

import java.util.ArrayList;
import java.util.Scanner;

public class Sanitizer
{
    public Sanitizer()
    {

    }

    public <T> boolean check_valid(T value, ArrayList<T> options) {
        // Assuming T is always String in this context, otherwise, this method should be adjusted or specifically typed for String.
        return options.contains(value) && !((String)value).trim().isEmpty();
    }

    public int to_int(String value)
    {
        return Integer.parseInt(value);
    }

    public String to_string(int value)
    {
        return String.valueOf(value);
    }

    public String handle_input()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            }
            else
            {
                throw new Exception("Not implemented");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
