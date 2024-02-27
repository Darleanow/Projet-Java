package Widgets;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.LinkedList;
import java.util.Queue;

public class Logger extends Pane {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Image[] sprites = new Image[9];
    private final Queue<String> messages = new LinkedList<>();
    private final double tileWidth;  // La largeur d'un sprite
    private final double tileHeight; // La hauteur d'un sprite
    private final double contentWidth; // La largeur de la zone de contenu
    private final double contentHeight; // La hauteur de la zone de contenu
    private double nextTextY; // La prochaine position Y pour le texte

    public Logger(double width, double height) {
        contentWidth = width;
        contentHeight = height;

        // Chargez les images des sprites ici
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Image("/Assets/GUI/hudLog/Sprite-000" + (i + 1) + ".png");
        }

        // On suppose que tous les sprites ont les mêmes dimensions
        tileWidth = sprites[0].getWidth();
        tileHeight = sprites[0].getHeight();

        // Initialisez le canvas avec une taille qui correspond à vos sprites + zone de texte
        canvas = new Canvas(contentWidth + 2 * tileWidth, contentHeight + 2 * tileHeight);
        gc = canvas.getGraphicsContext2D();

        // Préparez le fond avec les sprites
        initializeBackground();

        nextTextY = tileHeight; // Commencez à dessiner le texte juste en dessous du sprite supérieur du milieu

        // Ajoutez le canvas au Pane
        this.getChildren().add(canvas);
    }

    private void initializeBackground() {
        // Dessinez les coins
        gc.drawImage(sprites[0], 0, 0); // Coin supérieur gauche
        gc.drawImage(sprites[2], contentWidth + tileWidth, 0); // Coin supérieur droit
        gc.drawImage(sprites[6], 0, contentHeight + tileHeight); // Coin inférieur gauche
        gc.drawImage(sprites[8], contentWidth + tileWidth, contentHeight + tileHeight); // Coin inférieur droit

        // Dessinez les bords supérieur et inférieur
        for (double x = tileWidth; x < contentWidth + tileWidth; x += tileWidth) {
            gc.drawImage(sprites[1], x, 0); // Bord supérieur
            gc.drawImage(sprites[7], x, contentHeight + tileHeight); // Bord inférieur
        }

        // Dessinez les bords gauche et droit
        for (double y = tileHeight; y < contentHeight + tileHeight; y += tileHeight) {
            gc.drawImage(sprites[3], 0, y); // Bord gauche
            gc.drawImage(sprites[5], contentWidth + tileWidth, y); // Bord droit
        }

        // Remplissez le centre
        for (double x = tileWidth; x < contentWidth + tileWidth; x += tileWidth) {
            for (double y = tileHeight; y < contentHeight + tileHeight; y += tileHeight) {
                gc.drawImage(sprites[4], x, y);
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
        // Commencez par effacer la zone de contenu
        gc.setFill(Color.web("#2c242d")); // Ou la couleur de fond de vos sprites centraux
        gc.fillRect(tileWidth, tileHeight, contentWidth, contentHeight);

        // Redessinez le texte
        gc.setFont(Font.font("Verdana", 20)); // Choisissez la police souhaitée
        gc.setFill(Color.BLACK); // Choisissez la couleur du texte

        nextTextY = tileHeight; // Réinitialisez la position y du texte

        for (String message : messages) {
            nextTextY += gc.getFont().getSize();
            gc.fillText(message, tileWidth, nextTextY);
        }
    }
}
