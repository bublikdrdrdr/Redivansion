package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class GeometryAnimationManager extends GeometryManager{

    private Model model;
    private MeshRender meshRender;

    public GeometryAnimationManager(byte[] bytes){
        this(null, bytes);
    }

    public GeometryAnimationManager(Model model){
        this(null, model);
    }

    public GeometryAnimationManager(String name, byte[] bytes) {
        this(name, new Model(bytes));
    }

    public GeometryAnimationManager(String name, Model model){
        super(name);
        if (model==null) throw new NullPointerException("Model can't be null");
        this.model = model;
        setBase();
    }

    public GeometryAnimationManager(String name, Material material, Model model, Mesh mesh){
        super(name);
        setMaterial(material);
        this.model = model;
        this.mesh = mesh;
    }


    private void setBase(){
        this.setMesh(new Mesh());
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Specular", ColorRGBA.White);
        mat.setBoolean("UseVertexColor", true);
        this.setMaterial(mat);
    }

    /**
     * Create buffers for all triangles in animation
     * @param polyAnimation
     */
    private void prepareAnimation(PolyAnimation polyAnimation){
        meshRender = new MeshRender(polyAnimation);
        this.setMesh(meshRender.getMesh());
        meshRender.beginAnimation();
        done = false;
    }

    private void prepareAnimation(PolyAnimation polyAnimation, OnAnimationEndListener onAnimationEndListener){
        prepareAnimation(polyAnimation);
        meshRender.setListener(onAnimationEndListener);
    }

    @Override
    public void onUpdate() {
        if (meshRender!=null){
            meshRender.onUpdate();
            if (meshRender.isDone()) {
                if (meshRender.getListener()!=null) meshRender.getListener().animationEnd();
                meshRender = null;
                done = true;
            }
            this.updateModelBound();
        }
    }

    @Override
    public GeometryManager clone() {
        GeometryAnimationManager clone = new GeometryAnimationManager(name, getMaterial(), model, mesh);
        clone.setLocalScale(getLocalScale());
        clone.setLocalTranslation(getLocalTranslation());
        return clone;
    }

    public GeometryManager fullClone(){
        return new GeometryAnimationManager(name, model.clone());
    }

    public void beginAnimation(String name) {
        PolyAnimation polyAnimation = model.getAnimationByName(name);
        if (polyAnimation==null) throw new NullPointerException("Can't find animation with name +\""+name+"\"");
        prepareAnimation(polyAnimation);
    }

    public void beginAnimation(String name, OnAnimationEndListener listener) {
        PolyAnimation polyAnimation = model.getAnimationByName(name);
        if (polyAnimation==null) throw new NullPointerException("Can't find animation with name +\""+name+"\"");
        prepareAnimation(polyAnimation, listener);
    }
}
