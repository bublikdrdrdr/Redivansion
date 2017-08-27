package tk.ubublik.redivansion.gamelogic;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.Light;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

import java.util.logging.Logger;

import tk.ubublik.redivansion.gamelogic.graphics.DynamicGeometryImpl;
import tk.ubublik.redivansion.gamelogic.test.ExampleModel;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Main extends SimpleApplication {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    @Override
    public void simpleUpdate(float tpf) {
        // TODO: 20-Aug-17 process game logic
        testUpdate();
    }

    public void simpleInitApp() {
        setupApplication();
        addTestBox();
        testInit();
        getCamera().setLocation(new Vector3f(-4,3,6));
        getCamera().lookAt(new Vector3f(0,0,0), getCamera().getUp());
        //rootNode.updateGeometricState();
    }

    private void setupApplication(){
        this.setDisplayStatView(false);
        this.setDisplayFps(false);
        StaticAssetManager.setAssetManager(assetManager);
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

        com.jme3.scene.shape.Quad quad = new Quad(30,30);
        Material mat2 = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat2.setBoolean("UseMaterialColors",true);  // Set some parameters, e.g. blue.
        mat2.setColor("Ambient", ColorRGBA.Blue);   // ... color of this object
        mat2.setColor("Diffuse", ColorRGBA.Yellow);
        mat2.setColor("Specular", ColorRGBA.White);
        Texture texture2 = assetManager.loadTexture("Textures/specular.png");
        mat2.setTexture("SpecularMap",texture2);
        mat2.setFloat("Shininess", 2f);
        Geometry floor = new Geometry("floor", quad);
        floor.move(-15f,-5,15);
        floor.rotate(-FastMath.HALF_PI,0,0);
        floor.setMaterial(mat2);
        rootNode.attachChild(floor);
        rootNode.attachChild(geom);
    }



    //TEST FIELD
    DynamicGeometryImpl dynamicGeometry;
    SpotLight light;

    private void testInit(){
        Light allLight = new AmbientLight(ColorRGBA.DarkGray);
        rootNode.addLight(allLight);
        light = new SpotLight(getCamera().getLocation(), getCamera().getDirection());
        rootNode.addLight(light);
        dynamicGeometry = new DynamicGeometryImpl(new ExampleModel());
        rootNode.attachChild(dynamicGeometry);
        dynamicGeometry.beginAnimation("build");
    }

    private void testUpdate(){
        dynamicGeometry.onUpdate();
        //dynamicGeometry.rotate(0f, 0.02f, 0f);
        light.setDirection(getCamera().getDirection());
        light.setPosition(getCamera().getLocation());
        //change();
    }


    private long time;

    boolean built = false;
    boolean changed = false;
    private void change(){
        if (!built){
            if (System.currentTimeMillis()-time >= 4000){
                dynamicGeometry.beginAnimation("build");
                built = true;
            }
        }
        if (!changed){
            if (System.currentTimeMillis()-time >= 10000){
                dynamicGeometry.beginAnimation("change");
                changed = true;
            }
        }
    }
    /*private DynamicObject dynamicObject;

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
            if (System.currentTimeMillis()-time >= 3000){
                dynamicObject.changeSomething();
                updated = true;
            }
        }
    }*/
}
