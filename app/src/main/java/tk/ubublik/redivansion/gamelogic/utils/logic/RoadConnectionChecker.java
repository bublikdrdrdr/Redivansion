package tk.ubublik.redivansion.gamelogic.utils.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 18-Oct-17.
 */

public class RoadConnectionChecker extends Checker implements Runnable {

    private final Road mainRoad;
    private volatile boolean done = true;
    private List<WorldObjectRoadConnectionStatus> result;

    private Thread thread;

    public RoadConnectionChecker(WorldMap worldMap, Road mainRoad) {
        super(worldMap);
        this.mainRoad = mainRoad;
    }

    @Override
    public void run() {
        done = true;
    }

    @Override
    public void refresh() {
        if (thread!=null && thread.isAlive()){
            thread.interrupt();
        }
        done = false;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isWorking() {
        return false;
    }

    private List<WorldObjectRoadConnectionStatus> cloneMap(WorldMap worldMap){
        List<WorldObjectRoadConnectionStatus> list = new LinkedList<>();//worldMap.getWorldObjects().size()
        for (WorldObject worldObject: worldMap.getWorldObjects()){
            list.add(new WorldObjectRoadConnectionStatus(worldObject, false));
        }
        return list;
    }

    public List<WorldObjectRoadConnectionStatus> getResult() {
        if (!done) return null;
        done = false;
        return result;
    }

    class WorldObjectRoadConnectionStatus{
        public WorldObject worldObject;
        public boolean connected;

        public WorldObjectRoadConnectionStatus(WorldObject worldObject, boolean connected) {
            this.worldObject = worldObject;
            this.connected = connected;
        }
    }


}
