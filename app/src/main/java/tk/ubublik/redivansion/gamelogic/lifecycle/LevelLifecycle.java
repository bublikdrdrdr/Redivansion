package tk.ubublik.redivansion.gamelogic.lifecycle;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class LevelLifecycle implements Lifecycle {

    private int levelNumber;

    public LevelLifecycle(int levelNumber){

    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.LEVEL;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }
}
