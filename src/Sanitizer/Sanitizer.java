package Sanitizer;

import java.util.ArrayList;
import java.util.Scanner;

public class Sanitizer
{
    Scanner scanner;
    public Sanitizer()
    {
        this.scanner = new Scanner(System.in);
    }

    public <T> boolean check_valid(T value, ArrayList<T> options) {
        return options.contains(value);
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
        return this.scanner.nextLine();
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
