package tk.ubublik.redivansion.gamelogic.units;

import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class SavedLevel {

    List<WorldObject> worldObjects;
    long time;
    int money;
    int level;

    public SavedLevel(byte[] bytes){

    }

    public byte[] getBytes() {
        //// TODO: 01-Sep-17
        return null;
    }
}
