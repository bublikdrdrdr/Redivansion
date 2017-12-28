package tk.ubublik.redivansion.gamelogic.utils;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by SomeOne on 18.10.2017.
 */

public interface GUIListener {

    void addTree();
    void addHouse();
    void addPower();
    void addBuilding();
    void addRoad();
    void setRoadPoints();
    void selectTree();
    void selectPower();
    void selectOffice();
    void selectClear();
    void remove();
    void save();
    void removeSave();
    void upgrade();
    void objectSelected(WorldObject object);

    WorldMap getWorldMap();
    MapRenderer getMapRenderer();
    void pauseTime(boolean value);

    void setDone(boolean done);
}
