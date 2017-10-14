package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 11-Oct-17.
 */

public class SelectToolManager {

    enum SelectMode {NONE, SINGLE, OBJECT, ROAD};

    private static final float SELECT_OPACITY = 0.5f;
    public static final ColorRGBA OK_SELECT_COLOR = new ColorRGBA(0,240/256.f, 60/256.f, SELECT_OPACITY);
    public static final ColorRGBA DEFAULT_SELECT_COLOR = new ColorRGBA(1,1,1,SELECT_OPACITY);
    public static final ColorRGBA WARNING_SELECT_COLOR = new ColorRGBA(1,1,25/256.f, SELECT_OPACITY);
    public static final ColorRGBA ERROR_SELECT_COLOR = new ColorRGBA(1,0,0,SELECT_OPACITY);

    private WorldMap worldMap;
    private MapRenderer mapRenderer;
    private Node node;
    private CameraControl cameraControl;
    private SelectTool selectTool;

    public SelectToolManager(WorldMap worldMap, MapRenderer mapRenderer, Node node, CameraControl cameraControl) {
        this.worldMap = worldMap;
        this.mapRenderer = mapRenderer;
        this.node = node;
        this.cameraControl = cameraControl;
    }

    public void setMode(SelectMode selectMode){
        //need it?
    }

    public void setSelectSinglePoint(Class<? extends WorldObject> clazz){
        destroyTool();
        selectTool = new SingleBuilder();
    }

    public void setRoadStart(Point point){
        checkRoadBuilder();
        ((RoadBuilder)selectTool).setStartPoint(point);
    }

    public void setRoadEnd(Point point){
        checkRoadBuilder();
        ((RoadBuilder)selectTool).setEndPoint(point);
    }

    public void setObjectSelect(WorldObject worldObject){
        destroyTool();
        selectTool = new SingleSelectTool(worldObject, mapRenderer, node);
    }

    private void destroyTool(){
        if (selectTool!=null) selectTool.destroy();
    }

    private void checkRoadBuilder(){
        if (!(selectTool instanceof RoadBuilder)) {
            destroyTool();
            selectTool = new RoadBuilder();
        }
    }

    public boolean canPut() {
        return selectTool != null && selectTool.canPut();
    }

    public WorldObject buildObjectInPoint(){
        if (!(selectTool instanceof SingleBuilder)) return null;
        SingleBuilder singleBuilder = (SingleBuilder) selectTool;
        WorldObject worldObject = singleBuilder.build();
        if (worldObject!=null){
            worldMap.put(worldObject);
        }
        return worldObject;
    }

    public boolean buildRoad() {
        return selectTool instanceof RoadBuilder && ((RoadBuilder) selectTool).build();
    }

    public void onUpdate(){
        if (selectTool!=null) selectTool.onUpdate();
    }
}
