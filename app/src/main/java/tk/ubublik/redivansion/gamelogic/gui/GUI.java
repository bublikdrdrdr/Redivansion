package tk.ubublik.redivansion.gamelogic.gui;


import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.utils.TouchInputHook;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class GUI implements TouchInputHook{

    private Node guiNode;
    public static final int CLICK_OFFSET = 5;

    public GUI(Node guiNode){
        this.guiNode = guiNode;

    }

    public void onUpdate(){

    }

    @Override
    public boolean touchCaptured(TouchEvent touchEvent, float tfp) {
        return false;
    }
}
