package Game;

public class TimeManager
{
    int current_time;
    int NIGHT_TIME = 19;
    int DAY_TIME = 7;

    public TimeManager()
    {
        this.current_time = 10;
    }

    public int get_time()
    {
        return this.current_time;
    }

    public void increase_time(int value_to_add)
    {
        this.current_time += value_to_add;
        this.format_time();
    }

    public void increment_time()
    {
        this.current_time++;
        this.format_time();
    }

    public Boolean is_day()
    {
        return this.current_time < NIGHT_TIME && this.current_time >= DAY_TIME;
    }

    public void set_day()
    {
        this.current_time = DAY_TIME;
    }

    public void set_night()
    {
        this.current_time = NIGHT_TIME;
    }

    private void format_time()
    {
        this.current_time %= 24;
    }
}
