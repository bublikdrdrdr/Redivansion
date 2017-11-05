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
    private int hash;
    private Thread thread = new Thread(this);

    public RuntimeAnimation(GeometryManager geometryManager, int hash) {
        this.geometryManager = geometryManager;
        this.hash = hash;
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
        // TODO: 05-Nov-17 check thread conflict
        geometryManager.onUpdate();
    }

    public int getAnimationId() {
        return hashCode();
    }

    public GeometryManager getGeometryManager() {
        return geometryManager;
    }
}
