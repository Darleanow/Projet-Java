package Widgets;

import Game.FontManager;
import Game.GameTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import Game.GameTime;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class WeatherPanel {
    private final GameTime gameTime;
    private Text dayText, timeText;
    private Font gameFont22;
    private ImageView currentWeather;

    public WeatherPanel(GameTime gameTime, FontManager fontLoader) {
        this.gameTime = gameTime;
        this.gameFont22 = fontLoader.loadFont(22);
    }

    public VBox createPanel() {


        Text nameText = new Text("Joueur1");
        nameText.setFont(gameFont22);
        nameText.setFill(Color.web("#FF9F1C"));
        nameText.setFontSmoothingType(FontSmoothingType.LCD);
        nameText.setTextAlignment(TextAlignment.CENTER);

        // Center the player name in its container&
        StackPane nameContainer = new StackPane(nameText);
        nameContainer.setAlignment(Pos.CENTER);

        this.currentWeather = this.getImageFromTime("6");
        this.dayText = new Text();
        dayText.setFont(gameFont22);
        dayText.setFill(Color.web("#FF6B6B"));
        dayText.setFontSmoothingType(FontSmoothingType.LCD);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            gameTime.incrementHour();
            updateDateTimeDisplay(gameTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        this.timeText = new Text();
        timeText.setFont(gameFont22);
        timeText.setFill(Color.web("#FFE66D"));
        timeText.setFontSmoothingType(FontSmoothingType.LCD);

        updateDateTimeDisplay(gameTime);

        HBox dateTimeBox = new HBox(10);
        dateTimeBox.getChildren().addAll(currentWeather, dayText, timeText);
        dateTimeBox.setAlignment(Pos.CENTER);

        VBox gameInfoBox = new VBox(10, nameContainer, dateTimeBox);

        return gameInfoBox;
    }

    private ImageView getImageFromTime(String time)
    {
        Image image;

        // TODO: Test this
        int hour = Integer.parseInt(time.split(":")[0]);

        if (hour >= 6 && hour < 8) {
            image = new Image("Assets/GUI/WEATHER/sunrise.png");
        } else if (hour >= 8 && hour < 18) {
            image = new Image("Assets/GUI/WEATHER/sun.png");
        } else if (hour >= 18 && hour < 21) {
            image = new Image("Assets/GUI/WEATHER/sunset.png");
        } else {
            image = new Image("Assets/GUI/WEATHER/moon.png");
        }


        return new ImageView(image);
    }

    private void updateDateTimeDisplay(GameTime gameTime) {
        this.dayText.setText("Jour: " + gameTime.getDay());
        this.timeText.setText("Heure: " + String.format("%02d:00", gameTime.getHour()));
        this.currentWeather.setImage(getImageFromTime(String.format("%02d:00", gameTime.getHour())).getImage());
    }
}
