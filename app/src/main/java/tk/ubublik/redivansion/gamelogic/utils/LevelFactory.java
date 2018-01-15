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
import tk.ubublik.redivansion.gamelogic.units.objects.FireStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Hospital;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.PoliceStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.RoadState;
import tk.ubublik.redivansion.gamelogic.units.objects.School;
import tk.ubublik.redivansion.gamelogic.units.objects.ShoppingMall;
import tk.ubublik.redivansion.gamelogic.units.objects.ThermalPowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WaterPlant;
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
        Road road;
        List<WorldObject> list;
        switch (id){
            case 0: list = new LinkedList<>();
                list.add(new House(-1,-1));
                list.add(new House(-1,1));
                list.add(new House(-4,-4));
                list.add(new House(-2,-4));
                list.add(new House(1,-4));
                list.add(new Office(1,-1));
                list.add(new Office(1,1));
                list.add(new Office(-4,-1));
                list.add(new Office(-4,2));
                list.add(new Tree(-4,1));
                list.add(new Tree(-3,1));
                list.add(new Tree(0,-4));
                list.add(new Tree(0,-3));
                road = new Road(new Point(-2,-2), new RoadState(false,true,true,true));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,-1), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,0), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,1), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,2), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,4), new RoadState(true,false,true,false));
                list.add(road);
                road = new Road(new Point(-3,4), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-4,4), new RoadState(false,false,false,true));
                list.add(road);


                road = new Road(new Point(-4,-2), new RoadState(false,false,false,true));
                list.add(road);
                road = new Road(new Point(-3,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-1,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(0,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(1,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(2,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(3,-2), new RoadState(false,true,true,false));
                list.add(road);
                road = new Road(new Point(3,-1), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(3,0), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(3,1), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(3,2), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(3,3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(3,4), new RoadState(true,false,false,false));
                list.add(road);
                return list;

            case 1: list = new LinkedList<>();
                list.add(new House(-1,-4));
                list.add(new House(1,-4));
                list.add(new House(-4,-4));
                list.add(new House(-6,-4));
                list.add(new House(-4,-2));
                list.add(new House(-6,-2));
                list.add(new Office(-4,1));
                list.add(new Office(-6,1));
                list.add(new Office(-4,3));
                list.add(new ThermalPowerPlant(5,-5));
                list.add(new Tree(-1,-1));
                list.add(new Tree(1,-1));
                list.add(new Tree(3,-1));
                list.add(new Tree(5,-1));
                list.add(new Tree(7,-1));
                list.add(new Tree(-5,3));
                road = new Road(new Point(-2,-4), new RoadState(false,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,-3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,-2), new RoadState(true,true,false,true));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,-1), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,0), new RoadState(true,true,true,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,1), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,2), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,4), new RoadState(true,false,false,false));
                list.add(road);

                road = new Road(new Point(-7,-4), new RoadState(false,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-2), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-1), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,0), new RoadState(true,true,false,true));
                list.add(road);
                road = new Road(new Point(-7,1), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,2), new RoadState(true,false,false,false));
                list.add(road);


                road = new Road(new Point(-6,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-5,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-4,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-3,0), new RoadState(false,false,true,true));
                list.add(road);

                road = new Road(new Point(-1,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(0,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(1,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(2,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(3,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(4,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(5,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(6,-2), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(7,-2), new RoadState(false,false,true,false));
                list.add(road);
                return list;

            case 2: list = new LinkedList<>();
                list.add(new Office(-1,-2));
                list.add(new Office(-4,-2));
                list.add(new Office(-6,-2));
                list.add(new Office(-6,-4));
                list.add(new House(-1,1));
                list.add(new House(-6,1));
                list.add(new House(-6,3));
                list.add(new PoliceStation(new Point(-4,1)));
                list.add(new FireStation(new Point(-1,-4)));
                list.add(new ThermalPowerPlant(-11,-9));
                list.add(new WaterPlant(new Point(-9,-5)));
                list.add(new Tree(-3,-3));
                list.add(new Tree(-7, 1));
                road = new Road(new Point(-2,-4), new RoadState(false,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,-3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,-2), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,-1), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,0), new RoadState(true,true,true,true));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,1), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,2), new RoadState(true,true,false,false));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,3), new RoadState(true,false,true,false));
                list.add(road);
                road = new Road(new Point(-3,3), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-4,3), new RoadState(false,true,false,true));
                list.add(road);
                road = new Road(new Point(-4,4), new RoadState(true,false,false,false));
                list.add(road);

                road = new Road(new Point(-7,-6), new RoadState(false,true,true,false));
                list.add(road);
                road = new Road(new Point(-7,-5), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-4), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-3), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-2), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,-1), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(-7,0), new RoadState(true,false,false,true));
                list.add(road);

                road = new Road(new Point(-11,-6), new RoadState(false,false,false,true));
                list.add(road);
                road = new Road(new Point(-10,-6), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-9,-6), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-8,-6), new RoadState(false,false,true,true));
                list.add(road);

                road = new Road(new Point(-6,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-5,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-4,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-3,0), new RoadState(false,false,true,true));
                list.add(road);

                road = new Road(new Point(-1,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(0,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(1,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(2,0), new RoadState(false,false,true,false));
                list.add(road);

                return list;

            case 3: list = new LinkedList<>();
                list.add(new House(3,-6));
                list.add(new House(3,-9));
                list.add(new House(5,-6));
                list.add(new House(5,-9));
                list.add(new House(7,-6));
                list.add(new House(7,-9));
                list.add(new Office(-1,-2));
                list.add(new Office(-4,1));
                list.add(new Office(-4,3));
                list.add(new Office(-6,3));
                list.add(new Office(-1,3));
                list.add(new Office(1,3));

                list.add(new FireStation(new Point(-4,-2)));
                list.add(new PoliceStation(new Point(-4,6)));
                list.add(new School(new Point(-1,1)));
                list.add(new WaterPlant(new Point(-10,-2)));
                list.add(new ThermalPowerPlant(-9,-8));
                list.add(new Hospital(new Point(10,-6)));

                list.add(new Tree(-6,1));
                list.add(new Tree(-5,1));
                list.add(new Tree(-6,2));
                list.add(new Tree(-5,2));

                int i = 0;
                for(i = -6; i < 7; i++){
                    if(i != 0 && i != 5) {
                        road = new Road(new Point(-2, i), new RoadState(true, true, false, false));
                        if (i > -3 && i < 3) road.setPermanent(true);
                        list.add(road);
                    }
                }
                road = new Road(new Point(-2,0), new RoadState(true,true,true,true));
                road.setPermanent(true);
                list.add(road);
                road = new Road(new Point(-2,5), new RoadState(true,true,true,true));
                list.add(road);
                road = new Road(new Point(-2,7), new RoadState(true,false,false,false));
                list.add(road);
                road = new Road(new Point(-2,-7), new RoadState(false,true,true,true));
                list.add(road);

                for(i = -6; i < 3; i++){
                    if(i != -2){
                        road = new Road(new Point(i,0), new RoadState(false, false, true, true));
                        list.add(road);
                        road = new Road(new Point(i,5), new RoadState(false, false, true, true));
                        list.add(road);
                    }
                }
                for(i = 1; i < 5; i++){
                    if(i != -2){
                        road = new Road(new Point(3,i), new RoadState(true, true, false, false));
                        list.add(road);
                        road = new Road(new Point(-7,i), new RoadState(true, true, false, false));
                        list.add(road);
                    }
                }
                road = new Road(new Point(-10,0), new RoadState(false,false,false,true));
                list.add(road);
                road = new Road(new Point(-9,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-8,0), new RoadState(false,false,true,true));
                list.add(road);
                road = new Road(new Point(-7,0), new RoadState(false,true,true,true));
                list.add(road);
                road = new Road(new Point(3,0), new RoadState(false,true,true,false));
                list.add(road);
                road = new Road(new Point(3,5), new RoadState(true,false,true,false));
                list.add(road);
                road = new Road(new Point(-7,5), new RoadState(true,false,false,true));
                list.add(road);


                for(i = -5; i < 9; i++){
                    if(i != -2){
                        road = new Road(new Point(i,-7), new RoadState(false, false, true, true));
                        list.add(road);
                    }
                }
                road = new Road(new Point(-6,-7), new RoadState(false,false,false,true));
                list.add(road);
                road = new Road(new Point(9,-7), new RoadState(false,true,true,false));
                list.add(road);
                road = new Road(new Point(9,-6), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(9,-5), new RoadState(true,true,false,false));
                list.add(road);
                road = new Road(new Point(9,-4), new RoadState(true,false,false,false));
                list.add(road);

                return list;

            case 4: list = new LinkedList<>();
                list.add(new House(-1,-2));
                list.add(new Office(-1,1));
                list.add(new House(1,-2));
                list.add(new Office(3,1));
                list.add(new House(3,-2));
                list.add(new ShoppingMall(new Point(6,-2)));
                list.add(new ShoppingMall(new Point(-1,4)));
                list.add(new PoliceStation(new Point(1,1)));
                list.add(new FireStation(new Point(-4,-5)));
                list.add(new WaterPlant(new Point(-1,-12)));
                list.add(new Hospital(new Point(3,-6)));
                list.add(new ThermalPowerPlant(-5,-12));
                i = 0;
                for(i = -11; i < 3; i++){
                    if(i != -3) {
                        road = new Road(new Point(-2, i), new RoadState(true, true, false, false));
                        if (i > -3 && i < 3) road.setPermanent(true);
                        list.add(road);
                    }
                }
                road = new Road(new Point(-2,-3), new RoadState(true,true,false,true));
                list.add(road);
                road = new Road(new Point(-2,-12), new RoadState(false,true,false,false));
                list.add(road);
                road = new Road(new Point(-2,3), new RoadState(true,false,false,true));
                list.add(road);
                road = new Road(new Point(5,-3), new RoadState(false,true,true,false));
                list.add(road);
                road = new Road(new Point(5,3), new RoadState(true,false,true,false));
                list.add(road);
                for(i = -1; i < 5; i++){
                    road = new Road(new Point(i, -3), new RoadState(false, false, true, true));
                    list.add(road);
                    road = new Road(new Point(i, 3), new RoadState(false, false, true, true));
                    list.add(road);
                }

                return list;
            default: return new LinkedList<>();
        }
    }


}
