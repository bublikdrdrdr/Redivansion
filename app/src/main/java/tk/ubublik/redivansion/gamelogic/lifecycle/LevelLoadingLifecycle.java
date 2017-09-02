package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class LevelLoadingLifecycle extends LoadingLifecycle {

    private int levelNumber;

    public LevelLoadingLifecycle(int levelNumber, SimpleApplication simpleApplication){
        super(simpleApplication);
        this.levelNumber = levelNumber;
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.LEVEL_LOADING;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }

    public int getLevelNumber(){
        return levelNumber;
    }
}
