package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.WorldLight;
import tk.ubublik.redivansion.gamelogic.gui.DebugPanel;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.Terrain;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.utils.logic.GameLogicProcessor;
import tk.ubublik.redivansion.gamelogic.utils.LevelFactory;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;
import tk.ubublik.redivansion.gamelogic.utils.game_tools.SelectToolManager;

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
    private SelectToolManager selectToolManager;

    public TestLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        loadModels();
        cameraControl = new CameraControl(simpleApplication.getCamera(), simpleApplication.getInputManager());
        Level level = LevelFactory.getLevel(0);
        worldMap = new WorldMap();
        gameLogicProcessor = new GameLogicProcessor(worldMap, level);
        mapRenderer = new MapRenderer(simpleApplication.getRootNode(), 1f, simpleApplication.getCamera());
        gui = new GUI(simpleApplication.getGuiNode());
        worldLight = new WorldLight(simpleApplication.getRootNode(), new Vector3f(-1f, -2f, 0.1f)/*simpleApplication.getCamera().getDirection()*/);
        mapRenderer.addTerrain(new Terrain(5));
        selectToolManager = new SelectToolManager(worldMap, mapRenderer, simpleApplication.getRootNode(), cameraControl);
        cameraControl.setTouchInputHook(gui);
        worldMap.addObserver(mapRenderer);
        worldMap.addObserver(gameLogicProcessor);
        worldMap.addObserver(selectToolManager);
        worldMap.put(level.getWorldObjects());

        addDebugPanel();
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
        selectToolManager.onUpdate();
    }

    private void loadModels(){
        Model simpleModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/simple.crm");
        NodesCache.getInstance().put("officeModel", simpleModel);
        Model treeModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/tree.crm");
        NodesCache.getInstance().put("treeModel", treeModel);
        Model terrainModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/terrain.crm");
        NodesCache.getInstance().put("terrainModel", terrainModel);
        Model roadModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/road.crm");
        NodesCache.getInstance().put("roadModel", roadModel);
    }

    private void addDebugPanel(){
        DebugPanel debugPanel = new DebugPanel(simpleApplication);
        debugPanel.addButton("Add building", commands);
        debugPanel.addButton("Add tree", commands);
        debugPanel.addButton("Add road", commands);
        debugPanel.addButton("Set road points", commands);
        debugPanel.addButton("Select tree", commands);
        debugPanel.addButton("Select office", commands);
        debugPanel.addButton("Clear select", commands);
    }

    Command<Button> commands = new Command<Button>() {
        @Override
        public void execute(Button source) {
            switch (source.getText()){
                case "Console log": System.out.println("Console log"); break;
                case "Add building": addBuilding(); break;
                case "Add tree": addTree(); break;
                case "Add road": addRoad(); break;
                case "Set road points": selectToolManager.setRoadSelect(); break;
                case "Select tree": selectToolManager.setSelectSinglePoint(Tree.class); break;
                case "Select office": selectToolManager.setSelectSinglePoint(Office.class); break;
                case "Clear select": selectToolManager.cancel(); break;
            }
        }
    };


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

    private void addRoad(){
        Point position = getCenterPoint(1);
        if (worldMap.isFree(position, 1)) {
            Road road = new Road(position);
            worldMap.put(road);
        }
    }

    private Point getCenterPoint(int size){
        return mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint(), size);
    }
}
