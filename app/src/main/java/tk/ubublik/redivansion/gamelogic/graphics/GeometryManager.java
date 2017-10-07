package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.scene.Geometry;

/**
 * Created by Bublik on 27-Aug-17.
 */

public abstract class GeometryManager extends Geometry implements Cloneable{

    protected boolean done = false;

    public GeometryManager(String name) {
        super(name);
    }

    public void onUpdate(){

    }

    @Override
    public abstract GeometryManager clone();

    public interface OnAnimationEndListener{
        void animationEnd();
    }

    public boolean isDone() {
        return done;
    }
}
