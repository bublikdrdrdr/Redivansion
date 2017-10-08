package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.light.Light;

/**
 * Created by Bublik on 01-Sep-17.
 */

public abstract class Lifecycle {
    protected SimpleApplication SimpleApplication;
    public Lifecycle(final SimpleApplication SimpleApplication){
        this.SimpleApplication = SimpleApplication;
        SimpleApplication.getRootNode().detachAllChildren();
        SimpleApplication.getGuiNode().detachAllChildren();
        SimpleApplication.getRootNode().getLocalLightList().clear();
        SimpleApplication.getRootNode().getWorldLightList().clear();
    }
    public abstract LifecycleType getType();
    public abstract boolean isDone();
    public abstract void update();
}