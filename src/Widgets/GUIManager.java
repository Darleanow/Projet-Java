package Widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIManager {

    public Scene build_scene(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("dark-theme.css");

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(root.getHeight() / 2.5);


        HBox top_part = new HBox(10); // Espace entre les éléments
        top_part.setFillHeight(true);

        // Stats du joueur avec améliorations visuelles
        VBox statsBox = new VBox(10); // Espace vertical avec espace
        statsBox.setPadding(new Insets(15)); // Marge intérieure augmentée
        statsBox.setStyle("-fx-background-color: #222; -fx-border-color: #AAA; -fx-border-width: 2;"); // Fond et bordure

        Text title_stats = new Text("Statistiques");
        title_stats.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Police plus grande et grasse
        title_stats.setFill(Color.web("#FFD700"));

        Label hpLabel = new Label("HP: 100");
        hpLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #00FF00;"); // Vert brillant, taille de police augmentée

        Label energyLabel = new Label("Energie: 50");
        energyLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #1E90FF;"); // Bleu Dodger, taille de police augmentée

        statsBox.getChildren().addAll(title_stats, hpLabel, energyLabel);
        HBox.setHgrow(statsBox, Priority.ALWAYS); // Permet à statsBox de toujours s'étendre horizontalement


        // Info du joueur et inventaire avec améliorations visuelles
        VBox rightBox = new VBox(20); // Espace vertical plus grand pour une meilleure organisation
        rightBox.setPadding(new Insets(15)); // Marge intérieure légèrement augmentée
        rightBox.setStyle("-fx-background-color: #222; -fx-border-color: #AAA; -fx-border-width: 2;"); // Couleur de fond sombre et bordure claire

        // Info du joueur (nom et heure/date) avec mise en forme améliorée
        HBox playerInfo = new HBox(10); // Espacement entre les éléments
        playerInfo.setAlignment(Pos.TOP_CENTER); // Alignement à droite pour une distinction claire
        playerInfo.setStyle("-fx-background-color: #444; -fx-padding: 5; -fx-border-radius: 5; -fx-background-radius: 5;"); // Fond gris foncé, bordures arrondies

        Label nameLabel = new Label("Nom: Joueur1");
        nameLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #E0E0E0;"); // Taille de police et couleur de texte pour un contraste élevé

        Label dateLabel = new Label("Jour: 01 Heure: 12:00");
        dateLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #E0E0E0;"); // Identique à nameLabel pour la cohérence

        playerInfo.getChildren().addAll(nameLabel, dateLabel);

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
