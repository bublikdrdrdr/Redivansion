package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import java.util.Observable;
import java.util.Observer;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 11-Oct-17.
 */

public class SelectToolManager implements Observer{

    private static final float SELECT_OPACITY = 0.5f;
    public static final ColorRGBA OK_SELECT_COLOR = new ColorRGBA(0,240/256.f, 60/256.f, SELECT_OPACITY);
    public static final ColorRGBA DEFAULT_SELECT_COLOR = new ColorRGBA(1,1,1,SELECT_OPACITY);
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

    public void setSelectSinglePoint(Class<? extends WorldObject> clazz){
        destroyTool();
        selectTool = new SingleBuilder(clazz, mapRenderer, node, cameraControl, worldMap);
    }

    public void setRoadSelect(){
        checkRoadBuilder();
        RoadBuilder rb = (RoadBuilder)selectTool;
        switch (rb.getSelectStage()){
            case NONE: rb.setSelectStage(RoadBuilder.SelectStage.START); break;
            case START: rb.setSelectStage(RoadBuilder.SelectStage.END); break;
        }
    }

    public void cancel(){
        destroyTool();
        selectTool = null;
    }

    private void destroyTool(){
        if (selectTool!=null) selectTool.destroy();
    }

    private void checkRoadBuilder(){
        if (!(selectTool instanceof RoadBuilder)) {
            destroyTool();
            selectTool = new RoadBuilder(mapRenderer, node, cameraControl, worldMap);
        }
    }

    public boolean buildRoad() {
        return selectTool instanceof RoadBuilder && ((RoadBuilder) selectTool).build();
    }

    public void onUpdate(){
        if (selectTool!=null) selectTool.onUpdate();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (selectTool instanceof Observer){
            ((Observer)selectTool).update(o, arg);
        }
    }
}
