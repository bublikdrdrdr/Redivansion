package tk.ubublik.redivansion.gamelogic.units;

import java.util.ArrayList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Level {

    private int id;
    private String levelName;
    private LevelStatus levelStatus;
    private List<WorldObject> worldObjects = new ArrayList<>();
    private long time;
    private int money;
    private LevelGoal levelGoal;

    public Level(){

    }

    public Level(byte[] bytes){

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

    public LevelStatus getLevelStatus() {
        return levelStatus;
    }

    public void setLevelStatus(LevelStatus levelStatus) {
        this.levelStatus = levelStatus;
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public void setWorldObjects(List<WorldObject> worldObjects) {
        this.worldObjects = worldObjects;
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
}
