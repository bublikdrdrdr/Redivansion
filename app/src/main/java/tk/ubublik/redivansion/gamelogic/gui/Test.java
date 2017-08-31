package tk.ubublik.redivansion.gamelogic.gui;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

import java.util.Random;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Test {


    public void testElements(final Node guiNode){
        final Container myWindow = new Container();
        guiNode.attachChild(myWindow);
        myWindow.setLocalScale(4f);
// Put it somewhere that we will see it.
// Note: Lemur GUI elements grow down from the upper left corner.
        myWindow.setLocalTranslation(300, 300, 0);

// Add some elements
        myWindow.addChild(new Label("Hello, World."));
        Button clickMe = myWindow.addChild(new Button("Click Me"));
        clickMe.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                Label label = new Label("WORKS");
                label.setFontSize(120f);
                label.setShadowOffset(new Vector3f(1f, 1f, 1f));
                Container window2 = new Container();
                window2.setLocalTranslation(FastMath.nextRandomInt(0,1920), FastMath.nextRandomInt(0,1080), 0);
                window2.addChild(label);
                guiNode.attachChild(window2);
                //System.out.println("The world is yours.");
            }
        });
    }
}
