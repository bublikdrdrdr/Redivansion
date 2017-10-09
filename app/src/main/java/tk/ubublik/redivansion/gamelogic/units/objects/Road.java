package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Road extends WorldObject {


    public Road(Point position){
        this(position, new RoadState(false, false, false, false));
    }

    public Road(Point position, RoadState roadState) {
        if (NodesCache.getInstance().get("roadGeometryManager")==null){
            GeometryAnimationManager geometryAnimationManager = new GeometryAnimationManager((Model)NodesCache.getInstance().get("roadModel"));
            NodesCache.getInstance().put("roadGeometryManager", geometryAnimationManager);
        }
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }
}
