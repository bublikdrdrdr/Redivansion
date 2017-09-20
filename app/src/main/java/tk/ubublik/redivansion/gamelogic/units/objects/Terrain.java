package tk.ubublik.redivansion.gamelogic.units.objects;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 20-Sep-17.
 */

public class Terrain extends WorldObject {
    public Terrain(GeometryManager geometryManager) {
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
