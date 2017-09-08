package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.component.QuadBackgroundComponent;

/**
 * Created by Bublik on 08-Sep-17.
 */

public class DebugPanel {

    private final Container container;

    public DebugPanel(SimpleApplication simpleApplication){
        container = new Container();
        container.setBackground(new QuadBackgroundComponent(new ColorRGBA(1,1,1,0.3f)));
        container.setLocalScale(3f);//UI scale
        container.setLocalTranslation(0,simpleApplication.getCamera().getHeight(), 0); //left top corner
        simpleApplication.getGuiNode().attachChild(container);
    }

    public void addButton(String text, Command<Button> action){
        Button button = container.addChild(new Button(text));
        button.addClickCommands(action);
    }
}
