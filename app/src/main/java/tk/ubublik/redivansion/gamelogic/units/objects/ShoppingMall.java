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

public class ShoppingMall extends WorldObject {

    public ShoppingMall(Point position) {
        setGeometryManager(new GeometryAnimationManager("SHOPPING_MALL", (Model) NodesCache.getInstance().get("shopModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(0.5f, 1.2f, 0.5f);
        getGeometryManager().setLocalTranslation(-0.9f,0, -0.9f);
        //params
        setSize(2);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.SHOPPING_MALL_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build"+level);
    }

    @Override
    public void destroy(GeometryManager.OnAnimationEndListener onAnimationEndListener) {
        ((GeometryAnimationManager) getGeometryManager()).beginAnimation("destroy"+level, onAnimationEndListener);
    }

    @Override
    public void recalculateParams() {
        setLevelParams();
    }

    @Override
    public int getLevelsCount() {
        return -GameParams.SHOPPING_MALL_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        this.level = level;
        setLevelParams();
    }

    private void setLevelParams(){
        setParams(GameParams.SHOPPING_MALL_LEVELS_MONTH_COST[level],
                GameParams.SHOPPING_MALL_LEVELS_POWER[level],
                GameParams.SHOPPING_MALL_LEVELS_FIRE[level],
                GameParams.SHOPPING_MALL_LEVELS_WATER[level],
                GameParams.SHOPPING_MALL_LEVELS_POLLUTION[level],
                GameParams.SHOPPING_MALL_LEVELS_CRIMINAL[level],
                GameParams.SHOPPING_MALL_LEVELS_HEALTH[level],
                GameParams.SHOPPING_MALL_LEVELS_WORK[level],
                GameParams.SHOPPING_MALL_LEVELS_HAPPINESS[level],
                GameParams.SHOPPING_MALL_LEVELS_EDUCATION[level],
                GameParams.SHOPPING_MALL_LEVELS_RADIUS[level]);
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
        if (level==GameParams.SHOPPING_MALL_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.SHOPPING_MALL_LEVELS_BUILD_COST[level+1];
    }
}
