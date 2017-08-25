package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class MeshRender {

    private PolyAnimation polyAnimation;
    private Mesh mesh;
    private long animationStart = ANIMATION_DISABLED;
    private static long ANIMATION_DISABLED = -1;

    public MeshRender(PolyAnimation polyAnimation) {
        this.polyAnimation = polyAnimation;
    }

    public Mesh getMesh() {
        mesh = new Mesh();
        mesh.setDynamic();
        setMeshBuffer();
        positionVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Position);
        positionDataBuffer = (FloatBuffer)positionVertexBuffer.getData();
        indexVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Index);
        indexDataBuffer = (ShortBuffer) indexVertexBuffer.getData();

        fillIndexBuffer();
        return mesh;
    }

    private void setMeshBuffer(){
        int polyCount = polyAnimation.polygonCount();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(polyCount*3*3));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createShortBuffer(polyCount*3));
        mesh.setBuffer(VertexBuffer.Type.Color, 4, BufferUtils.createFloatBuffer(polyCount*3*4));
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(polyCount*3*3));
    }


    private VertexBuffer positionVertexBuffer;
    private FloatBuffer positionDataBuffer;

    private VertexBuffer indexVertexBuffer;
    private ShortBuffer indexDataBuffer;

    public void onUpdate() {
        if (animationStart != ANIMATION_DISABLED) {
            int polyCount = polyAnimation.polygonCount();
            int index = 0;
            long time = System.currentTimeMillis()-animationStart;
            boolean allDone = true;
            for (Polygon polygon: polyAnimation.getPolygons()) {
                if (!polygon.isDone(time)){
                    allDone=false;
                    if (polygon.isUpdating(time)){
                        float changeAmount = (time-polygon.getDelay())/(float)polygon.getDuration();
                        if (changeAmount>1) changeAmount = 1;
                        if (changeAmount<0) changeAmount = 0;
                        System.out.println("AMOUNT:"+changeAmount);
                        for (int i = 0; i < 3; i++) {
                            Vector3f point = getMiddlePoint(polygon.getStartPoints()[i], polygon.getEndPoints()[i], changeAmount);

                            positionDataBuffer.put(index+i*3, point.getX());
                            positionDataBuffer.put(index+i*3+1, point.getY());
                            positionDataBuffer.put(index+i*3+2, point.getZ());
                        }
                    }
                }
                index+=9;
            }
            positionVertexBuffer.setUpdateNeeded();
            if (allDone) done = true;
        }
    }

    private Vector3f getMiddlePoint(Vector3f v1, Vector3f v2, float changeAmount){
        return new Vector3f((v2.x-v1.x)*changeAmount+v1.x, (v2.y-v1.y)*changeAmount+v1.y, (v2.z-v1.z)*changeAmount+v1.z);
    }

    private void fillIndexBuffer(){
        int count = polyAnimation.polygonCount()*3;
        for (int i = 0; i < count; i++){
            indexDataBuffer.put(i, (short)i);
        }
        indexVertexBuffer.setUpdateNeeded();
    }

    private boolean done = false;
    public boolean isDone() {
        return done;
    }

    public void beginAnimation() {
        animationStart = System.currentTimeMillis();
    }
}
