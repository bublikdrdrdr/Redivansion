package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;

/**
 * Created by Bublik on 20-Oct-17.
 */

public class SingleObjectChecker extends Checker {

    public SingleObjectChecker(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
