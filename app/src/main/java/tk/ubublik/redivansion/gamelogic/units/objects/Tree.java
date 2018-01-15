package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryLoopAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Tree extends WorldObject {

    private static final String treeLoopAnimationKey = "treeLoopAnimation";

    public Tree(int x, int y){
        this(new Point(x,y));
    }

    public Tree(){
        this(0,0);
    }

    public Tree(Point position) {
        setGeometryManager(new GeometryLoopAnimationManager("tree", (Model) NodesCache.getInstance().get("treeModel")));
        setSize(1);
        setPosition(position);
        setNeedsRoad(false);
        setBuildCost(GameParams.TREE_LEVELS_BUILD_COST[0]);
        beginAnimation("build");
    }

    boolean loopStage = false;
    @Override
    public void onUpdate() {
        if (!loopStage) getGeometryManager().onUpdate();
    }

    @Override
    protected void beginAnimation(String animationName) {
        ((GeometryLoopAnimationManager)getGeometryManager()).beginAnimation(getLoopAnimation(), animationName);
    }

    @Override
    public void destroy(GeometryManager.OnAnimationEndListener onAnimationEndListener) {
        try {
            ((GeometryLoopAnimationManager) getGeometryManager()).beginAnimation(onAnimationEndListener, "destroy");
        } catch (Exception e){
            onAnimationEndListener.animationEnd();
        }
    }

    private GeometryLoopAnimationManager.OnAnimationEndListener getLoopAnimation(){
        return new GeometryLoopAnimationManager.OnAnimationEndListener() {
            @Override
            public void animationEnd() {
                loopStage = true;
                GeometryLoopAnimationManager geometryLoopAnimationManager = NodesCache.getInstance().getModel(treeLoopAnimationKey);
                if (geometryLoopAnimationManager==null){
                    geometryLoopAnimationManager = new GeometryLoopAnimationManager("tree", (Model) NodesCache.getInstance().get("treeModel"));
                    NodesCache.getInstance().addModel(treeLoopAnimationKey, geometryLoopAnimationManager);
                    geometryLoopAnimationManager.beginLoopAnimation(new String[]{"stage2", "stage1"});
                    geometryLoopAnimationManager.onUpdate();
                }
                Tree.this.setGeometryManager(geometryLoopAnimationManager.clone());
            }
        };
    }

    @Override
    public void recalculateParams() {
        setParams(GameParams.TREE_LEVELS_MONTH_COST[0],
                GameParams.TREE_LEVELS_POWER[0],
                GameParams.TREE_LEVELS_FIRE[0],
                GameParams.TREE_LEVELS_WATER[0],
                GameParams.TREE_LEVELS_POLLUTION[0],
                GameParams.TREE_LEVELS_CRIMINAL[0],
                GameParams.TREE_LEVELS_HEALTH[0],
                GameParams.TREE_LEVELS_WORK[0],
                GameParams.TREE_LEVELS_HAPPINESS[0],
                GameParams.TREE_LEVELS_EDUCATION[0],
                GameParams.TREE_LEVELS_RADIUS[0]);
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
