package Widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GUIManager {

    public Scene build_scene(Stage primaryStage) {
        Font gameFont = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/super_legend_boy/super-legend-boy.otf"), 18);

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("dark-theme.css");

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(root.getHeight() / 2.5);


        HBox top_part = new HBox();
        top_part.setFillHeight(true);

        // Stats du joueur
        VBox statsBox = new VBox(10); // Ajoute un espacement de 10 entre les éléments de la VBox
        statsBox.setPadding(new Insets(15));
        statsBox.setStyle("-fx-background-color: #222; -fx-border-color: #AAA; -fx-border-width: 1;");

        // Titre des statistiques
        Text title_stats = new Text("Statistiques");
        title_stats.setFont(gameFont);
        title_stats.setTextAlignment(TextAlignment.CENTER);
        title_stats.setFill(Color.web("#FFC857"));
        StackPane titleContainer = new StackPane(title_stats);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(0, 0, 10, 0));

        // HP
        Text hpText = new Text("HP: 100");
        hpText.setFont(gameFont);
        hpText.setStyle("-fx-font-size: 14; -fx-fill: #E63946");
        HBox hpBox = new HBox(hpText);
        hpBox.setAlignment(Pos.CENTER_LEFT);

        // Energie
        Text energyText = new Text("Energie: 50");
        energyText.setFont(gameFont);
        energyText.setStyle("-fx-font-size: 14; -fx-fill: #2A9D8F");
        HBox energyBox = new HBox(energyText);
        energyBox.setAlignment(Pos.CENTER_LEFT);

        // Ajouter les éléments à la VBox
        statsBox.getChildren().addAll(titleContainer, hpBox, energyBox);
        HBox.setHgrow(statsBox, Priority.ALWAYS);


        // Info du joueur et inventaire
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(15));
        rightBox.setStyle("-fx-background-color: #222; -fx-border-color: #AAA; -fx-border-width: 1;");

        // Info du joueur (nom et heure/date)
        HBox playerInfo = new HBox(10);
        playerInfo.setAlignment(Pos.TOP_CENTER);
        playerInfo.setStyle("-fx-background-color: #444; -fx-padding: 5; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label nameLabel = new Label("Nom: Joueur1");
        nameLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #E0E0E0;");

        Label dateLabel = new Label("Jour: 01 Heure: 12:00");
        dateLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #E0E0E0;");

        playerInfo.getChildren().addAll(nameLabel, dateLabel);
        top_part.setSpacing(-1);
        rightBox.getChildren().addAll(playerInfo, this.build_scroll_pane_inventory(scene));
        HBox.setHgrow(rightBox, Priority.ALWAYS);


        top_part.getChildren().addAll(statsBox, rightBox);

        root.setTop(top_part);

        VBox container_logs = new VBox(this.build_combo_boxes(), logArea);

        HBox.setHgrow(logArea, Priority.ALWAYS);

        root.setBottom(container_logs);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            logArea.setPrefWidth(newVal.intValue() / 2.5);
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            logArea.setPrefHeight(newVal.intValue() / 2.5);
        });

        return scene;
    }

    private HBox build_combo_boxes() {
        GroupedComboBoxWidget groupedComboBoxWidget = new GroupedComboBoxWidget();

        // Set main options
        groupedComboBoxWidget.setMainOptions(FXCollections.observableArrayList(
                "Collecter",
                "Explorer",
                "Consommer",
                "Se Reposer",
                "Construire",
                "Fabriquer"));

        // Set sub-options for each main option
        groupedComboBoxWidget.addSubOptions("Collecter",
                FXCollections.observableArrayList("Bois", "Pierre", "Baies", "Eau"));

        groupedComboBoxWidget.addSubOptions("Explorer",
                FXCollections.observableArrayList("Forêt dense", "Rivière", "Montagne", "Plaine"));

        // TODO: Fetch this from inventory
        groupedComboBoxWidget.addSubOptions("Consommer",
                FXCollections.observableArrayList("Baies", "Viande crue", "Viande cuite", "Eau purifiée"));

        groupedComboBoxWidget.addSubOptions("Se Reposer",
                FXCollections.observableArrayList("Courte sieste", "Nuit complète", "Méditation"));

        groupedComboBoxWidget.addSubOptions("Construire",
                FXCollections.observableArrayList("Abri de fortune", "Cabane en bois", "Maison en pierre", "Forteresse"));

        groupedComboBoxWidget.addSubOptions("Fabriquer",
                FXCollections.observableArrayList("Outils en pierre", "Arc et flèches", "Lance", "Poterie pour eau"));


        HBox container_actions = new HBox(groupedComboBoxWidget);
        container_actions.setAlignment(Pos.BOTTOM_LEFT);

        return container_actions;
    }

    private ScrollPane build_scroll_pane_inventory(Scene scene)
    {
        // Inventaire
        TableView<String> inventoryTable = new TableView<>();
        TableColumn<String, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        ObservableList<String> items = FXCollections.observableArrayList(
                new String("Item 1"),
                new String("Item 2"),
                new String("Item 3")
        );
        inventoryTable.setItems(items);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(inventoryTable);
        scrollPane.setPrefViewportWidth(scene.getWidth() / 5);
        scrollPane.setPrefViewportHeight(scene.getHeight() / 3);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }
}
