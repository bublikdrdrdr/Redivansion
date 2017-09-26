package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Office extends Building {

    public Office(){
        this(new Point(0,0));
    }

    public Office(Point position) {
        //TODO: add "office" model and load it
        super(new GeometryAnimationManager((Model)NodesCache.getInstance().get("officeModel")));
        setSize(1);
        setPosition(position);
        //getGeometryManager().setLocalTranslation(-.5f, 0,-.5f);
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation("build_lvl_1");
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
}
