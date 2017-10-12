package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import com.jme3.scene.Node;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.utils.MapRenderer;

/**
 * Created by Bublik on 10-Oct-17.
 */

public abstract class SelectTool {

    public abstract void destroy();
    public abstract boolean canPut();
    public void onUpdate(){};
}
