package tk.ubublik.redivansion.gamelogic.lifecycle;

/**
 * Created by Bublik on 01-Sep-17.
 */

public interface Lifecycle {
    LifecycleType getType();
    boolean isDone();
    void update();
}