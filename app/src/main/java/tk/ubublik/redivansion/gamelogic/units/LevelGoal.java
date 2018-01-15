package tk.ubublik.redivansion.gamelogic.units;

/**
 * Created by Bublik on 31-Aug-17.
 */
public abstract class LevelGoal {
    protected Level level;
    public LevelGoal(Level level){
        this.level = level;
    }
    public abstract boolean isDone();
}