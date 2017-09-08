package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;

/**
 * Created by Bublik on 02-Sep-17.
 */

public abstract class LoadingLifecycle extends Lifecycle {

    public LoadingLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        setLoadingNode();
    }

    @Override
    public void update() {
        if (loadingPanel != null)
            loadingPanel.setLocalTranslation(
                    FastMath.nextRandomInt(0, simpleApplication.getCamera().getWidth()),
                    FastMath.nextRandomInt(0, simpleApplication.getCamera().getHeight()),
                    0);
    }

    private Panel loadingPanel;
    private void setLoadingNode(){
        Label label = new Label("LOADING");
        label.setFontSize(50f);
        loadingPanel = label;
        simpleApplication.getGuiNode().attachChild(loadingPanel);
    }
}
