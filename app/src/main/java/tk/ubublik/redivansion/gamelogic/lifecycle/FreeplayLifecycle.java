package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.WorldLight;
import tk.ubublik.redivansion.gamelogic.gui.AllFrames;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.SavedLevel;
import tk.ubublik.redivansion.gamelogic.units.Settings;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.FireStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Hospital;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.PoliceStation;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.School;
import tk.ubublik.redivansion.gamelogic.units.objects.ShoppingMall;
import tk.ubublik.redivansion.gamelogic.units.objects.ThermalPowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WaterPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.GUIListener;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.LevelFactory;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.game_tools.SelectToolManager;
import tk.ubublik.redivansion.gamelogic.utils.logic.GameLogicProcessor;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class FreeplayLifecycle extends Lifecycle {

    private CameraControl cameraControl;
    private static GameLogicProcessor gameLogicProcessor;
    public MapRenderer mapRenderer;
    public WorldMap worldMap;
    private GUI gui;
    private WorldLight worldLight;
    private SelectToolManager selectToolManager;
    private Settings settings;
    private volatile boolean done = false;

    public FreeplayLifecycle(SimpleApplication simpleApplication){
        super(simpleApplication);
        done = false;
        Level level = LevelFactory.getLevel(0);
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
        gui.setTime(-666);
        gui.setStatusChanged(0, GameParams.LEVELS_MONEY[0], true);
        Main.registerBackPressListener(gui.touchListener, simpleApplication.getInputManager());
        worldLight = new WorldLight(simpleApplication.getRootNode(), new Vector3f(-1f, -2f, 0.1f));
        selectToolManager = new SelectToolManager(worldMap, mapRenderer, simpleApplication.getRootNode(), cameraControl);
        cameraControl.setTouchInputHook(gui);
        worldMap.addObserver(mapRenderer);
        worldMap.addObserver(gameLogicProcessor);
        worldMap.addObserver(selectToolManager);
        worldMap.put(level.getWorldObjects());
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
        return LifecycleType.FREEPLAY;
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
        mapRenderer.onUpdate();
        worldLight.onUpdate();
        worldMap.onUpdate();
        gui.onUpdate();
        gameLogicProcessor.onUpdate();
        cameraControl.onUpdate();
        selectToolManager.onUpdate();
        NodesCache.getInstance().updateModels();
    }

    private Point getCenterPoint(int size){
        return mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint(), size);
    }

    GameLogicProcessor.LogicResultListener logicResultListener = new GameLogicProcessor.LogicResultListener() {
        @Override
        public void setStatusChanged(int population, int money, boolean grow) {
            gui.setTime(-666);
            if(population <= 0)
                population = 0;
            gui.setStatusChanged(population, money, grow);
        }

        @Override
        public void setGameEnd(boolean win) {
            //You can't lose here.
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
        public GUI getGui() {
            return gui;
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
        public void changeTimeSpeed() {

        }

        @Override
        public void setDone(boolean done) {
            FreeplayLifecycle.this.setDone(done);
        }

        @Override
        public void cameraTutorial(TutorialLifecycle.CameraTutorial camTut) {

        }

        @Override
        public boolean addBuilding() {
            Point position = getCenterPoint(2);
            Office office = new Office(position);
            return worldMap.put(office);
        }

        @Override
        public boolean addTree(){
            Tree tree = new Tree(getCenterPoint(1));
            return worldMap.put(tree);
        }

        @Override
        public boolean addHouse() {
            return worldMap.put(new House(getCenterPoint(2)));
        }

        @Override
        public boolean addPower() {
            return worldMap.put(new ThermalPowerPlant(getCenterPoint(3)));
        }

        @Override
        public boolean addPolice() {
            return worldMap.put(new PoliceStation(getCenterPoint(2)));
        }

        @Override
        public boolean addFire() {
            return worldMap.put(new FireStation(getCenterPoint(2)));
        }

        @Override
        public boolean addWater() {
            return worldMap.put(new WaterPlant(getCenterPoint(2)));
        }

        @Override
        public boolean addHospital() {
            return worldMap.put(new Hospital(getCenterPoint(3)));
        }

        @Override
        public boolean addSchool() {
            return worldMap.put(new School(getCenterPoint(2)));
        }

        @Override
        public boolean addShop() {
            return worldMap.put(new ShoppingMall(getCenterPoint(2)));
        }


        @Override
        public boolean addRoad(){
            if (selectToolManager.buildRoad()){
                selectToolManager.cancel();
                return true;
            }
            return false;
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
