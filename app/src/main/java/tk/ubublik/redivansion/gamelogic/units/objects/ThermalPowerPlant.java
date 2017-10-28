package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class ThermalPowerPlant extends PowerPlant {

    public ThermalPowerPlant(){
        this(new Point());
    }

    public ThermalPowerPlant(Point position){
        setPosition(position);
        setSize(3);
        setNeedsRoad(true);
        setBuildCost(GameParams.THERMAL_POWER_PLANT_BUILD_COST);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }
}
