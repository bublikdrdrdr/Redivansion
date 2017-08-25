package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.scene.Geometry;

/**
 * Created by Bublik on 25-Aug-17.
 */

public interface DynamicGeometry{

    void onUpdate();
    void beginAnimation(String name);
}
