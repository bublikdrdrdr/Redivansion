package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.font.Rectangle;
import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Node;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 18.10.2017.
 */

public class Screen {

    private String screenName;
    private Node guiNode;
    private ArrayList<Frame> activeFrame = new ArrayList<>();
    public GUI gui;

    public Screen(String name, Node guiNode, Frame frame, GUI gui){
        this.screenName = name;
        this.guiNode = guiNode;
        this.gui = gui;
        guiNode.detachAllChildren();
        showFrame(frame);
    }

    public void showFrame(Frame frame){
        if(frame.frameName.equals("info"))
            removeFrame();
        activeFrame.add(frame);
        for(Element element:activeFrame.get(activeFrame.size()-1).elements){
            guiNode.attachChild(element.p);
            if(element.txt.getText() != null)
                guiNode.attachChild(element.txt);
        }
    }

    public void removeFrame(){
        if(!activeFrame.isEmpty()) {
            int in;
            for (Element element : getActiveFrame().elements) {
                if (element.p!=null) guiNode.detachChild(element.p);
                if (element.txt!=null) guiNode.detachChild(element.txt);
            }

            activeFrame.remove(activeFrame.size()-1);
        }
    }

    public Frame getActiveFrame(){
        return activeFrame.get(activeFrame.size()-1);
    }

    public boolean isEmpty(){
        return activeFrame.isEmpty();
    }

    public String getCurrentFrameName(){
        return activeFrame.get(activeFrame.size()-1).frameName;
    }

    public boolean touchEvent(float x, float y, GUIListener guiListener, TouchEvent touchEvent){
        return activeFrame.get(activeFrame.size()-1).touchResult(x,y, guiListener, touchEvent, this);
    }
}
