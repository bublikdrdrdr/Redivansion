package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.scene.Node;

import java.util.ArrayList;

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by SomeOne on 18.10.2017.
 */

public class GUIScreens {

    private String screenName;
    private Node guiNode;
    private ArrayList<GUIFrames> activeFrame = new ArrayList<>();

    public GUIScreens(String name, Node gui, GUIFrames frame){
        this.screenName = name;
        this.guiNode = gui;
        guiNode.detachAllChildren();
        showFrame(frame);
    }

    public void showFrame(GUIFrames frame){
        activeFrame.add(frame);
        for(GUIElements element:activeFrame.get(activeFrame.size()-1).elements){
            guiNode.attachChild(element.p);
            if(!element.text.equals(""))
                guiNode.attachChild(element.txt);
        }
    }

    public void removeFrame(){
        if(!activeFrame.isEmpty()) {
            int in;
            for (GUIElements element : activeFrame.get(activeFrame.size() - 1).elements) {
                in = guiNode.getChildIndex(element.p);
                guiNode.detachChildAt(in);
                if (!element.text.equals("")) {
                    in = guiNode.getChildIndex(element.txt);
                    guiNode.detachChildAt(in);
                }
            }

            activeFrame.remove(activeFrame.size()-1);
            showFrame(activeFrame.get(activeFrame.size()-1));
        }
    }

    public boolean touchEvent(float x, float y, GUIListener guiListener){
        return activeFrame.get(activeFrame.size()-1).touchResult(x,y, guiListener, this);
    }
}
