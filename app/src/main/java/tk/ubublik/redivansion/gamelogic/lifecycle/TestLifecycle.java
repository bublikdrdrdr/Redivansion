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
import com.simsilica.lemur.Panel;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.WorldLight;
import tk.ubublik.redivansion.gamelogic.gui.DebugPanel;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.utils.GameLogicProcessor;
import tk.ubublik.redivansion.gamelogic.utils.LevelFactory;
import tk.ubublik.redivansion.gamelogic.utils.MapManager;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 22-Sep-17.
 */

public class TestLifecycle extends Lifecycle {

    private CameraControl cameraControl;
    private GameLogicProcessor gameLogicProcessor;
    private MapRenderer mapRenderer;
    private WorldMap worldMap;
    private GUI gui;
    private WorldLight worldLight;

    public TestLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        loadModels();
        cameraControl = new CameraControl(simpleApplication.getCamera(), simpleApplication.getInputManager());
        Level level = LevelFactory.getLevel("test");
        gameLogicProcessor = new GameLogicProcessor();
        worldMap = new WorldMap(level.getWorldObjects());
        mapRenderer = new MapRenderer(simpleApplication.getRootNode(), 1f, simpleApplication.getCamera());
        gui = new GUI(simpleApplication.getGuiNode());
        worldLight = new WorldLight(simpleApplication.getRootNode(), new Vector3f(-1f, -2f, 0.1f)/*simpleApplication.getCamera().getDirection()*/);

        worldMap.addObserver(mapRenderer);
        worldMap.addObserver(gameLogicProcessor);

        addDebugPanel();
        addGrid();
        addCenterPoint();
        /*addGrid();
        addCamera();
        addLight();
        addDebugPanel();
        addCenterPoint();
        generateStuff();*/
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
        mapRenderer.onUpdate();
        worldLight.onUpdate();
        worldMap.onUpdate();
        gui.onUpdate();
        gameLogicProcessor.onUpdate();
        cameraControl.onUpdate();
    }

    private void loadModels(){
        Model simpleModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/simple.crm");
        NodesCache.getInstance().put("officeModel", simpleModel);
        Model treeModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/tree.crm");
        NodesCache.getInstance().put("treeModel", treeModel);
    }

    private Geometry addGrid(){
        int size = 10;
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

    private void addDebugPanel(){
        DebugPanel debugPanel = new DebugPanel(simpleApplication);
        debugPanel.addButton("Console log", commands);
        debugPanel.addButton("Add building", commands);
        debugPanel.addButton("Show select", commands);
        debugPanel.addButton("Add tree", commands);
        debugPanel.addButton("Change FoV", commands);
        debugPanel.addButton("Big select", commands);
    }

    Command<Button> commands = new Command<Button>() {
        @Override
        public void execute(Button source) {
            switch (source.getText()){
                case "Console log": System.out.println("Console log"); break;
                case "Add building": addBuilding(); break;
                case "Show select": mapRenderer.setSelectMode(!(mapRenderer.isSelectMode()&&mapRenderer.getSelectModeSize()==1), 1); break;
                case "Add tree": addTree(); break;
                case "Change FoV": changeFoV(); break;
                case "Big select": mapRenderer.setSelectMode(!(mapRenderer.isSelectMode()&&mapRenderer.getSelectModeSize()==2), 2); break;
            }
        }
    };

    private boolean wideFoV = false;
    private void changeFoV(){
        cameraControl.setFoV(wideFoV?30f:10f);
        wideFoV = !wideFoV;
    }

    private void addBuilding() {
        Point position = getCenterPoint(2);
        Office office = new Office(position);
        if (worldMap.put(office))
            System.out.println("Object created at " + office.getPosition());
    }

    private void addTree(){
        Tree tree = new Tree(getCenterPoint(1));
        worldMap.put(tree);
    }

    private void addCenterPoint(){
        final float pointSize = simpleApplication.getCamera().getWidth()/100;
        Panel panel = new Panel(pointSize, pointSize, ColorRGBA.Red);
        panel.setLocalTranslation(new Vector3f(simpleApplication.getCamera().getWidth()/2, simpleApplication.getCamera().getHeight()/2, 0));
        simpleApplication.getGuiNode().attachChild(panel);
    }

    private void generateStuff(){
        for (int i = -2; i < 2; i++)
            for (int j = -2; j < 2; j++){
                Tree tree = new Tree(new Point(i,j));
                worldMap.put(tree);
            }
    }

    private Point getCenterPoint(int size){
        return mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint(), size);
    }
}
