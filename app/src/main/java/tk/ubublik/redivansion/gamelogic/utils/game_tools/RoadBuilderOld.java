package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.RoadState;

/**
 * Created by Bublik on 10-Oct-17.
 */

public class RoadBuilderOld extends SelectTool{
    private Point startPoint;
    private Point endPoint;

    public RoadBuilderOld() {
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public boolean canPut(WorldMap worldMap, List<Point> list){
        if (!isStraightLine()) return false;
        for (Point point: list){
            if (!worldMap.isFree(point, 1)) return false;
        }
        return true;
    }

    private boolean isStraightLine(){
        return (startPoint.x==endPoint.x || startPoint.y==endPoint.y);
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean canPut() {
        return false;
    }

    public boolean build(){
        return false;
    }
}
