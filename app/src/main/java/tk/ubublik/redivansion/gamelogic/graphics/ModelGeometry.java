package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.shader.Shader;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 25-Aug-17.
 */
@Deprecated//does not work
public class ModelGeometry {

    private Model model;
    private String name;

    public ModelGeometry(String name, Model model){
        this.model = model;
        this.name = name;
        polygonMathRender = new PolygonMathRender();
        createGeometry();
    }

    private PolyAnimation currentAnimation = null;
    private long animationStartMillis;// TODO: 25-Aug-17 check performance difference with nanos
    /**
     * Begin animation
     * @param name - animation name
     * @throws IllegalArgumentException if animation not found
     */
    public void beginAnimation(String name) throws IllegalArgumentException{
        currentAnimation = model.getAnimationByName(name);
        if (currentAnimation==null) throw new IllegalArgumentException("Animation with name \""+name+"\" not found");
        int count = currentAnimation.polygonCount();
        setBuffers(count*3*3);
        animationStartMillis = System.currentTimeMillis();
    }

    private PolygonMathRender polygonMathRender;

    private Geometry geometry;
    private Mesh mesh;
    private VertexBuffer positionBuffer;
    private FloatBuffer floatPositionBuffer;

    private VertexBuffer indexBuffer;
    private ShortBuffer shortIndexBuffer;

    private VertexBuffer normalsBuffer;
    private FloatBuffer floatNormalBuffer;

    //called one time, when you add 3d model to node
    public Geometry getGeometry(){
        return geometry;
    }

    public void update(){
        if (currentAnimation!=null){
            int index = 0;
            boolean done = true;
            long time = System.currentTimeMillis()-animationStartMillis;
            for (Polygon polygon: currentAnimation.getPolygons()){
                if (!polygon.isDone(time)) {
                    done = false;
                    if (polygon.isUpdating(time)) {
                        float changeAmount = ((time-polygon.getDelay()) / polygon.getDuration());
                        polygonMathRender.renderPositions(index, floatPositionBuffer, polygon, changeAmount);
                        polygonMathRender.renderIndexes(index, shortIndexBuffer, polygon, changeAmount);
                        polygonMathRender.renderNormals(index, floatNormalBuffer, polygon, changeAmount);
                    }
                    index++;
                }
            }
            if (done) {
                simplifyAfterAnimation();
                currentAnimation = null;
            }
            updateGeometry();
        }
    }

    private void createGeometry() {
        mesh = new Mesh();
        mesh.setMode(Mesh.Mode.LineLoop);
        mesh.setDynamic();
        setBuffers();
        geometry = new Geometry(this.name, mesh);

        //todo debug section
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geometry.setMaterial(mat);
        // TODO: 25-Aug-17 use colors from polygons
    }

    private void setBuffers(){
        setBuffers(0);
    }

    private void setBuffers(int count){
        setBuffers(count, count, count);
    }

    private void setBuffers(int positionsCount, int indexCount, int normalCount){
        mesh.setBuffer(VertexBuffer.Type.Position, 3, new float[positionsCount]);
        mesh.setBuffer(VertexBuffer.Type.Index, 3, new short[indexCount]);
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, new float[normalCount]);

        positionBuffer = mesh.getBuffer(VertexBuffer.Type.Position);
        floatPositionBuffer = (FloatBuffer)positionBuffer.getData();

        indexBuffer = mesh.getBuffer(VertexBuffer.Type.Index);
        shortIndexBuffer = (ShortBuffer) indexBuffer.getData();

        normalsBuffer = mesh.getBuffer(VertexBuffer.Type.Normal);
        floatNormalBuffer = (FloatBuffer)normalsBuffer.getData();
    }

    private void updateGeometry(){
        positionBuffer.setUpdateNeeded();
        indexBuffer.setUpdateNeeded();
        normalsBuffer.setUpdateNeeded();
        geometry.updateModelBound();
    }

    private void simplifyAfterAnimation(){
        // TODO: 25-Aug-17 reduce vertex count by using indexes(because full model will have a lot of same points between triangles)
    }
}
