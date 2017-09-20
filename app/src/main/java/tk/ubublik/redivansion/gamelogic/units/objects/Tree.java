package tk.ubublik.redivansion.gamelogic.units.objects;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Tree extends WorldObject {
    public Tree(GeometryManager geometryManager) {
        super(geometryManager);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }
}
