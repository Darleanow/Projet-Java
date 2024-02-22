package Widgets;

import Game.FontManager;
import Game.GameTime;
import Items.InventoryItem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Duration;
import Player.*;


public class GUIManager {

    private Player player;
    private PlayerStatsPanel playerPannel;
    private WeatherPanel weatherPanel;
    private  InventoryPanel inventoryPanel;
    private PlayerActions playerActions;
    private Logger logger;
    private FontManager fontManager;

    public GUIManager(GameTime gameTime) {
        this.player = new Player();
        this.fontManager = new FontManager();
        this.playerPannel = new PlayerStatsPanel(this.player, this.fontManager);
        this.weatherPanel = new WeatherPanel(gameTime,this.fontManager);
        this.playerActions = new PlayerActions();
        this.inventoryPanel = new InventoryPanel();
        this.logger = new Logger();
    }

    public Scene buildScene(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("dark-theme.css");

        HBox topPart = new HBox();
        topPart.setFillHeight(true);

        // Assuming playerPanel.createPanel() returns a VBox or similar
        VBox playerPanelBox = this.playerPannel.createPanel();
        // Set right border to 0 for the player panel
        playerPanelBox.setStyle("-fx-background-color: #222; -fx-border-color: #999; -fx-border-width: 2 0 2 2;");

        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(10));
        // Set left border to 0 for the right box
        rightBox.setStyle("-fx-background-color: #222; -fx-border-color: #999; -fx-border-width: 2 2 2 2;");
        rightBox.getChildren().addAll(weatherPanel.createPanel(), this.inventoryPanel.createPanel(scene));

        topPart.getChildren().addAll(playerPanelBox, rightBox);
        HBox.setHgrow(rightBox, Priority.ALWAYS);

        VBox containerLogs = new VBox(this.playerActions.createPanel(), this.logger.buildLogger(primaryStage, root));

        root.setTop(topPart);
        root.setBottom(containerLogs);

        return scene;
    }


/*    private TabPane build_quests_constructions_pane()
    {
        // Quetes et constructions
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab questsTab = new Tab("Quêtes");
        VBox questsContent = new VBox(5);
        questsContent.setPadding(new Insets(10));

        // Exemple d'ajout d'une quête
        Button questButton = new Button("Nom de quête");
        questButton.setOnAction(event -> {
            // Affichage d'une popup pour les détails de la quête
            Alert questDetails = new Alert(Alert.AlertType.INFORMATION);
            questDetails.setTitle("Détails de la quête");
            questDetails.setHeaderText("Nom de quête");
            questDetails.setContentText("Description de la quête\nRécompense: ");
            // Ajoutez ici les icônes des récompenses si nécessaire
            questDetails.showAndWait();
        });

        questsContent.getChildren().add(questButton);

        ScrollPane questsScrollPane = new ScrollPane(questsContent);
        questsScrollPane.setFitToWidth(true);
        questsTab.setContent(questsScrollPane);


        Tab constructionsTab = new Tab("Constructions");
        VBox constructionsContent = new VBox(5);
        constructionsContent.setPadding(new Insets(10));

        // Exemple d'ajout d'une construction
        HBox constructionBox = new HBox(10);
        constructionBox.setAlignment(Pos.CENTER_LEFT);
        // Ajoutez une ImageView pour l'icône si vous en avez
        Label constructionLabel = new Label("Nom de construction");
        Label constructionUtilite = new Label("À quoi ça sert");
        constructionBox.getChildren().addAll(constructionLabel, constructionUtilite);

        constructionsContent.getChildren().add(constructionBox);

        ScrollPane constructionsScrollPane = new ScrollPane(constructionsContent);
        constructionsScrollPane.setFitToWidth(true);
        constructionsTab.setContent(constructionsScrollPane);

        tabPane.getTabs().addAll(questsTab, constructionsTab);

        return tabPane;
    }*/
}
