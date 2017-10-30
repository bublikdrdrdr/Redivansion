package tk.ubublik.redivansion.gamelogic.utils.logic;

import java.util.*;

import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 *
 * Calculates game units (population/resources)
 */
public class GameLogicProcessor implements Observer {

    static final Object threadLock = new Object();

    enum LogicStage{NONE, WAIT_FOR_ROAD, ROAD, RESOURCES, RESULTS, DONE}

    private final Level level;
    private Timer timer;
    private final WorldMap worldMap;
    private boolean stateChanged = true;
    private Thread logicThread;

    private RoadConnectionChecker roadConnectionChecker;
    private ResourcesChecker resourcesChecker;
    private SingleObjectChecker singleObjectChecker;
    private FinalChecker finalChecker;

    public GameLogicProcessor(WorldMap worldMap, Level level) {
        this.worldMap = worldMap;
        this.level = level;
        timer = new Timer();
        roadConnectionChecker = new RoadConnectionChecker(worldMap, level.getMainRoad());
        resourcesChecker = new ResourcesChecker(worldMap);
        singleObjectChecker = new SingleObjectChecker(worldMap);
        finalChecker = new FinalChecker(worldMap);
    }

    public void onUpdate() {
        if (timer.isPaused()) return;
        if (roadConnectionChecker.isDone()){
            if (!(resourcesChecker.isWorking()||singleObjectChecker.isWorking()||finalChecker.isWorking())){
                synchronizeRoadResults(true);
            }
        }
        if (timer.calculateReady()) {
            if (logicThread!=null && logicThread.isAlive()) logicThread.interrupt();
            //full calculation
            logicThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        resourcesChecker.refresh();
                        resourcesChecker.join();
                        finalChecker.refresh();
                        finalChecker.join();
                    } catch (InterruptedException ignored) {
                    }
                }
            });
            logicThread.start();
        }
    }

    public void start(){
        timer.start();
    }

    public void setPaused(boolean value){
        timer.setPaused(value);
    }

    public void stop(){
        setPaused(true);
    }

    public void invalidateRoad(){
        roadConnectionChecker.refresh();
    }

    private long elapsed(){
        return timer.elapsed();
    }

    public void setGameSpeed(int speed){
        timer.setGameSpeed(speed);
    }

    private void onLogicLoopEnd(){
        synchronized (threadLock){

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction){
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            if (worldMapAction.getAction() == WorldMapAction.Action.ADD || worldMapAction.getAction() == WorldMapAction.Action.REMOVE) invalidateRoad();
        }
    }

    private void synchronizeRoadResults(boolean showWarningIcon){
        List<RoadConnectionChecker.WorldObjectRoadConnectionStatus> results = roadConnectionChecker.getResult();
        Iterator<RoadConnectionChecker.WorldObjectRoadConnectionStatus> iterator = results.iterator();
        for (WorldObject worldObject: worldMap.getWorldObjects()){
            while (iterator.hasNext()){
                RoadConnectionChecker.WorldObjectRoadConnectionStatus current = iterator.next();
                if (worldObject == current.worldObject){
                    worldObject.roadConnected = current.connected;
                    if (showWarningIcon) worldObject.setIconState(current.connected||!current.worldObject.needsRoad()?WorldObject.IconState.NONE:WorldObject.IconState.WARNING);
                    break;
                } else{
                    iterator.remove();
                }
            }
        }
    }

    //when some house population is more than N% of max population - show alert icon
    public final static float HOUSE_POPULATION_ALERT_PERCENT = 0.9f;
}
