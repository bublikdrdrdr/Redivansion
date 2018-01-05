package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

import java.util.HashMap;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
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

    public TutorialLoadingLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Model simpleModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/simple.crm");
                    NodesCache.getInstance().put("officeModel", simpleModel);
                    Model treeModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/tree.crm");
                    NodesCache.getInstance().put("treeModel", treeModel);
                    Model terrainModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/terrain.crm");
                    NodesCache.getInstance().put("terrainModel", terrainModel);
                    Model roadModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/road.crm");
                    NodesCache.getInstance().put("roadModel", roadModel);
                    Model houseModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/house.crm");
                    NodesCache.getInstance().put("houseModel", houseModel);
                    Model powerPlantModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/powerplant.crm");
                    NodesCache.getInstance().put("powerPlantModel", powerPlantModel);

                    // TODO: 27-Dec-17 load other models
                } catch (Exception e){
                    e.printStackTrace();
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
