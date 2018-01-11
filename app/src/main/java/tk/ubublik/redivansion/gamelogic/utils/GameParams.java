package tk.ubublik.redivansion.gamelogic.utils;

/**
 * Created by Bublik on 28-Oct-17.
 * Radius here is RADIUS^2 (faster calculations)
 */

public class GameParams {

    public static final int ROAD_BUILD_COST = 1000;

    //office
    public static final int[] OFFICE_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000, 1000, 1000}; //build, upgrade, upgrade...
    public static final int[] OFFICE_LEVELS_MONTH_COST = new int[]{100, 100, 100, 100, 100}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_POWER = new int[]{-5, -10, -15, -20, -25}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_FIRE = new int[]{-5, -10, -15, -20, -25}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_WATER = new int[]{-5, -10, -15, -20, -25}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_POLLUTION = new int[]{-2, -4, -6, -8, -10}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_CRIMINAL = new int[]{0, 0, 0, 0, 0}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_HEALTH = new int[]{0, 0, 0, 0, 0}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_MAX_WORK = new int[]{20, 30, 40, 50, 60}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_HAPPINESS = new int[]{0, 0, 0, 0, 0}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_EDUCATION = new int[]{0, 0, 0, 0, 0}; //1,2,3,4,5...

    public static final float OFFICE_POWER_PRODUCTIVITY = 1.2f;//more energy office has, more work places it gives
    public static final float OFFICE_RADIUS = 36f; //for work

    //house
    public static final int[] HOUSE_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000, 1000, 1000}; //build, upgrade, upgrade...
    public static final int[] HOUSE_LEVELS_MONTH_COST = new int[]{100, 100, 100, 100, 100}; // this + per person
    public static final int[] HOUSE_LEVELS_MAX_POPULATION = new int[]{50, 200, 500, 1500, 3000};
    public static final int[] HOUSE_LEVELS_POWER = new int[]{-5, -10, -15, -20, -25}; // this + per person
    public static final int[] HOUSE_LEVELS_FIRE = new int[]{-5, -10, -15, -20, -25}; // this + per person
    public static final int[] HOUSE_LEVELS_WATER = new int[]{-5, -10, -15, -20, -25}; // this + per person
    public static final int[] HOUSE_LEVELS_POLLUTION = new int[]{-5, -10, -15, -20, -25}; // this + per person
    public static final int[] HOUSE_LEVELS_CRIMINAL = new int[]{0, 0, 0, 0, 0};
    public static final int[] HOUSE_LEVELS_HEALTH = new int[]{0, 0, 0, 0, 0};
    public static final int[] HOUSE_LEVELS_WORK = new int[]{0, 0, 0, 0, 0};
    public static final int[] HOUSE_LEVELS_HAPPINESS = new int[]{0, -5, -10, -15, -20}; // this + per person
    public static final int[] HOUSE_LEVELS_EDUCATION = new int[]{0, 0, 0, 0, 0}; // this + per person
    public static final float POPULATION_GROW_MAIN_RESOURCES_PERCENT = 0.5f; //for power - % of energy needed to population grow, in other case - decrease
    public static final float POPULATION_GROW_MINOR_RESOURCES_PERCENT = 0.7f; //for non main resources (happiness, education, pollution)
    public static final float POPULATION_GROW_DELTA = 2f; // TODO: full random, check in game (x in documentation algorithm)
    public static final float POWER_PP = -0.1f;
    public static final float FIRE_PP = -0.1f;
    public static final float WATER_PP = -0.1f;
    public static final float POLLUTION_PP = -0.1f;
    public static final float CRIMINAL_PP = -0.1f;
    public static final float HEALTH_PP = -0.1f;
    public static final float HAPPINESS_PP = -0.1f;
    public static final float WORK_PP = -0.1f;
    public static final float EDUCATION_PP = -0.1f;
    public static final float MONEY_PP = 0.5f;

    //thermal power plant
    public static final int[] THERMAL_POWER_PLANT_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000}; //build, upgrade, upgrade...
    public static final int[] THERMAL_POWER_PLANT_LEVELS_MONTH_COST = new int[]{100, 100, 100};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_POWER = new int[]{-5, -10, -15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_FIRE = new int[]{-5, -10, -15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_WATER = new int[]{-1, -2, -3};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_POLLUTION = new int[]{-10, -20, -30};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_HEALTH = new int[]{-5, -10, -15}; //vot tut hz, daje zabrudnennya, znachyt zabyraje zdorowja
    public static final int[] THERMAL_POWER_PLANT_LEVELS_WORK = new int[]{5, 10, 15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_HAPPINESS = new int[]{0, 0, 0};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] THERMAL_POWER_PLANT_LEVELS_RADIUS = new float[]{25, 49, 100};

    //tree
    public static final int[] TREE_LEVELS_BUILD_COST = new int[]{1000}; //build
    public static final int[] TREE_LEVELS_MONTH_COST = new int[]{0};
    public static final int[] TREE_LEVELS_POWER = new int[]{0};
    public static final int[] TREE_LEVELS_FIRE = new int[]{-1};
    public static final int[] TREE_LEVELS_WATER = new int[]{0};
    public static final int[] TREE_LEVELS_POLLUTION = new int[]{2};
    public static final int[] TREE_LEVELS_CRIMINAL = new int[]{0};
    public static final int[] TREE_LEVELS_HEALTH = new int[]{1};
    public static final int[] TREE_LEVELS_WORK = new int[]{0};
    public static final int[] TREE_LEVELS_HAPPINESS = new int[]{1};
    public static final int[] TREE_LEVELS_EDUCATION = new int[]{0};
    public static final float[] TREE_LEVELS_RADIUS = new float[]{10};
    //let levels (now 1) be here (maybe in the future will be possible to upgrade christmas tree to special one)

    //fire station
    public static final int[] FIRE_STATION_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000};
    public static final int[] FIRE_STATION_LEVELS_MONTH_COST = new int[]{100, 100, 100};
    public static final int[] FIRE_STATION_LEVELS_POWER = new int[]{-5, -10, -15};
    public static final int[] FIRE_STATION_LEVELS_FIRE = new int[]{10, 20, 30};
    public static final int[] FIRE_STATION_LEVELS_WATER = new int[]{-10, -20, -40};
    public static final int[] FIRE_STATION_LEVELS_POLLUTION = new int[]{0,0,0};
    public static final int[] FIRE_STATION_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] FIRE_STATION_LEVELS_HEALTH = new int[]{0,0,0};
    public static final int[] FIRE_STATION_LEVELS_WORK = new int[]{5, 10, 15};
    public static final int[] FIRE_STATION_LEVELS_HAPPINESS = new int[]{0, 0, 0};
    public static final int[] FIRE_STATION_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] FIRE_STATION_LEVELS_RADIUS = new float[]{25, 49, 100};

    //hospital
    public static final int[] HOSPITAL_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000};
    public static final int[] HOSPITAL_LEVELS_MONTH_COST = new int[]{200, 200, 200};
    public static final int[] HOSPITAL_LEVELS_POWER = new int[]{-5, -10, -15};
    public static final int[] HOSPITAL_LEVELS_FIRE = new int[]{-5,-10,-15};
    public static final int[] HOSPITAL_LEVELS_WATER = new int[]{-5,-10,-15};
    public static final int[] HOSPITAL_LEVELS_POLLUTION = new int[]{0,0,0};
    public static final int[] HOSPITAL_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] HOSPITAL_LEVELS_HEALTH = new int[]{15,25,35};
    public static final int[] HOSPITAL_LEVELS_WORK = new int[]{5, 10, 15};
    public static final int[] HOSPITAL_LEVELS_HAPPINESS = new int[]{1,2,3};
    public static final int[] HOSPITAL_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] HOSPITAL_LEVELS_RADIUS = new float[]{100, 225, 400};

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
    public static final float[] PARK_LEVELS_RADIUS = new float[]{100, 225, 400};

    //police station
    public static final int[] POLICE_STATION_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000};
    public static final int[] POLICE_STATION_LEVELS_MONTH_COST = new int[]{700,700,700};
    public static final int[] POLICE_STATION_LEVELS_POWER = new int[]{-5, -10, -15};
    public static final int[] POLICE_STATION_LEVELS_FIRE = new int[]{-5,-7,-8};
    public static final int[] POLICE_STATION_LEVELS_WATER = new int[]{-10, -20, -40};
    public static final int[] POLICE_STATION_LEVELS_POLLUTION = new int[]{0,0,0};
    public static final int[] POLICE_STATION_LEVELS_CRIMINAL = new int[]{20,30,40};
    public static final int[] POLICE_STATION_LEVELS_HEALTH = new int[]{0,0,0};
    public static final int[] POLICE_STATION_LEVELS_WORK = new int[]{5,8,11};
    public static final int[] POLICE_STATION_LEVELS_HAPPINESS = new int[]{0, 0, 0};
    public static final int[] POLICE_STATION_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] POLICE_STATION_LEVELS_RADIUS = new float[]{25, 49, 100};

    //school
    public static final int[] SCHOOL_LEVELS_BUILD_COST = new int[]{1000, 1000, 1000};
    public static final int[] SCHOOL_LEVELS_MONTH_COST = new int[]{100, 100, 100};
    public static final int[] SCHOOL_LEVELS_POWER = new int[]{-4,-6,-8};
    public static final int[] SCHOOL_LEVELS_FIRE = new int[]{-5,-7,-8};
    public static final int[] SCHOOL_LEVELS_WATER = new int[]{-3,-4,-5};
    public static final int[] SCHOOL_LEVELS_POLLUTION = new int[]{0,0,0};
    public static final int[] SCHOOL_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] SCHOOL_LEVELS_HEALTH = new int[]{0,0,0};
    public static final int[] SCHOOL_LEVELS_WORK = new int[]{1,2,3};
    public static final int[] SCHOOL_LEVELS_HAPPINESS = new int[]{1,2,3};
    public static final int[] SCHOOL_LEVELS_EDUCATION = new int[]{10,15,20};
    public static final float[] SCHOOL_LEVELS_RADIUS = new float[]{25, 49, 100};

    //shopping mall
    public static final int[] SHOPPING_MALL_LEVELS_BUILD_COST = new int[]{4000,4000,4000};
    public static final int[] SHOPPING_MALL_LEVELS_MONTH_COST = new int[]{0,0,0};
    public static final int[] SHOPPING_MALL_LEVELS_POWER = new int[]{-10,-20,-30};
    public static final int[] SHOPPING_MALL_LEVELS_FIRE = new int[]{-5,-10,-12};
    public static final int[] SHOPPING_MALL_LEVELS_WATER = new int[]{-3,-6,-9};
    public static final int[] SHOPPING_MALL_LEVELS_POLLUTION = new int[]{1,2,3};
    public static final int[] SHOPPING_MALL_LEVELS_CRIMINAL = new int[]{2,4,6};//tyryat' 3,14 door ы
    public static final int[] SHOPPING_MALL_LEVELS_HEALTH = new int[]{0,0,0};
    public static final int[] SHOPPING_MALL_LEVELS_WORK = new int[]{5, 10, 15};
    public static final int[] SHOPPING_MALL_LEVELS_HAPPINESS = new int[]{10,20,35};
    public static final int[] SHOPPING_MALL_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] SHOPPING_MALL_LEVELS_RADIUS = new float[]{25, 49, 100};

    //water plant
    public static final int[] WATER_PLANT_LEVELS_BUILD_COST = new int[]{2000,2000,2000};
    public static final int[] WATER_PLANT_LEVELS_MONTH_COST = new int[]{100, 100, 100};
    public static final int[] WATER_PLANT_LEVELS_POWER = new int[]{-2,-4,-6};
    public static final int[] WATER_PLANT_LEVELS_FIRE = new int[]{2,4,6};
    public static final int[] WATER_PLANT_LEVELS_WATER = new int[]{30,50,70};
    public static final int[] WATER_PLANT_LEVELS_POLLUTION = new int[]{0,0,0};
    public static final int[] WATER_PLANT_LEVELS_CRIMINAL = new int[]{0, 0, 0};
    public static final int[] WATER_PLANT_LEVELS_HEALTH = new int[]{0,0,0};
    public static final int[] WATER_PLANT_LEVELS_WORK = new int[]{2,4,5};
    public static final int[] WATER_PLANT_LEVELS_HAPPINESS = new int[]{0, 0, 0};
    public static final int[] WATER_PLANT_LEVELS_EDUCATION = new int[]{0, 0, 0};
    public static final float[] WATER_PLANT_LEVELS_RADIUS = new float[]{49, 64, 91};

    public static final boolean ROUND_MAP_LIMIT = true;
    public static final int[] LEVELS_MONEY = new int[]{500000, 10000, 20000, 25000, 30000, 40000};
    //public static final long[] LEVELS_TIMES = new long[]{5*60*1000, 5*60*1000, 7*60*1000, 10*60*1000, 12*60*1000, 15*60*1000};
    public static final long[] LEVELS_TIMES = new long[]{15*1000, 40*1000, 15*1000, 15*1000, 15*1000, 15*1000};
    //public static final int[] LEVELS_POPULATION_GOAL = new int[]{0, 3000, 10000, 30000, 100000, 500000};
    public static final int[] LEVELS_POPULATION_GOAL = new int[]{30, 30, 30, 30, 30, 30};
    public static final boolean[] LEVELS_MAP_LIMIT_TYPE = new boolean[]{ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT, ROUND_MAP_LIMIT};
    public static final float[] LEVELS_AREA_SIZE = new float[]{200, 10, 10, 20, 30, 60};
}
