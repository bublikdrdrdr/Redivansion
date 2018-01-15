package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Node;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.MenuListener;

/**
 * Created by SomeOne on 18.10.2017.
 */

public class Screen {

    private Node guiNode;
    private ArrayList<Frame> activeFrame = new ArrayList<>();
    public GUI gui;

    public Screen(Node guiNode, Frame frame, GUI gui){
        this.guiNode = guiNode;
        this.gui = gui;
        guiNode.detachAllChildren();
        showFrame(frame);
    }

    public void showFrame(Frame frame){
        if(frame.frameName.equals("info"))
            removeFrame();
        activeFrame.add(frame);
        for(Element element:getActiveFrame().elements){
            guiNode.attachChild(element.p);
            if(element.txt.getText() != null)
                guiNode.attachChild(element.txt);
        }
    }

    public String getCurrentFrameName(){
        return activeFrame.get(activeFrame.size()-1).frameName;
    }

    public void removeFrame(){
        if(activeFrame.size()-1 > 0) {
            for (Element element : getActiveFrame().elements) {
                if (element.p!=null) guiNode.detachChild(element.p);
                if (element.txt!=null) guiNode.detachChild(element.txt);
            }

            activeFrame.remove(activeFrame.size()-1);
        }
    }

    public void removeAllFrames(){
        while (!activeFrame.isEmpty()) {
            for (Element element : getActiveFrame().elements) {
                if (element.p != null) guiNode.detachChild(element.p);
                if (element.txt != null) guiNode.detachChild(element.txt);
            }
            activeFrame.remove(activeFrame.size() - 1);
        }
    }

    public Frame getActiveFrame(){
        return activeFrame.get(activeFrame.size()-1);
    }

    public boolean isEmpty(){
        return activeFrame.isEmpty();
    }


    public boolean touchEvent(float x, float y, GUIListener guiListener, MenuListener menuListener, TouchEvent touchEvent){
        return activeFrame.get(activeFrame.size()-1).touchResult(x,y, guiListener, menuListener, touchEvent, this);
    }
}
