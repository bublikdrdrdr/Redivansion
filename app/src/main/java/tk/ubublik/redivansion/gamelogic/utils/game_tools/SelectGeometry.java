package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 14-Oct-17.
 */

public class SelectGeometry extends Geometry{

    public SelectGeometry(ColorRGBA colorRGBA, int size, float scale) {
        super(SelectGeometry.class.getSimpleName(), getMesh(scale));
        Material material = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        material.setColor("Diffuse", colorRGBA);
        material.setBoolean("UseMaterialColors", true);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        this.setMaterial(material);
        setSize(size);
    }

    private static Mesh getMesh(float scale){
        float fSize = scale/2;
        float heightRatio = 0.2f*scale;
        return new Box(fSize, fSize*heightRatio, fSize);
    }

    public void setColor(ColorRGBA color){
        getMaterial().setColor("Diffuse", color);
    }

    public void setSize(int size){
        this.setLocalScale(size, 1, size);
    }

    public ColorRGBA getColor(){
        return (ColorRGBA)this.getMaterial().getParam("Diffuse").getValue();
    }
}
