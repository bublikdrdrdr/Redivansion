package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;

/**
 * Created by Bublik on 18-Oct-17.
 */

public abstract class Checker {

    protected final WorldMap worldMap;

    public Checker(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public abstract void refresh();
    public abstract boolean isDone();
    public abstract boolean isWorking();
}
