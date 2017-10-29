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
    private String levelName;
    private LevelStatus levelStatus;
    private List<WorldObject> worldObjects = new ArrayList<>();
    private long time;
    private int money;
    private LevelGoal levelGoal;
    private Road mainRoad;

    public Level(List<WorldObject> worldObjects){
        setWorldObjects(worldObjects);
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
}
