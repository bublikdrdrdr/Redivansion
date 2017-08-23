package tk.ubublik.redivansion.gamelogic;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import java.nio.ShortBuffer;
import java.util.Random;
import java.util.logging.Logger;

import tk.ubublik.redivansion.gamelogic.test.DynamicObject;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Main extends SimpleApplication {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void simpleUpdate(float tpf) {
        // TODO: 20-Aug-17 process game logic
        testUpdate();
    }

    public void simpleInitApp() {
        Box box = new Box(1f,1f,1f);

        setupApplication();
        addTestBox();
        testInit();
        getCamera().setLocation(new Vector3f(-4,3,6));
        getCamera().lookAt(new Vector3f(0,0,0), getCamera().getUp());
        rootNode.updateGeometricState();
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
        geom.move(new Vector3f(2,0,0));
        rootNode.attachChild(geom);
    }



    //TEST FIELD
    private DynamicObject dynamicObject;

    private void testInit(){
        dynamicObject = new DynamicObject(rootNode, assetManager);
        dynamicObject.drawSomething();
        time = System.currentTimeMillis();
    }

    private void testUpdate(){
        dynamicObject.update();
        changeAfter5Seconds();
    }

    private boolean updated = false;
    private long time;
    private void changeAfter5Seconds(){
        if (!updated){
            if (System.currentTimeMillis()-time >= 5000){
                dynamicObject.changeSomething();
                updated = true;
            }
        }
    }
}
