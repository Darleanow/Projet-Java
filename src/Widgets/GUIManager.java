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

    private final TextFlow logs = new TextFlow();
    private FontManager fontManager;
    private Font gameFont32, gameFont26, gameFont22;

    private Text dayText, timeText;
    private ImageView currentWeather;
    public GUIManager() {
        this.player = new Player();
        this.fontManager = new FontManager();
        this.playerPannel = new PlayerStatsPanel(this.player, this.fontManager);

        configureLogs();
        loadFonts();
        // Load gui

    }

    private void configureLogs() {
        this.logs.setStyle("-fx-background-color: #222; -fx-padding: 10;");
    }

    private void loadFonts() {
        gameFont32 = this.fontManager.loadFont(32);
        gameFont26 = this.fontManager.loadFont(26);
        gameFont22 = this.fontManager.loadFont(22);
    }

    public Scene buildScene(Stage primaryStage, GameTime gameTime) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("dark-theme.css");

        this.logs.setPrefHeight(root.getHeight() / 2.5);


        HBox top_part = new HBox();
        top_part.setFillHeight(true);



        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(10));
        rightBox.setStyle("-fx-background-color: #222; -fx-border-color: #999; -fx-border-width: 2;");

        Text nameText = new Text("Joueur1");
        nameText.setFont(gameFont22);
        nameText.setFill(Color.web("#FF9F1C"));
        nameText.setFontSmoothingType(FontSmoothingType.LCD);
        nameText.setTextAlignment(TextAlignment.CENTER);

        // Center the player name in its container&
        StackPane nameContainer = new StackPane(nameText);
        nameContainer.setAlignment(Pos.CENTER);

        // TODO: Make this dynamic
        this.currentWeather = this.getImageFromTime("6");
        this.dayText = new Text();
        dayText.setFont(gameFont22);
        dayText.setFill(Color.web("#FF6B6B"));
        dayText.setFontSmoothingType(FontSmoothingType.LCD);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            gameTime.incrementHour();
            updateDateTimeDisplay(gameTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        this.timeText = new Text();
        timeText.setFont(gameFont22);
        timeText.setFill(Color.web("#FFE66D"));
        timeText.setFontSmoothingType(FontSmoothingType.LCD);

        updateDateTimeDisplay(gameTime);

        HBox dateTimeBox = new HBox(10);
        dateTimeBox.getChildren().addAll(currentWeather, dayText, timeText);
        dateTimeBox.setAlignment(Pos.CENTER);

        VBox playerInfoBox = new VBox(10, nameContainer, dateTimeBox);

        rightBox.getChildren().addAll(playerInfoBox, buildScrollPaneInventory(scene));

        HBox.setHgrow(rightBox, Priority.ALWAYS);

        top_part.getChildren().addAll(this.playerPannel.createPanel(), rightBox);

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

    private void updateDateTimeDisplay(GameTime gameTime) {
        this.dayText.setText("Jour: " + gameTime.getDay());
        this.timeText.setText("Heure: " + String.format("%02d:00", gameTime.getHour()));
        this.currentWeather.setImage(getImageFromTime(String.format("%02d:00", gameTime.getHour())).getImage());
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
