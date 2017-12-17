package tk.ubublik.redivansion.gamelogic.utils;

import java.util.HashMap;
import java.util.Map;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryLoopAnimationManager;

/**
 * Created by Bublik on 02-Sep-17.
 *
 * Contains all loaded nodes (3d, gui, audio etc.)
 * Nodes must be loaded only when LoadingLifecycle is running and used later directly from this cache
 *
 * todo: make thread safe (optionally)
 */
public class NodesCache {
    private static final NodesCache ourInstance = new NodesCache();

    private Map<String, Object> map;
    private Map<String, GeometryLoopAnimationManager> animations;

    public static NodesCache getInstance() {
        return ourInstance;
    }

    private NodesCache() {
        map = new HashMap<>();
        animations = new HashMap<>();
    }

    public void put(String key, Object node){
        map.put(key, node);
    }

    public Object get(String key){
        return map.get(key);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public void removeAll(){
        map.clear();
        animations.clear();
    }

    public void addModel(String key, GeometryLoopAnimationManager geometryLoopAnimationManager){
        animations.put(key, geometryLoopAnimationManager);
    }

    public void removeModels(){
        animations.clear();
    }

    public GeometryLoopAnimationManager getModel(String key){
        return animations.get(key);
    }

    public void updateModels() {
        for (GeometryLoopAnimationManager geometryLoopAnimationManager: animations.values()){
            geometryLoopAnimationManager.onUpdate();
            //new ModelExecutor(geometryLoopAnimationManager);
        }
    }

    @Deprecated
    private class ModelExecutor implements Runnable{
        private GeometryLoopAnimationManager geometryLoopAnimationManager;

        public ModelExecutor(GeometryLoopAnimationManager geometryLoopAnimationManager) {
            this.geometryLoopAnimationManager = geometryLoopAnimationManager;
            Thread thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            geometryLoopAnimationManager.onUpdate();
        }
    }
}
