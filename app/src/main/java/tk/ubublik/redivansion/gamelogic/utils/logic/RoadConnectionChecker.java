package tk.ubublik.redivansion.gamelogic.utils.logic;

import android.graphics.Point;

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
    private List<WorldObjectRoadConnectionStatus> result;

    public RoadConnectionChecker(WorldMap worldMap, Road mainRoad) {
        super(worldMap);
        this.mainRoad = mainRoad;
    }

    @Override
    public void run() {
        try{
            for (WorldObjectRoadConnectionStatus status: result){
                if (status.worldObject==mainRoad){
                    checkNeighbors(status);
                    break;
                }
                checkInterrupted();
            }
            done = true;
        } catch (InterruptedException ignored){
            done = false;
        }
    }

    private void checkNeighbors(WorldObjectRoadConnectionStatus status) throws InterruptedException{
        checkInterrupted();
        if (status==null || status.connected) return;
        status.connected = true;
        if (!(status.worldObject instanceof Road)) return;
        checkNeighbors(getByPosition(new Point(status.worldObject.getPosition().x+1, status.worldObject.getPosition().y)));
        checkNeighbors(getByPosition(new Point(status.worldObject.getPosition().x-1, status.worldObject.getPosition().y)));
        checkNeighbors(getByPosition(new Point(status.worldObject.getPosition().x, status.worldObject.getPosition().y+1)));
        checkNeighbors(getByPosition(new Point(status.worldObject.getPosition().x, status.worldObject.getPosition().y-1)));
    }

    private WorldObjectRoadConnectionStatus getByPosition(Point position) throws InterruptedException {
        for (WorldObjectRoadConnectionStatus status: result){
            checkInterrupted();
            if (worldMap.objectInPoint(status.worldObject, position)){
                return status;
            }
        }
        return null;
    }

    @Override
    public void refresh() {
        if (thread!=null && thread.isAlive()){
            thread.interrupt();
        }
        done = false;
        thread = new Thread(this);
        result = cloneMap(worldMap);
        thread.start();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    private List<WorldObjectRoadConnectionStatus> cloneMap(WorldMap worldMap){
        List<WorldObjectRoadConnectionStatus> list = new LinkedList<>();
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
