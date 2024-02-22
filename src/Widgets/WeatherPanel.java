package Widgets;

import Game.FontManager;
import Game.GameTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class WeatherPanel {
    private final GameTime gameTime;
    private Text dayText, timeText;
    private final ImageView currentWeather = new ImageView();
    private final FontManager fontManager;

    public WeatherPanel(GameTime gameTime, FontManager fontManager) {
        this.gameTime = gameTime;
        this.fontManager = fontManager;
        initTimeUpdateLoop();
    }

    public VBox createPanel() {
        Text nameText = createStyledText("Joueur1", "#FF9F1C", 22, TextAlignment.CENTER);
        StackPane nameContainer = new StackPane(nameText);
        nameContainer.setAlignment(Pos.CENTER);

        this.dayText = createStyledText("", "#FF6B6B", 22, null);
        this.timeText = createStyledText("", "#FFE66D", 22, null);

        configureFixedSizeForText(this.dayText, 70);
        configureFixedSizeForText(this.timeText, 130);
        configureFixedSizeForImageView(this.currentWeather, 32,32);

        updateDateTimeDisplay(); // Initialize text

        HBox dateTimeBox = new HBox(10, currentWeather, dayText, timeText);
        dateTimeBox.setAlignment(Pos.CENTER);

        return new VBox(10, nameContainer, dateTimeBox);
    }

    private void configureFixedSizeForText(Text text, double width) {
        text.setWrappingWidth(width); // Set a fixed wrapping width
        text.setTextAlignment(TextAlignment.CENTER);
    }

    private void configureFixedSizeForImageView(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
    }

    private Text createStyledText(String text, String colorHex, double fontSize, TextAlignment alignment) {
        Text styledText = new Text(text);
        styledText.setFont(fontManager.loadFont(fontSize));
        styledText.setFill(Color.web(colorHex));
        styledText.setFontSmoothingType(FontSmoothingType.LCD);
        if (alignment != null) {
            styledText.setTextAlignment(alignment);
        }
        return styledText;
    }

    private void initTimeUpdateLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> updateDateTimeDisplay()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateDateTimeDisplay() {
        gameTime.setHour(gameTime.getHour() + 1);
        dayText.setText("Jour: " + gameTime.getDay());
        timeText.setText("Heure: " + String.format("%02d:00", gameTime.getHour()));
        currentWeather.setImage(getImageFromTime(gameTime.getHour()));
    }

    private Image getImageFromTime(int hour) {
        String imagePath = switch (hour) {
            case 6, 7 -> "Assets/GUI/WEATHER/sunrise.png";
            case 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 -> "Assets/GUI/WEATHER/sun.png";
            case 18, 19, 20 -> "Assets/GUI/WEATHER/sunset.png";
            default -> "Assets/GUI/WEATHER/moon.png";
        };
        return new Image(imagePath);
    }
}
