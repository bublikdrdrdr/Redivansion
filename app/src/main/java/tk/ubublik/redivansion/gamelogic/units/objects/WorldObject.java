package tk.ubublik.redivansion.gamelogic.units.objects;


import android.graphics.Point;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

/**
 * Created by Bublik on 20-Aug-17.
 */

public abstract class WorldObject extends Node{

    public enum IconState {NONE, WARNING, ERROR}

    //only 3D model
    private GeometryManager geometryManager;

    //notification icon (if something wrong with the object)
    private Node icon = new Node("icon");
    private IconState iconState = IconState.NONE;

    //game logic properties
    private Point position;
    private int buildCost;
    private int size;
    private WorldObjectLevel level;
    private boolean permanent;

    //state variables
    public boolean roadConnected = false; //mb volatile?
    private boolean needsRoad = false;

    public WorldObject(){
        this(0,0);
    }

    public WorldObject(int x, int y){
        this(new Point(x,y));
    }

    public WorldObject(Point position){
        this(position, 1);
    }

    public WorldObject(int x, int y, int size){
        this(new Point(x,y), size);
    }

    public WorldObject(Point position, int size){
        this.position = position;
        this.size = size;
        icon.setLocalTranslation(0,1,0);
        attachChild(icon);
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

    public static int getDefaultSize(){return 1;}

    public IconState getIconState() {
        return iconState;
    }

    public void setIconState(IconState iconState) {
        if (iconState != this.iconState){
            this.iconState = iconState;
            icon.detachAllChildren();
            switch (iconState){
                case WARNING: addIconBox(ColorRGBA.Yellow); break;
                case ERROR: addIconBox(ColorRGBA.Red); break;
            }
        }
    }

    private void addIconBox(ColorRGBA color){
        float boxSize = 0.2f;
        Box box = new Box(boxSize, boxSize, boxSize);
        Material material = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        Geometry iconBox = new Geometry("icon box", box);
        iconBox.setMaterial(material);
        icon.attachChild(iconBox);
    }

    //////////////////////

    public Node getIcon() {
        return icon;
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

    protected void setBuildCost(int buildCost) {
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

    protected void setLevel(WorldObjectLevel level) {
        this.level = level;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean needsRoad() {
        return needsRoad;
    }

    public void setNeedsRoad(boolean needsRoad) {
        this.needsRoad = needsRoad;
    }
}
