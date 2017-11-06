package tk.ubublik.redivansion.gamelogic.units.objects;


import android.graphics.Point;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
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
    public enum ResourceType{POWER, FIRE, WATER, POLLUTION, CRIMINAL, HEALTH, WORK, HAPPINESS, EDUCATION}

    //only 3D model
    private GeometryManager geometryManager;

    //notification icon (if something wrong with the object)
    private Node icon = new Node("icon");
    private IconState iconState = IconState.NONE;

    //game logic properties
    private Point position;
    private int buildCost;
    protected int level = 0;
    private int size;
    private boolean permanent;

    //state variables
    public boolean roadConnected = false;
    private boolean needsRoad = false;

    public int monthCost;
    public int power;
    public int fire;
    public int water;
    public int pollution;
    public int criminal;
    public int health;
    public int work;
    public int happiness;
    public int education;
    public float radiusSqr;

    public abstract void recalculateParams();
    public abstract int getLevelsCount();
    public abstract void setLevelNumber(int level);
    public abstract int getLevelNumber();
    public abstract int getMoneyDelta();
    public abstract int getUpgradeCost();

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
        //test
        BitmapFont fnt = StaticAssetManager.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, 6, 3));
        txt.setQueueBucket(RenderQueue.Bucket.Transparent);
        txt.setSize( 0.3f );
        txt.setText(this.getClass().getSimpleName());
        txt.setLocalTranslation(-0.5f,1,0);
        txt.rotate(-0.1f* FastMath.PI,0.25f* FastMath.PI, 0);
        this.attachChild(txt);
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

    private void addIconBox(ColorRGBA color) {
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

    ////////////////////

    public int getResourceValue(ResourceType resourceType) {
        switch (resourceType) {
            case POWER:
                return power;
            case FIRE:
                return fire;
            case WATER:
                return water;
            case POLLUTION:
                return pollution;
            case CRIMINAL:
                return criminal;
            case HEALTH:
                return health;
            case WORK:
                return work;
            case HAPPINESS:
                return happiness;
            case EDUCATION:
                return education;
            default:
                throw new IllegalArgumentException("Unknown ResourceType: " + resourceType);
        }
    }

    public void setResourceValue(ResourceType resourceType, int value){
        switch (resourceType){
            case POWER: power = value;
                break;
            case FIRE: fire = value;
                break;
            case WATER: water = value;
                break;
            case POLLUTION: pollution = value;
                break;
            case CRIMINAL: criminal = value;
                break;
            case HEALTH: health = value;
                break;
            case WORK: work = value;
                break;
            case HAPPINESS: happiness = value;
                break;
            case EDUCATION: education = value;
                break;
        }
    }

    protected void setParams(int monthCost, int power, int fire, int water, int pollution, int criminal, int health, int work, int happiness, int education, float radiusSqr){
        this.monthCost = monthCost;
        this.power = power;
        this.fire = fire;
        this.water = water;
        this.pollution = pollution;
        this.criminal = criminal;
        this.health = health;
        this.work = work;
        this.happiness = happiness;
        this.education = education;
        this.radiusSqr = radiusSqr;
    }
}
