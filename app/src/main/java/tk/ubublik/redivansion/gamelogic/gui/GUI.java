package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.TouchInputHook;

/*************************
 *
 TODO: Add counters, fix fonts
 *
 * ***********************/

public class GUI implements TouchInputHook {

    private Node guiNode;
    public static final int CLICK_OFFSET = 5;
    public GUIListener guiListener;
    public Screen guiScreen;
    public static AllFrames frames = new AllFrames();
    public static float startX;
    public static float startY;
    private boolean touchedGUI = false;

    public GUI(Node guiNode, GUIListener guiListener) {
        this.guiNode = guiNode;
        this.guiListener = guiListener;
        guiScreen = new Screen("main", guiNode, frames.main);
    }

    public void onUpdate() {

    }

    @Override
    public boolean touchCaptured(TouchEvent touchEvent, float tfp) {
        if(touchEvent.getType() == TouchEvent.Type.DOWN){
            this.startX = touchEvent.getX();
            this.startY = touchEvent.getY();
            touchedGUI = guiScreen.touchEvent(startX, startY, guiListener, touchEvent);
        }
        if(touchEvent.getType() == TouchEvent.Type.UP && touchedGUI){
            guiScreen.activeFrame.get(guiScreen.activeFrame.size()-1).touchedElem = false;
            guiScreen.touchEvent(touchEvent.getX(), touchEvent.getY(), guiListener, touchEvent);
            touchedGUI = false;
        }
        return touchedGUI;
    }
}
