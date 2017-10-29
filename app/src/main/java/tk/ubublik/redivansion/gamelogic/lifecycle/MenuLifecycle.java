package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MenuLifecycle extends Lifecycle {

    enum MenuResult{ START_LEVEL, START_TUTORIAL, EXIT};
    public int startLevelNumber;
    public MenuResult menuResult;
    private boolean done = false;

    public MenuLifecycle(SimpleApplication simpleApplication){
        super(simpleApplication);
        createMenuElements();
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
        addBackgroundImage();

        final Container myWindow = new Container();
        simpleApplication.getGuiNode().attachChild(myWindow);
        myWindow.setLocalScale(4f);
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
        });
    }

    private void addBackgroundImage(){
        Picture backgroundPicture = (Picture)NodesCache.getInstance().get("menu_background");
        backgroundPicture.setWidth(simpleApplication.getCamera().getWidth());
        backgroundPicture.setHeight(simpleApplication.getCamera().getHeight());
        backgroundPicture.setPosition(0,0);
        simpleApplication.getGuiNode().attachChild(backgroundPicture);
    }
}
