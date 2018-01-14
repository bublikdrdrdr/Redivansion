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
        System.out.println("AZAZAZAZA: "+screen.getCurrentFrameName());
        closeFrame(screen);
    }

    public static void closeFrame(Screen screen){
            if(tutorial){
                for(String name:TutorialFrames.tutorialFrames){
                    if(screen.getActiveFrame().frameName.equals(name))
                        return;
                }
            }
            if(screen.getActiveFrame().frameName.equals("info")){
                screen.removeAllFrames();
                screen.showFrame(AllFrames.main);
                screen.gui.cameraControl.restoreCameraPosition();
                if(tutorial){
                    screen.showFrame(TutorialFrames.blank());
                    screen.showFrame(TutorialFrames.frame("roadInfo"));
                }
                return;
            }
            else if(screen.getActiveFrame().frameName.equals("menu")){
                if(guiListener != null)
                    guiListener.pauseTime(false);
                screen.removeFrame();
                return;
            }
            else if(screen.getActiveFrame().frameName.equals("mainMenu")){
                screen.removeFrame();
                if(menuListener != null)
                    menuListener.exit();
                return;
            }
            else if (screen.getActiveFrame().frameName.equals("levelComplete")){
                AllFrames.levelEndShowed = false;
                guiListener.setDone(true);
                return;
            }
            else if(screen.getActiveFrame().frameName.equals("blank")) {
                screen.showFrame(AllFrames.gameMenu());
                return;
            }
            else if(screen.getActiveFrame().frameName.equals("main")) {
                screen.showFrame(AllFrames.gameMenu());
                return;
            }
            else if(screen.getActiveFrame().frameName.equals("confirmMenu")) {
                declined();
                return;
            }
            else screen.removeFrame();
            return;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Tutorial Behavior
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static void tutorial(Screen screen){
        switch (screen.getActiveFrame().frameName){
            case "about":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.frame("goals"));
                break;
            case "goals":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.population());
                break;
            case "population":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.time());
                break;
            case "time":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.money());
                break;
            case "money":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.frame("cameraMove"));
                break;
            case "cameraMove":
                guiListener.cameraTutorial(TutorialLifecycle.CameraTutorial.NONE);
                TutorialLifecycle.cameraTutorial = TutorialLifecycle.CameraTutorial.MOVE;
                screen.removeFrame();
                screen.showFrame(TutorialFrames.blank());
                break;
            case "cameraZoom":
                guiListener.cameraTutorial(TutorialLifecycle.CameraTutorial.NONE);
                TutorialLifecycle.cameraTutorial = TutorialLifecycle.CameraTutorial.ZOOM;
                screen.removeFrame();
                screen.showFrame(TutorialFrames.blank());
                break;
            case "buildMenu1":
                screen.removeFrame();
                screen.removeFrame(); //blank
                screen.showFrame(GUI.frames.add);
                screen.showFrame(TutorialFrames.buildChoose(false));
                break;
            case "buildChoose1":
                screen.removeFrame();
                screen.removeFrame();
                addEvents("addHouse");
                screen.showFrame(TutorialFrames.frame("buildAddHouse"));
                break;
            case "buildAddHouse":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.buildAdd(false));
                break;
            case "buildAdd1":
                build("build");
                break;
            case "objectInfo":
                screen.removeFrame();
                screen.showFrame(TutorialFrames.blank());
                break;
            case "objectInfo2":
                screen.removeFrame();
                break;
            case "roadInfo":
                screen.removeFrame();
                screen.removeFrame();
                screen.showFrame(TutorialFrames.blank());
                screen.showFrame(TutorialFrames.buildMenu(true));
                break;
            case "buildMenu2":
                screen.removeFrame();
                screen.removeFrame(); //blank
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
                screen.removeFrame();
                break;
        }
        return;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Menu Behavior
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Ingame Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object Upgrade
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static void info(String event){
        if(event.equals("upgrade")){
            screen.removeAllFrames();
            screen.showFrame(AllFrames.main);
            screen.gui.cameraControl.restoreCameraPosition();
            guiListener.getGui().upgradeObject();
            if(tutorial){
                screen.showFrame(TutorialFrames.blank());
                screen.showFrame(TutorialFrames.frame("roadInfo"));
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Ingame Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


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
                if(guiListener != null)
                    guiListener.pauseTime(true);
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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Building Choose
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static void addEvents(String event){
        switch (event){
            case "addNext1":
                screen.removeFrame();
                screen.showFrame(AllFrames.initAdd2());
                break;
            case "addNext2":
                screen.removeFrame();
                screen.showFrame(AllFrames.initAdd3());
                break;
            case "addPrev1":
                screen.removeFrame();
                screen.showFrame(AllFrames.add);
                break;
            case "addPrev2":
                screen.removeFrame();
                screen.showFrame(AllFrames.initAdd2());
                break;
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
            case "addPolice":
                object = "police";
                guiListener.selectOffice();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addFire":
                object = "fire";
                guiListener.selectOffice();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addWater":
                object = "water";
                guiListener.selectOffice();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addHospital":
                object = "hospital";
                guiListener.selectPower();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addSchool":
                object = "school";
                guiListener.selectOffice();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addShop":
                object = "shop";
                guiListener.selectOffice();
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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Build
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static void build(String event){
        switch (event){
            case "build":
                switch (object){
                    case "office":
                        if(guiListener.addBuilding())
                            added();
                        break;
                    case "tree":
                        if(guiListener.addTree())
                            added();
                        break;
                    case "house":
                        if(guiListener.addHouse()){
                            added();
                        if(tutorial){
                            screen.removeFrame();
                            screen.showFrame(TutorialFrames.frame("objectInfo"));
                        }}
                        break;
                    case "power":
                        if(guiListener.addPower())
                            added();
                        break;
                    case "road":
                        if(roadKostyl < 2){
                            guiListener.setRoadPoints();
                            roadKostyl++;
                        }
                        if(roadKostyl == 2){
                            if(guiListener.addRoad()) {
                                roadKostyl = 0;
                                added();
                            }
                        }
                        break;
                    case "police":
                        if(guiListener.addPolice())
                            added();
                        break;
                    case "fire":
                        if(guiListener.addFire())
                            added();
                        break;
                    case "water":
                        if(guiListener.addWater())
                            added();
                        break;
                    case "hospital":
                        if(guiListener.addHospital())
                            added();
                        break;
                    case "school":
                        if(guiListener.addSchool())
                            added();
                        break;
                    case "shop":
                        if(guiListener.addShop())
                            added();
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
    private static void added(){
        guiListener.selectClear();
        screen.removeFrame();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main Menu
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


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

    private static void levelMenu(String event){
        screen.removeFrame();
        menuListener.startLevel(Integer.valueOf(event));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Confirmation Window
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


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
                    confirmType = "NONE";
                    screen.removeFrame();
                    menuListener.exit();
                }
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
}
