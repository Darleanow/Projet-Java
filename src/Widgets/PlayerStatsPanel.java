package Widgets;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import Game.FontManager;
import Player.Player;
import javafx.scene.text.TextAlignment;

public class PlayerStatsPanel {
    private final Player player;
    private final FontManager fontManager;

    public PlayerStatsPanel(Player player, FontManager fontManager) {
        this.player = player;
        this.fontManager = fontManager;
    }

    public VBox createPanel() {
        VBox statsBox = new VBox(10);
        statsBox.setPadding(new Insets(15));
        statsBox.setStyle("-fx-background-color: #222; -fx-border-color: #AAA; -fx-border-width: 1;");

        Font font32 = this.fontManager.loadFont(32);
        Font font22 = this.fontManager.loadFont(22);

        // Titre des statistiques
        statsBox.getChildren().add(createStatTitle("Statistiques", font32));

        // Statistiques du joueur
        statsBox.getChildren().add(createStatLine("HP: ",
                player.hpProperty(), "#FF6768", font22));
        statsBox.getChildren().add(createStatLine("Satiete: ",
                player.hungerProperty(), "#FFD364", font22));
        statsBox.getChildren().add(createStatLine("Soif: ",
                player.thirstProperty(), "#68B0AB", font22));
        statsBox.getChildren().add(createStatLine("Fatigue: ",
                player.tirednessProperty(), "#596475", font22));
        statsBox.getChildren().add(createStatLine("Sante Mentale: ",
                player.mentalHealthProperty(), "#A9DFBF", font22));
        statsBox.getChildren().add(createStatLine("Force: ",
                player.strengthProperty(), "#DAA588", font22));
        statsBox.getChildren().add(createStatLine("Agilite: ",
                player.agilityProperty(), "#9AD1D4", font22));
        statsBox.getChildren().add(createStatLine("Endurance: ",
                player.staminaProperty(), "#C7B198", font22));


        HBox.setHgrow(statsBox, Priority.ALWAYS);
        return statsBox;
    }

    private StackPane createStatTitle(String titleText, Font font) {
        Text title = new Text(titleText);
        title.setFont(font);
        title.setFill(Color.web("#FFC857"));
        title.setTextAlignment(TextAlignment.CENTER);
        StackPane titleContainer = new StackPane(title);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(0, 0, 10, 0));
        return titleContainer;
    }

    private HBox createStatLine(String labelText, IntegerProperty property, String color, Font font) {
        Text label = new Text();
        label.textProperty().bind(Bindings.concat(labelText, property.asString()));
        label = configureText(label, font, Color.web(color));
        HBox lineBox = new HBox(label);
        lineBox.setAlignment(Pos.CENTER_LEFT);
        return lineBox;
    }

    private Text configureText(Text text, Font font, Color color) {
        text.setFont(font);
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setFill(color);
        return text;
    }

}
