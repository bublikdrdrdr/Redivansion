package tk.ubublik.redivansion.gamelogic.gui;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class GUITouchEvents {

    public static void doSmthing(String event, GUIListener guiListener, GUIScreens screen){
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
            case "addClose":
                screen.removeFrame();
                break;
            case "selectClose":
                screen.removeFrame();
                break;
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
}
