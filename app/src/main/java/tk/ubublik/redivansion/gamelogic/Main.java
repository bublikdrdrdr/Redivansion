package tk.ubublik.redivansion.gamelogic;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import java.nio.ShortBuffer;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Main extends SimpleApplication {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void simpleUpdate(float tpf) {
        // TODO: 20-Aug-17 process game logic
        testUpdate();
    }

    public void simpleInitApp() {
        setupApplication();
        //addTestBox();
        testInit();
    }

    private void setupApplication(){
        this.setDisplayStatView(false);
        this.setDisplayFps(false);
    }

    private void addTestBox(){
        Box box = new Box(1, 1, 1);
        Geometry geom = new Geometry("box", box);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);

        // Like normal Android projects, place your texture/models/sounds/etc files in the project
        // "assets" folder (src/main/assets).  jME includes this directory automatically when
        // looking for game assets.
        Texture texture = assetManager.loadTexture("Textures/Monkey.png");
        mat.setTexture("ColorMap", texture);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }




    //TEST FIELD
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
        indices.put(1, (short)r.nextInt(4));
        indexBuffer.setUpdateNeeded();
    }
}
