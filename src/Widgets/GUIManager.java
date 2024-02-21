package Widgets;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;


public class GUIManager {

    public Scene build_scene(Stage primaryStage) {
        Font gameFont18 = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 32);
        Font gameFont16 = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 26);
        Font gameFont14 = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 22);

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
        title_stats.setFont(gameFont18);
        title_stats.setTextAlignment(TextAlignment.CENTER);
        title_stats.setFill(Color.web("#FFC857"));
        StackPane titleContainer = new StackPane(title_stats);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(0, 0, 10, 0));

        // HP
        Text hpText = new Text("HP: 100");
        // On high resolution screens, antialiasing might affect the render of small fonts
        // We set the smoothing type to lcd, and it's fixed !
        hpText.setFontSmoothingType(FontSmoothingType.LCD);
        hpText.setFont(gameFont14);
        hpText.setFill(Color.web("#E63946"));
        HBox hpBox = new HBox(hpText);
        hpBox.setAlignment(Pos.CENTER_LEFT);

        // Faim
        Text hungerText = new Text("Faim: 0%");
        hungerText.setFont(gameFont14);
        hungerText.setFontSmoothingType(FontSmoothingType.LCD);
        hungerText.setFill(Color.web("#e67e39"));
        HBox hungerBox = new HBox(hungerText);
        hungerBox.setAlignment(Pos.CENTER_LEFT);

        // Energie
        Text energyText = new Text("Energie: 50");
        energyText.setFont(gameFont14);
        energyText.setFontSmoothingType(FontSmoothingType.LCD);
        energyText.setFill(Color.web("#2A9D8F"));
        HBox energyBox = new HBox(energyText);
        energyBox.setAlignment(Pos.CENTER_LEFT);

        // Ajouter les éléments à la VBox
        statsBox.getChildren().addAll(titleContainer, hpBox, hungerBox, energyBox);
        HBox.setHgrow(statsBox, Priority.ALWAYS);


        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(10));
        rightBox.setStyle("-fx-background-color: #222; -fx-border-color: #999; -fx-border-width: 2;");

        Text nameText = new Text("Joueur1");
        nameText.setFont(gameFont14);
        nameText.setFill(Color.web("#FF9F1C")); // Updated color for preference
        nameText.setFontSmoothingType(FontSmoothingType.LCD);
        nameText.setTextAlignment(TextAlignment.CENTER);

        // Center the player name in its container
        StackPane nameContainer = new StackPane(nameText);
        nameContainer.setAlignment(Pos.CENTER);

        // TODO: Make this dynamic
        ImageView currentWeather = this.getImageFromTime("22:00");
        Text dayText = new Text("Jour: 01");
        dayText.setFont(gameFont14);
        dayText.setFill(Color.web("#FF6B6B")); // Maintained color for differentiation
        dayText.setFontSmoothingType(FontSmoothingType.LCD);

        Text timeText = new Text("Heure: 12:00");
        timeText.setFont(gameFont14);
        timeText.setFill(Color.web("#FFE66D")); // Kept bright color for visibility
        timeText.setFontSmoothingType(FontSmoothingType.LCD);

        HBox dateTimeBox = new HBox(10);
        dateTimeBox.getChildren().addAll(currentWeather, dayText, timeText);
        dateTimeBox.setAlignment(Pos.CENTER);

        VBox playerInfoBox = new VBox(10, nameContainer, dateTimeBox);

        rightBox.getChildren().addAll(playerInfoBox, build_scroll_pane_inventory(scene));


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

    private ScrollPane build_scroll_pane_inventory(Scene scene) {
        // Inventaire avec trois colonnes : Item, Type, et Quantité
        TableView<ObservableList<String>> inventoryTable = new TableView<>();
        TableColumn<ObservableList<String>, String> itemColumn = new TableColumn<>("Item");
        TableColumn<ObservableList<String>, String> typeColumn = new TableColumn<>("Type");
        TableColumn<ObservableList<String>, String> quantityColumn = new TableColumn<>("Quantité");

        // Configuration des colonnes
        itemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));

        // Ajout des colonnes à la table
        inventoryTable.getColumns().addAll(itemColumn, typeColumn, quantityColumn);

        // Exemples d'items avec types et quantités
        ObservableList<ObservableList<String>> items = FXCollections.observableArrayList();
        items.add(FXCollections.observableArrayList("Épée", "Arme", "1"));
        items.add(FXCollections.observableArrayList("Bouclier", "Défense", "2"));
        items.add(FXCollections.observableArrayList("Potion", "Consommable", "5"));

        // Définition des items dans la table
        inventoryTable.setItems(items);

        // Configuration du ScrollPane
        ScrollPane scrollPane = new ScrollPane(inventoryTable);
        scrollPane.setPrefViewportWidth(scene.getWidth() / 5);
        scrollPane.setPrefViewportHeight(scene.getHeight() / 3);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #222; -fx-border-color: #AAA;");

        return scrollPane;
    }

    private ImageView getImageFromTime(String time)
    {
        Image image;

        // TODO: Test this
        int hour = Integer.parseInt(time.split(":")[0]);

        if (hour >= 6 && hour < 8) {
            image = new Image("Assets/GUI/WEATHER/sunrise.png");
        } else if (hour >= 8 && hour < 18) {
            image = new Image("Assets/GUI/WEATHER/sun.png");
        } else if (hour >= 18 && hour < 21) {
            image = new Image("Assets/GUI/WEATHER/sunset.png");
        } else {
            image = new Image("Assets/GUI/WEATHER/moon.png");
        }


        return new ImageView(image);
    }

}
