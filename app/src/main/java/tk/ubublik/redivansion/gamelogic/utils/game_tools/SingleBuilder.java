package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import java.util.Observable;
import java.util.Observer;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 12-Oct-17.
 */

public class SingleBuilder extends SelectTool implements Observer{

    private int size;
    private MapRenderer mapRenderer;
    private Node node;
    private SelectGeometry selectGeometry;
    private Point currentPosition;
    private CameraControl cameraControl;
    private WorldMap worldMap;

    public SingleBuilder(Class<? extends WorldObject> clazz, MapRenderer mapRenderer, Node node, CameraControl cameraControl, WorldMap worldMap){
        try {
            WorldObject worldObject = clazz.newInstance();
            this.size = worldObject.getSize();
        } catch (Exception e){
            throw new RuntimeException("Can't create "+SingleBuilder.class.getSimpleName()+" with "+clazz.getCanonicalName(), e);
        }
        this.mapRenderer = mapRenderer;
        this.node = node;
        this.cameraControl = cameraControl;
        this.worldMap = worldMap;

        selectGeometry = new SelectGeometry(SelectToolManager.DEFAULT_SELECT_COLOR, size, mapRenderer.getScale());
        node.attachChild(selectGeometry);
    }

    @Override
    public void destroy() {
        if (selectGeometry!=null) {
            node.detachChild(selectGeometry);
            selectGeometry = null;
        }
    }

    private Point lastPosition;
    private boolean lastValue;
    @Override
    public boolean canPut() {
        if (lastPosition!=null && lastPosition.equals(currentPosition)) return lastValue;
        if (currentPosition==null) return false;
        lastPosition = currentPosition;
        lastValue = worldMap.canPut(currentPosition, size);
        return lastValue;
    }

    private void clearLast(){
        lastPosition = null;
    }

    public WorldObject build(){
        return null;
    }

    @Override
    public void onUpdate() {
        currentPosition = mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint(), size);
        if (selectGeometry!=null){
            selectGeometry.setLocalTranslation(mapRenderer.mapPointToWorld(currentPosition, size));
            updateSelectGeometryPutState();
        }
    }

    private void updateSelectGeometryPutState(){
        if (canPut()){
            updateSelectGeometryColor(SelectToolManager.OK_SELECT_COLOR);
        } else{
            updateSelectGeometryColor(SelectToolManager.ERROR_SELECT_COLOR);
        }
    }

    private void updateSelectGeometryColor(ColorRGBA colorRGBA){
        if (!selectGeometry.getColor().equals(colorRGBA)) selectGeometry.setColor(colorRGBA);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction) {
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            if (worldMapAction.getAction() == WorldMapAction.Action.ADD || worldMapAction.getAction() == WorldMapAction.Action.REMOVE){
                clearLast();
            }
        }
    }
}
