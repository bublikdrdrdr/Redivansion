package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.gamelogic.lifecycle.TutorialLifecycle;
import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.MenuListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class TouchEvents {

    public static String confirmType = "NONE";
    public static String object = null;
    public static GUIListener guiListener;
    public static MenuListener menuListener;
    public static Screen screen;
    private static int roadKostyl = 0;
    private static boolean roadBuildOver = true;
    public static boolean removeSelect = false;
    public static boolean tutorial = false;

    public static void backKeyPressed(Screen screen){
        if(screen.getActiveFrame().frameName.equals("main")){
            guiListener.pauseTime(true);
            screen.showFrame(AllFrames.gameMenu());
        }
        else if(screen.getActiveFrame().frameName.equals("mainMenu")){
            screen.removeFrame();
            menuListener.exit();
        }
        else closeFrame(screen);
    }

    public static void closeFrame(Screen scr){
            if(tutorial){
                for(String name:TutorialFrames.tutorialFrames){
                    if(scr.getActiveFrame().frameName.equals(name))
                        return;
                }
            }
            if(scr.getActiveFrame().frameName.equals("info")){
                screen.removeAllFrames();
                screen.showFrame(AllFrames.main);
                screen.gui.cameraControl.restoreCameraPosition();
                if(tutorial){
                    scr.showFrame(TutorialFrames.blank());
                    scr.showFrame(TutorialFrames.frame("roadInfo"));
                }
            }
            else if(scr.getActiveFrame().frameName.equals("menu")){
                guiListener.pauseTime(false);
                screen.removeFrame();
            }
            else if(scr.getActiveFrame().frameName.equals("mainMenu")){
                screen.removeFrame();
                menuListener.exit();
            }
            else if (scr.getActiveFrame().frameName.equals("levelComplete")){
                AllFrames.levelEndShowed = false;
                guiListener.setDone(true);
            }
            else if(scr.getActiveFrame().frameName.equals("blank"))
                screen.showFrame(AllFrames.gameMenu());
            else screen.removeFrame();
            return;
    }

    public static void tutorial(Screen scr){
        switch (scr.getActiveFrame().frameName){
            case "about":
                scr.removeFrame();
                scr.showFrame(TutorialFrames.frame("goals"));
                break;
            case "goals":
                scr.removeFrame();
                scr.showFrame(TutorialFrames.population());
                break;
            case "population":
                scr.removeFrame();
                scr.showFrame(TutorialFrames.time());
                break;
            case "time":
                scr.removeFrame();
                scr.showFrame(TutorialFrames.money());
                break;
            case "money":
                scr.removeFrame();
                scr.showFrame(TutorialFrames.frame("cameraMove"));
                break;
            case "cameraMove":
                guiListener.cameraTutorial(TutorialLifecycle.CameraTutorial.NONE);
                TutorialLifecycle.cameraTutorial = TutorialLifecycle.CameraTutorial.MOVE;
                scr.removeFrame();
                scr.showFrame(TutorialFrames.blank());
                break;
            case "cameraZoom":
                guiListener.cameraTutorial(TutorialLifecycle.CameraTutorial.NONE);
                TutorialLifecycle.cameraTutorial = TutorialLifecycle.CameraTutorial.ZOOM;
                scr.removeFrame();
                scr.showFrame(TutorialFrames.blank());
                break;
            case "buildMenu1":
                scr.removeFrame();
                scr.removeFrame(); //blank
                screen.showFrame(GUI.frames.add);
                screen.showFrame(TutorialFrames.buildChoose(false));
                break;
            case "buildChoose1":
                screen.removeFrame();
                addEvents("addHouse");
                screen.showFrame(TutorialFrames.frame("buildAddHouse"));
                break;
            case "buildAddHouse":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.buildAdd(false));
                break;
            case "buildAdd1":
                screen.removeFrame();
                build("build");
                screen.showFrame(TutorialFrames.frame("objectInfo"));
                break;
            case "objectInfo":
                screen.removeFrame();
                scr.showFrame(TutorialFrames.blank());
                break;
            case "objectInfo2":
                screen.removeFrame();
                break;
            case "roadInfo":
                scr.removeFrame();
                scr.removeFrame();
                scr.showFrame(TutorialFrames.blank());
                screen.showFrame(TutorialFrames.buildMenu(true));
                break;
            case "buildMenu2":
                scr.removeFrame();
                scr.removeFrame(); //blank
                screen.showFrame(GUI.frames.add);
                screen.showFrame(TutorialFrames.buildChoose(true));
                break;
            case "buildChoose2":
                screen.removeFrame();
                addEvents("setRoadPoints");
                screen.showFrame(TutorialFrames.frame("buildAddRoad1"));
                break;
            case "buildAddRoad1":
                screen.removeFrame();
                roadBuildOver = false;
                screen.showFrame(TutorialFrames.buildAdd(true));
                break;
            case "buildAddRoad2":
                screen.removeFrame();
                roadBuildOver = true;
                break;
            case "buildAdd2":
                if(!roadBuildOver){
                    build("build");
                    screen.showFrame(TutorialFrames.frame("buildAddRoad2"));
                }else{
                    screen.removeFrame();
                    build("build");
                    screen.showFrame(TutorialFrames.frame("finish"));
                }
                break;
            case "finish":
                TouchEvents.tutorial = false;
                TutorialLifecycle.cameraTutorial = TutorialLifecycle.CameraTutorial.NONE;
                guiListener.setDone(true);
                break;
            default:
                scr.removeFrame();
                break;
        }
        return;
    }

    public static void doSmthing(String event, MenuListener menu, Screen scr){
        menuListener = menu;
        screen = scr;
        if(event.equals("close"))
            closeFrame(scr);
        else switch (screen.getCurrentFrameName()) {
            case "mainMenu":
                mainMenu(event);
                break;
            case "levelMenu":
                levelMenu(event);
                break;
            case "confirmMenu":
                confirmMenu(event);
                break;
        }
    }

    public static void doSmthing(String event, GUIListener gui, Screen scr) {
        guiListener = gui;
        screen = scr;
        if(event.equals("next"))
            tutorial(scr);
        if(event.equals("close"))
            closeFrame(scr);
        else switch (screen.getCurrentFrameName()) {
            case "main":
                mainEvents(event);
                break;
            case "add":
                addEvents(event);
                break;
            case "build":
                build(event);
                break;
            case "menu":
                guiListener.pauseTime(true);
                menu(event);
                break;
            case "mainMenu":
                mainMenu(event);
                break;
            case "levelMenu":
                levelMenu(event);
                break;
            case "info":
                info(event);
                break;
            case "confirmMenu":
                confirmMenu(event);
            default:
                break;
        }
    }

    public static void info(String event){
        if(event.equals("upgrade")){
            guiListener.getGui().upgradeObject();
        }
    }

    public static void menu(String event){
        switch (event){
            case "save":
                guiListener.save();
                screen.removeFrame();
                guiListener.pauseTime(false);
                break;
            case "removeSave":
                guiListener.removeSave();
                screen.removeFrame();
                guiListener.pauseTime(false);
                break;
            case "returnToMainMenu":
                screen.removeFrame();
                screen.showFrame(AllFrames.confirmMenu("exit"));
                break;
        }
    }

    public static void mainEvents(String event){
        switch (event){
            case "addSmthing":
                screen.showFrame(GUI.frames.add);
                break;
            case "menu":
                screen.showFrame(GUI.frames.gameMenu());
                break;
            case "remove":
                if(!removeSelect){
                    guiListener.selectTree();
                    removeSelect = true;
                }
                else{
                    guiListener.remove();
                    guiListener.selectClear();
                    removeSelect = false;
                }
                break;
            case "timeSpeed":
                guiListener.changeTimeSpeed();
                break;
            default: break;
        }
    }

    public static void addEvents(String event){
        switch (event){
            case "addTree":
                object = "tree";
                guiListener.selectTree();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addOffice":
                object = "office";
                guiListener.selectOffice();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addHouse":
                object = "house";
                guiListener.selectOffice();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addPower":
                object = "power";
                guiListener.selectPower();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "setRoadPoints":
                object = "road";
                roadKostyl = 0;
                guiListener.setRoadPoints();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            default: break;
        }
    }

    public static void build(String event){
        switch (event){
            case "build":
                switch (object){
                    case "office":
                        guiListener.addBuilding();
                        guiListener.selectClear();
                        screen.removeFrame();
                        break;
                    case "tree":
                        guiListener.addTree();
                        guiListener.selectClear();
                        screen.removeFrame();
                        break;
                    case "house":
                        guiListener.addHouse();
                        guiListener.selectClear();
                        screen.removeFrame();
                        break;
                    case "power":
                        guiListener.addPower();
                        guiListener.selectClear();
                        screen.removeFrame();
                        break;
                    case "road":
                        if(roadKostyl < 2){
                            guiListener.setRoadPoints();
                            roadKostyl++;
                        }
                        if(roadKostyl == 2){
                            guiListener.addRoad();
                            guiListener.selectClear();
                            roadKostyl = 0;
                            screen.removeFrame();
                        }
                        break;
                    default: break;
                }
                break;
            case "cancel":
                guiListener.selectClear();
                screen.removeFrame();
                break;
        }
    }

    private static void mainMenu(String event){
        switch (event){
            case "levelSelect":
                AllFrames.initLevelMenu();
                screen.showFrame(AllFrames.levelMenu);
                break;
            case "tutorial":
                screen.removeFrame();
                menuListener.startTutorial();
                break;
            case "freeplay":
                screen.removeFrame();
                menuListener.startFreeplay();
                break;
            case "exit":
                screen.removeFrame();
                menuListener.exit();
                break;
            case "resetButton":
                screen.showFrame(AllFrames.confirmMenu("reset"));
                break;
        }
    }

    private static void confirmMenu(String event){
        switch (event){
            case "confirm":
                confirmed();
                break;
            case "decline":
                declined();
                break;
        }
    }

    private static void confirmed(){
        Settings settings;
        switch (confirmType){
            case "firstLaunch":
                settings = Settings.getInstance();
                settings.open();
                settings.setFirstLaunch(false);
                settings.save();

                screen.removeAllFrames();
                menuListener.startTutorial();
                break;
            case "exit":
                if(guiListener != null){
                    if(tutorial){
                        TouchEvents.tutorial = false;
                        TutorialLifecycle.cameraTutorial = TutorialLifecycle.CameraTutorial.NONE;
                    }
                    screen.removeFrame();
                    guiListener.setDone(true);
                }
                else{
                    screen.removeFrame();
                    menuListener.exit();
                }
                break;
            case "reset":
                //reset
                screen.removeFrame();
                break;
        }
        confirmType = "NONE";
    }

    private static void declined(){
        switch (confirmType){
            case "firstLaunch":
                Settings settings = Settings.getInstance();
                settings.open();
                settings.setFirstLaunch(false);
                settings.save();

                screen.removeFrame();
                screen.showFrame(AllFrames.mainMenu);
                break;
            default:
                if(guiListener != null)
                    guiListener.pauseTime(false);
                screen.removeFrame();
                break;
        }
        confirmType = "NONE";
    }

    private static void levelMenu(String event){
        screen.removeFrame();
        menuListener.startLevel(Integer.valueOf(event));
    }


}
