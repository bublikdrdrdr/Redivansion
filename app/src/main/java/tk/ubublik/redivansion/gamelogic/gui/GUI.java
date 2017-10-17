package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.render.ScalingRenderDevice;
import de.lessvoid.nifty.render.batch.BatchRenderBackendInternal;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class GUI {

    private Node guiNode;
    public static final int CLICK_OFFSET = 5;
    Nifty nifty;

    public GUI(Node guiNode){
        this.guiNode = guiNode;
        nifty = Main.niftyDisplay.getNifty();
        nifty.fromXml("Interface/HelloJme.xml", "start");
    }

    public void onUpdate(){

    }
}
