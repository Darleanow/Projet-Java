package Game;
import Player.*;
import Sanitizer.*;

import java.util.Arrays;
import java.util.ArrayList;

public class Game {

    int current_time = 10;
    int NIGHT_TIME = 19;
    int DAY_TIME = 7;


    public Game()
    {
    }

    public void run_game(Player player)
    {
        Sanitizer sanitizer = new Sanitizer();

        while(player.is_alive())
        {
            System.out.println("Select your action:\n"
                    + "1. Option 1\n"
                    + "2. Option 2\n"
                    + "3. Option 3\n"
                    + "4. Option 4\n");

            String choice = sanitizer.handle_input();
            if (sanitizer.check_valid(choice, new ArrayList<String>(Arrays.asList("1", "2", "3", "4"))))
            {
                System.out.println("You have chosen: " + choice);
                System.out.println("Press enter to continue...");

                sanitizer.handle_input();
                sanitizer.clearConsole();
            }

            handle_time();
        }
    }

    private void handle_time()
    {
        this.current_time++;
        if (this.current_time == 24)
        {
            this.current_time = 0;
        }

        System.out.println("Current time: " + this.current_time);
        System.out.println("It's currently " + (this.current_time > this.NIGHT_TIME ? "night" : "day"));
    }
}
