package Widgets;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Logger {
    private final TextFlow logs = new TextFlow();

    public Logger() {
        configureLogs();
    }

    public ScrollPane buildLogger(Stage primaryStage, BorderPane root) {
        ScrollPane logsContainer = new ScrollPane(logs);
        logsContainer.setFitToWidth(true);
        VBox.setVgrow(logsContainer, Priority.ALWAYS); // Ensure logsContainer can expand vertically within any container

        // Dynamically adjust log size based on the stage size
        ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> adjustLogSize(primaryStage);
        primaryStage.widthProperty().addListener(sizeListener);
        primaryStage.heightProperty().addListener(sizeListener);

        // Initial size adjustment
        adjustLogSize(primaryStage);

        return logsContainer;
    }

    private void adjustLogSize(Stage primaryStage) {
        double width = primaryStage.getWidth() / 2.5;
        double height = primaryStage.getHeight() / 2.5;
        logs.setPrefSize(width, height);
    }

    private void configureLogs() {
        logs.setStyle("-fx-background-color: #222; -fx-padding: 10;");
    }

    public void paintLog(String log, boolean lineReturn, String color, boolean setBold) {
        Text text = createText(log, lineReturn, color, setBold);
        logs.getChildren().add(text);
    }

    public void paintLog(String log, boolean lineReturn, String color) {
        paintLog(log, lineReturn, color, false);
    }

    public void paintLog(String log, boolean lineReturn) {
        paintLog(log, lineReturn, "white", false);
    }

    private Text createText(String log, boolean lineReturn, String color, boolean setBold) {
        Text text = new Text(log + (lineReturn ? "\n" : ""));
        text.setStyle(String.format("-fx-fill: %s; %s",
                color, setBold ? "-fx-font-weight: bold;" : ""));
        return text;
    }
}
