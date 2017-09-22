package tk.ubublik.redivansion.gamelogic.utils;

import android.graphics.Point;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Collection;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 *
 * Puts geometries on their positions in 3D world
 */
public class MapRenderer {

    private final float scale;
    private final Node node;

    public MapRenderer(Node node) {
        this(node, 1);
    }

    public MapRenderer(Node node, float scale){
        this.node = node;
        this.scale = scale;
    }

    public Point worldPointToMap(Vector3f vector3f){
        return new Point((int)(vector3f.getX()/scale), (int)(vector3f.getZ()/scale));
    }

    public Vector3f mapPointToWorld(Point point){
        return new Vector3f(point.x*scale, 0, point.y*scale);
    }

    public void putObject(WorldObject worldObject){
        Vector3f position = new Vector3f(worldObject.getPosition().x*scale, 0, worldObject.getPosition().y*scale);
        worldObject.getGeometryManager().setLocalTranslation(position);
        node.attachChild(worldObject);
    }

    public void putObjects(Collection<WorldObject> worldObjects){
        for (WorldObject worldObject: worldObjects)
            putObject(worldObject);
    }

    public boolean contains(WorldObject worldObject){
        return contains(worldObject.getGeometryManager());
    }

    public boolean contains(Spatial spatial){
        return (node.getChildIndex(spatial)!=-1);
    }

    public void removeObject(WorldObject worldObject){
        removeObject(worldObject.getGeometryManager());
    }

    public void removeObject(Spatial spatial){
        node.detachChild(spatial);
    }

    public void removeObject(int index){
        node.detachChildAt(index);
    }
}
