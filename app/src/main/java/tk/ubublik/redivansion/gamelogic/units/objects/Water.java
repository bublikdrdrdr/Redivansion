package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Water extends WorldObject{

    public Water(Point position) {
        super(position);
        setSize(1);
        setPermanent(true);
    }

    @Override
    public void destroy(GeometryManager.OnAnimationEndListener onAnimationEndListener) {
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation("destroy", onAnimationEndListener);
    }

    @Override
    public void recalculateParams() {

    }

    @Override
    public int getLevelsCount() {
        return 0;
    }

    @Override
    public void setLevelNumber(int level) {

    }

    @Override
    public int getLevelNumber() {
        return 0;
    }

    @Override
    public int getMoneyDelta() {
        return 0;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }
}
