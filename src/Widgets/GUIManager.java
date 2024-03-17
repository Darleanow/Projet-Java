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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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
    private final Sidebar sidebar;
    private final Stats stats;

    public GUIManager() {
        this.sidebar = new Sidebar();
        this.stats = new Stats();
    }

    public Scene buildScene(Stage primaryStage) {

        // Root layout is now a StackPane
        StackPane root = new StackPane();

        // Apply CSS styling
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add("dark-theme.css");

        // Create the logger and stats bar as before
        Logger logger = new Logger(300);

        // Create a BorderPane for structured layout
        BorderPane borderPane = new BorderPane();

        HBox topArea = new HBox(logger);
        HBox.setHgrow(logger, Priority.ALWAYS); // Ensure logger takes full width

        borderPane.setTop(topArea);

        // Set the BorderPane in the center of the StackPane
        root.getChildren().add(borderPane);

        // Sidebar container created and positioned absolutely
        StackPane sidebarContainer = sidebar.createSidebarContainer(scene);
        sidebarContainer.setMaxWidth(200); // Set the max width for the sidebar

        // Absolute positioning of the sidebar on the left
        StackPane.setAlignment(sidebarContainer, Pos.CENTER_LEFT);
        root.getChildren().add(sidebarContainer);



        Image image = new Image("Assets/GUI/cursor.png"); // Load cursor image
        Cursor cursor = new ImageCursor(image);
        scene.setCursor(cursor); // Set custom cursor for the entire scene

        // Update the log from anywhere in the code
        logger.log("Hello world! This is a kind of long message for you to write, i hope you'll keep up to it nd of long message for you to write, i hope you'll keep up to it");
        logger.log("Hello world!");






        // Update stats from anywhere in the code

        primaryStage.setScene(scene);
        primaryStage.show();

        return scene;
    }
}
