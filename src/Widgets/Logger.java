package Widgets;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Logger {
    private static final TextFlow logs = new TextFlow();

    // Initialisation statique
    static {
        configureLogs();
    }

    // Blocks instanciation
    private Logger() {}

    public static ScrollPane buildLogger() {
        ScrollPane logsContainer = new ScrollPane(logs);
        logsContainer.setFitToWidth(true);
        VBox.setVgrow(logsContainer, Priority.ALWAYS); // Ensure logsContainer can expand vertically within any container
        return logsContainer;
    }

    private static void configureLogs() {
        logs.setStyle("-fx-background-color: #222; -fx-padding: 10;");
    }

    public static void paintLog(String log, boolean lineReturn, String color, boolean setBold) {
        Text text = createText(log, lineReturn, color, setBold);
        Platform.runLater(() -> logs.getChildren().add(text));
    }

    public static void paintLog(String log, boolean lineReturn, String color) {
        paintLog(log, lineReturn, color, false);
    }

    public static void paintLog(String log, boolean lineReturn) {
        paintLog(log, lineReturn, "white", false);
    }

    private static Text createText(String log, boolean lineReturn, String color, boolean setBold) {
        Text text = new Text(log + (lineReturn ? "\n" : ""));
        text.setStyle(String.format("-fx-fill: %s; %s",
                color, setBold ? "-fx-font-weight: bold;" : ""));
        return text;
    }
}
