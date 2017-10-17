package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 16-Oct-17.
 */

public class SelectLineGeometry {

    private List<Geometry> geometries = new LinkedList<>();
    private MapRenderer mapRenderer;
    private ColorRGBA colorRGBA;
    private Node node;
    private SelectGeometry origin;

    public SelectLineGeometry(MapRenderer mapRenderer, ColorRGBA colorRGBA, Node node){
        this.mapRenderer = mapRenderer;
        this.colorRGBA = colorRGBA;
        this.node = node;
        origin = new SelectGeometry(SelectToolManager.DEFAULT_SELECT_COLOR, Road.DEFAULT_SIZE, mapRenderer.getScale());
    }

    public void setPoints(Point start, Point end){
        switch (getAxis(start, end)){
            case NONE: throw new IllegalArgumentException("Points are not on one axis");
            case X:
                if (lastPointsChanged(start, end)) {
                    clearGeometries();
                    if (start.y > end.y) {
                        Point startCopy = start;
                        start = new Point(end);
                        end = new Point(startCopy);
                    }
                    for (int i = start.y; i <= end.y; i++) {
                        Geometry geometry = origin.clone(false);
                        geometry.setLocalTranslation(mapRenderer.mapPointToWorld(new Point(start.x, i)));
                        geometries.add(geometry);
                        node.attachChild(geometry);
                    }
                } break;
            case Y:
                if (lastPointsChanged(start, end)) {
                    clearGeometries();
                    if (start.x > end.x) {
                        Point startCopy = start;
                        start = new Point(end);
                        end = new Point(startCopy);
                    }
                    for (int i = start.x; i <= end.x; i++) {
                        Geometry geometry = origin.clone(false);
                        geometry.setLocalTranslation(mapRenderer.mapPointToWorld(new Point(i, start.y)));
                        geometries.add(geometry);
                        node.attachChild(geometry);
                    }
                } break;
        }
    }

    public void clearGeometries(){
        for (Geometry geometry: geometries){
            node.detachChild(geometry);
        }
        geometries.clear();
    }

    private Point lastStart, lastEnd;
    private boolean lastPointsChanged(Point start, Point end){
        if (start.equals(lastStart) && end.equals(lastEnd)) return false;
        lastStart = start;
        lastEnd = end;
        return true;
    }

    public void setChanged(boolean refresh){
        if (refresh){
            Point tempStart = lastStart;
            Point tempEnd = lastEnd;
            lastStart = null;
            lastEnd = null;
            if (tempStart!=null && tempEnd!=null) setPoints(tempStart, tempEnd);
        } else {
            lastStart = null;
            lastEnd = null;
        }
    }

    private boolean pointsOnLine(Point p1, Point p2){
        return p1.x==p2.x||p1.y==p2.y;
    }

    private enum Axis{NONE, X, Y}

    private Axis getAxis(Point p1, Point p2){
        if (p1.x == p2.x) return Axis.X;
        if (p1.y == p2.y) return Axis.Y;
        return Axis.NONE;
    }

    public void setColor(ColorRGBA color){
        origin.setColor(color);
    }
}
