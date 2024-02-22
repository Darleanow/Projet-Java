import Game.GameTime;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Widgets.*;
import Player.*;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameTime timeline = new GameTime();
        GUIManager gui = new GUIManager(timeline);
        Scene scene = gui.buildScene(primaryStage);

        primaryStage.setTitle("Survival Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        Logger.paintLog( "Hello dear adventurer, you just woke up on a apparent desert island, you'll have to survive " +
                "the longest possible !\n" +
                "You can select actions by clicking on the dropdown menu above this !", true);
        Logger.paintLog( "Hello world", true, "red", true);
        Logger.paintLog( "Hello world", true, "red");
        Logger.paintLog( "Hello world", true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}