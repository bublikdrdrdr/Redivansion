package tk.ubublik.redivansion.gamelogic.units.objects;


import android.graphics.Point;

import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public abstract class WorldObject extends Node{

    private GeometryManager geometryManager;

    //game logic properties
    private Point position;
    private int buildCost;
    private int size;
    private WorldObjectLevel level;
    private boolean permanent;

    public WorldObject(){};

    public WorldObject(GeometryManager geometryManager){
        this.geometryManager = geometryManager;
    }

    public GeometryManager getGeometryManager() {
        return geometryManager;
    }

    public void setGeometryManager(GeometryManager geometryManager) {
        this.geometryManager = geometryManager;
    }

    public abstract byte[] toBytes();

    public abstract void parseBytes();
}
