package tk.ubublik.redivansion.gamelogic.utils;

import tk.ubublik.redivansion.gamelogic.units.objects.FireStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Hospital;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.PoliceStation;
import tk.ubublik.redivansion.gamelogic.units.objects.School;
import tk.ubublik.redivansion.gamelogic.units.objects.ShoppingMall;
import tk.ubublik.redivansion.gamelogic.units.objects.ThermalPowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.WaterPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 28-Oct-17.
 * Radius here is RADIUS^2 (faster calculations)
 */

public class GameParams {

    public static final int ROAD_BUILD_COST = 150;

    //office
    public static final int[] OFFICE_LEVELS_BUILD_COST = new int[]{3000, 3500, 4000, 5000}; //build, upgrade, upgrade...
    public static final int[] OFFICE_LEVELS_MONTH_COST = new int[]{300, 350, 400, 500}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_POWER = new int[]{-3, -4, -5, -7}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_FIRE = new int[]{-2, -2, -3, -3}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_WATER = new int[]{-3, -3, -3, -3}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_POLLUTION = new int[]{-1, -1, -1, -2}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_CRIMINAL = new int[]{-1, -2, -2, -2}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_HEALTH = new int[]{0, 0, -1, -1}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_MAX_WORK = new int[]{40, 60, 100, 200}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_HAPPINESS = new int[]{1,2,2,3}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_EDUCATION = new int[]{1, 2, 2, 3}; //1,2,3,4,5...

    public static final float OFFICE_POWER_PRODUCTIVITY = 1.2f;//more energy office has, more work places it gives
    public static final float OFFICE_RADIUS = 50f; //for work

    //house
    public static final int[] HOUSE_LEVELS_BUILD_COST = new int[]{2000, 2500, 3000, 4000}; //build, upgrade, upgrade...
    public static final int[] HOUSE_LEVELS_MONTH_COST = new int[]{100, 150, 200, 250}; // this + per person
    public static final int[] HOUSE_LEVELS_MAX_POPULATION = new int[]{60, 80, 150, 300};
    public static final int[] HOUSE_LEVELS_POWER = new int[]{-4, -4, -5, -5}; // this + per person
    public static final int[] HOUSE_LEVELS_FIRE = new int[]{-3, -3, -4, -4}; // this + per person
    public static final int[] HOUSE_LEVELS_WATER = new int[]{-4, -4, -5, -5}; // this + per person
    public static final int[] HOUSE_LEVELS_POLLUTION = new int[]{-1, -1, -1, -2}; // this + per person
    public static final int[] HOUSE_LEVELS_CRIMINAL = new int[]{-2, -2, -3, -4};
    public static final int[] HOUSE_LEVELS_HEALTH = new int[]{-2,-2,-2,-3};
    public static final int[] HOUSE_LEVELS_WORK = new int[]{5, 6, 7, 8};
    public static final int[] HOUSE_LEVELS_HAPPINESS = new int[]{-1, -1, -2, -3}; // this + per person
    public static final int[] HOUSE_LEVELS_EDUCATION = new int[]{-1, -1, -1, -1}; // this + per person
    public static final float POPULATION_GROW_MAIN_RESOURCES_PERCENT = 0.5f; //for power - % of energy needed to population grow, in other case - decrease
    public static final float POPULATION_GROW_MINOR_RESOURCES_PERCENT = 0.5f; //for non main resources (happiness, education, pollution)
    public static final float POPULATION_GROW_DELTA = 2f; // TODO: full random, check in game (x in documentation algorithm)
    public static final float POWER_PP = -0.07f;
    public static final float FIRE_PP = -0.07f;
    public static final float WATER_PP = -0.07f;
    public static final float POLLUTION_PP = -0.07f;
    public static final float CRIMINAL_PP = -0.07f;
    public static final float HEALTH_PP = -0.07f;
    public static final float HAPPINESS_PP = -0.07f;
    public static final float WORK_PP = -0.07f;
    public static final float EDUCATION_PP = -0.07f;
    public static final float MONEY_PP = 1f;

    //thermal power plant
    public static final int[] THERMAL_POWER_PLANT_LEVELS_BUILD_COST = new int[]{5000, 5500, 7000}; //build, upgrade, upgrade...
    public static final int[] THERMAL_POWER_PLANT_LEVELS_MONTH_COST = new int[]{-150, -200, -300};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_POWER = new int[]{50, 60, 75};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_FIRE = new int[]{-5, -6, -7};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_WATER = new int[]{-3, -4, -5};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_POLLUTION = new int[]{-4, -5, -6};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_HEALTH = new int[]{-2, -3, -4}; //vot tut hz, daje zabrudnennya, znachyt zabyraje zdorowja
    public static final int[] THERMAL_POWER_PLANT_LEVELS_WORK = new int[]{10, 12, 15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_HAPPINESS = new int[]{-1, -2, -3};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] THERMAL_POWER_PLANT_LEVELS_RADIUS = new float[]{80, 90, 120};

