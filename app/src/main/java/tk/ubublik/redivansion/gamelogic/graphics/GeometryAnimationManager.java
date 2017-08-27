package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class GeometryAnimationManager extends GeometryManager{

    private Model model;
    private MeshRender meshRender;

    public GeometryAnimationManager(byte[] bytes) {
        this(new Model(bytes));
    }

    public GeometryAnimationManager(Model model){
        if (model==null) throw new NullPointerException("Model can't be null");
        this.model = model;
        prepareAnimation(model.getAnimations().get(0));
    }

    /**
     * Create buffers for all triangles in animation
     * @param polyAnimation
     */
    private void prepareAnimation(PolyAnimation polyAnimation){
        meshRender = new MeshRender(polyAnimation);
        this.setMesh(meshRender.getMesh());
        meshRender.beginAnimation();
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Specular", ColorRGBA.White);
        mat.setBoolean("UseVertexColor", true);
        //mat.setBoolean("VertexLighting", true);
        //mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);//debug mode: show polygons from both sides
        this.setMaterial(mat);
    }

    @Override
    public void onUpdate() {
        if (meshRender!=null){
            meshRender.onUpdate();
            if (meshRender.isDone())
                meshRender = null;
            this.updateModelBound();
        }
    }

    public void beginAnimation(String name) {
        PolyAnimation polyAnimation = model.getAnimationByName(name);
        if (polyAnimation==null) throw new NullPointerException("Can't find animation with name +\""+name+"\"");
        prepareAnimation(polyAnimation);
    }
}
