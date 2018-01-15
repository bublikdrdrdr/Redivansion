package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.ui.Picture;

import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MainLoadingLifecycle extends LoadingLifecycle {

    private volatile boolean done = false;


    public MainLoadingLifecycle(final SimpleApplication simpleApplication){
        super(simpleApplication);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Picture picture = new Picture("menu_background");
                    picture.setImage(StaticAssetManager.getAssetManager(), "Textures/loadingBackground.png", true);
                    NodesCache.getInstance().put("menu_background", picture);
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
        return LifecycleType.MAIN_LOADING;
    }

    @Override
    public boolean isDone() {
        return done;
    }

}
