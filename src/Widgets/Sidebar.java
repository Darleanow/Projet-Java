package Widgets;

        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.control.Button;
        import javafx.scene.effect.DropShadow;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;

public class Sidebar {

    private Image loadImageWithHighQuality(String path, double requestedWidth, double requestedHeight) {
        Image image = new Image(getClass().getResourceAsStream(path), requestedWidth, requestedHeight, false, false);
        return image;
    }

    public StackPane createSidebarContainer() {
        VBox sidebar = new VBox();
        sidebar.setAlignment(Pos.CENTER);
        sidebar.setSpacing(20); // Increased spacing

        // Create buttons
        Button buttonExplore = createButtonWithIcon("explore2.png");
        Button buttonCraft = createButtonWithIcon("craft.png");
        Button buttonBuild = createButtonWithIcon("build.png");
        Button buttonSleep = createButtonWithIcon("sleep.png");

        // Adding buttons to the sidebar VBox
        sidebar.getChildren().addAll(buttonExplore, buttonCraft, buttonBuild, buttonSleep);

        // Minimize button with an icon
        Button minimizeButton = createSmallButtonWithIcon("reduce.png");

        // Expand button, positioned to the left of the sidebar
        Button expandButton = createSmallButtonWithIcon("expand.png");
        expandButton.setVisible(false); // Initially hidden

        // Use a BorderPane for layout inside the StackPane
        BorderPane layout = new BorderPane();

        // Set the sidebar in the center of the BorderPane
        layout.setCenter(sidebar);

        // Set the minimize button to the right of the center buttons
        layout.setRight(minimizeButton);
        BorderPane.setAlignment(minimizeButton, Pos.CENTER); // Align to center vertically
        BorderPane.setMargin(minimizeButton, new Insets(0, 30, 0, 0)); // Right margin

        StackPane sidebarContainer = new StackPane(layout);

        // Align expandButton to the left of the sidebar
        sidebarContainer.getChildren().add(expandButton);
        StackPane.setAlignment(expandButton, Pos.CENTER_LEFT);
        StackPane.setMargin(expandButton, new Insets(0, 0, 0, 20)); // Left margin for expand button

        minimizeButton.setOnAction(e -> {
            sidebar.setVisible(false);
            expandButton.setVisible(true);
            minimizeButton.setVisible(false); // Also hide the minimizeButton
        });

        expandButton.setOnAction(e -> {
            sidebar.setVisible(true);
            expandButton.setVisible(false);
            minimizeButton.setVisible(true); // Show the minimizeButton again
        });

        // Add padding to the sidebar for spacing from the left
        sidebar.setPadding(new Insets(10, 0, 20, 20));

        return sidebarContainer;
    }

    private Button createButtonWithIcon(String iconName) {
        Button button = new Button();

        // Assuming you also have a generic background image for buttons
        String buttonBackgroundImageUrl = getClass().getResource("/Assets/GUI/ActionsIcons/Button.png").toExternalForm();

        Image icon = loadImageWithHighQuality("/Assets/GUI/ActionsIcons/" + iconName, 48, 48);
        ImageView iconView = new ImageView(icon);
        iconView.setSmooth(false); // Disable smoothing for pixel art
        button.setGraphic(iconView);

        button.setMinSize(64, 64);
        button.setMaxSize(64, 64);
        button.setPrefSize(64, 64);

        // Now including the background image in the button style
        String baseStyle = "-fx-background-image: url('" + buttonBackgroundImageUrl + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-position: center center; " +
                "-fx-background-color: transparent; " +
                "-fx-border-color: transparent; " +
                "-fx-focus-color: transparent; " +
                "-fx-faint-focus-color: transparent; ";

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE); // You can change the color to match your theme
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(shadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        button.setStyle(baseStyle);

        return button;
    }

    private Button createSmallButtonWithIcon(String iconName) {
        Button button = new Button();

        Image icon = loadImageWithHighQuality("/Assets/GUI/ActionsIcons/" + iconName, 30, 30); // Smaller icons
        ImageView iconView = new ImageView(icon);
        iconView.setSmooth(false); // Disable smoothing for pixel art
        button.setGraphic(iconView);

        String buttonBackgroundImageUrl = getClass().getResource("/Assets/GUI/ActionsIcons/ButtonSmall.png").toExternalForm();

        button.setMinSize(40, 40); // Smaller size
        button.setMaxSize(40, 40);
        button.setPrefSize(40, 40);

        // Apply the same base style but for a smaller button
        String baseStyle = "-fx-background-image: url('" + buttonBackgroundImageUrl + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-position: center center; " +
                "-fx-background-color: transparent; " +
                "-fx-border-color: transparent; " +
                "-fx-focus-color: transparent; " +
                "-fx-faint-focus-color: transparent; ";
        button.setStyle(baseStyle);

        DropShadow smallShadow = new DropShadow();
        smallShadow.setColor(Color.WHITE); // You can change the color to match your theme
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(smallShadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));

        return button;
    }
}
