package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.input.event.TouchEvent;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class Frame {

    public String frameName;
    public boolean touchedElem = false;
    ArrayList<Element> elements = new ArrayList<Element>();

    public Frame(String name){
        this.frameName = name;
    }

    public void addElement(String name, String text, float x, float y, float w, float h, boolean square, String path){
        Element elem = new Element(name, text, x, y, w, h, square, path);
        elements.add(elem);
    }

    public void removeElement(String name){
        for(Element element:elements){
            if(element.name.equals(name)) {
                elements.remove(elements.indexOf(element));
                break;
            }
        }
    }

    public boolean touchResult(float x, float y, GUIListener guiListener, TouchEvent touchEvent, Screen screen){
        for(Element element:elements){
            if (element.touchCheck(x, y)) {
                if(!element.name.equals("bg") && element.touchCheck(GUI.startX, GUI.startY) &&
                        touchEvent.getType() == TouchEvent.Type.UP) {
                    TouchEvents.doSmthing(element.name, guiListener, screen);
                    touchedElem = false;
                    return true;
                }
                touchedElem = true;
            }
        }
        return touchedElem;
    }


    //===============================================================
    //===============================================================
    //===============================================================



    public void addTransparent(String name, String text, float x, float y, float w, float h, boolean square, String path){
        Element elem = new Element();
        elem.setTransparent(name, text, x, y, w, h, square, path);
        elements.add(elem);
    }

}
