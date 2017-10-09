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

    private static final String MANAGER_NAME = "roadGeometryManager:";

    public Road(Point position){
        this(position, new RoadState(false, false, false, false));
    }

    public Road(Point position, RoadState roadState) {
        name = roadState.getModelName();
        if (NodesCache.getInstance().get(MANAGER_NAME+name)==null){
            GeometryAnimationManager geometryAnimationManager = new GeometryAnimationManager((Model)NodesCache.getInstance().get("roadModel"));
            geometryAnimationManager.setLocalScale(0.1f);
            geometryAnimationManager.setLocalTranslation(-0.5f, 0, -0.5f);
            NodesCache.getInstance().put(MANAGER_NAME+name, geometryAnimationManager);
            geometryAnimationManager.beginAnimation("build" + name, new GeometryManager.OnAnimationEndListener() {
                @Override
                public void animationEnd() {
                    NodesCache.getInstance().put(MANAGER_NAME+name, null);
                }
            });
            setGeometryManager(((GeometryAnimationManager)NodesCache.getInstance().get(MANAGER_NAME+name)));
        } else{
            setGeometryManager(((GeometryAnimationManager)NodesCache.getInstance().get(MANAGER_NAME+name)).clone());
        }
        setPosition(position);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }
}
