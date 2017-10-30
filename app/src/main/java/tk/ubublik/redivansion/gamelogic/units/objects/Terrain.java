package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 20-Sep-17.
 */

@Deprecated
public class Terrain extends WorldObject {

    private GeometryAnimationManager customGeometryManager;

    private Vector3f partShift = new Vector3f(5f,0,5f);//depends on model size
    private Vector3f worldCenter = Vector3f.ZERO;

    public Terrain(int size) {
        super();
        GeometryManager geometryManager = new GeometryAnimationManager((Model)NodesCache.getInstance().get("terrainModel"));
        setGeometryManager(geometryManager);
        beginBuildAnimation();
        cloneMesh(size);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }

    @Override
    public void recalculateParams() {

    }

    @Override
    public int getLevelsCount() {
        return 0;
    }

    @Override
    public void setLevelNumber(int level) {

    }

    @Override
    public int getLevelNumber() {
        return 0;
    }

    @Override
    public int getMoneyDelta() {
        return 0;
    }

    @Override
    public void setGeometryManager(GeometryManager geometryManager) {
        customGeometryManager = (GeometryAnimationManager)geometryManager;
    }

    @Override
    public void onUpdate() {
        customGeometryManager.onUpdate();
        if (!customGeometryManager.isDone()) {
            this.updateModelBound();
        }
    }

    private void cloneMesh(int size){
        cloneMesh(new Point(size, size));
    }

    private void cloneMesh(Point size){
        Geometry geometry = new Geometry("TerrainPart");
        geometry.setMesh(customGeometryManager.getMesh());
        geometry.setMaterial(customGeometryManager.getMaterial());
        for (int i = -size.x; i < size.x; i++){
            for (int j = -size.y; j < size.y; j++){
                Geometry clone = geometry.clone(false);
                clone.setLocalTranslation(i*partShift.x+worldCenter.x, worldCenter.y, j*partShift.z+worldCenter.z);
                this.attachChild(clone);
            }
        }
    }

    private void beginBuildAnimation(){
        customGeometryManager.beginAnimation("build");
    }
}
