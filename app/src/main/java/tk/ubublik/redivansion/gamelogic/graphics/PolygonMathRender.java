package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.math.Vector3f;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class PolygonMathRender {

    private class PolygonCache{
        public Polygon polygon;
        public Vector3f[] triangle;
        public float changeAmount;

        public PolygonCache(Polygon polygon, Vector3f[] triangle, float changeAmount) {
            this.polygon = polygon;
            this.triangle = triangle;
            this.changeAmount = changeAmount;
        }
    }

    //if next functions process same polygon, they don't need to re-calculate current animation state
    private PolygonCache polygonCache;

    private Vector3f[] getCurrentTriangle(Polygon polygon, float changeAmount){
        if (polygonCache!=null){
            if (polygonCache.polygon==polygon && polygonCache.changeAmount == changeAmount){
                return polygonCache.triangle;
            }
        }
        Vector3f[] triangle = new Vector3f[3];
        for (int i = 0; i < 3; i++){
            triangle[i] = polygon.getStartPoints()[i].clone().interpolateLocal(polygon.getEndPoints()[i], changeAmount);
        }
        this.polygonCache = new PolygonCache(polygon, triangle, changeAmount);
        return triangle;
    }

    public void renderPositions(int polyIndex, FloatBuffer buffer, Polygon polygon, float changeAmount){
        // TODO: 25-Aug-17 set polygon points to buffer (starting from index position)
        Vector3f[] triangle = getCurrentTriangle(polygon, changeAmount);
    }

    public void renderIndexes(int polyIndex, ShortBuffer buffer, Polygon polygon, float changeAmount){
    }

    public void renderNormals(int polyIndex, FloatBuffer buffer, Polygon polygon, float changeAmount){
    }
}
