package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.shader.VarType;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMap;

/**
 * Created by Bublik on 22-Sep-17.
 */

public class MapManager {

    private WorldMap worldMap = new WorldMap();
    private MapRenderer mapRenderer;

    private boolean selectMode = false;
    private int selectModeSize = 0;

    private Geometry selectGeometry = getSelectNode();
    private Node sceneNode;

    private CameraControl cameraControl;

    public MapManager(Node sceneNode, CameraControl cameraControl){
        mapRenderer = new MapRenderer(sceneNode);
        this.sceneNode = sceneNode;
        this.cameraControl = cameraControl;
    }

    public void setSelectMode(boolean isActive){
        setSelectMode(isActive, 1);
    }

    public void setSelectMode(boolean isActive, int size){
        if (this.selectMode!=isActive || this.selectModeSize!=size){
            if (isActive){
                selectGeometry.setLocalScale(size);
                sceneNode.attachChild(selectGeometry);
            } else {
                sceneNode.detachChild(selectGeometry);
            }
            selectMode = isActive;
            selectModeSize = size;
        }
    }

    public void onUpdate(){
        if (selectMode) updateSelectMode();
    }

    private void updateSelectMode(){
        selectGeometry.setLocalTranslation(cameraControl.getCameraCenterPoint().add(0, 0.5f, 0));
    }

    private Geometry getSelectNode(){
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", new ColorRGBA(1, 1, 1, 0.5f));
        mat.setBoolean("UseMaterialColors", true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        float boxSize = 0.5f;
        Box box = new Box(boxSize, boxSize, boxSize);
        Geometry geometry = new Geometry("select box", box);
        geometry.setMaterial(mat);
        return geometry;
    }

    public boolean isSelectMode(){
        return selectMode;
    }
}
