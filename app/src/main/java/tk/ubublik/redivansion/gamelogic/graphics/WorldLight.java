package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Created by Bublik on 02-Oct-17.
 */

public class WorldLight {

    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;

    public WorldLight(Node rootNode, Vector3f lightDirection){
        this(rootNode, lightDirection, ColorRGBA.DarkGray, ColorRGBA.White.mult(1.2f));
    }

    public WorldLight(Node rootNode, Vector3f lightDirection, ColorRGBA ambientColor, ColorRGBA directionalColor){
        ambientLight = new AmbientLight(ambientColor);
        directionalLight = new DirectionalLight(lightDirection, directionalColor);
        rootNode.addLight(ambientLight);
        rootNode.addLight(directionalLight);
    }

    public void onUpdate(){

    }
}
