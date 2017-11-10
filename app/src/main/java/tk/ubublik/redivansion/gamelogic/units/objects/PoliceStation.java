package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 06-Nov-17.
 */

public class PoliceStation extends WorldObject {

    public PoliceStation(Point position) {
        setGeometryManager(new GeometryAnimationManager("police station", (Model) NodesCache.getInstance().get("houseModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(1.8f, 0.5f, 1.8f);
        getGeometryManager().setLocalTranslation(-0.9f,0, -0.9f);
        //params
        setSize(2);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.POLICE_STATION_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build");
    }

    @Override
    public void recalculateParams() {
        setLevelParams();
    }

    @Override
    public int getLevelsCount() {
        return -GameParams.POLICE_STATION_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        this.level = level;
        setLevelParams();
    }

    private void setLevelParams(){
        setParams(GameParams.POLICE_STATION_LEVELS_MONTH_COST[level],
                GameParams.POLICE_STATION_LEVELS_POWER[level],
                GameParams.POLICE_STATION_LEVELS_FIRE[level],
                GameParams.POLICE_STATION_LEVELS_WATER[level],
                GameParams.POLICE_STATION_LEVELS_POLLUTION[level],
                GameParams.POLICE_STATION_LEVELS_CRIMINAL[level],
                GameParams.POLICE_STATION_LEVELS_HEALTH[level],
                GameParams.POLICE_STATION_LEVELS_WORK[level],
                GameParams.POLICE_STATION_LEVELS_HAPPINESS[level],
                GameParams.POLICE_STATION_LEVELS_EDUCATION[level],
                GameParams.POLICE_STATION_LEVELS_RADIUS[level]);
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
        if (level==GameParams.POLICE_STATION_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.POLICE_STATION_LEVELS_BUILD_COST[level+1];
    }
}
