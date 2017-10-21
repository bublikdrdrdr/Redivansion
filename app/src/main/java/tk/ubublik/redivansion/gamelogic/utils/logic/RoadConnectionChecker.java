package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;

/**
 * Created by Bublik on 18-Oct-17.
 */

public class RoadConnectionChecker extends Checker implements Runnable {

    private final Road mainRoad;

    public RoadConnectionChecker(WorldMap worldMap, Road mainRoad) {
        super(worldMap);
        this.mainRoad = mainRoad;
    }

    @Override
    public void run() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
