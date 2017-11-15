package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.input.event.TouchEvent;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class Frame {

    public String frameName;
    public boolean touchedElem = false;
    ArrayList<Element> elements = new ArrayList<Element>();
    private Element touchedElement = null;

    public Frame(String name){
        this.frameName = name;
    }

    public void addElement(String name, String text, int align, float x, float y, float w, float h, boolean square, String path, boolean transparent){
        Element elem = new Element(name, text, align, x, y, w, h, square, path, transparent);
        elements.add(elem);
    }

    public void removeElement(String name){
        for(Element element:elements){
            if(element.p.getName().equals(name)) {
                elements.remove(elements.indexOf(element));
                break;
            }
        }
    }

    public boolean touchResult(float x, float y, GUIListener guiListener, TouchEvent touchEvent, Screen screen){
        for(Element element:elements){
            if (element.touchCheck(x, y)) {
                if(!element.p.getName().equals("bg") && !element.p.getName().equals("time") && !element.p.getName().equals("money") && !element.p.getName().equals("population")
                        && touchEvent.getType() == TouchEvent.Type.DOWN) {
                    touchedElement = element;
                    touchedElement.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn2.png", false);
                }
                if(!element.p.getName().equals("bg") && !element.p.getName().equals("time") && !element.p.getName().equals("money") && !element.p.getName().equals("population")
                        && element == touchedElement &&
                        touchEvent.getType() == TouchEvent.Type.UP) {
                    TouchEvents.doSmthing(element.p.getName(), guiListener, screen);
                    touchedElem = false;
                    return true;
                }
                touchedElem = true;
            }
        }
        return touchedElem;
    }

    public void removeTouch(){
        if(touchedElement != null)
            touchedElement.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn1.png", false);
    }

}
