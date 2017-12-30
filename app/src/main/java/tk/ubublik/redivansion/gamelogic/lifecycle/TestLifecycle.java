package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Label;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.WorldLight;
import tk.ubublik.redivansion.gamelogic.gui.AllFrames;
import tk.ubublik.redivansion.gamelogic.gui.DebugPanel;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.test.FpsMeter;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.SavedLevel;
import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.ThermalPowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
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
    private static GameLogicProcessor gameLogicProcessor;
    public MapRenderer mapRenderer;
    public WorldMap worldMap;
    private GUI gui;
    private WorldLight worldLight;
    private SelectToolManager selectToolManager;
    private Settings settings;
    private FpsMeter fpsMeter = FpsMeter.getInstance();
    private boolean done = false;
    Level level;
    private WorldObject selectedObject = null;

    public TestLifecycle(SimpleApplication simpleApplication) {
        super(simpleApplication);
        done = false;
        loadModels();
        level = LevelFactory.getLevel(0);
        settings = Settings.getInstance();
        settings.open(true);
        loadLevel(level);
        cameraControl = new CameraControl(simpleApplication.getCamera(), simpleApplication.getInputManager());
        cameraControl.setAreaLimitRound(level.isLimitTypeRound());
        cameraControl.setAreaLimit(level.getMapLimit());
        worldMap = new WorldMap();
        gameLogicProcessor = new GameLogicProcessor(worldMap, level, logicResultListener);
        mapRenderer = new MapRenderer(simpleApplication.getRootNode(), 1f, simpleApplication.getCamera());
        gui = new GUI(simpleApplication.getGuiNode(), guiListener, cameraControl, AllFrames.main);
        Main.registerBackPressListener(gui.touchListener, simpleApplication.getInputManager());
        worldLight = new WorldLight(simpleApplication.getRootNode(), new Vector3f(-1f, -2f, 0.1f)/*simpleApplication.getCamera().getDirection()*/);
        selectToolManager = new SelectToolManager(worldMap, mapRenderer, simpleApplication.getRootNode(), cameraControl);
        cameraControl.setTouchInputHook(gui);
        worldMap.addObserver(mapRenderer);
        worldMap.addObserver(gameLogicProcessor);
        worldMap.addObserver(selectToolManager);
        worldMap.put(level.getWorldObjects());
        //addDebugPanel();
    }

    private void loadLevel(Level level) {
        SavedLevel savedLevel = settings.getSavedLevel();
        if (savedLevel != null && level.getId() == savedLevel.level) {
            level.setWorldObjects(savedLevel.worldObjects);
            level.setTime(savedLevel.time);
            level.setMoney(savedLevel.money);
        }
    }

    private void saveLevel(){
        settings.setSavedLevel(gameLogicProcessor.getSavedLevel());
        settings.save();
    }

    private void removeLevel(){
        settings.setSavedLevel(null);
        settings.save();
    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.TEST_LIFECYCLE;
    }

    public void setDone(boolean value){
        done = value;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {
        fpsMeter.beginCustom();
        mapRenderer.onUpdate();fpsMeter.logCustom("MAP RENDERER");
        worldLight.onUpdate();fpsMeter.logCustom("LIGHT");
        worldMap.onUpdate();fpsMeter.logCustom("MAP");
        gui.onUpdate();fpsMeter.logCustom("GUI");
        gameLogicProcessor.onUpdate();fpsMeter.logCustom("LOGIC");
        cameraControl.onUpdate();fpsMeter.logCustom("CAMERA");
        selectToolManager.onUpdate();fpsMeter.logCustom("SELECT");
        NodesCache.getInstance().updateModels();
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
        Model houseModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/house.crm");
        NodesCache.getInstance().put("houseModel", houseModel);
        Model powerPlantModel = (Model) StaticAssetManager.getAssetManager().loadAsset("Models/powerplant.crm");
        NodesCache.getInstance().put("powerPlantModel", powerPlantModel);
    }

    private void addDebugPanel(){
        DebugPanel debugPanel = new DebugPanel(simpleApplication);
        debugPanel.addButton("Add building", commands);
        debugPanel.addButton("Add tree", commands);
        debugPanel.addButton("Add road", commands);
        debugPanel.addButton("Set road points", commands);
        debugPanel.addButton("Select tree", commands);
        debugPanel.addButton("Select office", commands);
        debugPanel.addButton("Select power", commands);
        debugPanel.addButton("Clear select", commands);
        debugPanel.addButton("Remove", commands);
        //debugPanel.addButton("Set icon", commands);
        debugPanel.addButton("Add house", commands);
        //debugPanel.addButton("Add power", commands);
        //debugPanel.addButton("Upgrade", commands);
        //debugPanel.addButton("Game speed", commands);
        debugPanel.addButton("Save game", commands);
        debugPanel.addButton("Remove game", commands);
    }

    Command<Button> commands = new Command<Button>() {
        @Override
        public void execute(Button source) {
            switch (source.getText()){
                case "Console log": System.out.println("Console log"); break;
                case "Add building": guiListener.addBuilding(); break;
                case "Add tree": guiListener.addTree(); break;
                case "Add road": guiListener.addRoad(); break;
                case "Set road points": selectToolManager.setRoadSelect(); break;
                case "Select tree": selectToolManager.setSelectSinglePoint(Tree.class); break;
                case "Select office": selectToolManager.setSelectSinglePoint(Office.class); break;
                case "Select power": selectToolManager.setSelectSinglePoint(ThermalPowerPlant.class); break;
                case "Clear select": selectToolManager.cancel(); break;
                case "Remove": guiListener.remove(); break;
                case "Set icon": testSetIcon(); break;
                case "Add house": worldMap.put(new House(getCenterPoint(2))); break;
                case "Add power": worldMap.put(new ThermalPowerPlant(getCenterPoint(3))); break;
                case "Upgrade": guiListener.upgrade(); break;
                case "Game speed": gameLogicProcessor.getTimer().setGameSpeed(gameLogicProcessor.getTimer().getGameSpeed()>1?1:4); break;
                case "Save game": saveLevel(); break;
                case "Remove game": removeLevel(); break;
            }
        }
    };

    private void testSetIcon() {
        WorldObject worldObject = worldMap.get(getCenterPoint(1));
        if (worldObject!=null) worldObject.setIconState(WorldObject.IconState.WARNING);
    }

    public void selectedObject(WorldObject object){
        selectedObject = object;
    }

    private Point getCenterPoint(int size){
        return mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint(), size);
    }

    GameLogicProcessor.LogicResultListener logicResultListener = new GameLogicProcessor.LogicResultListener() {
        @Override
        public void setStatusChanged(int population, int money, boolean grow) {
            // TODO: 11-Nov-17 update gui
            long time = gameLogicProcessor.timeLeft()/1000;
            gui.setTime(time);
            gui.setStatusChanged(population, money, grow);
            if(time <= 0){
                gameLogicProcessor.setPaused(true);
                setGameEnd(level.getLevelGoal().isDone());
            }
        }

        @Override
        public void setGameEnd(boolean win) {
            if(win){
                if(level.getId() == settings.getProgress()){
                    settings.setProgress(settings.getProgress()+1);
                    settings.save();
                }
                AllFrames.initLevelComplete(true);
                gui.guiScreen.showFrame(AllFrames.levelComplete);
            }
            else{
                AllFrames.initLevelComplete(false);
                gui.guiScreen.showFrame(AllFrames.levelComplete);
            }
        }
    };

    GUIListener guiListener = new GUIListener() {
        @Override
        public void remove() {
            WorldObject worldObject = worldMap.fastRemove(getCenterPoint(1));
            if (worldObject instanceof Road){
                Road road = (Road)worldObject;
                Road.updateRoadStates(road.getPosition(), road.getPosition(), worldMap.getNearbyRoads(road.getPosition(), road.getPosition()));
            }
        }

        @Override
        public void save() {
            saveLevel();
        }

        @Override
        public void removeSave() {
            removeLevel();
        }

        @Override
        public void upgrade() {
            if (selectedObject!=null){
                if (selectedObject.getLevelNumber()<selectedObject.getLevelsCount()-1){
                    selectedObject.setLevelNumber(selectedObject.getLevelNumber()+1);
                }
            }
        }

        @Override
        public void objectSelected(WorldObject object) {
            selectedObject = object;
        }

        @Override
        public WorldMap getWorldMap() {
            return worldMap;
        }

        @Override
        public MapRenderer getMapRenderer() {
            return mapRenderer;
        }

        @Override
        public void pauseTime(boolean value) {
            gameLogicProcessor.setPaused(value);
        }

        @Override
        public void setDone(boolean done) {
            TestLifecycle.this.setDone(done);
        }

        @Override
        public void addBuilding() {
            Point position = getCenterPoint(2);
            Office office = new Office(position);
            if (worldMap.put(office))
                System.out.println("Object created at " + office.getPosition());
        }

        @Override
        public void addTree(){
            Tree tree = new Tree(getCenterPoint(1));
            worldMap.put(tree);
        }

        @Override
        public void addHouse() {
            worldMap.put(new House(getCenterPoint(2)));
        }

        @Override
        public void addPower() {
            worldMap.put(new ThermalPowerPlant(getCenterPoint(3)));
        }

        @Override
        public void addRoad(){
            if (selectToolManager.buildRoad()){
                selectToolManager.cancel();
            }
        }

        @Override
        public void setRoadPoints() {
            selectToolManager.setRoadSelect();
        }

        @Override
        public void selectTree() {
            selectToolManager.setSelectSinglePoint(Tree.class);
        }

        @Override
        public void selectPower() {
            selectToolManager.setSelectSinglePoint(ThermalPowerPlant.class);
        }

        @Override
        public void selectOffice() {
            selectToolManager.setSelectSinglePoint(Office.class);
        }

        @Override
        public void selectClear() {
            selectToolManager.cancel();
        }
    };
}
