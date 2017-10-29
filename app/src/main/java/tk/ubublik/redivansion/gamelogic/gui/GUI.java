package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.TouchInputHook;

/*************************
 *
 TODO: Add counters, fix fonts, ignore non-tap input on elements
 *
 * ***********************/

public class GUI implements TouchInputHook{

    private Node guiNode;
    public static final int CLICK_OFFSET = 5;
    public GUIListener guiListener;
    public String screen;
    public GUIScreens guiScreen;
    public static Frames frames = new Frames();

    public GUI(Node guiNode, GUIListener guiListener){
        this.guiNode = guiNode;
        this.guiListener = guiListener;
        guiScreen = new GUIScreens("main", guiNode, frames.main);
    }

    public void onUpdate(){

    }

    @Override
    public boolean touchCaptured(TouchEvent touchEvent, float tfp) {
        if(touchEvent.getType() == TouchEvent.Type.TAP){
            float x = touchEvent.getX();
            float y = touchEvent.getY();
            return guiScreen.touchEvent(x, y, guiListener);
        }
        else return false;
    }
}
