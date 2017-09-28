package tk.ubublik.redivansion.gamelogic.graphics;

import android.support.annotation.Nullable;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;

import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 04-Sep-17.
 */

public class GeometryLoopAnimationManager extends GeometryManager {

    private Model model;
    private MeshRender meshRender;

    public GeometryLoopAnimationManager(byte[] bytes){
        this(null, bytes);
    }

    public GeometryLoopAnimationManager(String name, byte[] bytes) {
        this(name, new Model(bytes));
    }

    public GeometryLoopAnimationManager(Model model){
        this(null, model);
    }

    public GeometryLoopAnimationManager(String name, Model model){
        super(name);
        if (model==null) throw new NullPointerException("Model can't be null");
        this.model = model;
        setBase();
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
    }

    private void prepareAnimation(PolyAnimation polyAnimation, OnAnimationEndListener onAnimationEndListener){
        prepareAnimation(polyAnimation);
        this.meshRender.setListener(onAnimationEndListener);
    }

    @Override
    public void onUpdate() {
        if (meshRender!=null){
            meshRender.onUpdate();
            if (meshRender.isDone()) {
                if (meshRender.getListener()!=null){
                    meshRender.getListener().animationEnd();
                }
                if (loopAnimations!=null){
                    prepareAnimation(loopAnimations[loopAnimationIndex]);
                    meshRender.onUpdate();
                    loopAnimationIndex++;
                    if (loopAnimationIndex>=loopAnimations.length){
                        loopAnimationIndex = 0;
                    }
                } else {
                    meshRender = null;
                    if (nextAnimation != null) {
                        prepareAnimation(nextAnimation);
                        nextAnimation = null;
                    }
                }
            }
            this.updateModelBound();
        }
    }

    public void beginAnimation(String name){
        beginAnimation(name, null);
    }

    private PolyAnimation nextAnimation = null;
    public void beginAnimation(String name, String runAfter) {
        PolyAnimation polyAnimation = model.getAnimationByName(name);
        if (polyAnimation==null) throw new NullPointerException("Can't find animation with name +\""+name+"\"");
        loopAnimations = null;
        if (runAfter==null) {
            prepareAnimation(polyAnimation);
        } else {
            nextAnimation = polyAnimation;
        }
    }

    public void beginAnimation(OnAnimationEndListener animationEndListener, String name){
        PolyAnimation polyAnimation = model.getAnimationByName(name);
        if (polyAnimation==null) throw new NullPointerException("Can't find animation with name +\""+name+"\"");
        loopAnimations = null;
        prepareAnimation(polyAnimation, animationEndListener);
    }

    private PolyAnimation[] loopAnimations = null;
    private int loopAnimationIndex;
    public void beginLoopAnimation(String[] animationNames){
        loopAnimations = new PolyAnimation[animationNames.length];
        for (int i = 0; i < animationNames.length;i++){
            loopAnimations[i] = model.getAnimationByName(animationNames[i]);
            if (loopAnimations[i]==null){
                loopAnimations = null;
                throw new NullPointerException("Can't find animation with name +\""+name+"\"");
            }
        }
        loopAnimationIndex = 0;
    }

    public interface OnAnimationEndListener{
        void animationEnd();
    }

    @Override
    public GeometryManager clone() {
        return new GeometryAnimationManager(model.clone());
    }
}
