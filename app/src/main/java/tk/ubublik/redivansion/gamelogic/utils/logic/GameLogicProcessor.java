package tk.ubublik.redivansion.gamelogic.utils.logic;

import java.util.*;

import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;

/**
 * Created by Bublik on 31-Aug-17.
 *
 * Calculates game units (population/resources)
 */
public class GameLogicProcessor implements Observer {

    private final Level level;
    private Timer timer;
    private final WorldMap worldMap;


    public GameLogicProcessor(WorldMap worldMap, Level level) {
        this.worldMap = worldMap;
        this.level = level;
        timer = new Timer();
    }

    public void onUpdate() {
        if (timer.isPaused()) return;
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction){
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            switch (worldMapAction.getAction()){

            }
        }
    }
}
