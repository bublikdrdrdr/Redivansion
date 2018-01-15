package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 */

public abstract class LoadingLifecycle extends Lifecycle {

    Picture spinner;
    Node spinnerNode = new Node();

    public LoadingLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        setLoadingNode();
    }

    @Override
    public void update() {
        spinnerNode.rotate(0,0,0.2f);
    }

    private void setLoadingNode(){
       Picture pic = new Picture("HUD Picture");
        pic.setImage(StaticAssetManager.getAssetManager(), "Textures/loadingBackground.png", true);
        pic.setWidth(simpleApplication.getCamera().getWidth());
        pic.setHeight(simpleApplication.getCamera().getHeight());
        pic.setPosition(0,0);
        simpleApplication.getGuiNode().attachChild(pic);

        spinner = new Picture("spinner");
        spinner.setImage(StaticAssetManager.getAssetManager(), "Textures/loadingSpinnerCircle.png", true);
        float height = simpleApplication.getCamera().getHeight()/10.f;
        spinnerNode.setLocalTranslation(simpleApplication.getCamera().getWidth()-height, height, 2);
        spinner.setWidth(height);
        spinner.setHeight(height);
        spinner.setLocalTranslation(new Vector3f(-height*.5f, -height*.5f,0));
        spinnerNode.attachChild(spinner);
        simpleApplication.getGuiNode().attachChild(spinnerNode);
    }
}
