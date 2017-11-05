package tk.ubublik.redivansion.gamelogic.utils;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 04-Nov-17.
 */

public class RuntimeAnimation implements Runnable {

    private static final String GEOMETRY_CLONE_NAME = "clonedGeometry";

    private GeometryManager geometryManager; //for cloning
    private String animationId;
    private Thread thread = new Thread(this);

    public RuntimeAnimation(GeometryManager geometryManager, String animationId) {
        this.geometryManager = geometryManager;
        this.animationId = animationId;
    }

    public WorldObject cloneInto(WorldObject worldObject){
        worldObject.detachChildNamed(GEOMETRY_CLONE_NAME);
        worldObject.attachChild(geometryManager.clone(false));
        return worldObject;
    }

    public void onUpdate(){
        if (thread.isAlive()) return; //or join?
        thread.start();
    }

    @Override
    public void run() {
        geometryManager.onUpdate();
    }
}
