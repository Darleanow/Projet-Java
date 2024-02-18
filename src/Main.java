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
        primaryStage.setTitle("Survival Game");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("dark-theme.css");

        VBox main_layout = new VBox();
        HBox top_part = new HBox();

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(root.getHeight() / 2.5);

        GroupedComboBoxWidget groupedComboBoxWidget = new GroupedComboBoxWidget();

        // Set main options
        groupedComboBoxWidget.setMainOptions(FXCollections.observableArrayList("Country A", "Country B"));

        // Set sub-options for each main option
        groupedComboBoxWidget.addSubOptions("Country A", FXCollections.observableArrayList("State A1", "State A2"));
        groupedComboBoxWidget.addSubOptions("Country B", FXCollections.observableArrayList("State B1", "State B2"));

        HBox container_actions = new HBox(groupedComboBoxWidget);
        container_actions.setAlignment(Pos.BOTTOM_CENTER);


        VBox container_logs = new VBox(container_actions, logArea);

        HBox.setHgrow(logArea, Priority.ALWAYS);

        root.setBottom(container_logs);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            logArea.setPrefWidth(newVal.intValue() / 2.5);
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            logArea.setPrefHeight(newVal.intValue() / 2.5);
        });
        /*// Zone d'état du joueur
        VBox playerStatus = new VBox(10); // Espacement pour une meilleure lisibilité
        Label healthLabel = new Label("Health: 100");
        Label fatigueLabel = new Label("Fatigue: 0");
        Label resourcesLabel = new Label("Resources: Wood(0), Stone(0)"); // Exemple de ressources
        playerStatus.getChildren().addAll(healthLabel, fatigueLabel, resourcesLabel);
        root.setLeft(playerStatus); // Position à gauche pour plus d'espace

        // Panneau d'actions
        VBox actions = new VBox(10); // Utilisez VBox pour un alignement vertical
        Button exploreBtn = new Button("Explore");
        Button collectBtn = new Button("Collect Resources");
        Button buildBtn = new Button("Build Shelter");
        Button restBtn = new Button("Rest");
        actions.getChildren().addAll(exploreBtn, collectBtn, buildBtn, restBtn);
        root.setCenter(actions);

        // Console de log
        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150); // Ajustez selon le besoin
        root.setBottom(logArea);

        // Gestion des actions
        exploreBtn.setOnAction(event -> {
            logArea.appendText("Exploring...\n");
            // Implémentez la logique d'exploration ici
        });
        collectBtn.setOnAction(event -> logArea.appendText("Collecting Resources...\n"));
        buildBtn.setOnAction(event -> logArea.appendText("Building Shelter...\n"));
        restBtn.setOnAction(event -> {
            logArea.appendText("Resting...\n");
            // Mettez à jour la fatigue et la santé ici
        });
*/
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}