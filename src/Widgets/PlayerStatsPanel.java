package Widgets;

import javafx.beans.binding.Bindings;
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

        // Font size 32
        Font font32 = this.fontManager.loadFont(32);
        Font font22 = this.fontManager.loadFont(22);

        // Titre des statistiques
        Text title_stats = new Text("Statistiques");
        title_stats.setFont(font32);
        title_stats.setTextAlignment(TextAlignment.CENTER);
        title_stats.setFill(Color.web("#FFC857"));
        StackPane titleContainer = new StackPane(title_stats);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(0, 0, 10, 0));

        // HP
        Text hpText = new Text();
        hpText.textProperty().bind(Bindings.concat("HP: ", player.hpProperty().asString()));
        hpText = configureText(hpText, font22,Color.web("#E63946"));
        HBox hpBox = new HBox(hpText);
        hpBox.setAlignment(Pos.CENTER_LEFT);

        // Faim
        Text hungerText = new Text("Faim: 0%");
        hungerText = configureText(hungerText, font22,Color.web("#E67E39"));
        HBox hungerBox = new HBox(hungerText);
        hungerBox.setAlignment(Pos.CENTER_LEFT);

        // Energie
        Text energyText = new Text("Energie: 50");
        energyText = configureText(energyText, font22,Color.web("#2A9D8F"));

        HBox energyBox = new HBox(energyText);
        energyBox.setAlignment(Pos.CENTER_LEFT);

        // Ajouter les éléments à la VBox
        statsBox.getChildren().addAll(titleContainer, hpBox, hungerBox, energyBox/*, build_quests_constructions_pane()*/);
        HBox.setHgrow(statsBox, Priority.ALWAYS);

        return statsBox;
    }

    private Text configureText(Text text, Font font, Color color)
    {
        text.setFont(font);
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setFill(color);

        return text;
    }
}
