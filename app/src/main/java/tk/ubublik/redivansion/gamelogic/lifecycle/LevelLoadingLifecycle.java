package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.ui.Picture;

import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class LevelLoadingLifecycle extends LoadingLifecycle {

    private int levelNumber;
    private volatile boolean done = false;

    public LevelLoadingLifecycle(int levelNumber, SimpleApplication simpleApplication){
        super(simpleApplication);
        this.levelNumber = levelNumber;
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(9000);
                } catch (Exception e){
                    e.printStackTrace();
                    System.exit(1);
                }
                done = true;
            }
        });
        thread.start();
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.LEVEL_LOADING;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public int getLevelNumber(){
        return levelNumber;
    }
}
