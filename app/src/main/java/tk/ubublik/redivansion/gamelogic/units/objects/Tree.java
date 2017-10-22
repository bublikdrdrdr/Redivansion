package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryLoopAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Tree extends WorldObject {

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
        buildAnimation();
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }

    private void buildAnimation(){
        ((GeometryLoopAnimationManager)getGeometryManager()).beginAnimation(getLoopAnimation(), "build");
    }

    private GeometryLoopAnimationManager.OnAnimationEndListener getLoopAnimation(){
        return new GeometryLoopAnimationManager.OnAnimationEndListener() {
            @Override
            public void animationEnd() {
                ((GeometryLoopAnimationManager)getGeometryManager()).beginLoopAnimation(new String[]{"stage1", "stage2"});
            }
        };
    }
}
