package tk.ubublik.redivansion.gamelogic.utils.logic;

import java.util.*;

import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.SavedLevel;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 */
public class GameLogicProcessor implements Observer {

    private final Level level;
    private Timer timer;
    private final WorldMap worldMap;
    private Thread logicThread;
    private LogicResultListener logicResultListener;

    private RoadConnectionChecker roadConnectionChecker;
    private ResourcesChecker resourcesChecker;
    private SingleObjectChecker singleObjectChecker;
    private FinalChecker finalChecker;

    public GameLogicProcessor(WorldMap worldMap, Level level, LogicResultListener logicResultListener) {
        this.worldMap = worldMap;
        this.level = level;
        timer = new Timer();
        this.logicResultListener = logicResultListener;
        roadConnectionChecker = new RoadConnectionChecker(worldMap, level.getMainRoad());
        resourcesChecker = new ResourcesChecker(worldMap);
        singleObjectChecker = new SingleObjectChecker(worldMap);
        finalChecker = new FinalChecker(worldMap);
    }

    public void onUpdate() {
        if (timer.isPaused()) return;
        if (roadConnectionChecker.isDone()){
            if (!(resourcesChecker.isWorking()||singleObjectChecker.isWorking()||finalChecker.isWorking())){
                synchronizeRoadResults();
            }
        }
        if (finalChecker.isDone()){
            FinalChecker.Result result = finalChecker.getResult();
            synchronizeGameLoopResults(result.population, result.moneyDelta);
        }
        if (timer.calculateReady()) {
            if (logicThread!=null && logicThread.isAlive()) {
                logicThread.interrupt();
            }
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
        if (timer.elapsed()>level.getTime()){
            logicResultListener.setGameEnd(level.getLevelGoal().isDone());
        }
    }

    public void start(){
        timer.start();
    }

    public void setPaused(boolean value){
        timer.setPaused(value);
    }

    public void invalidateRoad(){
        roadConnectionChecker.refresh();
    }

    private long elapsed(){
        return timer.elapsed();
    }

    public long timeLeft(){
        return level.getTime()-elapsed();
    }

    public void setGameSpeed(int speed){
        timer.setGameSpeed(speed);
    }

    @Deprecated
    public Timer getTimer(){
        return timer;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction){
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            if (worldMapAction.getAction() == WorldMapAction.Action.ADD || worldMapAction.getAction() == WorldMapAction.Action.REMOVE) invalidateRoad();
        }
    }

    private void synchronizeRoadResults(){
        List<RoadConnectionChecker.WorldObjectRoadConnectionStatus> results = roadConnectionChecker.getResult();
        Iterator<RoadConnectionChecker.WorldObjectRoadConnectionStatus> iterator = results.iterator();
        for (WorldObject worldObject: worldMap.getWorldObjects()){
            while (iterator.hasNext()){
                RoadConnectionChecker.WorldObjectRoadConnectionStatus current = iterator.next();
                if (worldObject == current.worldObject){
                    worldObject.roadConnected = current.connected;
                    break;
                } else{
                    iterator.remove();
                }
            }
        }
    }

    private void synchronizeGameLoopResults(int newPopulation, int deltaMoney){
        level.setMoney(level.getMoney()+deltaMoney);
        level.setPopulation(newPopulation);
        logicResultListener.setStatusChanged(newPopulation, level.getMoney(), deltaMoney>0);
    }

    public interface LogicResultListener{
        void setStatusChanged(int population, int money, boolean grow);
        void setGameEnd(boolean win);
    }

    public SavedLevel getSavedLevel() {
        return new SavedLevel(worldMap.getWorldObjects(), level.getTime()-timer.elapsed(), level.getMoney(), level.getId());
    }
}
