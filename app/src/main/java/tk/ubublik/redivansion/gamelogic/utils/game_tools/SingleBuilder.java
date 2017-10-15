package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 12-Oct-17.
 */

//select point on the center of screen
public class SingleBuilder extends SelectTool {

    private Class<? extends WorldObject> clazz;
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
            this.clazz = clazz;
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
}
