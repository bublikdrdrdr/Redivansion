package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Grid;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.gui.DebugPanel;
import tk.ubublik.redivansion.gamelogic.test.CameraDebugger;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapManager;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 22-Sep-17.
 */

public class TestLifecycle extends Lifecycle {

    private CameraControl cameraControl;

    private WorldObject worldObject;
    private MapManager mapManager;

    public TestLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        loadSimpleModel();
        addGrid();
        addCamera();
        addLight();
        addDebugPanel();
        addCenterPoint();

        mapManager = new MapManager(simpleApplication.getRootNode(), cameraControl);

        worldObject = createWorldObject();
        mapManager.putObject(worldObject);
        //showObject(worldObject);
    }

    @Override
    public LifecycleType getType() {
        return null;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {
        if (worldObject!=null){
            worldObject.onUpdate();
        }
        mapManager.onUpdate();
    }

    private void loadSimpleModel(){
        Model simpleModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/simple.crm");
        GeometryAnimationManager geometryAnimationManager = new GeometryAnimationManager("office", simpleModel);
        NodesCache.getInstance().put("office", geometryAnimationManager);
    }

    private Geometry addGrid(){
        int size = 50;
        ColorRGBA color = ColorRGBA.Magenta;
        Geometry g = new Geometry("wireframe grid", new Grid(size, size, 0.5f));
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(Vector3f.ZERO);
        simpleApplication.getRootNode().attachChild(g);
        return g;
    }

    private void addCamera(){
        simpleApplication.getCamera().setLocation(new Vector3f(20,40,20));
        simpleApplication.getCamera().setFrustumPerspective(5f, 1.7777f, 0.1f, 500f);
        simpleApplication.getCamera().lookAt(new Vector3f(0,0,0), simpleApplication.getCamera().getUp());
        cameraControl = new CameraControl(simpleApplication.getCamera(), simpleApplication.getInputManager());
    }

    private void addLight(){
        Light allLight = new AmbientLight(ColorRGBA.DarkGray);
        simpleApplication.getRootNode().addLight(allLight);
        Light light = new DirectionalLight(simpleApplication.getCamera().getDirection());
        simpleApplication.getRootNode().addLight(light);
    }

    private WorldObject createWorldObject(){
        return new Office(new Point(0,0));
    }

    private void showObject(WorldObject worldObject){
        simpleApplication.getRootNode().attachChild(worldObject);
    }

    private void addDebugPanel(){
        DebugPanel debugPanel = new DebugPanel(simpleApplication);
        debugPanel.addButton("Console log", commands);
        debugPanel.addButton("Add building", commands);
        debugPanel.addButton("Show select", commands);
    }

    Command<Button> commands = new Command<Button>() {
        @Override
        public void execute(Button source) {
            switch (source.getText()){
                case "Console log": System.out.println("Console log"); break;
                case "Add building": addBuilding(); break;
                case "Show select": mapManager.setSelectMode(!mapManager.isSelectMode()); break;
            }
        }
    };

    private void addBuilding(){

    }

    private void addCenterPoint(){
        final float pointSize = simpleApplication.getCamera().getWidth()/100;
        Panel panel = new Panel(pointSize, pointSize, ColorRGBA.Red);
        panel.setLocalTranslation(new Vector3f(simpleApplication.getCamera().getWidth()/2, simpleApplication.getCamera().getHeight()/2, 0));
        simpleApplication.getGuiNode().attachChild(panel);
    }
}
