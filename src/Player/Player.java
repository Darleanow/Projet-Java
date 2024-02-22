package Player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player
{
    boolean is_alive;
    private final IntegerProperty hp;
    private final IntegerProperty hunger;
    private final IntegerProperty thirst;
    private final IntegerProperty tiredness;
    private final IntegerProperty mentalHealth;
    private final IntegerProperty strength;
    private final IntegerProperty agility;
    private final IntegerProperty stamina;

    public final int getHp() { return hp.get(); }
    public final void setHp(int value) { hp.set(value); }
    public IntegerProperty hpProperty() { return this.hp; }
    public IntegerProperty hungerProperty() { return this.hunger; }
    public IntegerProperty thirstProperty() { return this.thirst; }
    public IntegerProperty tirednessProperty() { return this.tiredness; }
    public IntegerProperty mentalHealthProperty() { return this.mentalHealth; }
    public IntegerProperty strengthProperty() { return strength; }
    public IntegerProperty agilityProperty() { return agility; }
    public IntegerProperty staminaProperty() { return stamina; }

    public Player()
    {
        this.is_alive = true;
        this.hp = new SimpleIntegerProperty(100);
        this.hunger = new SimpleIntegerProperty(100);
        this.thirst = new SimpleIntegerProperty(100);
        this.tiredness = new SimpleIntegerProperty(0);
        this.mentalHealth = new SimpleIntegerProperty(100);
        this.strength = new SimpleIntegerProperty(10);
        this.agility  = new SimpleIntegerProperty(10);
        this.stamina = new SimpleIntegerProperty(10);

    }
    public boolean is_alive()
    {
        return this.is_alive;
    }


}