    //tree
    public static final int[] TREE_LEVELS_BUILD_COST = new int[]{250}; //build
    public static final int[] TREE_LEVELS_MONTH_COST = new int[]{0};
    public static final int[] TREE_LEVELS_POWER = new int[]{0};
    public static final int[] TREE_LEVELS_FIRE = new int[]{-1};
    public static final int[] TREE_LEVELS_WATER = new int[]{0};
    public static final int[] TREE_LEVELS_POLLUTION = new int[]{15};
    public static final int[] TREE_LEVELS_CRIMINAL = new int[]{0};
    public static final int[] TREE_LEVELS_HEALTH = new int[]{3};
    public static final int[] TREE_LEVELS_WORK = new int[]{0};
    public static final int[] TREE_LEVELS_HAPPINESS = new int[]{5};
    public static final int[] TREE_LEVELS_EDUCATION = new int[]{0};
    public static final float[] TREE_LEVELS_RADIUS = new float[]{50};
    //let levels (now 1) be here (maybe in the future will be possible to upgrade christmas tree to special one)

    //fire station
    public static final int[] FIRE_STATION_LEVELS_BUILD_COST = new int[]{1000};
    public static final int[] FIRE_STATION_LEVELS_MONTH_COST = new int[]{-100};
    public static final int[] FIRE_STATION_LEVELS_POWER = new int[]{-3};
    public static final int[] FIRE_STATION_LEVELS_FIRE = new int[]{30};
    public static final int[] FIRE_STATION_LEVELS_WATER = new int[]{-5};
    public static final int[] FIRE_STATION_LEVELS_POLLUTION = new int[]{0};
    public static final int[] FIRE_STATION_LEVELS_CRIMINAL = new int[]{0};
    public static final int[] FIRE_STATION_LEVELS_HEALTH = new int[]{-1};
    public static final int[] FIRE_STATION_LEVELS_WORK = new int[]{10};
    public static final int[] FIRE_STATION_LEVELS_HAPPINESS = new int[]{1};
    public static final int[] FIRE_STATION_LEVELS_EDUCATION = new int[]{0};
    public static final float[] FIRE_STATION_LEVELS_RADIUS = new float[]{60};

    //hospital
    public static final int[] HOSPITAL_LEVELS_BUILD_COST = new int[]{1200};
    public static final int[] HOSPITAL_LEVELS_MONTH_COST = new int[]{-120};
    public static final int[] HOSPITAL_LEVELS_POWER = new int[]{-4};
    public static final int[] HOSPITAL_LEVELS_FIRE = new int[]{-2};
    public static final int[] HOSPITAL_LEVELS_WATER = new int[]{-3};
    public static final int[] HOSPITAL_LEVELS_POLLUTION = new int[]{0};
    public static final int[] HOSPITAL_LEVELS_CRIMINAL = new int[]{0};
    public static final int[] HOSPITAL_LEVELS_HEALTH = new int[]{25};
    public static final int[] HOSPITAL_LEVELS_WORK = new int[]{20};
    public static final int[] HOSPITAL_LEVELS_HAPPINESS = new int[]{3};
    public static final int[] HOSPITAL_LEVELS_EDUCATION = new int[]{0};
    public static final float[] HOSPITAL_LEVELS_RADIUS = new float[]{120};

