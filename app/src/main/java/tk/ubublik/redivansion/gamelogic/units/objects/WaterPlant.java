package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 06-Nov-17.
 */

public class WaterPlant extends WorldObject {


    public WaterPlant(Point position) {
        setGeometryManager(new GeometryAnimationManager("WATER_PLANT", (Model) NodesCache.getInstance().get("waterplantModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(0.55f, 0.6f, 0.55f);
        getGeometryManager().setLocalTranslation(-0.7f,0, -0.7f);
        //params
        setSize(2);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.WATER_PLANT_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build"+level);
    }

    @Override
    public void destroy(GeometryManager.OnAnimationEndListener onAnimationEndListener) {
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation("destroy"+level, onAnimationEndListener);
    }

    @Override
    public void recalculateParams() {
        setLevelParams();
    }

    @Override
    public int getLevelsCount() {
        return GameParams.WATER_PLANT_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        beginAnimation("destroy"+(level-1), "build"+(level));
        this.level = level;
        setLevelParams();
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

    private void setLevelParams(){
        setParams(GameParams.WATER_PLANT_LEVELS_MONTH_COST[level],
                GameParams.WATER_PLANT_LEVELS_POWER[level],
                GameParams.WATER_PLANT_LEVELS_FIRE[level],
                GameParams.WATER_PLANT_LEVELS_WATER[level],
                GameParams.WATER_PLANT_LEVELS_POLLUTION[level],
                GameParams.WATER_PLANT_LEVELS_CRIMINAL[level],
                GameParams.WATER_PLANT_LEVELS_HEALTH[level],
                GameParams.WATER_PLANT_LEVELS_WORK[level],
                GameParams.WATER_PLANT_LEVELS_HAPPINESS[level],
                GameParams.WATER_PLANT_LEVELS_EDUCATION[level],
                GameParams.WATER_PLANT_LEVELS_RADIUS[level]);
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
        if (level==GameParams.WATER_PLANT_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.WATER_PLANT_LEVELS_BUILD_COST[level+1];
    }
}
