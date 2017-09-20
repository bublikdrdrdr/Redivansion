package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.scene.Node;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class GUI {

    private Node guiNode;
    public static final int CLICK_OFFSET = 5;

    public GUI(Node guiNode){
        this.guiNode = guiNode;
        test();
    }

    private void test(){
        Test test = new Test();
        test.testElements(guiNode);
    }
}
