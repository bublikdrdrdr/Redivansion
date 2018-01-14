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

public class Hospital extends WorldObject {

    public Hospital(Point position) {
        setGeometryManager(new GeometryAnimationManager("hospital", (Model) NodesCache.getInstance().get("hospitalModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(1.8f, 2f, 1.8f);//fix model size to 3
        getGeometryManager().setLocalTranslation(-0.9f,0, -0.9f);
        //params
        setSize(3);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.HOSPITAL_LEVELS_BUILD_COST[0]);
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
        return -GameParams.HOSPITAL_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        this.level = level;
        setLevelParams();
    }

    private void setLevelParams(){
        setParams(GameParams.HOSPITAL_LEVELS_MONTH_COST[level],
                GameParams.HOSPITAL_LEVELS_POWER[level],
                GameParams.HOSPITAL_LEVELS_FIRE[level],
                GameParams.HOSPITAL_LEVELS_WATER[level],
                GameParams.HOSPITAL_LEVELS_POLLUTION[level],
                GameParams.HOSPITAL_LEVELS_CRIMINAL[level],
                GameParams.HOSPITAL_LEVELS_HEALTH[level],
                GameParams.HOSPITAL_LEVELS_WORK[level],
                GameParams.HOSPITAL_LEVELS_HAPPINESS[level],
                GameParams.HOSPITAL_LEVELS_EDUCATION[level],
                GameParams.HOSPITAL_LEVELS_RADIUS[level]);
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
        if (level==GameParams.HOSPITAL_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.HOSPITAL_LEVELS_BUILD_COST[level+1];
    }
}
