package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;

import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 */
public class TutorialLoadingLifecycle extends LoadingLifecycle {

    private volatile boolean done = false;

    public TutorialLoadingLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {Model simpleModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/officeModel.crm");
                    NodesCache.getInstance().put("officeModel", simpleModel);
                    Model treeModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/tree.crm");
                    NodesCache.getInstance().put("treeModel", treeModel);
                    Model terrainModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/terrain.crm");
                    NodesCache.getInstance().put("terrainModel", terrainModel);
                    Model roadModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/road.crm");
                    NodesCache.getInstance().put("roadModel", roadModel);
                    Model houseModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/houseModel.crm");
                    NodesCache.getInstance().put("houseModel", houseModel);
                    Model powerPlantModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/powerplantModel.crm");
                    NodesCache.getInstance().put("powerplantModel", powerPlantModel);
                    Model firestationModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/firestationModel.crm");
                    NodesCache.getInstance().put("firestationModel", firestationModel);
                    Model hospitalModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/hospitalModel.crm");
                    NodesCache.getInstance().put("hospitalModel", hospitalModel);
                    Model policestationModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/policestationModel.crm");
                    NodesCache.getInstance().put("policestationModel", policestationModel);
                    Model schoolModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/schoolModel.crm");
                    NodesCache.getInstance().put("schoolModel", schoolModel);
                    Model shopModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/shopModel.crm");
                    NodesCache.getInstance().put("shopModel", shopModel);
                    Model waterplantModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/waterplantModel.crm");
                    NodesCache.getInstance().put("waterplantModel", waterplantModel);
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
