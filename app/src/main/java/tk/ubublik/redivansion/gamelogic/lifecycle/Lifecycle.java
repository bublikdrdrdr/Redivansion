package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 01-Sep-17.
 */

public abstract class Lifecycle {
    protected SimpleApplication simpleApplication;
    public Lifecycle(final SimpleApplication simpleApplication){
        this.simpleApplication = simpleApplication;
        simpleApplication.getRootNode().detachAllChildren();
        simpleApplication.getGuiNode().detachAllChildren();
        simpleApplication.getRootNode().getLocalLightList().clear();
        simpleApplication.getRootNode().getWorldLightList().clear();
    }
    public abstract LifecycleType getType();
    public abstract boolean isDone();
    public abstract void update();
}