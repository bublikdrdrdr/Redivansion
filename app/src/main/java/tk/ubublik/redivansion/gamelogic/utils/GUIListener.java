package tk.ubublik.redivansion.gamelogic.utils;

import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.lifecycle.TutorialLifecycle;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;

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
    WorldMap getWorldMap();
    MapRenderer getMapRenderer();
    GUI getGui();
    void pauseTime(boolean value);
    void changeTimeSpeed();

    void setDone(boolean done);

    void cameraTutorial(TutorialLifecycle.CameraTutorial camTut);
}
