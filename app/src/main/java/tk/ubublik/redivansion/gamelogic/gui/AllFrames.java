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

    public static Frame main, add, build, menu, info, levelMenu, mainMenu, levelComplete;

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
        //name, text (null if none), interactive, align (null if center), (in percents) x, y, wight, height,
        //square (if true then width = height), picture path
        mainMenu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/temp_menu.png");
        mainMenu.addElement("levelSelect", "Levels", true, null, 32, 58, 36, 10, false, "Textures/btnLong1.png");
        mainMenu.addElement("tutorial", "Tutorial", true, null, 32, 46, 36, 10, false, "Textures/btnLong1.png");
        mainMenu.addElement("freeplay", "Free Play", true, null, 32, 34, 36, 10, false, "Textures/btnLong1.png");
        mainMenu.addElement("exit", "Exit", true, null, 32, 22, 36, 10, false, "Textures/btnLong1.png");

    }

    private void initMain(){
        main = new Frame("main");
        Element.z = -2;
        main.addElement("bg", null, false, null, 0, 85, 40, 15, false, "Textures/btnLong1.png");
        main.addElement("bg", null, false, null, 2, 87, 11, 11, true, "Textures/time.png");
        main.addElement("time", "Time: ", false, Element.TextPosition.Left_VCenter, 11, 87, 26, 11, false, "Textures/btn2.png");
        main.addElement("population", "Population: ", false, Element.TextPosition.Left_VCenter, 74, 89, 26, 11, false, "Textures/btnLong1.png");
        main.addElement("money", "Money: ", false, Element.TextPosition.Left_VCenter, 74, 78, 26, 11, false, "Textures/btnLong1.png");
        main.addElement("addSmthing", "+", true, null, 0, 65, 20, 20, true, "Textures/btn1.png");
        main.addElement("remove", "Remove", true, null, 0, 45, 20, 20, true, "Textures/btn1.png");
        main.addElement("menu", "Menu", true, null, 0, 0, 20, 20, true, "Textures/btn1.png");
    }

    private void initBuild(){
        Element.z = -0.5f;
        build = new Frame("build");
        build.addElement("build", "+", true, null, 0, 65, 20, 20, true, "Textures/btn1.png");
        build.addElement("cancel", "X", true, null, 0, 45, 20, 20, true, "Textures/btn1.png");
    }

    private void initMenu(){
        Element.z = 0;
        menu = new Frame("menu");
        menu.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        menu.addElement("bg", null, false, null, 30, 23, 40, 41, false, "Textures/menubg.png");
        menu.addElement("bg", "Menu", false, Element.TextPosition.Left_VCenter, 30, 61, 33, 10, false, "Textures/bg.jpg");
        menu.addElement("close", "X", true, null, 63, 61, 7, 10, false, "Textures/btn1.png");
        menu.addElement("save", "Save", true, null, 32, 49, 36, 10, false, "Textures/btnLong1.png");
        menu.addElement("removeSave", "Remove last save", true, null, 32, 37, 36, 10, false, "Textures/btnLong1.png");
        menu.addElement("returnToMainMenu", "Main Menu", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png");
    }

    private void initAdd(){
        add = new Frame("add");
        Element.z = 0;
        add.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        add.addElement("bg", null, false, null, 30, 5, 40, 90, false, "Textures/menubg.png");
        add.addElement("bg", "Buildings", false, Element.TextPosition.Left_VCenter, 30, 85, 33, 10, false, "Textures/bg.jpg");
        add.addElement("close", "X", true, null, 63, 85, 7, 10, false, "Textures/btn1.png");
        add.addElement("addTree", "Add Tree", true, null, 32, 73, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addOffice", "Add Office", true, null, 32, 61, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addHouse", "Add House", true, null, 32, 49, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addPower", "Add Powerplant", true, null, 32, 37, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("setRoadPoints", "Add Road Points", true, null, 32, 25, 36, 10, false, "Textures/btnLong1.png");
        add.addElement("addPrev", "<--", false, null, 32, 7, 16, 10, false, "Textures/btn2.png");
        add.addElement("addNext", "-->", false, null, 52, 7, 16, 10, false, "Textures/btn2.png");
    }

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
            levelMenu.addElement("time", "Time: " + GameParams.LEVELS_TIMES[i]/1000, false, Element.TextPosition.Left_VTop, 23, 78 - y, 26, 10, false, "Textures/2.png");
            levelMenu.addElement("money", "Money: " + GameParams.LEVELS_MONEY[i], false, Element.TextPosition.Left_VTop, 51, 78 - y, 26, 10, false, "Textures/2.png");
            levelMenu.addElement("info", "Short description, level goals, maybe something else.", false, Element.TextPosition.Center_VTop, 23, 59 - y, 54, 18, false, "Textures/2.png");
        }

    }

    public static void initInfo(WorldObject object){
        Element.z = 10;
        info = new Frame("info");
        info.addElement("bg", null, false, null, 0, 0, 60, 100, false, "Textures/pickedbg.png");
        info.addElement("bg", null, false, null, 60, 0, 40, 100, false, "Textures/menubg.png");
        info.addElement("close", "X", true, null, 93, 90, 7, 10, false, "Textures/btn1.png");

        info.addElement("bg", object.getGeometryManager().getName(), false, Element.TextPosition.Left_VCenter, 60, 90, 33, 10, false, "Textures/bg.jpg");

        float x = 60.8f;
        String state = (object.getResourceValue(WorldObject.ResourceType.POWER)>0)?"stable":"warning";
        info.addElement("power", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/power.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.FIRE)>0)?"stable":"warning";
        info.addElement("fire", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/fire.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.WATER)>0)?"stable":"warning";
        info.addElement("water", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/water.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.POLLUTION)>0)?"stable":"warning";
        info.addElement("pollution", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/pollution.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.CRIMINAL)>0)?"stable":"warning";
        info.addElement("criminal", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/criminal.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.HEALTH)>0)?"stable":"warning";
        info.addElement("health", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/health.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.WORK)>0)?"stable":"warning";
        info.addElement("work", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/work.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.HAPPINESS)>0)?"stable":"warning";
        info.addElement("happiness", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/happiness.png"); x += 4.3f;

        state = (object.getResourceValue(WorldObject.ResourceType.EDUCATION)>0)?"stable":"warning";
        info.addElement("education", null, false, null, x, 82, 7, 7, true, "Textures/" + state + "/education.png");

        int y;
        if(object instanceof House){
            y = 58;
            House h = (House)object;
            info.addElement("population", null, false, null, 62, 70, 10, 10, true, "Textures/pop.png");
            info.addElement("population", "Population: " + h.getPopulation(), false, Element.TextPosition.Left_VCenter, 70, 70, 28, 10, false, "Textures/2.png");
        }
        else y = 70;

        //TODO: condition when upgrade is available

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
        info.addElement("info", "Short description, maybe something else.", false, null, 62, y-28, 36, 26, false, "Textures/2.png");
    }

    public static void initLevelComplete(boolean victory){
        Element.z = 100;
        levelComplete = new Frame("levelComplete");
        levelComplete.addElement("bg", null, false, null, 0, 0, 100, 100, false, "Textures/2.png");
        levelComplete.addElement("bg", null, false, null, 30, 23, 40, 41, false, "Textures/menubg.png");
        String win = victory?"Victory!":"Failure!";
        levelComplete.addElement("status", win, false, Element.TextPosition.Left_VCenter, 30, 61, 33, 10, false, "Textures/bg.jpg");
        levelComplete.addElement("close", "X", true, null, 63, 61, 7, 10, false, "Textures/btn1.png");
        win = victory?"You complete the goal of this level! Now you can choose next one from main menu.":"You failed! Try again!";
        levelComplete.addElement("bg", win, false, null, 32, 25, 36, 34, false, "Textures/2.png");
    }
}
