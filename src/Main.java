import Game.*;
import Player.Player;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player player = new Player();
        game.run_game(player);
    }
}