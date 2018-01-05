package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.gui.AllFrames;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.utils.MenuListener;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MenuLifecycle extends Lifecycle implements TouchListener {

    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        gui.touchCaptured(event, tpf);
    }

    public enum MenuResult{ START_LEVEL, START_TUTORIAL, START_FREEPLAY, EXIT, IDLE};
    public  int startLevelNumber;
    public  MenuResult menuResult;
    private  boolean done = false;
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

    private void createMenuElements(){
        setIdle();
        settings = Settings.getInstance();
        settings.open();
        gui = new GUI(simpleApplication.getGuiNode(), menuListener, AllFrames.mainMenu);
        Main.registerBackPressListener(gui.touchListener, simpleApplication.getInputManager());
        done = false;
    }

    public void setIdle(){
        menuResult = MenuResult.IDLE;
    }

    MenuListener menuListener = new MenuListener() {
        @Override
        public void startTutorial() {
            menuResult = MenuResult.START_TUTORIAL;
            done = true;
        }

        @Override
        public void startLevel(int level) {
            startLevelNumber = level;
            menuResult = MenuResult.START_LEVEL;
            done = true;
        }

        @Override
        public void startFreeplay() {
            menuResult = MenuResult.START_FREEPLAY;
            done = true;
        }

        @Override
        public void exit() {
            menuResult = MenuResult.EXIT;
            done = true;
        }
    };
}

