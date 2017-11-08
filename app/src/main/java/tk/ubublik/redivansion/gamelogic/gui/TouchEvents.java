package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class TouchEvents {

    public static void doSmthing(String event, GUIListener guiListener, Screen screen) {
        switch (screen.getCurrentFrame()) {
            case "main":
                mainEvents(event, guiListener, screen);
                break;
            case "select":
                selectEvents(event, guiListener, screen);
                break;
            case "add":
                addEvents(event, guiListener, screen);
                break;
        }
    }

    public static void mainEvents(String event, GUIListener guiListener, Screen screen){
        switch (event){
            case "addSmthing":
                screen.showFrame(GUI.frames.add);
                break;
            case "showSelect":
                screen.showFrame(GUI.frames.select);
                break;
            case "remove":
                guiListener.remove();
            default: break;
        }
    }

    public static void addEvents(String event, GUIListener guiListener, Screen screen){
        switch (event){
            case "addTree":
                guiListener.addTree();
                screen.removeFrame();
                break;
            case "addOffice":
                guiListener.addBuilding();
                screen.removeFrame();
                break;
            case "addRoad":
                guiListener.addRoad();
                screen.removeFrame();
                break;
            case "setRoadPoints":
                guiListener.setRoadPoints();
                screen.removeFrame();
                break;
            case "addClose":
                screen.removeFrame();
                break;
            default: break;
        }
    }

    public static void selectEvents(String event, GUIListener guiListener, Screen screen){
        switch (event){
            case "selectTree":
                guiListener.selectTree();
                screen.removeFrame();
                break;
            case "selectOffice":
                guiListener.selectOffice();
                screen.removeFrame();
                break;
            case "selectClear":
                guiListener.selectClear();
                screen.removeFrame();
                break;
            case "selectClose":
                screen.removeFrame();
                break;
            default: break;
        }
    }
}
