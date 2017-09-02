package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class TutorialLifecycle extends Lifecycle {
    public TutorialLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.TUTORIAL;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }
}
