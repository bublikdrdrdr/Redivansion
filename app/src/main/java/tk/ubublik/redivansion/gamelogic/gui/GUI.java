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

    public void setTime(){

    }

    public void setStatusChanged(int population, int money, boolean grow){
        for(Element element:frames.main.elements){
            if(element.p.getName().equals("population")){
                element.txt.setText("Population: " + population);
            }
            else if(element.p.getName().equals("money")){
                element.txt.setText("Money: " + money);
            }
        }
    }

    @Override
    public boolean touchCaptured(TouchEvent touchEvent, float tfp) {
        if(touchEvent.getType() == TouchEvent.Type.DOWN){
            this.startX = touchEvent.getX();
            this.startY = touchEvent.getY();
            touchedGUI = guiScreen.touchEvent(startX, startY, guiListener, touchEvent);
        }
        else if(touchEvent.getType() == TouchEvent.Type.UP && touchedGUI){
            guiScreen.activeFrame.get(guiScreen.activeFrame.size()-1).touchedElem = false;
            guiScreen.activeFrame.get(guiScreen.activeFrame.size()-1).removeTouch();
            guiScreen.touchEvent(touchEvent.getX(), touchEvent.getY(), guiListener, touchEvent);
            touchedGUI = false;
        }
        return touchedGUI;
    }
}
