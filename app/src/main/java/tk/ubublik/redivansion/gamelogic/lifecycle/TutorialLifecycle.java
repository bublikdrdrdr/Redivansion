package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.app.AlertDialog;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.event.TouchEvent;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.ubublik.redivansion.JmeFragment;
import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.gui.DebugPanel;
import tk.ubublik.redivansion.gamelogic.test.ExampleModel;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

import static com.jme3.input.event.TouchEvent.Type.KEY_UP;
import static tk.ubublik.redivansion.JmeFragment.BACK_PRESS_EVENT;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class TutorialLifecycle extends Lifecycle {

    private boolean done = false;

    public TutorialLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        simpleApplication.getInputManager().addListener(touchListener,  new String[]{BACK_PRESS_EVENT});
        addDebugPanel();
        testContent();
        addTestModel();
        CameraControl cameraControl = new CameraControl(simpleApplication.getCamera(), geometryAnimationManager, simpleApplication.getInputManager());
    }

    GeometryAnimationManager geometryAnimationManager;
    private void addTestModel(){
        //surprise: does not work on emulator... but on real device is good
        geometryAnimationManager = (GeometryAnimationManager) NodesCache.getInstance().get("simple");
        simpleApplication.getRootNode().attachChild(geometryAnimationManager);
        geometryAnimationManager.beginAnimation("build_lvl_1");
    }

    private void testContent(){
        simpleApplication.getCamera().setLocation(new Vector3f(3,3,3));
        simpleApplication.getCamera().setFrustumPerspective(60f, 1.7777f, 0.1f, 500f);
        simpleApplication.getCamera().lookAt(new Vector3f(0,0,0), simpleApplication.getCamera().getUp());
        Light allLight = new AmbientLight(ColorRGBA.DarkGray);
        simpleApplication.getRootNode().addLight(allLight);
        Light light = new DirectionalLight(simpleApplication.getCamera().getDirection());
        simpleApplication.getRootNode().addLight(light);
        attachGrid(Vector3f.ZERO, 100, ColorRGBA.Cyan);
    }

    private Geometry attachGrid(Vector3f pos, int size, ColorRGBA color){
        Geometry g = new Geometry("wireframe grid", new Grid(size, size, 0.5f) );
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(pos);
        simpleApplication.getRootNode().attachChild(g);
        return g;
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
        geometryAnimationManager.onUpdate();
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
        DebugPanel debugPanel = new DebugPanel(simpleApplication);
        debugPanel.addButton("Console log", commands);
    }

    Command<Button> commands = new Command<Button>() {
        @Override
        public void execute(Button source) {
            switch (source.getText()){
                case "Console log": System.out.println("Console log"); break;
            }
        }
    };
}
