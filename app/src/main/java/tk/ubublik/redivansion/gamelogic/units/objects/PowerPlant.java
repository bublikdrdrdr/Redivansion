package tk.ubublik.redivansion.gamelogic.units.objects;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 31-Aug-17.
 */

public abstract class PowerPlant extends WorldObject {

    private float radiusSqr;

    public float getRadiusSqr(){
        return radiusSqr;
    }

    protected void setRadiusSqr(float radiusSqr){
        this.radiusSqr = radiusSqr;
    }
}
