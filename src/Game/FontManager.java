package Game;

import javafx.scene.text.Font;

public class FontManager {
    private final String fontPath = "/Assets/FONT/alagard/alagard.ttf";

    public Font loadFont(double size) {
        return Font.loadFont(getClass().getResourceAsStream(fontPath), size);
    }
}
