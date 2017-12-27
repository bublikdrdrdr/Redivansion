package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class AllFrames {

    public static Frame main, add, build, menu, info, levelMenu, mainMenu;

    public AllFrames() {
        initMain();
        initMenu();
        initAdd();
        initBuild();
        initMainMenu();
    }

    private void initMainMenu(){
        Element.z = 2;
        mainMenu = new Frame("mainMenu");
        mainMenu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/temp_menu.png", true);
        mainMenu.addElement("levelSelect", "Levels", true, null, 32, 58, 36, 10, false, "Textures/btnLong1.png", false);
        mainMenu.addElement("tutorial", "Tutorial", true, null, 32, 46, 36, 10, false, "Textures/btnLong1.png", false);
        mainMenu.addElement("exit", "Exit", true, null, 32, 34, 36, 10, false, "Textures/btnLong1.png", false);

    }

    private void initMain(){
        main = new Frame("main");
        Element.z = -2;
        //name, text (null if none), interactive, align (null if center), (in percents) x, y, wight, height,
        //square (if true then width = height), picture path, transparent
        main.addElement("bg", null, false, null, 0, 85, 40, 15, false, "Textures/btnLong1.png", false);
        main.addElement("bg", null, false, null, 2, 87, 11, 11, true, "Textures/time.png", true);
        main.addElement("time", "Time: ", false, Element.TextPosition.Left_VCenter, 11, 87, 26, 11, false, "Textures/btn2.png", false);
        main.addElement("population", "Population: ", false, Element.TextPosition.Left_VCenter, 74, 89, 26, 11, false, "Textures/btnLong1.png", false);
        main.addElement("money", "Money: ", false, Element.TextPosition.Left_VCenter, 74, 78, 26, 11, false, "Textures/btnLong1.png", false);
        main.addElement("addSmthing", "+", true, null, 0, 65, 20, 20, true, "Textures/btn1.png", false);
        main.addElement("remove", "Remove", true, null, 0, 45, 20, 20, true, "Textures/btn1.png", false);
        main.addElement("menu", "Menu", true, null, 0, 0, 20, 20, true, "Textures/btn1.png", false);
    }

    private void initBuild(){
        Element.z = -0.5f;
        build = new Frame("build");
        build.addElement("build", "+", true, null, 0, 65, 20, 20, true, "Textures/btn1.png", false);
        build.addElement("cancel", "X", true, null, 0, 45, 20, 20, true, "Textures/btn1.png", false);
    }

    private void initMenu(){
        Element.z = 0;
        menu = new Frame("menu");
        menu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png", true);
        menu.addElement("bg", null, false, null, 30, 23, 40, 41, false, "Textures/menubg.png", false);
        menu.addElement("bg", "Menu", false, Element.TextPosition.Left_VCenter, 30, 61, 33, 10, false, "Textures/bg.jpg", false);
        menu.addElement("close", "X", true, null, 63, 61, 7, 10, false, "Textures/btn1.png", false);
        menu.addElement("save", "Save", true, null, 32, 49, 36, 10, false, "Textures/btnLong1.png", false);
        menu.addElement("removeSave", "Remove last save", true, null, 32, 37, 36, 10, false, "Textures/btnLong1.png", false);
        menu.addElement("returnToMainMenu", "Main Menu", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png", false);
    }

    private void initAdd(){
        add = new Frame("add");
        Element.z = 0;
        add.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png", true);
        add.addElement("bg", null, false, null, 30, 5, 40, 90, false, "Textures/menubg.png", false);
        add.addElement("bg", "Buildings", false, Element.TextPosition.Left_VCenter, 30, 85, 33, 10, false, "Textures/bg.jpg", false);
        add.addElement("close", "X", true, null, 63, 85, 7, 10, false, "Textures/btn1.png", false);
        add.addElement("addTree", "Add Tree", true, null, 32, 73, 36, 10, false, "Textures/btnLong1.png", false);
        add.addElement("addOffice", "Add Office", true, null, 32, 61, 36, 10, false, "Textures/btnLong1.png", false);
        add.addElement("addHouse", "Add House", true, null, 32, 49, 36, 10, false, "Textures/btnLong1.png", false);
        add.addElement("addPower", "Add Powerplant", true, null, 32, 37, 36, 10, false, "Textures/btnLong1.png", false);
        add.addElement("setRoadPoints", "Add Road Points", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png", false);
        add.addElement("addPrev", "<--", false, null, 32, 7, 16, 10, false, "Textures/btn2.png", false);
        add.addElement("addNext", "-->", false, null, 52, 7, 16, 10, false, "Textures/btn2.png", false);
    }

    public static void initLevelMenu(){
        Settings settings = Settings.getInstance();
        settings.open();
        int progress = settings.getProgress();

        Element.z = 3;
        levelMenu = new Frame("levelMenu");
        levelMenu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png", true);
        levelMenu.addElement("bg", null, false, null, 20, 0, 60, 100, false, "Textures/menubg.png", false);

        for(int i = 0; i < GameParams.LEVELS_MONEY.length - 1; i++){
            int y = i*44;
            if(i <= progress)
                levelMenu.addElement(Integer.toString(i), "Level " + (i + 1), true, Element.TextPosition.Left_VTop, 22, 89 - y, 56, 10, false, "Textures/btnLong1.png", false);
            else levelMenu.addElement(Integer.toString(i), "Level " + (i + 1), false, Element.TextPosition.Left_VTop, 22, 89 - y, 56, 10, false, "Textures/btnLong2.png", false);
            levelMenu.addElement("background", null, false, null, 22, 57 - y, 56, 32, false, "Textures/bg.jpg", false);
            levelMenu.addElement("time", "Time: " + GameParams.LEVELS_TIMES[i], false, Element.TextPosition.Left_VTop, 23, 78 - y, 26, 10, false, "Textures/2.png", true);
            levelMenu.addElement("money", "Money: " + GameParams.LEVELS_MONEY[i], false, Element.TextPosition.Left_VTop, 51, 78 - y, 26, 10, false, "Textures/2.png", true);
            levelMenu.addElement("info", "Short description, level goals, maybe something else.", false, Element.TextPosition.Center_VTop, 23, 59 - y, 54, 18, false, "Textures/2.png", true);
        }

    }

    public static void initInfo(WorldObject object){
        info = new Frame("info");
        info.addElement("bg", null, false, null, 0, 0, 60, 100, false, "Textures/pickedbg.png", true);
        info.addElement("bg", null, false, null, 60, 0, 40, 100, false, "Textures/menubg.png", false);
        info.addElement("close", "X", true, null, 93, 90, 7, 10, false, "Textures/btn1.png", false);

        info.addElement("bg", object.getGeometryManager().getName(), false, Element.TextPosition.Left_VCenter, 60, 90, 33, 10, false, "Textures/bg.jpg", false);

        float x = 60.8f;
        String state = "stable";
        state = (object.getResourceValue(WorldObject.ResourceType.POWER)>0)?"stable":"warning";
        info.addElement("power", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/power.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.FIRE)>0)?"stable":"warning";
        info.addElement("fire", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/fire.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.WATER)>0)?"stable":"warning";
        info.addElement("water", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/water.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.POLLUTION)>0)?"stable":"warning";
        info.addElement("pollution", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/pollution.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.CRIMINAL)>0)?"stable":"warning";
        info.addElement("criminal", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/criminal.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.HEALTH)>0)?"stable":"warning";
        info.addElement("health", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/health.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.WORK)>0)?"stable":"warning";
        info.addElement("work", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/work.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.HAPPINESS)>0)?"stable":"warning";
        info.addElement("happiness", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/happiness.png", true); x += 4.3f;
        state = (object.getResourceValue(WorldObject.ResourceType.EDUCATION)>0)?"stable":"warning";
        info.addElement("education", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/education.png", true);
        int y;
        if(object instanceof House){
            y = 42;
            House h = (House)object;
            info.addElement("population", null, false, null, 62, 70, 10, 10, true, "Textures/pop.png", true);
            info.addElement("population", "Population: " + h.getPopulation(), false, Element.TextPosition.Left_VCenter, 70, 70, 28, 10, false, "Textures/2.png", true);
        }
        else{ y = 54;}
        info.addElement("info", "Short description, maybe something else.", false, null, 62, y, 36, 26, false, "Textures/2.png", true);
    }
}
