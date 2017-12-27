package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Container;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.gui.AllFrames;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MenuLifecycle extends Lifecycle implements TouchListener {

    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        gui.touchCaptured(event, tpf);
    }

    public enum MenuResult{ START_LEVEL, START_TUTORIAL, EXIT, IDLE};
    public static int startLevelNumber;
    public static MenuResult menuResult;
    private static boolean done = false;
    private GUI gui;
    private Settings settings;

    private static final String TOUCH_MAPPING = "touch";

    public MenuLifecycle(SimpleApplication simpleApplication){
        super(simpleApplication);
        initTouchListener(simpleApplication);
        createMenuElements();
    }

    private void initTouchListener(SimpleApplication simpleApplication){
        simpleApplication.getInputManager().clearMappings();
        simpleApplication.getInputManager().clearRawInputListeners();
        Main.registerBackPressListener(this, simpleApplication.getInputManager());
        simpleApplication.getInputManager().addMapping(TOUCH_MAPPING, new TouchTrigger(TouchInput.ALL));
        simpleApplication.getInputManager().addListener(this, TOUCH_MAPPING);
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.MENU;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {

    }

    Spatial background;
    private void createMenuElements(){

        settings = Settings.getInstance();
        settings.open();
        gui = new GUI(simpleApplication.getGuiNode(), null, null, AllFrames.mainMenu);
        Main.registerBackPressListener(gui.touchListener, simpleApplication.getInputManager());
        done = false;
    }

    public static void buttonClicked(MenuResult result){
        menuResult = result;
        done = true;
    }
}

