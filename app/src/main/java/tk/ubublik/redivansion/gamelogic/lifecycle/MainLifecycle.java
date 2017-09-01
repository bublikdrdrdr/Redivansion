package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

/**
 * Created by Bublik on 01-Sep-17.
 */

public class MainLifecycle implements Lifecycle{

    public MainLifecycle(SimpleApplication simpleApplication){
        //todo: use simpleApplication to get rootNode, guiNode, listeners etc.
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }
}
