package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.WorldLight;
import tk.ubublik.redivansion.gamelogic.gui.AllFrames;
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
import tk.ubublik.redivansion.gamelogic.utils.LevelFactory;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;
import tk.ubublik.redivansion.gamelogic.utils.game_tools.SelectToolManager;
import tk.ubublik.redivansion.gamelogic.utils.logic.GameLogicProcessor;

import static tk.ubublik.redivansion.gamelogic.gui.TouchEvents.guiListener;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class LevelLifecycle extends Lifecycle {

    private int levelNumber;
    private CameraControl cameraControl;
    private GameLogicProcessor gameLogicProcessor;
    private MapRenderer mapRenderer;
    public WorldMap worldMap;
    private GUI gui;
    private WorldLight worldLight;
    private SelectToolManager selectToolManager;
    private Settings settings;
    private volatile boolean done = false;

    public LevelLifecycle(int levelNumber, SimpleApplication simpleApplication){
        super(simpleApplication);
        this.levelNumber = levelNumber;
        done = false;
        Level level = LevelFactory.getLevel(levelNumber);// TODO: getLevel(levelNumber);
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
        AllFrames.levelEndShowed = false;
        worldLight = new WorldLight(simpleApplication.getRootNode(), new Vector3f(-1f, -2f, 0.1f)/*simpleApplication.getCamera().getDirection()*/);
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
        mapRenderer.onUpdate();
        worldLight.onUpdate();
        worldMap.onUpdate();
        gui.onUpdate();
        gameLogicProcessor.onUpdate();
        cameraControl.onUpdate();
        selectToolManager.onUpdate();
        NodesCache.getInstance().updateModels();
    }

    private void testSetIcon() {
        WorldObject worldObject = worldMap.get(getCenterPoint(1));
        if (worldObject!=null) worldObject.setIconState(WorldObject.IconState.WARNING);
    }

    private Point getCenterPoint(int size){
        return mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint(), size);
    }

    private GameLogicProcessor.LogicResultListener logicResultListener = new GameLogicProcessor.LogicResultListener() {
        @Override
        public void setStatusChanged(int population, int money, boolean grow) {
            long time = gameLogicProcessor.timeLeft()/1000;
            gui.setTime(time);
            gui.setStatusChanged(population, money, grow);
        }

        @Override
        public void setGameEnd(boolean win) {
            if(!AllFrames.levelEndShowed) //without this statement creates and shows multiply frames at once and it looks ugly
                gui.guiScreen.showFrame(AllFrames.initLevelComplete(win));
            if(win && (levelNumber == settings.getProgress())) {
                    settings.setProgress(settings.getProgress() + 1);
                    settings.save();
            }
        }
    };

    private GUIListener guiListener = new GUIListener() {
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
        public WorldMap getWorldMap() {
            return worldMap;
        }

        @Override
        public MapRenderer getMapRenderer() {
            return mapRenderer;
        }

        @Override
        public GUI getGui() {
            return gui;
        }

        @Override
        public void pauseTime(boolean value) {
            gameLogicProcessor.setPaused(value);
        }

        @Override
        public void changeTimeSpeed() {
            if(gameLogicProcessor.getTimer().getGameSpeed() == 1)
                gameLogicProcessor.setGameSpeed(2);
            else gameLogicProcessor.setGameSpeed(1);
        }

        @Override
        public void setDone(boolean done) {
            LevelLifecycle.this.setDone(done);
        }

        @Override
        public void cameraTutorial(TutorialLifecycle.CameraTutorial camTut) {

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
