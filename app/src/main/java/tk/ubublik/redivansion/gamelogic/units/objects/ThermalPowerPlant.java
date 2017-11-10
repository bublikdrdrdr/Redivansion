package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class ThermalPowerPlant extends PowerPlant {

    public ThermalPowerPlant(){
        this(new Point());
    }

    public ThermalPowerPlant(Point position){

        setGeometryManager(new GeometryAnimationManager("powerPlant", (Model) NodesCache.getInstance().get("powerPlantModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(2.7f, 0.5f, 2.7f);
        getGeometryManager().setLocalTranslation(-1.3f,0,-1.3f);
        //params
        setPosition(position);
        setSize(3);
        setNeedsRoad(true);
        setBuildCost(GameParams.THERMAL_POWER_PLANT_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build");
    }

    private void beginAnimation(String animationName, final String nextAnimation){
        //// FIXME: 04-Nov-17
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation(animationName, new GeometryManager.OnAnimationEndListener() {
            @Override
            public void animationEnd() {
                ((GeometryAnimationManager)getGeometryManager()).beginAnimation(nextAnimation);
            }
        });
    }

    /*
    logic block
     */

    @Override
    public void recalculateParams() {
        setLevelParams();
    }

    @Override
    public int getLevelsCount() {
        return -GameParams.THERMAL_POWER_PLANT_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        //beginAnimation("destroy", "build");
        //beginAnimation("destroy"+Integer.toString(getLevelNumber()), "build"+Integer.toString(level));
        this.level = level;
        setLevelParams();
    }

    private void setLevelParams(){
        setParams(GameParams.THERMAL_POWER_PLANT_LEVELS_MONTH_COST[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_POWER[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_FIRE[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_WATER[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_POLLUTION[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_CRIMINAL[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_HEALTH[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_WORK[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_HAPPINESS[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_EDUCATION[level],
                GameParams.THERMAL_POWER_PLANT_LEVELS_RADIUS[level]);
    }

    @Override
    public int getLevelNumber() {
        return level;
    }

    @Override
    public int getMoneyDelta() {
        return monthCost;
    }

    @Override
    public int getUpgradeCost() {
        if (level==GameParams.THERMAL_POWER_PLANT_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.THERMAL_POWER_PLANT_LEVELS_BUILD_COST[level+1];
    }
}
