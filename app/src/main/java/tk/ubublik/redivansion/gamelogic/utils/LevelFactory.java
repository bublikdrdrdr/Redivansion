package tk.ubublik.redivansion.gamelogic.utils;

import java.util.HashMap;
import java.util.Map;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class LevelFactory {

    private static final Map<Object, Level> map = new HashMap<>();

    public static Level getLevel(Object key, Map<String, GeometryManager> models){
        Level level = new Level();
        /*if (key.equals("tutorial")){
            GeometryManager simpleModel = models.get("simple");
            level.setLevelStatus(LevelStatus.ENABLED);
            LevelGoal levelGoal = new LevelGoal(level) {
                @Override
                public boolean isDone() {
                    //level goal - build more than 5 objects
                    return (level.getWorldObjects().size()>5);
                }
            };
            level.setLevelGoal(levelGoal);
            level.setMoney(5000);
            level.setTime(-1);
            List<WorldObject> worldObjects = new LinkedList<>();
            House house = new House(simpleModel);
            worldObjects.add(house);
            house.setSize(1);
            house.setPosition(new Point(0,0));
            level.setWorldObjects(worldObjects);
            return level;
        }*/
        throw new IllegalArgumentException("Unregistered level");
    }

    public static Level getLevel(int id){
        //todo: get level by name
        Level level = new Level();
        level.getWorldObjects().add(new Office(0,-1));
        level.getWorldObjects().add(new Office(0,2));
        level.getWorldObjects().add(new Tree(2,1));
        return level;
    }

}
