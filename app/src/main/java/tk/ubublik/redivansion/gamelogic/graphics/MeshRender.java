package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class MeshRender {

    private PolyAnimation polyAnimation;
    private Mesh mesh;
    private long animationStart = ANIMATION_DISABLED;
    private static long ANIMATION_DISABLED = -1;
    private GeometryLoopAnimationManager.OnAnimationEndListener listener = null;

    public MeshRender(PolyAnimation polyAnimation) {
        this.polyAnimation = polyAnimation;
    }

    public Mesh getMesh() {
        mesh = new Mesh();
        mesh.setDynamic();
        setMeshBuffer();
        positionVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Position);
        positionDataBuffer = (FloatBuffer) positionVertexBuffer.getData();
        indexVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Index);
        indexDataBuffer = (ShortBuffer) indexVertexBuffer.getData();
        normalVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Normal);
        normalDataBuffer = (FloatBuffer) normalVertexBuffer.getData();
        colorVertexBuffer = mesh.getBuffer(VertexBuffer.Type.Color);
        colorDataBuffer = (FloatBuffer) colorVertexBuffer.getData();

        fillIndexBuffer();
        return mesh;
    }

    public void setOnAnimationEndListener(GeometryLoopAnimationManager.OnAnimationEndListener onAnimationEndListener){
        this.listener = onAnimationEndListener;
    }

    private void setMeshBuffer() {
        int polyCount = polyAnimation.polygonCount();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(polyCount * 3 * 3));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createShortBuffer(polyCount * 3));
        mesh.setBuffer(VertexBuffer.Type.Color, 4, BufferUtils.createFloatBuffer(polyCount * 3 * 4));
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(polyCount * 3 * 3));
    }


    private VertexBuffer positionVertexBuffer;
    private FloatBuffer positionDataBuffer;

    private VertexBuffer indexVertexBuffer;
    private ShortBuffer indexDataBuffer;

    private VertexBuffer normalVertexBuffer;
    private FloatBuffer normalDataBuffer;

    private VertexBuffer colorVertexBuffer;
    private FloatBuffer colorDataBuffer;

    public void onUpdate() {
        if (animationStart != ANIMATION_DISABLED) {
            int index = 0;
            long time = System.currentTimeMillis() - animationStart;
            boolean allDone = true;
            for (Polygon polygon : polyAnimation.getPolygons()) {
                if (!polygon.isDone(time)) {
                    allDone = false;
                    if (polygon.isUpdating(time)) {
                        float changeAmount = (time - polygon.getDelay()) / (float) polygon.getDuration();
                        changeAmount = FastMath.clamp(changeAmount, 0f, 1f);

                        Vector3f[] middleTriangle = getMiddleTriangle(polygon, changeAmount);
                        Vector3f normalVector = getPolygonNormalVector(middleTriangle);
                        ColorRGBA middleColor = getMiddleColor(polygon, changeAmount);
                        System.out.println(middleColor);

                        for (int i = 0; i < 3; i++) {
                            positionDataBuffer.put(index * 3 + i * 3, middleTriangle[i].getX());
                            positionDataBuffer.put(index * 3 + i * 3 + 1, middleTriangle[i].getY());
                            positionDataBuffer.put(index * 3 + i * 3 + 2, middleTriangle[i].getZ());

                            normalDataBuffer.put(index * 3 + i * 3, normalVector.getX());
                            normalDataBuffer.put(index * 3 + i * 3 + 1, normalVector.getY());
                            normalDataBuffer.put(index * 3 + i * 3 + 2, normalVector.getZ());

                            colorDataBuffer.put(index*3+i*4, middleColor.getRed());
                            colorDataBuffer.put(index*3+i*4+1, middleColor.getGreen());
                            colorDataBuffer.put(index*3+i*4+2, middleColor.getBlue());
                            colorDataBuffer.put(index*3+i*4+3, middleColor.getAlpha());
                        }
                    }
                }
                index += 3;
            }
            positionVertexBuffer.setUpdateNeeded();
            normalVertexBuffer.setUpdateNeeded();
            colorVertexBuffer.setUpdateNeeded();


            if (allDone) {
                done = true;
                animationStart = ANIMATION_DISABLED;
            }
        }
    }

    private ColorRGBA getMiddleColor(Polygon polygon, float changeAmount) {
        ColorRGBA colorRGBA = new ColorRGBA();
        return colorRGBA.interpolateLocal(polygon.getStartColor(), polygon.getEndColor(), changeAmount);
    }

    private Vector3f getPolygonNormalVector(Vector3f[] triangle) {
        // FIXME: 27-Aug-17 works, but not for spot lights
        return FastMath.computeNormal(triangle[0], triangle[1], triangle[2]);
    }

    private Vector3f[] getMiddleTriangle(Polygon polygon, float changeAmount) {
        Vector3f[] vertices = new Vector3f[3];
        for (int i = 0; i < 3; i++) {
            vertices[i] = getMiddlePoint(polygon.getStartPoints()[i], polygon.getEndPoints()[i], changeAmount);
        }
        return vertices;
    }

    private Vector3f getMiddlePoint(Vector3f v1, Vector3f v2, float changeAmount) {
        return new Vector3f((v2.x - v1.x) * changeAmount + v1.x, (v2.y - v1.y) * changeAmount + v1.y, (v2.z - v1.z) * changeAmount + v1.z);
    }

    private void fillIndexBuffer() {
        int count = polyAnimation.polygonCount() * 3;
        for (int i = 0; i < count; i++) {
            indexDataBuffer.put(i, (short) i);
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

    public GeometryLoopAnimationManager.OnAnimationEndListener getListener() {
        return listener;
    }

    public void setListener(GeometryLoopAnimationManager.OnAnimationEndListener listener) {
        this.listener = listener;
    }
}
