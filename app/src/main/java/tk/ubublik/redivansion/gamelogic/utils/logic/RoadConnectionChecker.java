package tk.ubublik.redivansion.gamelogic.utils.logic;

import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 18-Oct-17.
 */

public class RoadConnectionChecker extends Checker implements Runnable {

    private final Road mainRoad;
    private volatile boolean done;
    private List<WorldObjectRoadConnectionStatus> result;

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
        return done;
    }

    public List<WorldObjectRoadConnectionStatus> getResult() {
        if (!done) return null;
        done = false;
        return result;
    }

    class WorldObjectRoadConnectionStatus{
        public WorldObject worldObject;
        public boolean connected;
    }


}
