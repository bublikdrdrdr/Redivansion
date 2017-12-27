package tk.ubublik.redivansion.gamelogic.utils;

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

    void setDone(boolean done);
}
