package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.asset.AssetManager;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class StaticAssetManager {
    private static AssetManager assetManager;

    public StaticAssetManager(AssetManager assetManager){
        StaticAssetManager.assetManager = assetManager;
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }

    public static void setAssetManager(AssetManager assetManager){
        StaticAssetManager.assetManager = assetManager;
    }

}
