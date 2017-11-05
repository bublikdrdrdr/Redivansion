package tk.ubublik.redivansion.gamelogic.utils.logic;

/**
 * Created by Bublik on 18-Oct-17.
 */

public class Timer {

    private long startTime;
    private boolean paused = false;
    private int gameSpeed = 1;
    private long calculateUnitTime;//once per 5 seconds
    private long lastCalculateElapsed = 0;
    private long pauseTime;

    public Timer() {
        this(5000);
    }

    public Timer(long calculateUnitTime){
        startTime = System.currentTimeMillis();
        this.calculateUnitTime = calculateUnitTime;
    }

    public void start(){
        startTime = System.currentTimeMillis();
        setPaused(false);
    }

    public void setPaused(boolean value){
        if (paused^value) {
            this.paused = value;
            if (paused)
                pauseTime = System.currentTimeMillis();
            else startTime += System.currentTimeMillis() - pauseTime;
        }
    }

    public long elapsed(){
        return (System.currentTimeMillis()-startTime)*gameSpeed;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setGameSpeed(int speed){
        boolean pausedState = paused;
        setPaused(false);
        long c = System.currentTimeMillis();
        long d = c-startTime;
        startTime = c-d*gameSpeed+d-d/speed;
        gameSpeed = speed;
        setPaused(pausedState);
    }

    public boolean calculateReady(){
        long elapsed = elapsed();
        if (elapsed-lastCalculateElapsed>=calculateUnitTime){
            lastCalculateElapsed = elapsed;
            return true;
        } else return false;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }
}
