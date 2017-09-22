package tk.ubublik.redivansion.gamelogic.units;

import android.graphics.Point;

import com.jme3.scene.Spatial;
import com.simsilica.lemur.component.BorderLayout;

import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 */
public class WorldMap {

    private List<WorldObject> worldObjects;

    public WorldMap(){
        this(new LinkedList<WorldObject>());
    }

    public WorldMap(List<WorldObject> worldObjects){
        this.worldObjects = worldObjects;
    }

    public void put(WorldObject worldObject){
        if (canPut(worldObject)) worldObjects.add(worldObject);
    }

    public boolean canPut(WorldObject worldObject){
        for (WorldObject object: worldObjects){
            if (objectsIntersect(worldObject, object)) return false;
        }
        return true;
    }

    public boolean objectsIntersect(WorldObject o1, WorldObject o2){
        return (o1.getPosition().x+o1.getSize()>o2.getPosition().x)&&
                (o1.getPosition().x<o2.getPosition().x+o2.getSize())&&
                (o1.getPosition().y+o1.getSize()>o2.getPosition().y)&&
                (o1.getPosition().y<o2.getPosition().y+o2.getSize());
    }

    public WorldObject getFromSpatial(Spatial spatial){
        for (WorldObject worldObject: worldObjects){
            if (worldObject.getGeometryManager().equals(spatial))
                return worldObject;
        }
        return null;
    }
}
