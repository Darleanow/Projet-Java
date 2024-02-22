package Game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameTime {
    private final IntegerProperty hour = new SimpleIntegerProperty(this, "hour", 6); // Starting at 6 AM
    private final IntegerProperty day = new SimpleIntegerProperty(this, "day", 1);

    public int getHour() { return hour.get(); }
    public void setHour(int hour) { this.hour.set(hour); }
    public IntegerProperty hourProperty() { return hour; }

    public int getDay() { return day.get(); }
    public void setDay(int day) { this.day.set(day); }
    public IntegerProperty dayProperty() { return day; }

    public void incrementHour() {
        int newHour = (getHour() + 1) % 24;
        if (newHour == 0) { // A new day
            setDay(getDay() + 1);
        }
        setHour(newHour);
    }
}
