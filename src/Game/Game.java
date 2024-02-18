package Game;

import Player.*;
import Sanitizer.*;

import java.util.Arrays;
import java.util.ArrayList;

public class Game {

    TimeManager time_manager;

    public Game() {
        this.time_manager = new TimeManager();
    }

    public void run_game(Player player) {
        Sanitizer sanitizer = new Sanitizer();

        while (player.is_alive()) {
            System.out.println("""
                    Select your action:
                    1. Add x health
                    2. Remove x health
                    3. Set day
                    4. Set night
                    """);

            String choice = sanitizer.handle_input();
            if (sanitizer.check_valid(sanitizer.to_int(choice), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)))) {
                int value_choice = sanitizer.to_int(choice);
                switch (value_choice) {
                    case 1:
                        System.out.println("Enter amount of health to add:");
                        int addAmount = Integer.parseInt(sanitizer.handle_input());
                        player.add_health(addAmount);
                        break;
                    case 2:
                        System.out.println("Enter amount of health to remove:");
                        int removeAmount = Integer.parseInt(sanitizer.handle_input());
                        player.remove_health(removeAmount);
                        break;
                    case 3:
                        this.time_manager.set_day();
                        break;
                    case 4:
                        this.time_manager.set_night();
                        break;
                    default:
                        System.out.println("Not implemented");
                }
                if (!player.is_alive()) {
                    System.out.println("Player died, ending game");
                    break;
                }

                System.out.println("Press enter to continue...");

                sanitizer.handle_input();
                sanitizer.clearConsole();
            }
            System.out.println("Player health: " + player.get_health());
            handle_time();
        }
    }

    private void handle_time() {
        this.time_manager.increment_time();

        System.out.println("Current time: " + this.time_manager.get_time());
        System.out.println("It's currently " + (this.time_manager.is_day() ? "day" : "night"));
    }
}
