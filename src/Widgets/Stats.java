package Widgets;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Stats {

    // Assuming you have a method to create an ImageView for a given stat
    private static ImageView createStatIcon(String iconName) {
        Image image = new Image(Stats.class.getResourceAsStream("/Assets/GUI/StatsIcons/" + iconName + "1.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(24); // Adjust size as needed
        imageView.setFitHeight(24);
        return imageView;
    }

    // Create a styled ProgressBar for a given stat
    private static ProgressBar createStatBar(double value) {
        ProgressBar progressBar = new ProgressBar(value);
        progressBar.setStyle("-fx-accent: green;"); // Use your own style here
        progressBar.setPrefWidth(200); // Adjust size as needed
        return progressBar;
    }

    // Example method to create a stat display, with icon and progress bar
    private static VBox createStatDisplay(String statName, String iconName, double value) {
        ImageView icon = createStatIcon(iconName);
        ProgressBar bar = createStatBar(value);

        Label label = new Label(statName);
        label.setGraphic(icon);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(javafx.scene.paint.Color.WHITE);

        Tooltip tooltip = new Tooltip(statName + ": " + (int)(value * 100) + "%");
        tooltip.setShowDelay(Duration.seconds(0.1));
        Tooltip.install(bar, tooltip);

        VBox statDisplay = new VBox(5, label, bar);
        statDisplay.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        return statDisplay;
    }

    // Method to create the entire Stats bar
    public static VBox createStatsBar() {
        VBox statsBar = new VBox(10);
        statsBar.getChildren().add(createStatDisplay("Health", "heart", 0.75));
        statsBar.getChildren().add(createStatDisplay("Hunger", "hunger", 0.60));
        // Add other stats as needed

        statsBar.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 10;");
        return statsBar;
    }
}
