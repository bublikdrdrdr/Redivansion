package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 06-Nov-17.
 */

public class Park extends WorldObject {

    public Park(Point position) {
        setGeometryManager(new GeometryAnimationManager("park", (Model) NodesCache.getInstance().get("houseModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(1.8f, 0.5f, 1.8f);// TODO: 07-Nov-17 fix model size
        getGeometryManager().setLocalTranslation(-0.9f,0, -0.9f);
        //params
        setSize(3);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.PARK_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build");
    }

    @Override
    public void recalculateParams() {
        setLevelParams();
    }

    @Override
    public int getLevelsCount() {
        return -GameParams.PARK_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        this.level = level;
        setLevelParams();
    }

    private void setLevelParams(){
        setParams(GameParams.PARK_LEVELS_MONTH_COST[level],
                GameParams.PARK_LEVELS_POWER[level],
                GameParams.PARK_LEVELS_FIRE[level],
                GameParams.PARK_LEVELS_WATER[level],
                GameParams.PARK_LEVELS_POLLUTION[level],
                GameParams.PARK_LEVELS_CRIMINAL[level],
                GameParams.PARK_LEVELS_HEALTH[level],
                GameParams.PARK_LEVELS_WORK[level],
                GameParams.PARK_LEVELS_HAPPINESS[level],
                GameParams.PARK_LEVELS_EDUCATION[level],
                GameParams.PARK_LEVELS_RADIUS[level]);
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
        if (level==GameParams.PARK_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.PARK_LEVELS_BUILD_COST[level+1];
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }
}
