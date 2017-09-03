package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 02-Sep-17.
 *
 * Difference between LevelLoadingLifecycle - additional tutorial nodes
 */
public class TutorialLoadingLifecycle extends LoadingLifecycle {

    private boolean done = false;

    public TutorialLoadingLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        done = true;
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.TUTORIAL_LOADING;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {

    }
}
