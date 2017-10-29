package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
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
        setBuildCost(GameParams.OFFICE_BUILD_COST);
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

    public void setLevel(int level){

    }

    private void beginAnimation(String animationName){
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation(animationName);
    }


}
