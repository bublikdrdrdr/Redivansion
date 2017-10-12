package tk.ubublik.redivansion.gamelogic.utils.game_tools;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 12-Oct-17.
 */

//select point on the center of screen
public class SingleBuilder extends SelectTool {
    @Override
    public void destroy() {

    }

    @Override
    public boolean canPut() {
        return false;
    }

    public WorldObject build(){
        return null;
    }
}
