import Game.GameTime;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Widgets.*;
import Player.*;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GUIManager gui = new GUIManager();
        GameTime timeline = new GameTime();
        Scene scene = gui.buildScene(primaryStage, timeline);

        primaryStage.setTitle("Survival Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        gui.paintLog( "Hello dear adventurer, you just woke up on a apparent desert island, you'll have to survive " +
                "the longest possible !\n" +
                "You can select actions by clicking on the dropdown menu above this !", true);
        /*gui.paintLog( "Hello world", true, "red", true);
        gui.paintLog( "Hello world", true, "red");
        gui.paintLog( "Hello world", true);*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}