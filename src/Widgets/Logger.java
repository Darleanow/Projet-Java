package Widgets;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;

public class Logger extends Pane {
    private Canvas backgroundCanvas, textCanvas;
    private GraphicsContext bgGC, textGC;
    private ScrollPane scrollPane;
    private final Image[] sprites = new Image[9];
    private final Queue<String> messages = new LinkedList<>();
    private final double tileWidth = 64;
    private final double tileHeight = 64;
    private double contentWidth;
    private double contentHeight;

    private final Queue<String> animationQueue = new ArrayDeque<>();
    private boolean isAnimating = false;

    public Logger(double height) {
        this.setPrefHeight(height);
        this.setMinHeight(height);
        this.setMaxHeight(height);
        this.setPrefWidth(300);

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Image("/Assets/GUI/hudLog/Sprite-000" + (i + 1) + ".png");
        }

        initializeBackgroundCanvas();
        initializeTextCanvas();
        initializeScrollPane();

        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.doubleValue() > 0) {
                adjustLoggerWidth(newVal.doubleValue());
            }
        });
    }

    private void initializeBackgroundCanvas() {
        double initialWidth = 300;
        double initialHeight = this.getPrefHeight();
        backgroundCanvas = new Canvas(initialWidth, initialHeight);
        bgGC = backgroundCanvas.getGraphicsContext2D();
        drawBackground();
        this.getChildren().add(backgroundCanvas);
    }

    private void initializeTextCanvas() {
        textCanvas = new Canvas(300, this.getPrefHeight());
        textGC = textCanvas.getGraphicsContext2D();
    }

    private void initializeScrollPane() {
        scrollPane = new ScrollPane();
        scrollPane.setContent(textCanvas);

        final double reducedMargin = 60;

        double scrollPaneWidth = this.getPrefWidth() - reducedMargin;
        double scrollPaneHeight = this.getPrefHeight() - reducedMargin;

        scrollPane.setLayoutX((tileWidth - reducedMargin) / 2 + reducedMargin / 2);
        scrollPane.setLayoutY((tileHeight - reducedMargin) / 2 + reducedMargin / 2);
        scrollPane.setPrefSize(scrollPaneWidth, scrollPaneHeight);
        scrollPane.setPrefViewportWidth(scrollPaneWidth);
        scrollPane.setPrefViewportHeight(scrollPaneHeight);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setStyle("-fx-background-color: transparent;" +
                " -fx-background: transparent;" +
                " -fx-background-insets: 0;" +
                " -fx-padding: 0;");

        this.getChildren().add(scrollPane);
    }

    private void adjustLoggerWidth(double newWidth) {
        final double reducedMargin = 60;

        backgroundCanvas.setWidth(newWidth);
        double newContentWidth = newWidth - reducedMargin;

        textCanvas.setWidth(newContentWidth);

        scrollPane.setLayoutX((tileWidth - reducedMargin) / 2 + reducedMargin / 2);
        scrollPane.setPrefWidth(newContentWidth);
        scrollPane.setPrefViewportWidth(newContentWidth);

        drawBackground();

    }

    private void drawBackground() {
        bgGC.clearRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());

        contentWidth = backgroundCanvas.getWidth() - 2 * tileWidth;
        contentHeight = backgroundCanvas.getHeight() - 2 * tileHeight;

        bgGC.drawImage(sprites[0], 0, 0, tileWidth, tileHeight);
        bgGC.drawImage(sprites[2], backgroundCanvas.getWidth() - tileWidth, 0, tileWidth, tileHeight);
        bgGC.drawImage(sprites[6], 0, backgroundCanvas.getHeight() - tileHeight, tileWidth, tileHeight);
        bgGC.drawImage(sprites[8], backgroundCanvas.getWidth() - tileWidth, backgroundCanvas.getHeight() - tileHeight, tileWidth, tileHeight);

        double maxX = backgroundCanvas.getWidth() - tileWidth;
        double maxY = backgroundCanvas.getHeight() - tileHeight;

        for (double x = tileWidth; x < maxX; x += tileWidth) {
            bgGC.drawImage(sprites[1], x, 0, tileWidth, tileHeight);
            bgGC.drawImage(sprites[7], x, maxY, tileWidth, tileHeight);
        }

        for (double y = tileHeight; y < maxY; y += tileHeight) {
            bgGC.drawImage(sprites[3], 0, y, tileWidth, tileHeight);
            bgGC.drawImage(sprites[5], maxX, y, tileWidth, tileHeight);
        }

        for (double x = tileWidth; x < maxX; x += tileWidth) {
            for (double y = tileHeight; y < maxY; y += tileHeight) {
                bgGC.drawImage(sprites[4], x, y, tileWidth, tileHeight);
            }
        }
    }

    public void log(String message) {
        Platform.runLater(() -> {
            animationQueue.offer(message);
            if (!isAnimating) {
                animateNextMessage();
            }
        });
    }

    private void animateTextGradually(String message) {
        isAnimating = true;
        final StringBuilder displayedMessage = new StringBuilder();
        final int delayBetweenChars = 50;
        final Timeline timeline = new Timeline();
        final String[] messageChars = message.split("");

        for (int i = 0; i < messageChars.length; i++) {
            final String charToAdd = messageChars[i];
            final int delay = i * delayBetweenChars;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), event -> {
                displayedMessage.append(charToAdd);
                redrawCanvasWithNewChar(displayedMessage.toString());
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(event -> {
            messages.add(message);
            redrawCanvasWithNewChar("");

            if (textCanvas.getHeight() > scrollPane.getViewportBounds().getHeight()) {
                smoothScrollToBottom();
            }
            isAnimating = false;
            Platform.runLater(this::animateNextMessage);
        });

        timeline.playFromStart();
    }

    private void smoothScrollToBottom() {

        final Timeline scrollTimeline = new Timeline();
        final KeyValue kv = new KeyValue(scrollPane.vvalueProperty(), 1);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        scrollTimeline.getKeyFrames().add(kf);
        scrollTimeline.play();
    }

    private void animateNextMessage() {
        if (!animationQueue.isEmpty() && !isAnimating) {
            String message = animationQueue.poll();
            animateTextGradually(message);
        }
    }

    private Image avatarImage = new Image("/Assets/Icons/base/explore.png");

    private void redrawCanvasWithNewChar(String currentText) {
        textGC.clearRect(0, 0, textCanvas.getWidth(), textCanvas.getHeight());
        drawBackground();

        final double avatarSize = 40; // Taille de l'avatar
        final double textStartX = tileWidth; // Position X de départ pour le texte, alignée avec le nom du narrateur
        double y = tileHeight + 5 + avatarSize + 5; // Position Y de départ pour le texte, sous l'avatar

        // Dessiner l'avatar une seule fois
        textGC.drawImage(avatarImage, (tileWidth - avatarSize / 2) - 30, tileHeight + 5, avatarSize, avatarSize);

        // Définir le nom du narrateur et l'afficher une seule fois au-dessus de l'avatar
        textGC.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        textGC.setFill(Color.WHITESMOKE);
        String narratorName = "Narrateur"; // Nom du narrateur
        textGC.fillText(narratorName, tileWidth, tileHeight + 20);

        // Réinitialiser la police pour le corps du texte
        textGC.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));

        // Traitement et affichage de chaque message
        for (String msg : messages) {
            List<String> lines = wrapText(msg, textCanvas.getWidth() - textStartX - 10, textGC);
            for (String line : lines) {
                textGC.fillText(line, textStartX, y);
                y += textGC.getFont().getSize() + 5; // Ajouter un espace entre les lignes
            }
        }

        // Gestion du message actuel si non vide et non déjà affiché
        if (!currentText.isEmpty() && !messages.contains(currentText)) {
            List<String> lines = wrapText(currentText, textCanvas.getWidth() - textStartX - 10, textGC);
            for (String line : lines) {
                textGC.fillText(line, textStartX, y);
                y += textGC.getFont().getSize() + 5; // Ajouter un espace entre les lignes
            }
        }

        // Ajuster la hauteur du canvas si nécessaire
        if (y > textCanvas.getHeight()) {
            textCanvas.setHeight(y);
        }
    }

    private List<String> wrapText(String text, double maxWidth, GraphicsContext gc) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        Text tempText = new Text();
        tempText.setFont(gc.getFont());

        for (String word : words) {
            tempText.setText(line.toString() + word);
            double lineWidth = tempText.getLayoutBounds().getWidth();
            if (lineWidth < maxWidth) {
                line.append(word).append(" ");
            } else {
                lines.add(line.toString().trim());
                line = new StringBuilder(word + " ");
            }
        }

        if (!line.toString().isEmpty()) {
            lines.add(line.toString().trim());
        }

        return lines;
    }

}