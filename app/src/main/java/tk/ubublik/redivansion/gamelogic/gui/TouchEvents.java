package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class TouchEvents {

    public static String object = null;
    public static GUIListener guiListener;
    public static Screen screen;
    public static int roadKostyl = 0;
    public static boolean removeSelect = false;

    public static void doSmthing(String event, GUIListener gui, Screen scr) {
        guiListener = gui;
        screen = scr;
        switch (screen.getCurrentFrameName()) {
            case "main":
                mainEvents(event);
                break;
            case "add":
                addEvents(event);
                break;
            case "build":
                build(event);
                break;
        }
    }

    public static void mainEvents(String event){
        switch (event){
            case "addSmthing":
                screen.showFrame(GUI.frames.add);
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
            case "setRoadPoints":
                object = "road";
                roadKostyl = 0;
                guiListener.setRoadPoints();
                screen.removeFrame();
                screen.showFrame(GUI.frames.build);
                break;
            case "addClose":
                screen.removeFrame();
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


}
