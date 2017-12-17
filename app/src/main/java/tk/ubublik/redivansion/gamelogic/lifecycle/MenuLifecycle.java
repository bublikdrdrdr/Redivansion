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
        //loadingPanel.setLocalTranslation(FastMath.nextRandomInt(0, simpleApplication.getCamera().getWidth()), FastMath.nextRandomInt(0, simpleApplication.getCamera().getHeight()), 0);
    }

    Spatial background;
    private void createMenuElements(){
        //addBackgroundImage();

        final Container myWindow = new Container();
        simpleApplication.getGuiNode().attachChild(myWindow);
        settings = Settings.getInstance();
        settings.open();
        gui = new GUI(simpleApplication.getGuiNode(), null, null, AllFrames.mainMenu);
        done = false;

        /*myWindow.setLocalScale(4f);
        myWindow.setLocalTranslation(simpleApplication.getCamera().getWidth()/2-200, simpleApplication.getCamera().getHeight()-200, 0);
        Label label = myWindow.addChild(new Label("Main menu"));
        label.setColor(ColorRGBA.Cyan);
        Button startGame = myWindow.addChild(new Button("Start game"));
        Button tutorial = myWindow.addChild(new Button("Tutorial"));
        Button settings = myWindow.addChild(new Button("Settings"));
        Button exit = myWindow.addChild(new Button("Exit"));
        startGame.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {

            }
        });
        tutorial.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                menuResult = MenuResult.START_TUTORIAL;
                done = true;
            }
        });
        exit.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                menuResult = MenuResult.EXIT;
                done = true;
            }
        });*/
    }

    public static void buttonClicked(MenuResult result){
        menuResult = result;
        done = true;
    }

    /*private void addBackgroundImage(){
        Picture backgroundPicture = (Picture)NodesCache.getInstance().get("menu_background");
        backgroundPicture.setWidth(simpleApplication.getCamera().getWidth());
        backgroundPicture.setHeight(simpleApplication.getCamera().getHeight());
        backgroundPicture.setPosition(0,0);
        simpleApplication.getGuiNode().attachChild(backgroundPicture);
    }*/

}

