package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class LevelLifecycle extends Lifecycle {

    private int levelNumber;

    public LevelLifecycle(int levelNumber, SimpleApplication simpleApplication){
        super(simpleApplication);
        this.levelNumber = levelNumber;
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
