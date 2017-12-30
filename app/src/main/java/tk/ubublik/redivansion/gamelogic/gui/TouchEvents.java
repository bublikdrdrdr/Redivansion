package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.gamelogic.lifecycle.MenuLifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.TestLifecycle;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.MenuListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class TouchEvents {

    public static String object = null;
    public static GUIListener guiListener;
    public static MenuListener menuListener;
    public static Screen screen;
    public static int roadKostyl = 0;
    public static boolean removeSelect = false;

    public static void closeFrame(Screen scr){
            if(scr.getActiveFrame().frameName.equals("info")){
                screen.removeFrame();
                //guiListener.objectSelected(null); //у вот і нафіг не треба, GUI пропадає разом з об'єктом
                screen.showFrame(AllFrames.main);
                screen.gui.cameraControl.restoreCameraPosition();
            }
            else if(scr.getActiveFrame().frameName.equals("menu")){
                guiListener.pauseTime(false);
                screen.removeFrame();
            }
            else if (scr.getActiveFrame().frameName.equals("levelComplete")){
                guiListener.setDone(true);
            }
            else screen.removeFrame();
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
        }
    }

    public static void doSmthing(String event, GUIListener gui, Screen scr) {
        guiListener = gui;
        screen = scr;
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
        }
    }

    public static void menu(String event){
        switch (event){
            case "save":
                guiListener.save();
                screen.removeFrame();
                break;
            case "removeSave":
                guiListener.removeSave();
                screen.removeFrame();
                break;
            case "returnToMainMenu":
                screen.removeFrame();
                guiListener.setDone(true);
                break;
        }
    }

    public static void mainEvents(String event){
        switch (event){
            case "addSmthing":
                screen.showFrame(GUI.frames.add);
                break;
            case "menu":
                screen.showFrame(GUI.frames.menu);
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
            case "exit":
                screen.removeFrame();
                menuListener.exit();
                break;
        }
    }

    private static void levelMenu(String event){
        screen.removeFrame();
        menuListener.startLevel(Integer.valueOf(event));
    }


}
