package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private Map<String, Spatial> map;

    public static NodesCache getInstance() {
        return ourInstance;
    }

    private NodesCache() {
        map = new HashMap<>();
    }

    public void put(String key, Spatial node){
        map.put(key, node);
    }

    public Spatial  get(String key){
        return map.get(key);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public void removeAll(){
        map.clear();
    }
}
