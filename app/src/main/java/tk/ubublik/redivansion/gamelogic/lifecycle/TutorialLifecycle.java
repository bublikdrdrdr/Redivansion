package tk.ubublik.redivansion.gamelogic.lifecycle;

/**
 * Created by Bublik on 02-Sep-17.
 */

public class TutorialLifecycle implements Lifecycle {
    @Override
    public LifecycleType getType() {
        return LifecycleType.TUTORIAL;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }
}
