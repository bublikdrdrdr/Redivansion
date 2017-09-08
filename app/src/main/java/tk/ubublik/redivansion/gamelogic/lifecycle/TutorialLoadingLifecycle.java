package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.ui.Picture;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.ModelManager;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 *
 * Difference between LevelLoadingLifecycle - additional tutorial nodes
 */
public class TutorialLoadingLifecycle extends LoadingLifecycle {

    private volatile boolean done = false;

    public TutorialLoadingLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Model simpleModel = (Model)StaticAssetManager.getAssetManager().loadAsset("Models/simple.crm");
                    GeometryAnimationManager geometryAnimationManager = new GeometryAnimationManager(simpleModel);
                    NodesCache.getInstance().put("simple", geometryAnimationManager);
                } catch (Exception e){
                    System.exit(1);
                }
                done = true;
            }
        });
        thread.start();
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.TUTORIAL_LOADING;
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
