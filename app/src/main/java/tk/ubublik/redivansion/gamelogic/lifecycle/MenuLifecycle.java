package tk.ubublik.redivansion.gamelogic.lifecycle;

import android.view.Menu;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class MenuLifecycle implements Lifecycle {

    enum MenuResult{ START_LEVEL, START_TUTORIAL, EXIT};
    public int startLevelNumber;
    public MenuResult menuResult;

    public MenuLifecycle(){

    }

    @Override
    public LifecycleType getType() {
        return LifecycleType.MENU;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }
}
