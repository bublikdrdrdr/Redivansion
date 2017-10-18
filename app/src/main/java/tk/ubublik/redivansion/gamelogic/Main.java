package tk.ubublik.redivansion.gamelogic;

import com.jme3.app.SimpleApplication;
import com.simsilica.lemur.GuiGlobals;

import tk.ubublik.redivansion.gamelogic.lifecycle.Lifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.TestLifecycle;
import tk.ubublik.redivansion.gamelogic.utils.CustomModelLoader;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Main extends SimpleApplication {

    private Lifecycle lifecycle;

    @Override
    public void simpleInitApp() {
        setupApplication();
        initCameraControl();
        //lifecycle = new MainLifecycle(this);
        lifecycle = new TestLifecycle(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
        logFps();
        long nanos = System.nanoTime();
        lifecycle.update();
        if (lifecycle.isDone()) {
            this.stop();
        }
        logLogic(nanos);
    }

    private void setupApplication(){
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        GuiGlobals.initialize(this);
        this.setDisplayStatView(false);
        this.setDisplayFps(false);
        StaticAssetManager.setAssetManager(assetManager);
        assetManager.registerLoader(CustomModelLoader.class, "crm");
    }

    private void initCameraControl(){
        flyCam.setEnabled(false);
        flyCam.unregisterInput();
        flyCam = null;
    }

    long nano = System.nanoTime();
    long fpsCounter = 0;
    private void logFps(){
        if (System.nanoTime()-nano >= 500000000L){
            System.out.println("RENDER FPS: "+fpsCounter*2);
            fpsCounter = 0;
            nano = System.nanoTime();
        }
        fpsCounter++;
    }

    long lastLogicShow = System.currentTimeMillis();
    private void logLogic(long nanos){
        if (System.currentTimeMillis()-lastLogicShow>=500) {
            long elapsed = System.nanoTime() - nanos;
            //yes, I got divide by zero exception
            if (elapsed > 0)
                System.out.println("LOGIC FPS: " + (1000000000L / elapsed));
            else
                System.out.println("LOGIC FPS: over9000");
            lastLogicShow = System.currentTimeMillis();
        }
    }

}
