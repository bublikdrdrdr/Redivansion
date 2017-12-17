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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.utils.ByteSettings.*;
import tk.ubublik.redivansion.gamelogic.utils.StaticAssetManager;

import static tk.ubublik.redivansion.gamelogic.utils.ByteSettings.ByteConverter.*;

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
        /*/test
        BitmapFont fnt = StaticAssetManager.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, 6, 3));
        txt.setQueueBucket(RenderQueue.Bucket.Transparent);
        txt.setSize( 0.3f );
        txt.setText(this.getClass().getSimpleName());
        txt.setLocalTranslation(-0.5f,1,0);
        txt.rotate(-0.1f* FastMath.PI,0.25f* FastMath.PI, 0);
        this.attachChild(txt);*/
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

    public abstract void destroy(GeometryManager.OnAnimationEndListener onAnimationEndListener);

    protected void beginAnimation(String animationName){
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation(animationName);
    }

    public void onUpdate(){
        this.geometryManager.onUpdate();
    }

    private static final int BYTE_SIZE = 1;
    private static final int INT_SIZE = 4;
    private static final int FLOAT_SIZE = 4;
    private static final int POINT_SIZE = INT_SIZE*2;
    public byte[] toBytes() {
        int index = 0;
        byte[] bytes = new byte[INT_SIZE + POINT_SIZE + INT_SIZE * 12 + BYTE_SIZE + FLOAT_SIZE];
        insertArray(bytes, getArray(this.getClass().getName().hashCode()), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(position.x), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(position.y), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(level), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(size), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(permanent), index);
        index += BYTE_SIZE;
        insertArray(bytes, getArray(monthCost), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(power), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(fire), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(water), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(pollution), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(criminal), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(health), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(work), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(happiness), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(education), index);
        index += INT_SIZE;
        insertArray(bytes, getArray(radiusSqr), index);
        //index += FLOAT_SIZE;
        return bytes;
    }

    private static final Class[] supportedObjects = new Class[]{
            FireStation.class,
            Hospital.class,
            House.class,
            Office.class,
            Park.class,
            PoliceStation.class,
            Road.class,
            School.class,
            ShoppingMall.class,
            ThermalPowerPlant.class,
            Tree.class,
            Water.class,
            WaterPlant.class
    };

    public static WorldObject parseFromBytes(byte[] bytes, int index) throws InstantiationException, IllegalAccessException, UnsupportedOperationException, NoSuchMethodException, InvocationTargetException {
        int hash = getInt(bytes, index);
        index +=INT_SIZE;
        for (Class clazz: supportedObjects){
            if (clazz.getName().hashCode()==hash){
                return fromTypedBytes((Class<? extends WorldObject>) clazz, bytes, index);
            }
        }
        throw new UnsupportedOperationException("Unknown class to parse with hash: "+hash);
    }

    private static WorldObject fromTypedBytes(Class<? extends WorldObject> clazz, byte[] bytes, int index) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = clazz.getDeclaredConstructor(new Class[]{Point.class});
        WorldObject worldObject = (WorldObject) constructor.newInstance(new Point(0,0));
        worldObject.parseBytes(bytes, index);
        return worldObject;
    }

    public int parseBytes(byte[] bytes, int index) {
        int originIndex = index;
        position = new Point(getInt(bytes, index), ByteConverter.getInt(bytes, index + INT_SIZE));
        index += INT_SIZE * 2;
        level = getInt(bytes, index);
        index += INT_SIZE;
        size = getInt(bytes, index);
        index += INT_SIZE;
        permanent = getBoolean(bytes, index);
        index += BYTE_SIZE;
        monthCost = getInt(bytes, index);
        index += INT_SIZE;
        power = getInt(bytes, index);
        index += INT_SIZE;
        fire = getInt(bytes, index);
        index += INT_SIZE;
        water = getInt(bytes, index);
        index += INT_SIZE;
        pollution = getInt(bytes, index);
        index += INT_SIZE;
        criminal = getInt(bytes, index);
        index += INT_SIZE;
        health = getInt(bytes, index);
        index += INT_SIZE;
        work = getInt(bytes, index);
        index += INT_SIZE;
        happiness = getInt(bytes, index);
        index += INT_SIZE;
        education = getInt(bytes, index);
        index += INT_SIZE;
        radiusSqr = getFloat(bytes, index);
        index+=FLOAT_SIZE;
        return index-originIndex;
    }

    public static int getDefaultSize(){return 1;}

    public IconState getIconState() {
        return iconState;
    }

    public void setIconState(IconState iconState) {
        if (iconState != this.iconState){
            this.iconState = iconState;
            icon.detachAllChildren();
            switch (iconState){
                //case WARNING: addIconBox(ColorRGBA.Yellow); break;
                //case ERROR: addIconBox(ColorRGBA.Red); break;
            }
        }
    }

    /*private void addIconBox(ColorRGBA color) {
        float boxSize = 0.2f;
        Box box = new Box(boxSize, boxSize, boxSize);
        Material material = new Material(StaticAssetManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        Geometry iconBox = new Geometry("icon box", box);
        iconBox.setMaterial(material);
        icon.attachChild(iconBox);
    }*/

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
