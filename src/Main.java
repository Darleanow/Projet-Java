/*
import Game.*;
import Player.Player;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player player = new Player();
        game.run_game(player);
    }
}*/
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import Widgets.*;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GUIManager gui = new GUIManager();
        Scene scene = gui.buildScene(primaryStage);

/*        gui.paintLog( "Hello world", true, "red", true);
        gui.paintLog( "Hello world", true, "red");
        gui.paintLog( "Hello world", true);*/
        primaryStage.setTitle("Survival Game");

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}