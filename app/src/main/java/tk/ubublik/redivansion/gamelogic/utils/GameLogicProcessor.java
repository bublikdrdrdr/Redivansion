package tk.ubublik.redivansion.gamelogic.utils;

import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 *
 * Calculates game units (population/resources)
 */
public class GameLogicProcessor {

    // TODO: 21-Sep-17 use only needed data from level object, not itself
    private Level level;
    private long startTime;
    private boolean paused = false;

    public void onUpdate() {
        if (paused) return;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void start(){
        if (level==null) throw new NullPointerException("Current level is null");
        startTime = System.currentTimeMillis();
        setPaused(false);
    }

    private long pauseTime;
    public void setPaused(boolean value){
        if ((!paused && value) || (paused && !value)) {
            this.paused = value;
            if (paused)
                pauseTime = System.currentTimeMillis();
            else startTime += System.currentTimeMillis() - pauseTime;
        }
    }

    public void stop(){
        //??
    }
}
