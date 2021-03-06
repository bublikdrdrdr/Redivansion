package tk.ubublik.redivansion.gamelogic.units;

import java.util.ArrayList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Level {

    private int id;
    private List<WorldObject> worldObjects = new ArrayList<>();
    private long time;
    private int money;
    private boolean limitTypeRound;
    private float mapLimit;
    private LevelGoal levelGoal;
    private Road mainRoad;
    private int population;

    public Level(List<WorldObject> worldObjects){
        setWorldObjects(worldObjects);
    }

    public byte[] getBytes(){
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public void setWorldObjects(List<WorldObject> worldObjects) {
        this.worldObjects = worldObjects;
        mainRoad = findMainRoad();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public LevelGoal getLevelGoal() {
        return levelGoal;
    }

    public void setLevelGoal(LevelGoal levelGoal) {
        this.levelGoal = levelGoal;
    }

    private Road findMainRoad(){
        Road firstRoad = null;
        for (WorldObject worldObject: worldObjects){
            if (worldObject instanceof Road){
                if (firstRoad==null) firstRoad = (Road) worldObject;
                if (worldObject.isPermanent()) return (Road)worldObject;
            }
        }
        return firstRoad;
    }

    public Road getMainRoad() {
        return mainRoad;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public boolean isLimitTypeRound() {
        return limitTypeRound;
    }

    public void setLimitTypeRound(boolean limitTypeRound) {
        this.limitTypeRound = limitTypeRound;
    }

    public float getMapLimit() {
        return mapLimit;
    }

    public void setMapLimit(float mapLimit) {
        this.mapLimit = mapLimit;
    }
}
