package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.font.Rectangle;
import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Node;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 18.10.2017.
 */

public class Screen {

    private String screenName;
    private Node guiNode;
    public ArrayList<Frame> activeFrame = new ArrayList<>();
    public boolean scrolled = false;
    private float prevScrollY = 0;
    private float[] elemY;

    public Screen(String name, Node gui, Frame frame){
        this.screenName = name;
        this.guiNode = gui;
        guiNode.detachAllChildren();
        showFrame(frame);
    }

    public void showFrame(Frame frame){
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
            for (Element element : activeFrame.get(activeFrame.size() - 1).elements) {
                in = guiNode.getChildIndex(element.p);
                guiNode.detachChildAt(in);
                if (element.txt.getText() != null) {
                    in = guiNode.getChildIndex(element.txt);
                    guiNode.detachChildAt(in);
                }
            }

            activeFrame.remove(activeFrame.size()-1);
            if(!activeFrame.isEmpty())
                showFrame(activeFrame.get(activeFrame.size()-1));
        }
    }

    public String getCurrentFrameName(){
        return activeFrame.get(activeFrame.size()-1).frameName;
    }

    public boolean touchEvent(float x, float y, GUIListener guiListener, TouchEvent touchEvent){
        return activeFrame.get(activeFrame.size()-1).touchResult(x,y, guiListener, touchEvent, this);
    }
}
