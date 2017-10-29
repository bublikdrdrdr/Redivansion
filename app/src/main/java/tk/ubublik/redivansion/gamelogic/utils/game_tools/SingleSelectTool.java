package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

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
