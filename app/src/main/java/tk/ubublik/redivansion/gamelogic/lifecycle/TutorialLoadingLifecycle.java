package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.ui.Picture;

import java.util.HashMap;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.ModelManager;
import tk.ubublik.redivansion.gamelogic.utils.LevelFactory;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 *
 * Difference between LevelLoadingLifecycle - additional tutorial nodes
 */
public class TutorialLoadingLifecycle extends LoadingLifecycle {

    private volatile boolean done = false;

    public TutorialLoadingLifecycle(SimpleApplication SimpleApplication) {
        super(SimpleApplication);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //simple model
                    Model simpleModel = (Model)StaticAssetManager.getAssetManager().loadAsset("Models/simple.crm");
                    GeometryAnimationManager geometryAnimationManager = new GeometryAnimationManager(simpleModel);
                    NodesCache.getInstance().put("simple", geometryAnimationManager);
                    //models map
                    HashMap<String, GeometryManager> models = new HashMap<>();
                    models.put("simple", geometryAnimationManager);
                    //tutorial level
                    NodesCache.getInstance().put("tutorial_level", LevelFactory.getLevel("tutorial", models));
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
