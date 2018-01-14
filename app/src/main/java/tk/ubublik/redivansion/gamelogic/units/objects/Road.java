package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;

import java.util.Arrays;
import java.util.List;

import tk.ubublik.redivansion.R;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.ByteSettings;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

import static tk.ubublik.redivansion.gamelogic.units.objects.RoadState.ROAD_STATE_SIZE;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Road extends WorldObject {

    public static final int DEFAULT_SIZE = 1;
    private static final String MANAGER_NAME = "roadGeometryManager:";
    private RoadState roadState;

    @Override
    public void recalculateParams() {

    }

    @Override
    public int getLevelsCount() {
        return 0;
    }

    @Override
    public void setLevelNumber(int level) {

    }

    @Override
    public int getLevelNumber() {
        return 0;
    }

    @Override
    public int getMoneyDelta() {
        return 0;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    public Road(Point position){
        this(position, new RoadState(false, false, false, false));
    }

    public Road(Point position, final RoadState roadState) {
        setRoadState(roadState);
        setPosition(position);
        setSize(DEFAULT_SIZE);
        setNeedsRoad(true);
        setBuildCost(GameParams.ROAD_BUILD_COST);
    }

    @Override
    public byte[] toBytes() {
        byte[] superBytes = super.toBytes();
        byte[] bytes = new byte[superBytes.length+ROAD_STATE_SIZE];
        ByteSettings.ByteConverter.insertArray(bytes, superBytes, 0);
        byte[] state = roadState.toBytes();
        ByteSettings.ByteConverter.insertArray(bytes, state, superBytes.length);
        System.out.println("AAA"+state[0]);
        return bytes;
    }

    @Override
    public int parseBytes(byte[] bytes, int index) {
        int size = super.parseBytes(bytes, index);
        index+= size;
        byte[] state = Arrays.copyOfRange(bytes, index, index+ROAD_STATE_SIZE);
        roadState = new RoadState(state);
        setRoadState(roadState);
        System.out.println("AAA"+state[0]);
        return size+ROAD_STATE_SIZE;
    }

    @Override
    public void destroy(final GeometryManager.OnAnimationEndListener onAnimationEndListener) {
        try {
            ((GeometryAnimationManager) getGeometryManager()).beginAnimation("destroy"+roadState.getModelName(), onAnimationEndListener);
        } catch (Exception e){
            onAnimationEndListener.animationEnd();
        }
    }

    private void setCacheInstance(String key, GeometryAnimationManager geometryAnimationManager){
        synchronized (NodesCache.getInstance()){
            NodesCache.getInstance().put(key, geometryAnimationManager);
        }
    }

    public RoadState getRoadState() {
        return roadState;
    }

    public void setRoadState(RoadState roadState) {
        // TODO: 21-Oct-17 fix it with animation cache
        this.roadState = roadState;
        String animationName = roadState.getModelName();
        float rotation = roadState.getRotation();
        if (getGeometryManager()!=null){
            this.detachChild(getGeometryManager());
        }
        name = MANAGER_NAME+animationName+Float.toString(rotation);
            GeometryAnimationManager geometryAnimationManager = new GeometryAnimationManager("road", (Model) NodesCache.getInstance().get("roadModel"));
            setCacheInstance(name, geometryAnimationManager);
            geometryAnimationManager.setLocalScale(0.1f);
            geometryAnimationManager.setLocalTranslation(-0.5f, 0, -0.5f);
            geometryAnimationManager.beginAnimation("build" + animationName);
            setGeometryManager(((GeometryAnimationManager) NodesCache.getInstance().get(name)));
        this.setLocalRotation(getLocalRotation().fromAngles(0,rotation*FastMath.TWO_PI, 0));
    }

    public static void updateRoadStates(Point startPoint, Point endPoint, List<Road> nearbyRoads){
        Point offset = new Point();
        Road[][] array = toArray(startPoint, endPoint, nearbyRoads, offset);
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                if ((i==0 || i == array.length-1)&& (j==0|| j==array[0].length-1)) continue;
                if (array[i][j]!=null) updateSingleRoadStateFromArray(array, i, j);
            }
        }
    }

    private static void updateSingleRoadStateFromArray(Road[][] array, int i, int j){
        RoadState rs = array[i][j].getRoadState();
        if (i>0){
            rs.setRight(array[i-1][j]!=null);
        }
        if (j>0){
            rs.setFront(array[i][j-1]!=null);
        }
        if (i<array.length-1){
            rs.setLeft(array[i+1][j]!=null);
        }
        if (j<array[0].length-1){
            rs.setBack(array[i][j+1]!=null);
        }
        array[i][j].setRoadState(rs);
    }

    private static Road[][] toArray(Point startPoint, Point endPoint, List<Road> roads, Point offsetCallback){
        startPoint = new Point(startPoint);
        endPoint = new Point(endPoint);
        Road[][] array;
        if (startPoint.x == endPoint.x){
            if (startPoint.y > endPoint.y) swap(startPoint, endPoint);
            offsetCallback.set(1-startPoint.x, 1-startPoint.y);
            array = createArray(3, endPoint.y-startPoint.y+3);
        } else {
            if (startPoint.x > endPoint.x) swap(startPoint, endPoint);
            offsetCallback.set(1-startPoint.x, 1-startPoint.y);
            array = createArray(endPoint.x-startPoint.x+3, 3);
        }
        for (Road road: roads){
            array[offsetCallback.x+road.getPosition().x][offsetCallback.y+road.getPosition().y] = road;
        }
        return array;
    }

    private static Road[][] createArray(int x, int y){
        Road[][] array = new Road[x][];
        for (int  i = 0; i < x; i++) array[i] = new Road[y];
        return array;
    }

    private static void swap(Point p1, Point p2){
        Point temp = new Point(p1);
        p1.set(p2.x, p2.y);
        p2.set(temp.x, temp.y);
    }
}
