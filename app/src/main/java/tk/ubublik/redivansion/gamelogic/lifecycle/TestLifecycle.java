package tk.ubublik.redivansion.gamelogic.lifecycle;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Grid;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 22-Sep-17.
 */

public class TestLifecycle extends Lifecycle {

    private CameraControl cameraControl;

    private WorldObject worldObject;

    public TestLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        loadSimpleModel();
        addGrid();
        addCamera();
        addLight();

        worldObject = createWorldObject();
        showObject(worldObject);
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
        return new Office();
    }

    private void showObject(WorldObject worldObject){
        simpleApplication.getRootNode().attachChild(worldObject);
    }
}
