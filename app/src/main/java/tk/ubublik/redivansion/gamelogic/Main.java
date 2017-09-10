package tk.ubublik.redivansion.gamelogic;

import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.simsilica.lemur.GuiGlobals;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.ModelManager;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.lifecycle.Lifecycle;
import tk.ubublik.redivansion.gamelogic.lifecycle.MainLifecycle;
import tk.ubublik.redivansion.gamelogic.test.ExampleModel;
import tk.ubublik.redivansion.gamelogic.utils.CustomModelLoader;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Main extends SimpleApplication {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private Lifecycle lifecycle;

    @Override
    public void simpleInitApp() {
        setupApplication();
        initCameraControl();
        lifecycle = new MainLifecycle(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
        logFps();
        long nanos = System.nanoTime();
        lifecycle.update();
        if (lifecycle.isDone()) {
            this.stop();
        }
        logLogic(nanos);
    }

    private void setupApplication(){
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        GuiGlobals.initialize(this);
        this.setDisplayStatView(false);
        this.setDisplayFps(false);
        StaticAssetManager.setAssetManager(assetManager);
        assetManager.registerLoader(CustomModelLoader.class, "crm");
    }

    private void initCameraControl(){
        flyCam.setEnabled(false);
        flyCam.unregisterInput();
        flyCam = null;
    }

    long nano = System.nanoTime();
    long fpsCounter = 0;
    private void logFps(){
        if (System.nanoTime()-nano >= 500000000L){
            System.out.println("RENDER FPS: "+fpsCounter*2);
            fpsCounter = 0;
            nano = System.nanoTime();
        }
        fpsCounter++;
    }

    long lastLogicShow = System.currentTimeMillis();
    private void logLogic(long nanos){
        if (System.currentTimeMillis()-lastLogicShow>=500) {
            System.out.println("LOGIC FPS: " + (1000000000L / (System.nanoTime() - nanos)));
            lastLogicShow = System.currentTimeMillis();
        }
    }





    /*GUI gui;

    @Override
    public void simpleUpdate(float tpf) {
        // TODO: 20-Aug-17 process game logic
        testUpdate();
    }

    ModelManager modelManager = new ModelManager();

    public void simpleInitApp() {
        setupApplication();
        setupGui();
        Model model = new ExampleModel();
        modelManager.loadModel("polyModel1.crm");
        addTestBox();
        testInit();
        getCamera().setLocation(new Vector3f(-3,2,6));
        getCamera().setFrustumPerspective(60f, 1.7777f, 0.1f, 500f);
        getCamera().lookAt(new Vector3f(0,0,0), getCamera().getUp());
        //rootNode.updateGeometricState();
    }

    private void setupGui(){
        GuiGlobals.initialize(this);
        gui = new GUI(guiNode);
    }

    private void setupApplication(){
        this.setDisplayStatView(false);
        this.setDisplayFps(true);
        StaticAssetManager.setAssetManager(assetManager);
        assetManager.registerLoader(CustomModelLoader.class, "crm");
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

        VertexBuffer vertexBuffer = floor.getMesh().getBuffer(VertexBuffer.Type.Normal);
        FloatBuffer floatBuffer = (FloatBuffer)vertexBuffer.getData();

        rootNode.attachChild(floor);
        rootNode.attachChild(geom);
    }



    //TEST FIELD
    GeometryAnimationManager dynamicGeometry;
    DirectionalLight light;

    private void testInit(){
        attachGrid(new Vector3f(0,-1,0), 50, ColorRGBA.Cyan);
        Light allLight = new AmbientLight(ColorRGBA.DarkGray);
        rootNode.addLight(allLight);
        light = new DirectionalLight(getCamera().getDirection());
        rootNode.addLight(light);
        dynamicGeometry = new GeometryAnimationManager(modelManager.getModel("polyModel1.crm"));
        rootNode.attachChild(dynamicGeometry);
        dynamicGeometry.beginAnimation("build");
    }

    private void testUpdate(){
        dynamicGeometry.onUpdate();
        light.setDirection(getCamera().getDirection());
    }

    private Geometry attachGrid(Vector3f pos, int size, ColorRGBA color){
        Geometry g = new Geometry("wireframe grid", new Grid(size, size, 0.5f) );
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(pos);
        rootNode.attachChild(g);
        return g;
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
    }*/
}