    //park
    public static final int[] PARK_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000};
    public static final int[] PARK_LEVELS_MONTH_COST = new int[]{50,50,50};
    public static final int[] PARK_LEVELS_POWER = new int[]{-2,-4,-6};
    public static final int[] PARK_LEVELS_FIRE = new int[]{-5,-6,-7};
    public static final int[] PARK_LEVELS_WATER = new int[]{-5,-10,-15};
    public static final int[] PARK_LEVELS_POLLUTION = new int[]{10,20,25};
    public static final int[] PARK_LEVELS_CRIMINAL = new int[]{5,7,9};
    public static final int[] PARK_LEVELS_HEALTH = new int[]{2,4,6};//fresh air from trees?
    public static final int[] PARK_LEVELS_WORK = new int[]{1,2,3};
    public static final int[] PARK_LEVELS_HAPPINESS = new int[]{10,25,45};
    public static final int[] PARK_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] PARK_LEVELS_RADIUS = new float[]{100, 110, 140};

    //police station
    public static final int[] POLICE_STATION_LEVELS_BUILD_COST = new int[]{750};
    public static final int[] POLICE_STATION_LEVELS_MONTH_COST = new int[]{-100};
    public static final int[] POLICE_STATION_LEVELS_POWER = new int[]{-2};
    public static final int[] POLICE_STATION_LEVELS_FIRE = new int[]{-1};
    public static final int[] POLICE_STATION_LEVELS_WATER = new int[]{-2};
    public static final int[] POLICE_STATION_LEVELS_POLLUTION = new int[]{0};
    public static final int[] POLICE_STATION_LEVELS_CRIMINAL = new int[]{25};
    public static final int[] POLICE_STATION_LEVELS_HEALTH = new int[]{-1};
    public static final int[] POLICE_STATION_LEVELS_WORK = new int[]{15};
    public static final int[] POLICE_STATION_LEVELS_HAPPINESS = new int[]{2};
    public static final int[] POLICE_STATION_LEVELS_EDUCATION = new int[]{0};
    public static final float[] POLICE_STATION_LEVELS_RADIUS = new float[]{60};

    //school
    public static final int[] SCHOOL_LEVELS_BUILD_COST = new int[]{1200};
    public static final int[] SCHOOL_LEVELS_MONTH_COST = new int[]{-150};
    public static final int[] SCHOOL_LEVELS_POWER = new int[]{-2};
    public static final int[] SCHOOL_LEVELS_FIRE = new int[]{-2};
    public static final int[] SCHOOL_LEVELS_WATER = new int[]{-3};
    public static final int[] SCHOOL_LEVELS_POLLUTION = new int[]{1};
    public static final int[] SCHOOL_LEVELS_CRIMINAL = new int[]{-1};
    public static final int[] SCHOOL_LEVELS_HEALTH = new int[]{-3};
    public static final int[] SCHOOL_LEVELS_WORK = new int[]{15};
    public static final int[] SCHOOL_LEVELS_HAPPINESS = new int[]{2};
    public static final int[] SCHOOL_LEVELS_EDUCATION = new int[]{40};
    public static final float[] SCHOOL_LEVELS_RADIUS = new float[]{120};

    //shopping mall
    public static final int[] SHOPPING_MALL_LEVELS_BUILD_COST = new int[]{4000};
    public static final int[] SHOPPING_MALL_LEVELS_MONTH_COST = new int[]{300};
    public static final int[] SHOPPING_MALL_LEVELS_POWER = new int[]{-5};
    public static final int[] SHOPPING_MALL_LEVELS_FIRE = new int[]{-4};
    public static final int[] SHOPPING_MALL_LEVELS_WATER = new int[]{-2};
    public static final int[] SHOPPING_MALL_LEVELS_POLLUTION = new int[]{2};
    public static final int[] SHOPPING_MALL_LEVELS_CRIMINAL = new int[]{-5};//tyryat' 3,14 door ы
    public static final int[] SHOPPING_MALL_LEVELS_HEALTH = new int[]{-1};
    public static final int[] SHOPPING_MALL_LEVELS_WORK = new int[]{20};
    public static final int[] SHOPPING_MALL_LEVELS_HAPPINESS = new int[]{10};
    public static final int[] SHOPPING_MALL_LEVELS_EDUCATION = new int[]{0};
    public static final float[] SHOPPING_MALL_LEVELS_RADIUS = new float[]{40};

    //water plant
    public static final int[] WATER_PLANT_LEVELS_BUILD_COST = new int[]{2500,3000,4000};
    public static final int[] WATER_PLANT_LEVELS_MONTH_COST = new int[]{-100, -150, -200};
    public static final int[] WATER_PLANT_LEVELS_POWER = new int[]{30,40,50};
    public static final int[] WATER_PLANT_LEVELS_FIRE = new int[]{5,7,10};
    public static final int[] WATER_PLANT_LEVELS_WATER = new int[]{35,45,60};
    public static final int[] WATER_PLANT_LEVELS_POLLUTION = new int[]{0,0,0};
    public static final int[] WATER_PLANT_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] WATER_PLANT_LEVELS_HEALTH = new int[]{-1,-2,-3};
    public static final int[] WATER_PLANT_LEVELS_WORK = new int[]{10,12,15};
    public static final int[] WATER_PLANT_LEVELS_HAPPINESS = new int[]{-2, -3, -5};
    public static final int[] WATER_PLANT_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] WATER_PLANT_LEVELS_RADIUS = new float[]{80, 100, 120};

    public static final boolean ROUND_MAP_LIMIT = true;
    public static final int[] LEVELS_MONEY = new int[]{20000, 25000, 30000, 35000, 40000, 45000};
    public static final long[] LEVELS_TIMES = new long[]{5*60*1000, 5*60*1000, 7*60*1000, 10*60*1000, 12*60*1000, 15*60*1000};
    //public static final long[] LEVELS_TIMES = new long[]{15*1000, 15*1000, 15*1000, 15*1000, 15*1000, 15*60*1000};
    public static final int[] LEVELS_POPULATION_GOAL = new int[]{200, 500, 1000, 1500, 2000, 2500};
    //public static final int[] LEVELS_POPULATION_GOAL = new int[]{30, 30, 30, 30, 30, 30};
    public static final boolean[] LEVELS_MAP_LIMIT_TYPE = new boolean[]{ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT};
    public static final float[] LEVELS_AREA_SIZE = new float[]{60, 70, 80, 90, 100, 120};

    public static int getResourceValue(WorldObject object, WorldObject.ResourceType resourceType) {
        if(object instanceof Office) {
            switch (resourceType) {
                case POWER:
                    return OFFICE_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return OFFICE_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return OFFICE_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return OFFICE_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return OFFICE_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return OFFICE_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return OFFICE_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return OFFICE_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof House) {
            switch (resourceType) {
                case POWER:
                    return HOUSE_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return HOUSE_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return HOUSE_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return HOUSE_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return HOUSE_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return HOUSE_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return HOUSE_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return HOUSE_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof ThermalPowerPlant) {
            switch (resourceType) {
                case POWER:
                    return THERMAL_POWER_PLANT_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return THERMAL_POWER_PLANT_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return THERMAL_POWER_PLANT_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return THERMAL_POWER_PLANT_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return THERMAL_POWER_PLANT_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return THERMAL_POWER_PLANT_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return THERMAL_POWER_PLANT_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return THERMAL_POWER_PLANT_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof FireStation) {
            switch (resourceType) {
                case POWER:
                    return FIRE_STATION_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return FIRE_STATION_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return FIRE_STATION_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return FIRE_STATION_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return FIRE_STATION_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return FIRE_STATION_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return FIRE_STATION_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return FIRE_STATION_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof PoliceStation) {
            switch (resourceType) {
                case POWER:
                    return POLICE_STATION_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return POLICE_STATION_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return POLICE_STATION_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return POLICE_STATION_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return POLICE_STATION_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return POLICE_STATION_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return POLICE_STATION_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return POLICE_STATION_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof Hospital) {
            switch (resourceType) {
                case POWER:
                    return HOSPITAL_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return HOSPITAL_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return HOSPITAL_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return HOSPITAL_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return HOSPITAL_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return HOSPITAL_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return HOSPITAL_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return HOSPITAL_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof School) {
            switch (resourceType) {
                case POWER:
                    return SCHOOL_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return SCHOOL_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return SCHOOL_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return SCHOOL_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return SCHOOL_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return SCHOOL_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return SCHOOL_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return SCHOOL_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof ShoppingMall) {
            switch (resourceType) {
                case POWER:
                    return SHOPPING_MALL_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return SHOPPING_MALL_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return SHOPPING_MALL_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return SHOPPING_MALL_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return SHOPPING_MALL_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return SHOPPING_MALL_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return SHOPPING_MALL_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return SHOPPING_MALL_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }else if(object instanceof WaterPlant) {
            switch (resourceType) {
                case POWER:
                    return WATER_PLANT_LEVELS_POWER[object.getLevelNumber()];
                case FIRE:
                    return WATER_PLANT_LEVELS_FIRE[object.getLevelNumber()];
                case WATER:
                    return WATER_PLANT_LEVELS_WATER[object.getLevelNumber()];
                case POLLUTION:
                    return WATER_PLANT_LEVELS_POLLUTION[object.getLevelNumber()];
                case CRIMINAL:
                    return WATER_PLANT_LEVELS_CRIMINAL[object.getLevelNumber()];
                case HEALTH:
                    return WATER_PLANT_LEVELS_HEALTH[object.getLevelNumber()];
                case WORK:
                    return 0;
                case HAPPINESS:
                    return WATER_PLANT_LEVELS_HAPPINESS[object.getLevelNumber()];
                case EDUCATION:
                    return WATER_PLANT_LEVELS_EDUCATION[object.getLevelNumber()];
                default:
                    throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
            }
        }
        return 0;
    }
}
