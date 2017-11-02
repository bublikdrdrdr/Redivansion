package tk.ubublik.redivansion.gamelogic.utils;

/**
 * Created by Bublik on 28-Oct-17.
 * Radius here is RADIUS^2 (faster calculations)
 */

public class GameParams {

    @Deprecated
    public static final int ROAD_BUILD_COST = 1000;

    //office
    public static final int[] OFFICE_LEVELS_BUILD_COST = new int[]{1000,1000,1000,1000,1000}; //build, upgrade, upgrade...
    public static final int[] OFFICE_LEVELS_MONTH_COST = new int[]{100,100,100,100,100}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_POWER = new int[]{-5,-10,-15,-20,-25}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_FIRE = new int[]{-5,-10,-15,-20,-25}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_WATER = new int[]{-5,-10,-15,-20,-25}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_POLLUTION = new int[]{-2, -4, -6, -8, -10}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_CRIMINAL = new int[]{0,0,0,0,0}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_HEALTH = new int[]{0,0,0,0,0}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_MAX_WORK = new int[]{20,30,40,50,60}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_HAPPINESS = new int[]{0,0,0,0,0}; //1,2,3,4,5...
    public static final int[] OFFICE_LEVELS_EDUCATION = new int[]{0,0,0,0,0}; //1,2,3,4,5...

    public static final float OFFICE_POWER_PRODUCTIVITY = 1.2f;//more energy office has, more work places it gives
    public static final float OFFICE_RADIUS = 36f;

    //house
    public static final int[] HOUSE_LEVELS_BUILD_COST = new int[]{1000,1000,1000,1000,1000}; //build, upgrade, upgrade...
    public static final int[] HOUSE_LEVELS_MONTH_COST = new int[]{100,100,100,100,100}; // this + per person
    public static final int[] HOUSE_LEVELS_MAX_POPULATION = new int[]{50,200,500,1500,3000};
    public static final int[] HOUSE_LEVELS_POWER = new int[]{-5,-10,-15,-20,-25}; // this + per person
    public static final int[] HOUSE_LEVELS_FIRE = new int[]{-5,-10,-15,-20,-25}; // this + per person
    public static final int[] HOUSE_LEVELS_WATER = new int[]{-5,-10,-15,-20,-25}; // this + per person
    public static final int[] HOUSE_LEVELS_POLLUTION = new int[]{-5,-10,-15,-20,-25}; // this + per person
    public static final int[] HOUSE_LEVELS_CRIMINAL = new int[]{0,0,0,0,0};
    public static final int[] HOUSE_LEVELS_HEALTH = new int[]{0,0,0,0,0};
    public static final int[] HOUSE_LEVELS_WORK = new int[]{0,0,0,0,0};
    public static final int[] HOUSE_LEVELS_HAPPINESS = new int[]{0,-5,-10,-15,-20}; // this + per person
    public static final int[] HOUSE_LEVELS_EDUCATION = new int[]{0,0,0,0,0}; // this + per person
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
    public static final int[] THERMAL_POWER_PLANT_LEVELS_BUILD_COST = new int[]{1000,1000,1000}; //build, upgrade, upgrade...
    public static final int[] THERMAL_POWER_PLANT_LEVELS_MONTH_COST = new int[]{100,100,100};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_POWER = new int[]{-5,-10,-15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_FIRE = new int[]{-5,-10,-15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_WATER = new int[]{-1,-2,-3};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_POLLUTION = new int[]{-10,-20,-30};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_CRIMINAL = new int[]{0,0,0};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_HEALTH = new int[]{-5,-10,-15}; //vot tut hz, daje zabrudnennya, znachyt zabyraje zdorowja
    public static final int[] THERMAL_POWER_PLANT_LEVELS_WORK = new int[]{5,10,15};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_HAPPINESS = new int[]{0,0,0};
    public static final int[] THERMAL_POWER_PLANT_LEVELS_EDUCATION = new int[]{0,0,0};
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


    // TODO: 02-Nov-17 for all objects

    @Deprecated //(all)
    public static final int OFFICE_DEFAULT_MONTH_COST = 100;
    public static final int OFFICE_DEFAULT_POWER = 100;
    public static final int OFFICE_DEFAULT_FIRE = 100;
    public static final int OFFICE_DEFAULT_WATER = 100;
    public static final int OFFICE_DEFAULT_POLLUTION = 100;
    public static final int OFFICE_DEFAULT_MONEY = 100;
    public static final int OFFICE_DEFAULT_CRIMINAL = 100;
    public static final int OFFICE_DEFAULT_HEALTH = 100;
}
