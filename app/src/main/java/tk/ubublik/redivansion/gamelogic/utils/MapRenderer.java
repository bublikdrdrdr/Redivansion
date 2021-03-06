package tk.ubublik.redivansion.gamelogic.utils;

import android.graphics.Point;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.TerrainDrawer;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 */
public class MapRenderer implements Observer{

    private final float scale;
    private final Node node;
    private final Camera camera;
    private LinkedList<WorldObject> worldObjectList = new LinkedList<>();

    private TerrainDrawer terrainDrawer;

    public MapRenderer(Node node, Camera camera){
        this(node, 1, camera);
    }

    public MapRenderer(Node node, float scale, Camera camera){
        this.node = node;
        this.scale = scale;
        this.camera = camera;
        terrainDrawer = new TerrainDrawer(node, 6);
    }

    public Point worldPointToMap(Vector3f vector3f){
        return worldPointToMap(vector3f, 1);
    }

    public Point worldPointToMap(Vector3f vector3f, int size){
        final float offset = -.5f*size+.5f;
        return new Point((int)FastMath.floor(offset+vector3f.getX()/scale), (int)FastMath.floor(offset+vector3f.getZ()/scale));
    }

    public Vector3f mapPointToWorld(Point point){
        return mapPointToWorld(point, 1);
    }
    public Vector3f mapPointToWorld(Point point, int size){
        final float offset = .5f*size;
        return new Vector3f((point.x+offset)*scale, 0, (point.y+offset)*scale);
    }

    public void putObject(WorldObject worldObject){
        Vector3f position = mapPointToWorld(worldObject.getPosition(), worldObject.getSize());
        worldObject.setLocalTranslation(position);
        worldObjectList.add(worldObject);
        node.attachChild(worldObject);
    }

    public boolean contains(Spatial spatial){
        return (node.getChildIndex(spatial)!=-1);
    }

    public boolean removeObject(final WorldObject worldObject){
        final boolean[] result = {false};
        worldObject.destroy(new GeometryManager.OnAnimationEndListener() {
            @Override
            public void animationEnd() {
                node.detachChild(worldObject);
                worldObjectList.remove(worldObject);
                result[0] = true;
            }
        });
        return result[0];
    }

    public void onUpdate() {
        try {
            for (WorldObject worldObject : worldObjectList) {
                if ((camera.contains(worldObject.getWorldBound()) != Camera.FrustumIntersect.Outside)) {
                    worldObject.onUpdate();
                }
            }
        } catch (Exception ignored) {
        }
        terrainDrawer.onUpdate(CameraControl.getCameraCenterPoint(camera));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction){
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            switch (worldMapAction.getAction()){
                case ADD: putObject(worldMapAction.getWorldObject()); break;
                case REMOVE: removeObject(worldMapAction.getWorldObject()); break;
                default: worldMapAction.getWorldObject().onUpdate();
            }
        }
    }

    public float getScale() {
        return scale;
    }
}
