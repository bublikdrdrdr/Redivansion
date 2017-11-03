package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;

/**
 * Created by Bublik on 18-Oct-17.
 */

public abstract class Checker {

    protected final WorldMap worldMap;
    protected volatile boolean done = false;
    protected Thread thread;

    public Checker(WorldMap worldMap){
        this.worldMap = worldMap;
    }

    public abstract void refresh();

    public boolean isDone() {
        if (done) {
            done = false;
            return true;
        } else return false;
    }

    public boolean isWorking() {
        return (thread != null && thread.isAlive());
    }

    public void join() throws InterruptedException {
        if (thread != null && thread.isAlive()) thread.join();
    }

    protected void checkInterrupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
    }
}
