package tk.ubublik.redivansion.gamelogic.utils;

import android.graphics.Point;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.lifecycle.Lifecycle;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.LevelGoal;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class LevelFactory {

    public static Level getLevel(int id){
        List<WorldObject> list = new LinkedList<>();
        Road road = new Road(new Point(2,2));
        road.setPermanent(true);
        list.add(road);
        list.add(new Office(0,-1));
        list.add(new Office(0,2));
        list.add(new Tree(2,1));
        Level level = new Level(list);
        level.setId(0);
        level.setMoney(20000);
        level.setTime(5*60*1000);
        level.setLevelGoal(new LevelGoal(level) {
            @Override
            public boolean isDone() {
                return  (level.getPopulation()>5000 && level.getMoney()>=0);
            }
        });
        return level;
    }
}
