package tk.ubublik.redivansion.gamelogic.test;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

/**
 * Created by Bublik on 23-Aug-17.
 */

//testing jME possibilities
public class DynamicObject {
//so, how it will work(god, please, let this shit will do it)

    private Node rootNode;
    private AssetManager assetManager;

    //constructor MUST get the rootNode (or other for attach) and put graphics into it
    public DynamicObject(Node rootNode, AssetManager assetManager){
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        createModel();
    }


    Geometry geometry;
    private void createModel(){
        Mesh mesh = new Mesh();
        mesh.setDynamic();
        mesh.setMode(Mesh.Mode.Triangles);
        float[] vertex = new float[]{0, 0, 0, 0, 3, 0, 3, 0, 0};
        short[] index = new short[]{1,0,2};
        mesh.setBuffer(VertexBuffer.Type.Position, 3, vertex);
        mesh.setBuffer(VertexBuffer.Type.Index, 1, index);
        geometry = new Geometry("geometry", mesh);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Red);
        geometry.setMaterial(material);
        geometry.updateModelBound();
    }

    public void drawSomething(){
        rootNode.attachChild(geometry);
        rootNode.updateGeometricState();
    }

    public void changeSomething(){
        vertexBuffer = geometry.getMesh().getBuffer(VertexBuffer.Type.Position);
        buffer = (FloatBuffer) vertexBuffer.getData();
        animationNow = true;
        startTime = System.currentTimeMillis();
    }

    VertexBuffer vertexBuffer;
    FloatBuffer buffer;

    long duration = 1000;
    long startTime;
    boolean animationNow = false;
    public void update(){
        if (animationNow){
            long time = System.currentTimeMillis()-startTime;
            if (time>=duration){
                setVertex(1f);
                animationNow = false;
            } else {
                setVertex(time / (float) duration);
            }
            geometry.updateModelBound();
            vertexBuffer.setUpdateNeeded();
        }
    }

    private void setVertex(float percent){
        buffer.put(0, (-3)*percent);
    }

    VertexBuffer indexBuffer;
    ShortBuffer indices;

    private void testInit(){
        short[] indexbuffer = new short[]{ 1, 2 };
        float[] vertexbuffer = new float[]{ 0, 0, 0, 0, 3, 0, 3, 0, 0, -3, 0, 0};
        Mesh lineMesh = new Mesh();
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setDynamic();
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3, vertexbuffer);
        lineMesh.setBuffer(VertexBuffer.Type.Index, 1, indexbuffer);
        Geometry lineGeometry = new Geometry("line", lineMesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); // create a simple material
        mat.setColor("Color", ColorRGBA.Red); // set color of material to blue
        lineGeometry.setMaterial(mat);
        rootNode.attachChild(lineGeometry);
        rootNode.updateGeometricState();
        indexBuffer = (VertexBuffer)lineGeometry.getMesh().getBuffer(VertexBuffer.Type.Index);
        indices = (ShortBuffer)indexBuffer.getData();
    }

    private void testUpdate(){
        Random r = new Random();
        indices.put(0, (short)r.nextInt(4));
        //indices.put(1, (short)r.nextInt(4));
        indexBuffer.setUpdateNeeded();
    }

}
