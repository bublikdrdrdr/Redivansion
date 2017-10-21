package tk.ubublik.redivansion.gamelogic.units;

import android.graphics.Point;

import com.jme3.scene.Spatial;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 */
public class WorldMap extends Observable{

    private List<WorldObject> worldObjects = new LinkedList<>();

    public boolean put(Collection<WorldObject> worldObjects){
        boolean res = true;
        for (WorldObject worldObject: worldObjects){
            if (!put(worldObject)) res = false;
        }
        return res;
    }

    public boolean put(WorldObject worldObject){
        if (canPut(worldObject)) {
            worldObjects.add(worldObject);
            WorldMapAction worldMapAction = new WorldMapAction(WorldMapAction.Action.ADD, worldObject);
            setChanged();
            notifyObservers(worldMapAction);
            return true;
        } else return false;
    }

    public boolean canPut(WorldObject worldObject){
        setChanged();
        notifyObservers(new WorldMapAction(WorldMapAction.Action.CHECK, worldObject));
        return canPut(worldObject.getPosition(), worldObject.getSize());
    }

    public boolean canPut(Point position, int size){
        for (WorldObject object: worldObjects){
            if (objectsIntersect(object, position, size)) return false;
        }
        return true;
    }

    public boolean canPutRectangle(Point p1, Point p2){
        p1 = new Point(p1);
        p2 = new Point(p2);
        if (p1.x>p2.x) swapX(p1, p2);
        if (p1.y>p2.y) swapY(p1, p2);
        for (WorldObject object: worldObjects){
            if (objectInRectangle(object, p1, p2)) return false;
        }
        return true;
    }

    private void swapX(Point p1, Point p2){
        int x = p1.x;
        p1.x = p2.x;
        p2.x = x;
    }

    private void swapY(Point p1, Point p2){
        int y = p1.y;
        p1.y = p2.y;
        p2.y = y;
    }

    public boolean objectInRectangle(WorldObject worldObject, Point p1, Point p2){
        return (worldObject.getPosition().x<=p2.x &&
                worldObject.getPosition().x+worldObject.getSize() > p1.x &&
                worldObject.getPosition().y<=p2.y &&
                worldObject.getPosition().y+worldObject.getSize() > p1.y);
    }

    public WorldObject get(Point position){
        for (WorldObject worldObject: worldObjects) {
            if (objectInPoint(worldObject, position))
                setChanged();
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
            setChanged();
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
                setChanged();
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
        return objectsIntersect(o1, o2.getPosition(), o2.getSize());
    }

    public boolean objectsIntersect(WorldObject o1, Point o2Position, int o2Size){
        return objectsIntersect(o1.getPosition(), o1.getSize(), o2Position, o2Size);
    }

    public boolean objectsIntersect(Point o1Position, int o1Size, Point o2Position, int o2Size){
        return (o1Position.x + o1Size > o2Position.x) &&
                (o1Position.x < o2Position.x + o2Size) &&
                (o1Position.y + o1Size > o2Position.y) &&
                (o1Position.y < o2Position.y + o2Size);
    }

    public WorldObject getFromSpatial(Spatial spatial){
        for (WorldObject worldObject: worldObjects){
            if (worldObject.getGeometryManager().equals(spatial))
                return worldObject;
        }
        return null;
    }

    public void onUpdate() {

    }

    public void update(WorldObject worldObject){
        setChanged();
        notifyObservers(new WorldMapAction(WorldMapAction.Action.UPDATE, worldObject));
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public boolean isFree(Point position, int size){
        for (WorldObject worldObject: worldObjects){
            if ((position.x + size > worldObject.getPosition().x) &&
                    (position.x < worldObject.getPosition().x + worldObject.getSize()) &&
                    (position.y + size > worldObject.getPosition().y) &&
                    (position.y < worldObject.getPosition().y + worldObject.getSize())) {
                return false;
            }
        }
        return true;
    }

    public List<Road> getNearbyRoads(Point p1, Point p2){
        p1 = new Point(p1);
        p2 = new Point(p2);
        if (p1.x>p2.x) swapX(p1, p2);
        if (p1.y>p2.y) swapY(p1, p2);
        pointShift(p1, false);
        pointShift(p2, true);
        List<Road> list = new LinkedList<>();
        for (WorldObject worldObject: worldObjects)
            if (worldObject instanceof Road && objectInRectangle(worldObject, p1, p2))
                list.add((Road)worldObject);
        return list;
    }

    private void pointShift(Point point, boolean increment){
        int shift = increment?1:-1;
        point.x+=shift;
        point.y+=shift;
    }

    public List<WorldObject> getNearbyObjects(Point p1, Point p2){
        p1 = new Point(p1);
        p2 = new Point(p2);
        if (p1.x>p2.x) swapX(p1, p2);
        if (p1.y>p2.y) swapY(p1, p2);
        pointShift(p1, false);
        pointShift(p2, true);
        List<WorldObject> list = new LinkedList<>();
        for (WorldObject worldObject: worldObjects)
            if (objectInRectangle(worldObject, p1, p2))
                list.add(worldObject);
        return list;
    }
}
