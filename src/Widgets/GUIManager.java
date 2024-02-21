package Widgets;

import Items.InventoryItem;
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


public class GUIManager {

    TextFlow logs;

    public GUIManager()
    {
        this.logs = new TextFlow();
        this.logs.setStyle("-fx-background-color: #222; -fx-padding: 10;");
    }

    public Scene buildScene(Stage primaryStage) {
        Font gameFont18 = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 32);
        Font gameFont16 = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 26);
        Font gameFont14 = Font.loadFont(getClass()
                .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 22);

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("dark-theme.css");

        this.logs.setPrefHeight(root.getHeight() / 2.5);


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
        statsBox.getChildren().addAll(titleContainer, hpBox, hungerBox, energyBox/*, build_quests_constructions_pane()*/);
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

        rightBox.getChildren().addAll(playerInfoBox, buildScrollPaneInventory(scene));

        HBox.setHgrow(rightBox, Priority.ALWAYS);

        top_part.getChildren().addAll(statsBox, rightBox);

        root.setTop(top_part);


        ScrollPane logsContainer = new ScrollPane();
        logsContainer.setContent(this.logs);
        logsContainer.setFitToWidth(true);
        VBox container_logs = new VBox(this.build_combo_boxes(), logsContainer);

        HBox.setHgrow(this.logs, Priority.ALWAYS);

        root.setBottom(container_logs);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.logs.setPrefWidth(newVal.intValue() / 2.5);
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.logs.setPrefHeight(newVal.intValue() / 2.5);
        });

        return scene;
    }

    private HBox build_combo_boxes() {
        GroupedComboBoxWidget groupedComboBoxWidget = new GroupedComboBoxWidget();

        // Set main options
        groupedComboBoxWidget.setMainOptions(FXCollections.observableArrayList(
                "Collecter",
                "Explorer",
                "Se Reposer",
                "Construire",
                "Fabriquer"));

        // Set sub-options for each main option
        groupedComboBoxWidget.addSubOptions("Collecter",
                FXCollections.observableArrayList("Bois", "Pierre", "Baies", "Eau"));

        groupedComboBoxWidget.addSubOptions("Explorer",
                FXCollections.observableArrayList("Forêt dense", "Rivière", "Montagne", "Plaine"));

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

    private ScrollPane buildScrollPaneInventory(Scene scene) {
        TableView<InventoryItem> inventoryTable = setupInventoryTable();

        // Add items to the inventory
        ObservableList<InventoryItem> items = FXCollections.observableArrayList(
                new InventoryItem("Epee", "Arme",
                        1, "Assets/ICONS/base/tile049.png"),
                new InventoryItem("Bouclier", "Defense",
                        2, "Assets/ICONS/base/tile064.png"),
                new InventoryItem("Potion", "Consommable",
                        5, "Assets/ICONS/base/tile114.png")
        );
        inventoryTable.setItems(items);

        // Context menu setup omitted for brevity
        setupContextMenu(inventoryTable);

        ScrollPane scrollPane = new ScrollPane(inventoryTable);
        configureScrollPane(scrollPane, scene);
        return scrollPane;
    }

    private TableView<InventoryItem> setupInventoryTable() {
        TableView<InventoryItem> inventoryTable = new TableView<>();

        // Column for item icons
        TableColumn<InventoryItem, ImageView> iconColumn = new TableColumn<>("Icon");
        /*iconColumn.setCellValueFactory(cellData -> {
            String iconPath = cellData.getValue().getIconPath();
            ImageView imageView = null;
            if (iconPath != null && !iconPath.isEmpty()) {
                try {
                    Image image = new Image(iconPath, true); // true for background loading
                    imageView = new ImageView(image);
                    imageView.setFitHeight(32);
                    imageView.setFitWidth(32);
                    imageView.setStyle("-fx-start-margin: 5.0");
                } catch (IllegalArgumentException e) {
                    // We just authorize no icons
                }
            }
            return new SimpleObjectProperty<>(imageView);
        });*/
        iconColumn.setCellValueFactory(cellData -> {
            String iconPath = cellData.getValue().getIconPath();
            ImageView imageView = null;
            if (iconPath != null && !iconPath.isEmpty()) {
                try {
                    Image image = new Image(iconPath, true); // true for background loading
                    imageView = new ImageView(image);
                    imageView.setFitHeight(32);
                    imageView.setFitWidth(32);
                } catch (IllegalArgumentException e) {
                    // Handle the case where the icon path is invalid
                }
            }
            return new SimpleObjectProperty<>(imageView);
        });

        iconColumn.setCellFactory(col -> {
            // TableCell customization
            TableCell<InventoryItem, ImageView> cell = new TableCell<InventoryItem, ImageView>() {
                @Override
                protected void updateItem(ImageView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(item); // Wrap the ImageView in an HBox
                        hbox.setAlignment(Pos.CENTER_LEFT); // Center-left alignment for the icon
                        hbox.setPadding(new Insets(0, 0, 0, 5)); // Add padding on the left
                        setGraphic(hbox);
                    }
                }
            };
            return cell;
        });


        // Item Name
        TableColumn<InventoryItem, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellFactory((Callback)generalCellFactory);
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        // Item Type
        TableColumn<InventoryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellFactory((Callback)generalCellFactory);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));

        // Quantity
        TableColumn<InventoryItem, Integer> quantityColumn = new TableColumn<>("Quantité");
        quantityColumn.setCellFactory((Callback)generalCellFactory);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        inventoryTable.getColumns().addAll(iconColumn, itemColumn, typeColumn, quantityColumn);
        return inventoryTable;
    }

    private void setupContextMenu(TableView<InventoryItem> inventoryTable) {
        ContextMenu contextMenu = new ContextMenu();
        inventoryTable.setRowFactory(tv -> {
            TableRow<InventoryItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                    InventoryItem item = row.getItem();
                    configureContextMenuForRow(contextMenu, item);
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    private void configureContextMenuForRow(ContextMenu contextMenu, InventoryItem item) {
        contextMenu.getItems().clear(); // Clear previous items
        // Add menu items based on the item type
        switch (item.getItemType()) {
            case "Arme":
                contextMenu.getItems().addAll(new MenuItem("Utiliser"), new MenuItem("Jeter"));
                break;
            case "Defense":
                contextMenu.getItems().addAll(new MenuItem("Equiper"), new MenuItem("Jeter"));
                break;
            case "Consommable":
                contextMenu.getItems().addAll(new MenuItem("Consommer"), new MenuItem("Jeter"));
                break;
        }
    }

    private void configureScrollPane(ScrollPane scrollPane, Scene scene) {
        scrollPane.setPrefViewportWidth(scene.getWidth() / 5);
        scrollPane.setPrefViewportHeight(scene.getHeight() / 3);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #222; -fx-border-color: #AAA;");
    }

    /* Custom table factory */
    Callback<TableColumn<InventoryItem, Object>, TableCell<InventoryItem, Object>> generalCellFactory = column -> new TableCell<InventoryItem, Object>() {
        @Override
        protected void updateItem(Object item, boolean empty) {
            Font gameFont12 = Font.loadFont(getClass()
                    .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 16);
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
            } else {
                setText(item.toString());
                setFont(gameFont12);
                setStyle("-fx-alignment: CENTER;");
            }
        }
    };

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

    /* Overloading paintLog for flexibility*/
    public void paintLog( String log, Boolean lineReturn, String color, boolean setBold) {
        Text text = new Text(log + (lineReturn ? "\n" : ""));
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setStyle("-fx-fill: " + color + ";" + (setBold ? "-fx-font-weight: bold;" : "")); // Couleur du texte
        this.logs.getChildren().add(text);
    }
    public void paintLog( String log, Boolean lineReturn, String color) {
        Text text = new Text(log + (lineReturn ? "\n" : ""));
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setStyle("-fx-fill: " + color + ";");
        this.logs.getChildren().add(text);
    }
    public void paintLog( String log, Boolean lineReturn) {
        Text text = new Text(log + (lineReturn ? "\n" : ""));
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setStyle("-fx-fill: white;");
        this.logs.getChildren().add(text);
    }
}
