package tk.ubublik.redivansion.gamelogic.units;

import android.graphics.Point;
import android.net.wifi.WifiManager;

import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.component.BorderLayout;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 */
public class WorldMap extends Observable{

    private List<WorldObject> worldObjects;

    public WorldMap(){
        this(new LinkedList<WorldObject>());
    }

    public WorldMap(List<WorldObject> worldObjects){
        this.worldObjects = worldObjects;
    }

    public boolean put(WorldObject worldObject){
        if (canPut(worldObject)) {
            worldObjects.add(worldObject);
            notifyObservers(new WorldMapAction(WorldMapAction.Action.ADD, worldObject));
            return true;
        } else return false;
    }

    public boolean canPut(WorldObject worldObject){
        notifyObservers(new WorldMapAction(WorldMapAction.Action.CHECK, worldObject));
        for (WorldObject object: worldObjects){
            if (objectsIntersect(worldObject, object)) return false;
        }
        return true;
    }

    public WorldObject get(Point position){
        for (WorldObject worldObject: worldObjects) {
            if (objectInPoint(worldObject, position))
                notifyObservers(new WorldMapAction(WorldMapAction.Action.GET, worldObject));
                return worldObject;
        }
        return null;
    }

    public boolean remove(Point position){
        WorldObject worldObject = get(position);
        if (worldObject==null) return false;
        return remove(worldObject);
    }

    public boolean remove(WorldObject worldObject){
        if (worldObjects.remove(worldObject)){
            notifyObservers(new WorldMapAction(WorldMapAction.Action.REMOVE, worldObject));
            return true;
        } else return false;
    }

    public boolean fastRemove(Point position){
        // TODO: 04-Oct-17 check speed difference, in "remove" function we search for object in position and then search it again in list
        Iterator<WorldObject> iterator = worldObjects.iterator();
        while (iterator.hasNext()){
            WorldObject worldObject = iterator.next();
            if (objectInPoint(worldObject, position)){
                iterator.remove();
                notifyObservers(new WorldMapAction(WorldMapAction.Action.REMOVE, worldObject));
                return true;
            }
        }
        return false;
    }

    public boolean objectInPoint(WorldObject worldObject, Point position){
        return (position.x >= worldObject.getPosition().x &&
                position.x < worldObject.getPosition().x + worldObject.getSize() &&
                position.y >= worldObject.getPosition().y &&
                position.y < worldObject.getPosition().y + worldObject.getSize());
    }

    public boolean objectsIntersect(WorldObject o1, WorldObject o2) {
        return (o1.getPosition().x + o1.getSize() > o2.getPosition().x) &&
                (o1.getPosition().x < o2.getPosition().x + o2.getSize()) &&
                (o1.getPosition().y + o1.getSize() > o2.getPosition().y) &&
                (o1.getPosition().y < o2.getPosition().y + o2.getSize());
    }

    public WorldObject getFromSpatial(Spatial spatial){
        for (WorldObject worldObject: worldObjects){
            if (worldObject.getGeometryManager().equals(spatial))
                return worldObject;
        }
        return null;
    }

    public void onUpdate(Camera camera) {
        for (WorldObject worldObject: worldObjects){
            if (camera.contains(worldObject.getWorldBound())!= Camera.FrustumIntersect.Outside)
            worldObject.onUpdate();
        }
    }

    public void update(WorldObject worldObject){
        notifyObservers(new WorldMapAction(WorldMapAction.Action.UPDATE, worldObject));
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }
}
