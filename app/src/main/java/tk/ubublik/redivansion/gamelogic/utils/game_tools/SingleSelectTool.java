package tk.ubublik.redivansion.gamelogic.utils.game_tools;

/**
 * Created by Bublik on 11-Oct-17.
 */

//select building to edit/remove
public class SingleSelectTool extends SelectTool{

    @Override
    public void destroy() {

    }

    @Override
    public boolean canPut() {
        return false;
    }
}
