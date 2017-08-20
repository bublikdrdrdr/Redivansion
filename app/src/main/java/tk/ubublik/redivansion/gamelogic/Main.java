package tk.ubublik.redivansion.gamelogic;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import java.util.logging.Logger;

public class Main extends SimpleApplication {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void simpleUpdate(float tpf) {
        // TODO: 20-Aug-17 process game logic 
    }

    public void simpleInitApp() {
        setupApplication();
        addTestBox();
    }

    private void setupApplication(){
        this.setDisplayStatView(false);
        this.setDisplayFps(false);
    }

    private void addTestBox(){
        Box box = new Box(1, 1, 1);
        Geometry geom = new Geometry("box", box);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);

        // Like normal Android projects, place your texture/models/sounds/etc files in the project
        // "assets" folder (src/main/assets).  jME includes this directory automatically when
        // looking for game assets.
        Texture texture = assetManager.loadTexture("Textures/Monkey.png");
        mat.setTexture("ColorMap", texture);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }
}
