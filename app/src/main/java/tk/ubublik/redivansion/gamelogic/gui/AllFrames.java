package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.units.objects.FireStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Hospital;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.PoliceStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.School;
import tk.ubublik.redivansion.gamelogic.units.objects.ShoppingMall;
import tk.ubublik.redivansion.gamelogic.units.objects.ThermalPowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WaterPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class AllFrames {

    public static Frame main, add, build, info, levelMenu, mainMenu, levelComplete;
    public static boolean levelEndShowed = false;
    private static String[] levelDesc = {"Build two power plants and get " + GameParams.LEVELS_POPULATION_GOAL[0] +" people in city.",
                        "This city needs fire and police stations. Build them and reach  " + GameParams.LEVELS_POPULATION_GOAL[1] +" population.",
                        "This one needs fire and police stations, one hospital and " + GameParams.LEVELS_POPULATION_GOAL[2] +"  population.",
                        "Shopping centre. Build 5 shopping malls and get  " + GameParams.LEVELS_POPULATION_GOAL[3] +"  people.",
                        "Just build here three schools. Also reach "  + GameParams.LEVELS_POPULATION_GOAL[4] +"  people in city.",};

    public AllFrames() {
        initMain();
        initAdd();
        initBuild();
        initMainMenu();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void initMainMenu(){
        Element.z = 2;
        mainMenu = new Frame("mainMenu");
        //name, text (null if none), interactive, align (null if center), (in percents) x, y, wight, height,
        //square (if true then width = height), picture path
        mainMenu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/temp_menu.png");
        mainMenu.addElement("levelSelect", "Levels", true, null, 32, 58, 36, 10, false, "Textures/btnLong1.png");
        mainMenu.addElement("tutorial", "Tutorial", true, null, 32, 46, 36, 10, false, "Textures/btnLong1.png");
        mainMenu.addElement("freeplay", "Freeplay", true, null, 32, 34, 36, 10, false, "Textures/btnLong1.png");
        mainMenu.addElement("exit", "Exit", true, null, 32, 22, 36, 10, false, "Textures/btnLong1.png");

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Confirmation Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Frame confirmMenu(String confirmType){
        Element.z = 200;

        Frame confirmMenu = new Frame("confirmMenu");
        confirmMenu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        confirmMenu.addElement("bg", null, false, null, 30, 23, 40, 41, false, "Textures/menubg.png");

        if(confirmType.equals("firstLaunch")){
            TouchEvents.confirmType = confirmType;
            confirmMenu.addElement("bg", "Seems like you are new here. Do you want to play tutorial first?", false, null, 31, 35, 38, 28, false, "Textures/2.png");
        } else if(confirmType.equals("exit")){
            TouchEvents.confirmType = confirmType;
            confirmMenu.addElement("bg", "Exit?", false, null, 31, 35, 38, 28, false, "Textures/2.png");
        }
        confirmMenu.addElement("confirm", "Yes", true, null, 31, 24, 18, 10, false, "Textures/btn1.png");
        confirmMenu.addElement("decline", "No", true, null, 50, 24, 18, 10, false, "Textures/btn1.png");
        return confirmMenu;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main Gui Frame
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void initMain(){
        //name, text (null if none), interactive, align (null if center), (in percents) x, y, wight, height,
        //square (if true then width = height), picture path
        main = new Frame("main");
        Element.z = -2;
        main.addElement("bg", null, false, null, 0, 85, 46, 15, false, "Textures/btnLong1.png");
        main.addElement("bg", null, true, null, 2, 87, 11, 11, true, "Textures/time.png");
        main.addElement("time", "Time: ", false, Element.TextPosition.Left_VCenter, 11, 87, 26, 11, false, "Textures/btn2.png");
        main.addElement("timeSpeedBg", null, true, null, 38, 87, 11, 11, true, "Textures/btn1.png");
        main.addElement("timeSpeed", null, true, null, 38, 87, 11, 11, true, "Textures/timeSpeed.png");
        main.addElement("population", "Population: ", false, Element.TextPosition.Left_VCenter, 74, 89, 26, 11, false, "Textures/btnLong1.png");
        main.addElement("money", "Money: ", false, Element.TextPosition.Left_VCenter, 74, 78, 26, 11, false, "Textures/btnLong1.png");
        main.addElement("plus", null, true, null, 0, 65, 20, 20, true, "Textures/btn1.png");
        main.addElement("addSmthing", null, true, null, 0, 65, 20, 20, true, "Textures/addX.png");
        main.addElement("x", null, true, null, 0, 45, 20, 20, true, "Textures/btn1.png");
        main.addElement("remove", null, true, null, 0, 45, 20, 20, true, "Textures/closeX.png");
        main.addElement("menu", "Menu", true, null, 0, 0, 20, 20, true, "Textures/btn1.png");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Building Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void initBuild(){
        Element.z = 30;
        build = new Frame("build");
        build.addElement("plus", null, true, null, 0, 65, 20, 20, true, "Textures/btn1.png");
        build.addElement("build", null, true, null, 0, 65, 20, 20, true, "Textures/addX.png");
        build.addElement("x", null, true, null, 0, 45, 20, 20, true, "Textures/btn1.png");
        build.addElement("cancel", null, true, null, 0, 45, 20, 20, true, "Textures/closeX.png");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Ingame Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static Frame gameMenu(){
        Element.z = 30;
        Frame menu = new Frame("menu");
        menu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        menu.addElement("bg", null, false, null, 30, 23, 40, 41, false, "Textures/menubg.png");
        menu.addElement("bg", "Menu", false, Element.TextPosition.Left_VCenter, 30, 61, 33, 10, false, "Textures/bg.jpg");
        menu.addElement("x", null, true, null, 63, 61, 7, 10, false, "Textures/btn1.png");
        menu.addElement("close", null, true, null, 63, 61, 7, 10, false, "Textures/closeX.png");
        boolean interactive; String path;
        if(TouchEvents.tutorial){
            interactive = false; path = "Textures/btnLong2.png";
        } else{
            interactive = true; path = "Textures/btnLong1.png";
        }
        menu.addElement("save", "Save", interactive, null, 32, 49, 36, 10, false, path);
        menu.addElement("removeSave", "Remove last save", interactive, null, 32, 37, 36, 10, false, path);
        menu.addElement("returnToMainMenu", "Main Menu", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png");
        return menu;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Building choose menus
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void initAdd(){
        add = new Frame("add");
        Element.z = 30;
        add.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        add.addElement("bg", null, false, null, 30, 5, 40, 90, false, "Textures/menubg.png");
        add.addElement("bg", "Buildings", false, Element.TextPosition.Left_VCenter, 30, 85, 33, 10, false, "Textures/bg.jpg");
        add.addElement("addTree", "Add Tree", true, null, 32, 73, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addOffice", "Add Office", true, null, 32, 61, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addHouse", "Add House", true, null, 32, 49, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addPower", "Add Powerplant", true, null, 32, 37, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("setRoadPoints", "Add Road", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("x", null, true, null, 63, 85, 7, 10, false, "Textures/btn1.png");
        add.addElement("close", null, true, null, 63, 85, 7, 10, false, "Textures/closeX.png");
        add.addElement("bg", null, false, null, 32, 7, 16, 10, false, "Textures/btn2.png");
        add.addElement("addNext", null, true, null, 52, 7, 16, 10, false, "Textures/btn1.png");
        add.addElement("bg", null, false, null, 32, 7, 16, 10, false, "Textures/arrowPrev.png");
        add.addElement("addNext1", null, true, null, 52, 7, 16, 10, false, "Textures/arrowNext.png");
    }

    public static Frame initAdd2(){
        Settings settings = Settings.getInstance();
        settings.open();
        Frame add = new Frame("add");
        Element.z = 30;
        add.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        add.addElement("bg", null, false, null, 30, 15, 40, 70, false, "Textures/menubg.png");
        add.addElement("bg", "Buildings", false, Element.TextPosition.Left_VCenter, 30, 75, 33, 10, false, "Textures/bg.jpg");
        add.addElement("addPolice", "Add Police Station", true, null, 32, 60, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addFire", "Add Fire Station", true, null, 32, 48, 36, 10, false,  "Textures/btnLong1.png");
        add.addElement("addWater", "Add Water Plant", true, null, 32, 36, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("x", null, true, null, 63, 75, 7, 10, false, "Textures/btn1.png");
        add.addElement("close", null, true, null, 63, 75, 7, 10, false, "Textures/closeX.png");
        add.addElement("addPrev", null, true, null, 32, 17, 16, 10, false, "Textures/btn1.png");
        add.addElement("addNext", null, true, null, 52, 17, 16, 10, false, "Textures/btn1.png");
        add.addElement("addPrev1", null, true, null, 32, 17, 16, 10, false, "Textures/arrowPrev.png");
        add.addElement("addNext2", null, true, null, 52, 17, 16, 10, false, "Textures/arrowNext.png");
        return add;
    }

    public static Frame initAdd3(){
        Settings settings = Settings.getInstance();
        settings.open();
        int progress = settings.getProgress();
        Frame add = new Frame("add");
        Element.z = 30;
        add.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        add.addElement("bg", null, false, null, 30, 15, 40, 70, false, "Textures/menubg.png");
        add.addElement("bg", "Buildings", false, Element.TextPosition.Left_VCenter, 30, 75, 33, 10, false, "Textures/bg.jpg");
        add.addElement("addHospital", "Add Hospital", true, null, 32, 60, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addShop", "Add Shop", (progress > 2)?true:false, null, 32, 48, 36, 10, false,  (progress > 2)?"Textures/btnLong1.png":"Textures/btnLong2.png");
        add.addElement("addSchool", "Add School", (progress > 3)?true:false, null, 32, 36, 36, 10, false,  (progress > 3)?"Textures/btnLong1.png":"Textures/btnLong2.png");
        add.addElement("x", null, true, null, 63, 75, 7, 10, false, "Textures/btn1.png");
        add.addElement("close", null, true, null, 63, 75, 7, 10, false, "Textures/closeX.png");
        add.addElement("addPrev", null, true, null, 32, 17, 16, 10, false, "Textures/btn1.png");
        add.addElement("bg", null, false, null, 52, 17, 16, 10, false, "Textures/btn2.png");
        add.addElement("addPrev2", null, true, null, 32, 17, 16, 10, false, "Textures/arrowPrev.png");
        add.addElement("bg", null, false, null, 52, 17, 16, 10, false, "Textures/arrowNext.png");
        return add;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Level List
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static void initLevelMenu(){
        Settings settings = Settings.getInstance();
        settings.open();
        int progress = settings.getProgress();

        Element.z = 3;
        levelMenu = new Frame("levelMenu");
        levelMenu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        levelMenu.addElement("bg", null, false, null, 20, 0, 60, 100, false, "Textures/menubg.png");

        for(int i = 0; i < GameParams.LEVELS_MONEY.length - 1; i++){
            int y = i*44;
            if(i <= progress)
                levelMenu.addElement(Integer.toString(i), "Level " + (i + 1), true, Element.TextPosition.Left_VTop, 22, 89 - y, 56, 10, false, "Textures/btnLong1.png");
            else levelMenu.addElement(Integer.toString(i), "Level " + (i + 1), false, Element.TextPosition.Left_VTop, 22, 89 - y, 56, 10, false, "Textures/btnLong2.png");
            levelMenu.addElement("background", null, false, null, 22, 57 - y, 56, 32, false, "Textures/bg.jpg");

            int min = (int) GameParams.LEVELS_TIMES[i]/1000/60;
            int sec1 = (int)GameParams.LEVELS_TIMES[i]/1000 % 60 / 10;
            levelMenu.addElement("time", "Time: " + min + " m " + sec1 + " s" , false, Element.TextPosition.Left_VTop, 23, 78 - y, 26, 10, false, "Textures/2.png");
            levelMenu.addElement("money", "Money: " + GameParams.LEVELS_MONEY[i], false, Element.TextPosition.Left_VTop, 51, 78 - y, 26, 10, false, "Textures/2.png");
            levelMenu.addElement("info", levelDesc[i], false, Element.TextPosition.Center_VTop, 23, 59 - y, 54, 18, false, "Textures/2.png");
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object Info
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static Frame initInfo(WorldObject object){
        Element.z = 50;
        info = new Frame("info");
        info.addElement("bg", null, false, null, 0, 0, 60, 100, false, "Textures/pickedbg.png");
        info.addElement("bg", null, false, null, 60, 0, 40, 100, false, "Textures/menubg.png");
        info.addElement("x", null, true, null, 93, 90, 7, 10, false, "Textures/btn1.png");
        info.addElement("close", null, true, null, 93, 90, 7, 10, false, "Textures/closeX.png");

        info.addElement("bg", object.getGeometryManager().getName(), false, Element.TextPosition.Left_VCenter, 60, 90, 33, 10, false, "Textures/bg.jpg");

        float x = 60.8f;
        String state = (object.getResourceValue(WorldObject.ResourceType.POWER)>= GameParams.getResourceValue(object, WorldObject.ResourceType.POWER)*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("power", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/power.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.FIRE)>= GameParams.getResourceValue(object, WorldObject.ResourceType.FIRE)*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("fire", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/fire.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.WATER)>= GameParams.getResourceValue(object, WorldObject.ResourceType.WATER)*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("water", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/water.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.POLLUTION)>= GameParams.getResourceValue(object, WorldObject.ResourceType.POLLUTION)*GameParams.POPULATION_GROW_MINOR_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("pollution", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/pollution.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.CRIMINAL)>= GameParams.getResourceValue(object, WorldObject.ResourceType.CRIMINAL)*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("criminal", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/criminal.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.HEALTH)>= GameParams.getResourceValue(object, WorldObject.ResourceType.HEALTH)*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("health", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/health.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.WORK)>= GameParams.getResourceValue(object, WorldObject.ResourceType.WORK)*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("work", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/work.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.HAPPINESS)>= GameParams.getResourceValue(object, WorldObject.ResourceType.HAPPINESS)*GameParams.POPULATION_GROW_MINOR_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("happiness", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/happiness.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.EDUCATION)>= GameParams.getResourceValue(object, WorldObject.ResourceType.EDUCATION)*GameParams.POPULATION_GROW_MINOR_RESOURCES_PERCENT)?"stable":"warning";
        info.addElement("education", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/education.png");

        int y;
        if(object instanceof House){
            y = 58;
            House h = (House)object;
            info.addElement("population", null, false, null, 62, 70, 10, 10, true, "Textures/pop.png");
            info.addElement("population", "Population: " + h.getPopulation(), false, Element.TextPosition.Left_VCenter, 70, 70, 28, 10, false, "Textures/2.png");
        }
        else y = 70;

        boolean interactive;
        String button;
        if((object.getLevelNumber()<object.getLevelsCount()-1)) {
            interactive = true;
            button = "btnLong1.png";
        } else{
            interactive = false;
            button = "btnLong2.png";
        }
        info.addElement("upgrade", "Upgrade", interactive, null, 62, y, 36, 10, false, "Textures/" + button);
        info.addElement("info", getObjectDesc(object), false, Element.TextPosition.Center_VCenter, 62, y-25, 36, 23, false, "Textures/2.png");

        return info;
    }

    private static String getObjectDesc(WorldObject object){
        if(object instanceof FireStation)
            return "Reduces the risk of starting fire in a building.";
        else if(object instanceof Hospital)
            return "Here people come to get medical help and increase their health.";
        else if(object instanceof House)
            return "Place where people live.";
        else if(object instanceof Office)
            return "Place where people work.";
        else if(object instanceof PoliceStation)
            return "Loud neighbours? Shoplifters? Who you gonna call? Policemen!";
        else if(object instanceof Road)
            return "A single piece of road. Nothing interesting";
        else if(object instanceof School)
            return "Place where people get education.";
        else if(object instanceof ShoppingMall)
            return "Here people buy new stuff and just have some fun in different centers.";
        else if(object instanceof ThermalPowerPlant)
            return "Produces power.";
        else if(object instanceof WaterPlant)
            return "Filters and delivers clean water.";
        else if(object instanceof Tree)
            return "Cute tree :)";
        return "";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Level Complete Frame
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static Frame initLevelComplete(boolean victory){
        Element.z = 150;
        levelEndShowed = true;
        levelComplete = new Frame("levelComplete");
        levelComplete.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        levelComplete.addElement("bg", null, false, null, 30, 28, 40, 31, false, "Textures/menubg.png");
        String win = victory?"Victory!":"Failure!";
        levelComplete.addElement("status", win, false, Element.TextPosition.Left_VCenter, 30, 59, 33, 10, false, "Textures/bg.jpg");
        levelComplete.addElement("x", null, true, null, 63, 59, 7, 10, false, "Textures/btn1.png");
        levelComplete.addElement("close", null, true, null, 63, 59, 7, 10, false, "Textures/closeX.png");
        win = victory?"You complete the goal of this level! Now you can choose next one from main menu.":"You didn't complete the goals of this level! Try again and you will win. Good luck!";
        levelComplete.addElement("bg", win, false, null, 31, 29, 38, 29, false, "Textures/2.png");
        return levelComplete;
    }
}
