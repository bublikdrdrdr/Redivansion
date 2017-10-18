package tk.ubublik.redivansion.gamelogic.utils;

import android.graphics.Point;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import tk.ubublik.redivansion.gamelogic.camera.CameraControl;
import tk.ubublik.redivansion.gamelogic.units.WorldMapAction;
import tk.ubublik.redivansion.gamelogic.units.objects.Terrain;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 *
 * Puts geometries on their positions in 3D world
 */
public class MapRenderer implements Observer{

    private final float scale;
    private final Node node;
    private final Camera camera;

    private Spatial selectNode = getSelectNode();
    private int selectModeSize = 1;
    private boolean selectMode = false;

    private Terrain terrain;

    public MapRenderer(Node node) {
        this(node, null);
    }

    public MapRenderer(Node node, Camera camera){
        this(node, 1, camera);
    }

    public MapRenderer(Node node, float scale, Camera camera){
        this.node = node;
        this.scale = scale;
        this.camera = camera;
    }

    public Point worldPointToMap(Vector3f vector3f){
        return worldPointToMap(vector3f, 1);
    }

    public Point worldPointToMap(Vector3f vector3f, int size){
        final float offset = -.5f*size+.5f;
        return new Point((int)FastMath.floor(offset+vector3f.getX()/scale), (int)FastMath.floor(offset+vector3f.getZ()/scale));
    }

    public Vector3f mapPointToWorld(Point point){
        return mapPointToWorld(point, 1);
    }
    public Vector3f mapPointToWorld(Point point, int size){
        final float offset = .5f*size;
        return new Vector3f((point.x+offset)*scale, 0, (point.y+offset)*scale);
    }

    public void putObject(WorldObject worldObject){
        Vector3f position = mapPointToWorld(worldObject.getPosition(), worldObject.getSize());
        worldObject.setLocalTranslation(position);
        node.attachChild(worldObject);
    }

    public void putObjects(Collection<WorldObject> worldObjects){
        for (WorldObject worldObject: worldObjects)
            putObject(worldObject);
    }

    public boolean contains(WorldObject worldObject){
        return contains(worldObject.getGeometryManager());
    }

    public boolean contains(Spatial spatial){
        return (node.getChildIndex(spatial)!=-1);
    }

    public void removeObject(WorldObject worldObject){
        removeObject(worldObject.getGeometryManager());
    }

    public void removeObject(Spatial spatial){
        node.detachChild(spatial);
    }

    public void removeObject(int index){
        node.detachChildAt(index);
    }

    public void onUpdate(){
        for (Spatial spatial: node.getChildren()){
            if ((spatial instanceof WorldObject) && ((camera.contains(spatial.getWorldBound())!= Camera.FrustumIntersect.Outside))){
                ((WorldObject)spatial).onUpdate();
            }
        }
        if (selectMode) updateSelectNode(selectNode, CameraControl.getCameraCenterPoint(camera), selectModeSize);
        if (terrain!=null) terrain.onUpdate();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WorldMapAction){
            WorldMapAction worldMapAction = (WorldMapAction) arg;
            switch (worldMapAction.getAction()){
                case ADD: putObject(worldMapAction.getWorldObject()); break;
                case REMOVE: removeObject(worldMapAction.getWorldObject()); break;
                default: worldMapAction.getWorldObject().onUpdate();
            }
        }
    }

    private void updateSelectNode(Spatial selectNode, Vector3f center, int size){
        Point centerPoint = worldPointToMap(center, size);
        selectNode.setLocalTranslation(mapPointToWorld(centerPoint, size).add(0,0.5f,0));
    }

    private Geometry getSelectNode(){
        Material mat = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", new ColorRGBA(1, 1, 1, 0.5f));
        mat.setBoolean("UseMaterialColors", true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        float boxSize = 0.4f;
        Box box = new Box(boxSize, boxSize, boxSize);
        Geometry geometry = new Geometry("select box", box);
        geometry.setMaterial(mat);
        return geometry;
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public int getSelectModeSize() {
        return selectModeSize;
    }

    public void setSelectMode(boolean isActive){
        setSelectMode(isActive, 1);
    }

    public void setSelectMode(boolean isActive, int size){
        if (this.selectMode!=isActive || this.selectModeSize!=size){
            if (isActive){
                selectNode.setLocalScale(size, 1f, size);
                node.attachChild(selectNode);
            } else {
                node.detachChild(selectNode);
            }
            selectMode = isActive;
            selectModeSize = size;
        }
    }

    public void addTerrain(Terrain terrain){
        this.terrain = terrain;
        node.attachChild(terrain);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void removeTerrain(){
        node.detachChild(terrain);
        terrain = null;
    }

    public float getScale() {
        return scale;
    }
}
