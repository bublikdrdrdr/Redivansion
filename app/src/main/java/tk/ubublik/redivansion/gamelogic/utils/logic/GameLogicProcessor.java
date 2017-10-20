package tk.ubublik.redivansion.gamelogic.utils.logic;

import java.util.*;

import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;
import tk.ubublik.redivansion.gamelogic.units.objects.PowerPlant;

/**
 * Created by Bublik on 31-Aug-17.
 *
 * Calculates game units (population/resources)
 */
public class GameLogicProcessor implements Observer {

    private final Level level;
    private Timer timer;
    private final WorldMap worldMap;
    private boolean stateChanged = true;

    private RoadConnectionChecker roadConnectionChecker;
    private PowerChecker powerChecker;

    public GameLogicProcessor(WorldMap worldMap, Level level) {
        this.worldMap = worldMap;
        this.level = level;
        timer = new Timer();
        roadConnectionChecker = new RoadConnectionChecker(worldMap, level.getMainRoad());
        powerChecker = new PowerChecker(worldMap);
    }

    public void onUpdate() {
        if (timer.isPaused()) return;
        if (stateChanged) roadConnectionChecker.refresh();
        powerChecker.refresh();
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

    private long elapsed(){
        return timer.elapsed();
    }

    public void setGameSpeed(int speed){
        timer.setGameSpeed(speed);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction){
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            switch (worldMapAction.getAction()){

            }
        }
    }
}
