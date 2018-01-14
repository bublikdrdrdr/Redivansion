package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.input.event.TouchEvent;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.MenuListener;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by SomeOne on 28.10.2017.
 */

public class Frame {

    public String frameName;
    public boolean touchedElem = false;
    public ArrayList<Element> elements = new ArrayList<Element>();
    private Element touchedElement = null;
    private Element touchedElementX = null;

    public Frame(String name){
        this.frameName = name;
    }

    public void addElement(String name, String text, boolean interactive, Element.TextPosition align, float x, float y, float w, float h, boolean square, String path){
        Element elem = new Element(name, text, interactive, align, x, y, w, h, square, path);
        elements.add(elem);
    }

    public void removeAllElements(){
        elements.removeAll(elements);
    }

    public void removeElement(String name){
        for(Element element:elements){
            if(element.p.getName().equals(name)) {
                elements.remove(elements.indexOf(element));
                break;
            }
        }
    }

    public void scrollElements(float deltaY){
        for(Element element:elements){
            if(!element.p.getName().equals("bg")){
                {
                    element.y += deltaY;
                    element.p.setPosition(element.x, element.y);
                    element.setTextPosition();
                }
            }
        }
    }

    public boolean touchResult(float x, float y, GUIListener guiListener, MenuListener menuListener, TouchEvent touchEvent, Screen screen){
        for(Element element:elements){
            if (element.touchCheck(x, y)) {
                if(element.interactive && touchEvent.getType() == TouchEvent.Type.DOWN) {
                    touchedElement = element;
                    //change button image
                    String sit1 = null, sit2 = null;
                    switch(element.p.getName()) {
                        case "addNext1":
                        case "addNext2":
                            sit1 = "addNext";
                            break;
                        case "addPrev1":
                        case "addPrev2":
                            sit1 = "addPrev";
                            break;
                        case "build":
                        case "addSmthing":
                            sit1 = "plus";
                            break;
                        case "cancel":
                            sit1 = "x";
                            sit2 = "close";
                            break;
                        case "next":
                            sit1 = "plus";
                            sit2 = "close";
                            break;
                    }
                        for(Element elementX:elements)
                            if(elementX.p.getName().equals(sit1) || element.p.getName().equals(sit2)){
                                touchedElementX = elementX;
                                break;
                            }
                    if(touchedElementX != null) {
                        touchedElementX.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn2.png", false);
                    }
                    else if(!touchedElement.p.getName().equals("timeSpeed"))
                        touchedElement.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn2.png", false);
                }
                if(element.interactive && element == touchedElement && touchEvent.getType() == TouchEvent.Type.UP) {
                    touchedElement = null;
                    touchedElementX = null;
                    if(guiListener != null)
                        TouchEvents.doSmthing(element.p.getName(), guiListener, screen);
                    else TouchEvents.doSmthing(element.p.getName(), menuListener, screen);
                    touchedElem = false;
                    return true;
                }
                touchedElem = true;
            }
        }
        return touchedElem;
    }

    //restore button image
    public void removeTouch(){
        if(touchedElementX != null)
            touchedElementX.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btnLong1.png", false);
        else if(touchedElement != null && !touchedElement.p.getName().equals("timeSpeed"))
            touchedElement.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btnLong1.png", false);
    }

}
