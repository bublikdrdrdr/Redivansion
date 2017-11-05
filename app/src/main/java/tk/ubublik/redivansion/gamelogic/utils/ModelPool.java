package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.scene.Node;

import java.util.LinkedList;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 04-Nov-17.
 */

public class ModelPool {

    private Node node;
    private DrawChecker drawChecker;
    private LinkedList<RuntimeAnimation> runtimeAnimations = new LinkedList<>();

    public ModelPool(Node node, DrawChecker drawChecker) {
        this.node = node;
        this.drawChecker = drawChecker;
    }

    public WorldObject add(WorldObject worldObject){
        RuntimeAnimation runtimeAnimation = getByWorldObject(worldObject);
        if (runtimeAnimation==null){
            runtimeAnimation = new RuntimeAnimation(worldObject.getGeometryManager(), worldObject.hashCode());
            runtimeAnimations.add(runtimeAnimation);
        }
        return worldObject;
    }

    private RuntimeAnimation getByWorldObject(WorldObject worldObject){
        for (RuntimeAnimation runtimeAnimation: runtimeAnimations){
            if (runtimeAnimation.getAnimationId() == worldObject.hashCode()){
                return runtimeAnimation;
            }
        }
        return null;
    }

    public void onUpdate(){
        for (RuntimeAnimation runtimeAnimation: runtimeAnimations){
        }
    }

    public WorldObject remove(WorldObject worldObject) {
        return worldObject;
    }

    public interface DrawChecker{
        boolean canUpdate(WorldObject worldObject);
    }
}
