package Widgets;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Logger {
    private final TextFlow logs;

    public Logger()
    {
        this.logs = new TextFlow();
        configureLogs();
    }

    public ScrollPane buildLogger(Stage PrimaryStage, BorderPane root)
    {
        this.logs.setPrefHeight(root.getHeight() / 2.5);

        ScrollPane logsContainer = new ScrollPane();
        logsContainer.setContent(this.logs);
        logsContainer.setFitToWidth(true);


        HBox.setHgrow(this.logs, Priority.ALWAYS);



        PrimaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.logs.setPrefWidth(newVal.intValue() / 2.5);
        });

        PrimaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.logs.setPrefHeight(newVal.intValue() / 2.5);
        });

        return logsContainer;
    }

    private void configureLogs() {
        this.logs.setStyle("-fx-background-color: #222; -fx-padding: 10;");
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
