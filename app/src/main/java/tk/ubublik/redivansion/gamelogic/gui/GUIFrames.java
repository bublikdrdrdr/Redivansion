package tk.ubublik.redivansion.gamelogic.gui;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class GUIFrames {

    String frameName;
    ArrayList<GUIElements> elements = new ArrayList<GUIElements>();

    public GUIFrames(String name){
        this.frameName = name;
    }

    public void addElement(String name, String text, float x, float y, float w, float h, String path){
        GUIElements elem = new GUIElements(name, text, x, y, w, h, path);
        elements.add(elem);
    }

    public void removeElement(String name){
        for(GUIElements element:elements){
            if(element.name.equals(name)) {
                elements.remove(elements.indexOf(element));
                break;
            }
        }
    }

    public boolean touchResult(float x, float y, GUIListener guiListener, GUIScreens screens){
        for(GUIElements element:elements){
            if(!element.name.equals("bg")) {
                if (element.touchCheck(x, y)) {
                    GUITouchEvents.doSmthing(element.name, guiListener, screens);
                    return true;
                }
            }
        }
        return false;
    }

}
