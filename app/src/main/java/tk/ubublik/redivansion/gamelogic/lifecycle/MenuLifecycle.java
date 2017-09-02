package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.view.Menu;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.component.BoxLayout;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.SpringGridLayout;

import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MenuLifecycle extends Lifecycle {

    enum MenuResult{ START_LEVEL, START_TUTORIAL, EXIT};
    public int startLevelNumber;
    public MenuResult menuResult;

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
        return false;
    }

    @Override
    public void update() {
        //loadingPanel.setLocalTranslation(FastMath.nextRandomInt(0, simpleApplication.getCamera().getWidth()), FastMath.nextRandomInt(0, simpleApplication.getCamera().getHeight()), 0);
    }

    Spatial background;
    private void createMenuElements(){
        background = NodesCache.getInstance().get("menu_background");
        simpleApplication.getGuiNode().attachChild(background);

        final Container myWindow = new Container();
        simpleApplication.getGuiNode().attachChild(myWindow);
        myWindow.setLocalScale(4f);
        myWindow.setLocalTranslation(300, 300, 0);
        myWindow.addChild(new Label("This is menu."));
        Button clickMe = myWindow.addChild(new Button("button"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
            }
        });
    }

    private Panel loadingPanel;
    private void setLoadingNode(){
        Label label = new Label("MENU");
        label.setFontSize(50f);
        loadingPanel = label;
        simpleApplication.getGuiNode().attachChild(loadingPanel);
    }
}
