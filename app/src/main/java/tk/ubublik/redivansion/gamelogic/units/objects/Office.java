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

public class Office extends Building {

    public Office(){
        this(new Point(0,0));
    }

    public Office(int x, int y){
        this(new Point(x,y));
    }

    public Office(Point position) {
        setGeometryManager(new GeometryAnimationManager("office", (Model)NodesCache.getInstance().get("officeModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(1.8f, 0.5f, 1.8f);
        getGeometryManager().setLocalTranslation(0,0, 0);
        //params
        setSize(2);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.OFFICE_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build_lvl_1");
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }

    private void beginAnimation(String animationName){
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation(animationName);
    }

    private void beginAnimation(String animationName, final String nextAnimation){
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation(animationName, new GeometryManager.OnAnimationEndListener() {
            @Override
            public void animationEnd() {
                ((GeometryAnimationManager)getGeometryManager()).beginAnimation(nextAnimation);
            }
        });
    }

    /*

    Level logic block

     */
    @Override
    public void recalculateParams() {
        int oldPower = power;
        setParamsByLevel(level);
        work = Math.max(GameParams.OFFICE_LEVELS_MAX_WORK[level],
                (int)(GameParams.OFFICE_LEVELS_MAX_WORK[level]*
                        (oldPower/(float)GameParams.OFFICE_LEVELS_POWER[level])*
                        GameParams.OFFICE_POWER_PRODUCTIVITY));
    }

    @Override
    public int getLevelsCount() {
        return GameParams.OFFICE_LEVELS_BUILD_COST.length;
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        beginAnimation("fastDestroy"+Integer.toString(getLevelNumber()), "fastBuild"+Integer.toString(level));
        this.level = level;
        setParamsByLevel(level);
        power = 0;//will set in next recalculateParams()
    }

    private void setParamsByLevel(int level){
        setParams(GameParams.OFFICE_LEVELS_MONTH_COST[level],
                GameParams.OFFICE_LEVELS_POWER[level],
                GameParams.OFFICE_LEVELS_FIRE[level],
                GameParams.OFFICE_LEVELS_WATER[level],
                GameParams.OFFICE_LEVELS_POLLUTION[level],
                GameParams.OFFICE_LEVELS_CRIMINAL[level],
                GameParams.OFFICE_LEVELS_HEALTH[level],
                GameParams.OFFICE_LEVELS_MAX_WORK[level],
                GameParams.OFFICE_LEVELS_HAPPINESS[level],
                GameParams.OFFICE_LEVELS_EDUCATION[level],
                GameParams.OFFICE_RADIUS);
    }

    @Override
    public int getLevelNumber() {
        return level;
    }

    @Override
    public int getMoneyDelta() {
        return -GameParams.OFFICE_LEVELS_MONTH_COST[getLevelNumber()];
    }

    @Override
    public int getUpgradeCost() {
        if (level==GameParams.OFFICE_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.OFFICE_LEVELS_BUILD_COST[level+1];
    }
}
