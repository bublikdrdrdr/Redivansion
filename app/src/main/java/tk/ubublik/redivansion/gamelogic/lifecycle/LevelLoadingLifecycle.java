package tk.ubublik.redivansion.gamelogic.lifecycle;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class LevelLoadingLifecycle extends LoadingLifecycle {

    private int levelNumber;

    public LevelLoadingLifecycle(int levelNumber){
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
