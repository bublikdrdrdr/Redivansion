package tk.ubublik.redivansion.gamelogic;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.GuiGlobals;

import tk.ubublik.redivansion.gamelogic.lifecycle.Lifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.MainLifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.TestLifecycle;
import tk.ubublik.redivansion.gamelogic.test.FpsMeter;
import tk.ubublik.redivansion.gamelogic.utils.CustomModelLoader;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Main extends SimpleApplication {

    private FpsMeter fpsMeter = FpsMeter.getInstance();
    private Lifecycle lifecycle;

    @Override
    public void simpleInitApp() {
        setupApplication();
        initCameraControl();
        lifecycle = new MainLifecycle(this);
        //lifecycle = new TestLifecycle(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
        fpsMeter.logFps();
        long nanos = System.nanoTime();
        lifecycle.update();
        if (lifecycle.isDone()) {
            this.stop();
        }
        fpsMeter.logLogic(nanos);
    }

    private void setupApplication(){
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        GuiGlobals.initialize(this);
        this.setDisplayStatView(false);
        this.setDisplayFps(false);
        StaticAssetManager.setAssetManager(assetManager);
        StaticAssetManager.setBitmapFont(guiFont);
        assetManager.registerLoader(CustomModelLoader.class, "crm");
    }

    private void initCameraControl(){
        flyCam.setEnabled(false);
        flyCam.unregisterInput();
        flyCam = null;
    }

    // TODO: 03-Sep-17 find a better place to put next code
    public static final String BACK_PRESS_EVENT = "BackPressEvent";
    public static void registerBackPressListener(TouchListener touchListener, InputManager inputManager) {
        inputManager.addMapping(BACK_PRESS_EVENT, new TouchTrigger(TouchInput.KEYCODE_BACK));
        inputManager.addListener(touchListener,  new String[]{BACK_PRESS_EVENT});
    }

}
