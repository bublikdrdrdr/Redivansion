package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.app.AlertDialog;
import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.event.MouseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tk.ubublik.redivansion.JmeFragment;
import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.gui.DebugPanel;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.test.ExampleModel;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GameLogicProcessor;
import tk.ubublik.redivansion.gamelogic.utils.LevelFactory;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;
import tk.ubublik.redivansion.gamelogic.utils.WorldObjectFactory;

import static com.jme3.input.event.TouchEvent.Type.KEY_UP;
import static tk.ubublik.redivansion.JmeFragment.BACK_PRESS_EVENT;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class TutorialLifecycle extends Lifecycle {

    private boolean done = false;
    private GameLogicProcessor gameLogicProcessor;
    private WorldMap worldMap;
    private CameraControl cameraControl;
    private MapRenderer mapRenderer;
    private Level currentLevel = (Level)NodesCache.getInstance().get("tutorial_level");

    public TutorialLifecycle(SimpleApplication SimpleApplication) {
        super(SimpleApplication);
        setup();
        setCamera();
        setLight();
        addDebugPanel();
        attachGrid(Vector3f.ZERO, 20, ColorRGBA.Yellow);
        prepareLevel();
        //putGeometriesToNode();
    }

    private void setup(){
        //exit on back press
        SimpleApplication.getInputManager().addListener(touchListener,  new String[]{BACK_PRESS_EVENT});
        mapRenderer = new MapRenderer(SimpleApplication.getRootNode());
    }

    /*GeometryAnimationManager geometryAnimationManager;
    private void addTestModel(){
        //surprise: does not work on emulator... but on real device is good
        geometryAnimationManager = (GeometryAnimationManager) NodesCache.getInstance().get("simple");
        if (geometryAnimationManager!=null) {
            SimpleApplication.getRootNode().attachChild(geometryAnimationManager);
            geometryAnimationManager.beginAnimation("build_lvl_1");
        }
    }*/

    private void setCamera(){
        SimpleApplication.getCamera().setLocation(new Vector3f(3,6,3));
        SimpleApplication.getCamera().setFrustumPerspective(30f, 1.7777f, 0.1f, 500f);
        SimpleApplication.getCamera().lookAt(new Vector3f(0,0,0), SimpleApplication.getCamera().getUp());
        cameraControl = new CameraControl(SimpleApplication.getCamera(), SimpleApplication.getInputManager());
        //cameraControl.setEnabled(true);
    }

    private void setLight(){
        Light allLight = new AmbientLight(ColorRGBA.DarkGray);
        SimpleApplication.getRootNode().addLight(allLight);
        Light light = new DirectionalLight(SimpleApplication.getCamera().getDirection());
        SimpleApplication.getRootNode().addLight(light);
    }

    private Geometry attachGrid(Vector3f pos, int size, ColorRGBA color){
        Geometry g = new Geometry("wireframe grid", new Grid(size, size, 0.5f) );
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(pos);
        SimpleApplication.getRootNode().attachChild(g);
        return g;
    }

    private void prepareLevel(){
        gameLogicProcessor = new GameLogicProcessor();
        gameLogicProcessor.setLevel(currentLevel);
        worldMap = new WorldMap(currentLevel.getWorldObjects());
        gameLogicProcessor.start();
    }

    private void putGeometriesToNode(){
        mapRenderer.putObjects(currentLevel.getWorldObjects());
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.TUTORIAL;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {
        if (gameLogicProcessor!=null) {
            gameLogicProcessor.onUpdate();
        }
    }

    TouchListener touchListener = new TouchListener() {
        @Override
        public void onTouch(String name, TouchEvent event, float tpf) {
            if (name.equals(BACK_PRESS_EVENT)) {
                switch (event.getType()) {
                    case KEY_UP:
                        done = true;
                        break;
                }
            }
        }
    };

    private void addDebugPanel(){
        DebugPanel debugPanel = new DebugPanel(SimpleApplication);
        debugPanel.addButton("Console log", commands);
        debugPanel.addButton("Add building", commands);
    }

    Command<Button> commands = new Command<Button>() {
        @Override
        public void execute(Button source) {
            switch (source.getText()){
                case "Console log": System.out.println("Console log"); break;
                case "Add building": addBuilding(); break;
            }
        }
    };

    private Geometry getBox(){
        Box b = new Box(0.3f, 0.3f, 0.3f); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(SimpleApplication.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", new ColorRGBA(0f, 0.5f, 1f, 0.5f));   // set color of material to blue
        geom.setMaterial(mat);
        mat.setTransparent(true);
        return geom;
    }

    private void addBuilding(){
        Vector3f vector3f = cameraControl.getCameraCenterPoint();
        Point position = mapRenderer.worldPointToMap(vector3f);
        WorldObject worldObject = WorldObjectFactory.get(Office.class);
        worldObject.setPosition(position);
        if (worldMap.canPut(worldObject)){
            worldMap.put(worldObject);
            //mapRenderer.putObject(worldObject);
            Geometry geometry = getBox();
            geometry.move(mapRenderer.mapPointToWorld(position));
            SimpleApplication.getRootNode().attachChild(geometry);

            geometry = getBox();
            position.x += 1;
            geometry.move(mapRenderer.mapPointToWorld(position));
            SimpleApplication.getRootNode().attachChild(geometry);

            geometry = getBox();
            position.y+=1;
            geometry.move(mapRenderer.mapPointToWorld(position));
            SimpleApplication.getRootNode().attachChild(geometry);

            geometry = getBox();
            position.x-=1;
            geometry.move(mapRenderer.mapPointToWorld(position));
            SimpleApplication.getRootNode().attachChild(geometry);
        }
    }

    MouseListener objectPickListener = new DefaultMouseListener(GUI.CLICK_OFFSET, GUI.CLICK_OFFSET){
        @Override
        protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
            Vector3f v2 = new Vector3f(2f, 2f, 2f);
            Vector3f v1 = new Vector3f(1f, 1f, 1f);
            if (!target.getLocalScale().equals(v2)){
                target.setLocalScale(v2);
            } else {
                target.setLocalScale(v1);
            }
        }
    };
}
