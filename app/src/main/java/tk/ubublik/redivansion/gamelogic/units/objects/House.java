package tk.ubublik.redivansion.gamelogic.units.objects;

import android.graphics.Point;

import com.jme3.cinematic.events.SoundTrack;
import com.jme3.math.Vector3f;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.utils.ByteSettings;
import tk.ubublik.redivansion.gamelogic.utils.GameParams;
import tk.ubublik.redivansion.gamelogic.utils.NodesCache;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class House extends Building {

    private int population = GameParams.HOUSE_LEVELS_MAX_POPULATION[0]/3;

    public House(int x, int y){
        this(new Point(x,y));
    }

    public House(Point position) {
        setGeometryManager(new GeometryAnimationManager("house", (Model) NodesCache.getInstance().get("houseModel")));
        //local model scale and move
        getGeometryManager().setLocalScale(0.8f, 0.9f, 0.8f);
        getGeometryManager().setLocalTranslation(-0.9f,0, -0.9f);
        //params
        setSize(2);
        setPosition(position);
        setNeedsRoad(true);
        setBuildCost(GameParams.HOUSE_LEVELS_BUILD_COST[0]);
        //
        beginAnimation("build"+level);
    }

    @Override
    public void destroy(GeometryManager.OnAnimationEndListener onAnimationEndListener) {
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation("destroy"+level, onAnimationEndListener);
    }

    private static final int INT_SIZE = 4;
    @Override
    public byte[] toBytes() {
        byte[] superBytes = super.toBytes();
        byte[] bytes = new byte[superBytes.length+INT_SIZE];
        ByteSettings.ByteConverter.insertArray(bytes, superBytes, 0);
        ByteSettings.ByteConverter.insertArray(bytes, ByteSettings.ByteConverter.getArray(population), superBytes.length);
        return bytes;
    }

    @Override
    public int parseBytes(byte[] bytes, int index) {
        int size = super.parseBytes(bytes, index);
        index+= size;
        population = ByteSettings.ByteConverter.getInt(bytes, index);
        return size+INT_SIZE;
    }

    private void beginAnimation(String animationName, final String nextAnimation){
        ((GeometryAnimationManager)getGeometryManager()).beginAnimation(animationName, new GeometryManager.OnAnimationEndListener() {
            @Override
            public void animationEnd() {
                ((GeometryAnimationManager)getGeometryManager()).beginAnimation(nextAnimation);
            }
        });
    }

    @Override
    public int getLevelsCount() {
        return GameParams.HOUSE_LEVELS_BUILD_COST.length;
    }

    @Override
    public void recalculateParams() {
        setParams(GameParams.HOUSE_LEVELS_MONTH_COST[level],
                getPowerNeed(),
                getFireNeed(),
                getWaterNeed(),
                getPollutionNeed(),
                getCriminalNeed(),
                getHealthNeed(),
                getWorkNeed(),
                getHappinessNeed(),
                getEducationNeed(),
                0);
    }

    @Override
    public void setLevelNumber(int level) {
        if (level<0 || level>=getLevelsCount()) throw new IllegalArgumentException("Wrong level number: "+level);
        beginAnimation("destroy"+(level-1), "build"+(level));
        this.level = level;
        setPopulation(getPopulation());
    }

    @Override
    public int getLevelNumber() {
        return level;
    }

    @Override
    public int getMoneyDelta() {
        return monthCost +(int)(population*GameParams.MONEY_PP);
    }

    @Override
    public int getUpgradeCost() {
        if (level==GameParams.HOUSE_LEVELS_BUILD_COST.length-1) return 0;
        return GameParams.HOUSE_LEVELS_BUILD_COST[level+1];
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = Math.min(population, getMaxPopulation());
    }

    public int getMaxPopulation(){
        return GameParams.HOUSE_LEVELS_MAX_POPULATION[level];
    }

    public void checkPopulation(){
        float result;
        if(checkMainParams() <=0 && checkMinorParams() > checkMainParams())
            result = checkMainParams();
        else result = checkMainParams() + checkMinorParams();
        int lastPopulation = getPopulation();
        setPopulation(lastPopulation + (int)result);
    }

    private float checkMainParams(){
        int positive = 0;
        boolean pow = false;
        if(power > getPowerNeed()*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)
            pow = true;
        if(water > getWaterNeed()*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)
            positive++;
        if(fire > getFireNeed()*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)
            positive++;
        if(health > getHealthNeed()*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)
            positive++;
        if(work > getWorkNeed()*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)
            positive++;
        if(criminal > getCriminalNeed()*GameParams.POPULATION_GROW_MAIN_RESOURCES_PERCENT)
            positive++;
        if(pow && positive >= 5)
            return 2;
        else if(pow && positive >= 3)
            return 1;
        else if(!pow && positive >= 3)
            return 0;
        else return -1;
    }

    private float checkMinorParams(){
        int positive = 0;
        if(pollution > getPollutionNeed()*GameParams.POPULATION_GROW_MINOR_RESOURCES_PERCENT)
            positive++;
        if(happiness > getHappinessNeed()*GameParams.POPULATION_GROW_MINOR_RESOURCES_PERCENT)
            positive++;
        if(education > getEducationNeed()*GameParams.POPULATION_GROW_MINOR_RESOURCES_PERCENT)
            positive++;
        if(positive >= 2)
            return 1;
        else if(positive >= 0)
            return 0;
        else return -1;
    }

    private int getPowerNeed(){
        return GameParams.HOUSE_LEVELS_POWER[level]+(int)(population*GameParams.POWER_PP);
    }

    private int getFireNeed(){
        return GameParams.HOUSE_LEVELS_FIRE[level]+(int)(population*GameParams.FIRE_PP);
    }

    private int getWaterNeed(){
        return GameParams.HOUSE_LEVELS_WATER[level]+(int)(population*GameParams.WATER_PP);
    }

    private int getPollutionNeed(){
        return GameParams.HOUSE_LEVELS_POLLUTION[level]+(int)(population*GameParams.POLLUTION_PP);
    }

    private int getCriminalNeed(){
        return GameParams.HOUSE_LEVELS_CRIMINAL[level]+(int)(population*GameParams.CRIMINAL_PP);
    }

    private int getHealthNeed(){
        return GameParams.HOUSE_LEVELS_HEALTH[level]+(int)(population*GameParams.HEALTH_PP);
    }

    private int getWorkNeed(){
        return GameParams.HOUSE_LEVELS_WORK[level]+(int)(population*GameParams.WORK_PP);
    }

    private int getHappinessNeed(){
        return GameParams.HOUSE_LEVELS_HAPPINESS[level]+(int)(population*GameParams.HAPPINESS_PP);
    }

    private int getEducationNeed(){
        return GameParams.HOUSE_LEVELS_EDUCATION[level]+(int)(population*GameParams.EDUCATION_PP);
    }
}
