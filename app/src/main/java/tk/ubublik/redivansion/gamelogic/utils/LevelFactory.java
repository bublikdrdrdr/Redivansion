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
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

import static tk.ubublik.redivansion.gamelogic.utils.GameParams.*;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class LevelFactory {

    public static Level getLevel(final int id){
        if (id<0 || id>=LEVELS_MONEY.length-1) throw new IllegalArgumentException("Unregistered levek with id "+Integer.toString(id));
        Level level = new Level(getLevelWorldObjects(id));
        level.setId(id);
        level.setMoney(LEVELS_MONEY[id]);
        level.setTime(LEVELS_TIMES[id]);
        level.setLimitTypeRound(LEVELS_MAP_LIMIT_TYPE[id]);
        level.setMapLimit(LEVELS_AREA_SIZE[id]);
        level.setLevelGoal(new LevelGoal(level) {
            @Override
            public boolean isDone() {
                return  (level.getPopulation()>=LEVELS_POPULATION_GOAL[id] && level.getMoney()>=0);
            }
        });
        return level;
    }

    private static List<WorldObject> getLevelWorldObjects(int id){
        switch (id){
            case 0: List<WorldObject> list = new LinkedList<>();
                Road road = new Road(new Point(2,2));
                road.setPermanent(true);
                list.add(road);
                list.add(new House(0,-1));
                list.add(new Office(0,2));
                list.add(new Tree(2,1));
                return list;
            default: return new LinkedList<>();
        }
    }


}
