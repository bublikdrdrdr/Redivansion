package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.R;
import tk.ubublik.redivansion.gamelogic.lifecycle.Lifecycle;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.Road;
import tk.ubublik.redivansion.gamelogic.units.objects.RoadState;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 10-Oct-17.
 */

//select two points and build road between them
public class RoadBuilderOld extends SelectTool{
    //todo: rewrite
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

    public List<Road> getRoadObjectsList(WorldMap worldMap){
        try { //kill me pls
            List<Point> points = getPoints();
            if (!canPut(worldMap, points)) throw new IllegalArgumentException("Can't put road on this line");
            ArrayList<Road> roads = new ArrayList<>(points.size());
            Point previousPoint = null;
            Iterator<Point> iterator = points.iterator();
            while (iterator.hasNext()){
                Point temp = iterator.next();
                RoadState roadState;
                if (previousPoint==null){//first point
                    if (iterator.hasNext()){//not single
                        roadState = getPointState(temp, worldMap);
                        Point nextPoint = points.get(1);
                        if (temp.x==nextPoint.x){
                            if (nextPoint.y<temp.y) roadState.setRight(true); else roadState.setLeft(true);
                        } else {
                            if (nextPoint.x<temp.x) roadState.setFront(true); else roadState.setBack(true);
                        }
                    } else {//single
                        roadState = getPointState(temp, worldMap);
                    }
                } else if (!iterator.hasNext()){ //last point
                    roadState = getPointState(temp, worldMap);
                    if (temp.x==previousPoint.x){
                        if (previousPoint.y<temp.y) roadState.setRight(true); else roadState.setLeft(true);
                    } else {
                        if (previousPoint.x<temp.x) roadState.setFront(true); else roadState.setBack(true);
                    }
                } else {//default
                    roadState = getPointState(temp, worldMap);
                    if (temp.x==previousPoint.x){
                        roadState.setFront(true);
                        roadState.setBack(true);
                    } else {
                        roadState.setLeft(true);
                        roadState.setRight(true);
                    }
                }
                roads.add(new Road(temp, roadState));
                previousPoint = temp;
            }
            clean();//
            return roads;
        } catch (IllegalArgumentException e){
            return new LinkedList<>();
        }
    }

    public boolean canPut(WorldMap worldMap){
        return canPut(worldMap, getPoints());
    }

    public boolean canPut(WorldMap worldMap, List<Point> list){
        if (!isStraightLine()) return false;
        for (Point point: list){
            if (!worldMap.isFree(point, 1)) return false;
        }
        return true;
    }

    public List<Point> getPoints(){
        if (!isStraightLine()) throw new IllegalArgumentException("Road is not straight");
        boolean byX = (startPoint.x==endPoint.x);
        int from, to;
        if (byX){
            from = startPoint.y;
            to = endPoint.y;
        } else{
            from = startPoint.x;
            to = endPoint.x;
        }
        if (from>to){
            int temp = from;
            from = to;
            to = temp;
        }
        ArrayList<Point> list = new ArrayList<>(to-from+1);
        for (int i = from; i <= to; i++){
            Point point;
            if (byX) point = new Point(startPoint.x, i);
            else point = new Point(i, startPoint.y);

            list.add(point);
        }
        return list;
    }

    private boolean isStraightLine(){
        return (startPoint.x==endPoint.x || startPoint.y==endPoint.y);
    }

    public void clean(){
        startPoint = null;
        endPoint = null;
    }

    public RoadState getPointState(Point point, WorldMap worldMap){
        RoadState roadState = new RoadState();
        if (worldMap.get(new Point(point.x+1, point.y)) instanceof Road) roadState.setFront(true);
        if (worldMap.get(new Point(point.x-1, point.y)) instanceof Road) roadState.setBack(true);
        if (worldMap.get(new Point(point.x, point.y+1)) instanceof Road) roadState.setRight(true);
        if (worldMap.get(new Point(point.x, point.y-1)) instanceof Road) roadState.setLeft(true);
        return roadState;
    }


    public boolean isStartSet(){
        return (startPoint!=null);
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
