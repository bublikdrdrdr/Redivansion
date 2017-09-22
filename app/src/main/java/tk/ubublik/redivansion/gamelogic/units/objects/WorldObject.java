package tk.ubublik.redivansion.gamelogic.units.objects;


import android.graphics.Point;

import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public abstract class WorldObject extends Node{

    //only 3D model
    private GeometryManager geometryManager;

    //notification icon (if something wrong with the object)
    private Node icon;

    //game logic properties
    private Point position;
    private int buildCost;
    private int size;
    private WorldObjectLevel level;
    private boolean permanent;

    public WorldObject(GeometryManager geometryManager){
        setGeometryManager(geometryManager);
    }

    public GeometryManager getGeometryManager() {
        return geometryManager;
    }

    public void setGeometryManager(GeometryManager geometryManager) {
        this.geometryManager = geometryManager;
        if (geometryManager!=null){
            this.attachChild(geometryManager);
        }
    }

    public void onUpdate(){
        this.geometryManager.onUpdate();
    }

    public abstract byte[] toBytes();

    public abstract void parseBytes();

    public Node getIcon() {
        return icon;
    }

    public void setIcon(Node icon) {
        this.icon = icon;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public void setBuildCost(int buildCost) {
        this.buildCost = buildCost;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public WorldObjectLevel getLevel() {
        return level;
    }

    public void setLevel(WorldObjectLevel level) {
        this.level = level;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
}
