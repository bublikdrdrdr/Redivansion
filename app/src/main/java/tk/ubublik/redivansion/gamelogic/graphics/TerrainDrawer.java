package tk.ubublik.redivansion.gamelogic.graphics;

import android.graphics.Point;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 03-Nov-17.
 */

public class TerrainDrawer {

    private Node mainNode;
    private Node terrainNode = new Node("Terrain node");
    private GeometryAnimationManager geometryManager;
    private int radius = 1;
    private Geometry[][] terrainParts;
    private static final Vector3f MODEL_SIZE = new Vector3f(7,0,7);
    private static final Vector3f SHIFT = new Vector3f(-3,0,-3);

    private Point lastChanged = new Point(0,0);

    public TerrainDrawer(Node node, int radius){
        this.mainNode = node;
        this.radius = radius;
        mainNode.attachChild(terrainNode);
        geometryManager = new GeometryAnimationManager((Model) NodesCache.getInstance().get("terrainModel"));
        geometryManager.setLocalScale(1.4f);
        beginAnimation();
        onUpdate();
        createArray(radius);
        fixArrayPositions();
    }

    public void beginAnimation(){
        geometryManager.beginAnimation("build");
    }

    public void onUpdate(){
        geometryManager.onUpdate();
    }

    public void onUpdate(Vector3f screenCenterPoint){
        onUpdate();
        Point currentPoint = getCenterPoint(screenCenterPoint);
        if (lastChanged.equals(currentPoint)) return;
        lastChanged = currentPoint;
        createArray(radius);
        fixArrayPositions();
    }

    private Point getCenterPoint(Vector3f center){
        return new Point((int)(center.x/MODEL_SIZE.x), (int)(center.z/MODEL_SIZE.z));
    }

    public void remove(){
        mainNode.detachChild(terrainNode);
    }

    private void createArray(int radius){
        terrainNode.detachAllChildren();
        terrainParts = new Geometry[radius][];
        for (int i = 0; i < radius; i++){
            terrainParts[i] = new Geometry[radius];
        }
        fillArray();
    }

    private void fillArray(){
        for (int i = 0; i < terrainParts.length; i++){
            for (int j = 0; j < terrainParts[0].length; j++){
                if (terrainParts[i][j]==null){
                    terrainParts[i][j] = getClone();
                    terrainNode.attachChild(terrainParts[i][j]);
                    setLocation(i, j);
                }
            }
        }
    }

    private void fixArrayPositions(){
        for (int i = 0; i < terrainParts.length; i++) {
            for (int j = 0; j < terrainParts[0].length; j++) {
                setLocation(i, j);
            }
        }
    }

    private void setLocation(int i, int j){
        terrainParts[i][j].setLocalTranslation(MODEL_SIZE.x*(i+lastChanged.x-radius/2)+SHIFT.x, SHIFT.y, MODEL_SIZE.z*(j+lastChanged.y-radius/2)+SHIFT.z);
    }

    private Geometry getClone(){
        return geometryManager.clone(false);
    }

}
