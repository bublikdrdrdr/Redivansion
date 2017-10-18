package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import com.jme3.scene.Node;

import java.util.Observable;
import java.util.Observer;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 15-Oct-17.
 */

public class RoadBuilder extends SelectTool implements Observer {

    public enum SelectStage {START, END, NONE};

    private Point startPoint;
    private Point endPoint;
    private SelectStage selectStage;

    private MapRenderer mapRenderer;
    private Node node;
    private CameraControl cameraControl;
    private WorldMap worldMap;

    private SelectLineGeometry selectLineGeometry;

    public RoadBuilder(MapRenderer mapRenderer, Node node, CameraControl cameraControl, WorldMap worldMap) {
        this.mapRenderer = mapRenderer;
        this.node = node;
        this.cameraControl = cameraControl;
        this.worldMap = worldMap;
        this.selectLineGeometry = new SelectLineGeometry(mapRenderer, SelectToolManager.DEFAULT_SELECT_COLOR, node);
        selectStage = SelectStage.NONE;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void destroy() {
        selectLineGeometry.clearGeometries();
    }

    private Point lastStartPoint, lastEndPoint;
    private boolean lastValue;
    @Override
    public boolean canPut() {
        if (lastStartPoint!=null && lastEndPoint!=null && lastStartPoint.equals(startPoint) && lastEndPoint.equals(endPoint)) return lastValue;
        if (startPoint==null || endPoint==null) return false;
        lastStartPoint = startPoint;
        lastEndPoint = endPoint;
        lastValue = worldMap.canPutRectangle(startPoint, endPoint);
        return lastValue;
    }

    @Override
    public void onUpdate() {
        if (selectStage==SelectStage.NONE) return;
        Point current = mapRenderer.worldPointToMap(cameraControl.getCameraCenterPoint());
        if (selectStage == SelectStage.START) {
            startPoint = current;
            endPoint = startPoint;
        } else {
            if (Math.abs(startPoint.x-current.x)>Math.abs(startPoint.y-current.y))
                current.y = startPoint.y;
            else
                current.x = startPoint.x;

            endPoint = current;
        }
        selectLineGeometry.setPoints(startPoint, endPoint);
        if (canPut()) selectLineGeometry.setColor(SelectToolManager.OK_SELECT_COLOR);
        else selectLineGeometry.setColor(SelectToolManager.ERROR_SELECT_COLOR);
    }

    public void setSelectStage(SelectStage selectStage) {
        switch (selectStage){
            case NONE:
                this.startPoint = null;
                this.endPoint = null;
                break;
            case START:
                this.startPoint = null;
                this.endPoint = null;
                break;
            case END:
                if (startPoint==null) throw new IllegalArgumentException("Can't switch to END point mode, start point is null");
                this.endPoint = null;
        }
        this.selectStage = selectStage;
    }

    public boolean build(){
        return false;
    }

    public boolean build(Point start, Point end){
        return false;
    }


    ////////////////////////////

    public boolean isStartSet(){
        return startPoint!=null;
    }

    public boolean isEndSet(){
        return endPoint!=null;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public SelectStage getSelectStage() {
        return selectStage;
    }
}
