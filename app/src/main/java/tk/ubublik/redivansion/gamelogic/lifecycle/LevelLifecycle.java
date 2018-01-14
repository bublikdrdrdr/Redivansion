package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.graphics.Point;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;

import java.util.Iterator;

import tk.ubublik.redivansion.gamelogic.Main;
import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.graphics.WorldLight;
import tk.ubublik.redivansion.gamelogic.gui.AllFrames;
import tk.ubublik.redivansion.gamelogic.gui.Element;
import tk.ubublik.redivansion.gamelogic.gui.GUI;
import tk.ubublik.redivansion.gamelogic.test.FpsMeter;
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
        gui.setTime(GameParams.LEVELS_TIMES[levelNumber]/1000);
        gui.setStatusChanged(0, GameParams.LEVELS_MONEY[levelNumber], true);
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
            if(population < 0 || money < 0){
                if(population < 0)
                    population = 0;
                setGameEnd(false);
            }
            gui.setStatusChanged(population, money, grow);
        }

        @Override
        public void setGameEnd(boolean win) {
            for(Element element:gui.guiScreen.getActiveFrame().elements)
                if(element.p.getName().equals("timeSpeedBg")){
                    element.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn1.png", false);
                    break;
                }
            gameLogicProcessor.setGameSpeed(1);
            boolean additionalGoalCompleted = checkAdditionalGoals();
            if(!additionalGoalCompleted) win = false;
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
            if(gameLogicProcessor.getTimer().getGameSpeed() == 1) {
                for(Element element:getGui().guiScreen.getActiveFrame().elements)
                    if(element.p.getName().equals("timeSpeedBg")){
                        element.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn2.png", false);
                        break;
                    }
                gameLogicProcessor.setGameSpeed(5);
            }
            else{
                for(Element element:getGui().guiScreen.getActiveFrame().elements)
                if(element.p.getName().equals("timeSpeedBg")){
                    element.p.setImage(StaticAssetManager.getAssetManager(), "Textures/btn1.png", false);
                    break;
                }
                gameLogicProcessor.setGameSpeed(1);
            }
        }

        @Override
        public void setDone(boolean done) {
            LevelLifecycle.this.setDone(done);
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

    private boolean checkAdditionalGoals(){
        switch (levelNumber){
            case 0: //2 power plants
                int i = 0;
                for(WorldObject object:worldMap.getWorldObjects()){
                    if(object instanceof ThermalPowerPlant)
                        i++;
                }
                if(i >= 1)
                    return true;
                break;
            case 1: //fire and police stations
                boolean police = false, fire = false;
                for(WorldObject object:worldMap.getWorldObjects()){
                    if(object instanceof PoliceStation)
                        police = true;
                    else if(object instanceof FireStation)
                        fire = true;
                }
                if(police && fire)
                    return true;
                break;
            case 2: // like 2 but with two hospitals too
                police = false; fire = false;
                i = 0;
                for(WorldObject object:worldMap.getWorldObjects()){
                    if(object instanceof PoliceStation)
                        police = true;
                    else if(object instanceof FireStation)
                        fire = true;
                    else if(object instanceof Hospital)
                        i++;
                }
                if(police && fire && i >= 2)
                    return true;
                break;
            case 3: //five shops
                i = 0;
                for(WorldObject object:worldMap.getWorldObjects()){
                    if(object instanceof ShoppingMall)
                        i++;
                }
                if(i >= 5)
                    return true;
                break;
            case 4: // three schools
                i = 0;
                for(WorldObject object:worldMap.getWorldObjects()){
                    if(object instanceof School)
                        i++;
                }
                if(i >= 3)
                    return true;
                break;
        }
        return false;
    }
}
