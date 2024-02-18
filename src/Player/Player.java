package Player;

public class Player
{
    boolean is_alive;
    int health;

    public Player()
    {
        this.is_alive = true;
        this.health  = 100;
    }
    public boolean is_alive()
    {
        return this.is_alive;
    }

    public int get_health() { return this.health; }
    public void remove_health(int value_to_remove)
    {
        this.health -= value_to_remove;
        if (health <= 0 ) { this.is_alive = false; }
    }
    public void add_health(int value_to_add)
    {
        this.health += value_to_add;
    }

}
