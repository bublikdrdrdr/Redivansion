package tk.ubublik.redivansion.gamelogic.units;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 04-Oct-17.
 */

public class WorldMapAction {

    public enum Action{ADD, GET, CHECK, UPDATE, REMOVE}

    private Action action;
    private WorldObject worldObject;

    public WorldMapAction() {
    }

    public WorldMapAction(Action action, WorldObject worldObject) {
        this.action = action;
        this.worldObject = worldObject;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public WorldObject getWorldObject() {
        return worldObject;
    }

    public void setWorldObject(WorldObject worldObject) {
        this.worldObject = worldObject;
    }
}
