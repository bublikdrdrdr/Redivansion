package tk.ubublik.redivansion.gamelogic.units;

import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class SavedLevel {

    public List<WorldObject> worldObjects;
    public long time;
    public int money;
    public int level;

    public SavedLevel(List<WorldObject> worldObjects, long time, int money, int level) {
        this.worldObjects = worldObjects;
        this.time = time;
        this.money = money;
        this.level = level;
    }

    public SavedLevel(byte[] bytes){

    }

    public byte[] getBytes() {
        //// TODO: 01-Sep-17
        return null;
    }
}
