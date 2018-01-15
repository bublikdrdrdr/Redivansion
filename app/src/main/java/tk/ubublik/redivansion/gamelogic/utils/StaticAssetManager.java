package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class StaticAssetManager {
    private static AssetManager assetManager;
    private static BitmapFont bitmapFont;

    public StaticAssetManager(AssetManager assetManager){
        StaticAssetManager.assetManager = assetManager;
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }

    public static void setAssetManager(AssetManager assetManager){
        StaticAssetManager.assetManager = assetManager;
    }

    public static void setBitmapFont(BitmapFont bitmapFont) {
        StaticAssetManager.bitmapFont = bitmapFont;
    }

    public static BitmapFont loadFont(String name){
        return assetManager.loadFont(name);
    }
}
