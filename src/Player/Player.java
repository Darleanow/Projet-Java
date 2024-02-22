package Player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player
{
    boolean is_alive;
    private final IntegerProperty hp;

    public final int getHp() { return hp.get(); }
    public final void setHp(int value) { hp.set(value); }
    public IntegerProperty hpProperty() { return hp; }

    public Player()
    {
        this.is_alive = true;
        this.hp = new SimpleIntegerProperty(100);
    }
    public boolean is_alive()
    {
        return this.is_alive;
    }


}
