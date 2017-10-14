package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import com.jme3.effect.influencers.NewtonianParticleInfluencer;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 11-Oct-17.
 */

//select building to edit/remove
public class SingleSelectTool extends SelectTool{

    private SelectGeometry selectGeometry;
    private MapRenderer mapRenderer;
    private Node node;

    public SingleSelectTool(WorldObject worldObject, MapRenderer mapRenderer, Node node){
        this.selectGeometry = getSelectGeometry(worldObject.getSize());
        this.mapRenderer = mapRenderer;
        this.node = node;
        selectGeometry.setLocalTranslation(worldObject.getLocalTranslation());
    }

    @Override
    public void destroy() {
        if (selectGeometry!=null) node.detachChild(selectGeometry);
        selectGeometry = null;
    }

    @Override
    public boolean canPut() {
        return false;
    }

    private SelectGeometry getSelectGeometry(int size){
        return new SelectGeometry(SelectToolManager.DEFAULT_SELECT_COLOR, size, mapRenderer.getScale());
    }
}
