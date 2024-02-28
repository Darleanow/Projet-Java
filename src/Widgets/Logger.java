package Widgets;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.LinkedList;
import java.util.Queue;

public class Logger extends Pane {
    private Canvas canvas;
    private ScrollPane scrollPane; // ScrollPane to contain the canvas
    private VBox contentBox;
    private GraphicsContext gc;
    private final Image[] sprites = new Image[9];
    private final Queue<String> messages = new LinkedList<>();
    private final double tileWidth = 64;
    private final double tileHeight = 64;
    private double contentWidth;
    private double contentHeight;
    private double nextTextY;

    public Logger(double height) {
        this.setPrefHeight(height);
        this.setMinHeight(height);
        this.setMaxHeight(height);

        this.setPrefWidth(300);

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Image("/Assets/GUI/hudLog/Sprite-000" + (i + 1) + ".png");
        }

        initializeCanvas();


        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.doubleValue() > 0) {
                adjustLoggerWidth(newVal.doubleValue());
            }
        });
    }

    private void initializeCanvas() {
        double initialWidth = 300;
        double initialHeight = this.getPrefHeight();
        canvas = new Canvas(initialWidth, initialHeight);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);

        initializeBackground();
    }

    private void adjustLoggerWidth(double newWidth) {

        contentWidth = newWidth - 2 * tileWidth;

        canvas.setWidth(newWidth);
        canvas.setHeight(this.getPrefHeight());

        initializeBackground();
        redrawText();
    }

    private void initializeBackground() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Correctly calculate the dimensions for content.
        contentWidth = canvas.getWidth() - 2 * tileWidth;
        contentHeight = canvas.getHeight() - 2 * tileHeight;

        // Draw corners
        gc.drawImage(sprites[0], 0, 0, tileWidth, tileHeight); // Top-left
        gc.drawImage(sprites[2], canvas.getWidth() - tileWidth, 0, tileWidth, tileHeight); // Top-right
        gc.drawImage(sprites[6], 0, canvas.getHeight() - tileHeight, tileWidth, tileHeight); // Bottom-left
        gc.drawImage(sprites[8], canvas.getWidth() - tileWidth, canvas.getHeight() - tileHeight, tileWidth, tileHeight); // Bottom-right

        // Fill borders and center, ensuring full coverage
        double maxX = canvas.getWidth() - tileWidth; // Right edge for drawing
        double maxY = canvas.getHeight() - tileHeight; // Bottom edge for drawing

        // Top and bottom borders
        for (double x = tileWidth; x < maxX; x += tileWidth) {
            gc.drawImage(sprites[1], x, 0, tileWidth, tileHeight); // Top
            gc.drawImage(sprites[7], x, maxY, tileWidth, tileHeight); // Bottom
        }

        // Left and right borders
        for (double y = tileHeight; y < maxY; y += tileHeight) {
            gc.drawImage(sprites[3], 0, y, tileWidth, tileHeight); // Left
            gc.drawImage(sprites[5], maxX, y, tileWidth, tileHeight); // Right
        }

        // Center filling
        for (double x = tileWidth; x < maxX; x += tileWidth) {
            for (double y = tileHeight; y < maxY; y += tileHeight) {
                gc.drawImage(sprites[4], x, y, tileWidth, tileHeight);
            }
        }
    }


    public void log(String message) {
        Platform.runLater(() -> {
            messages.add(message);
            redrawText();
        });
    }

    private void redrawText() {
        gc.setFill(Color.web("#2c242d"));
        gc.fillRect(tileWidth, tileHeight, contentWidth, contentHeight);

        gc.setFont(Font.font("Verdana", 20));
        gc.setFill(Color.WHITESMOKE);

        nextTextY = tileHeight;

        for (String message : messages) {
            nextTextY += gc.getFont().getSize();
            gc.fillText(message, tileWidth, nextTextY);
        }
    }
}
