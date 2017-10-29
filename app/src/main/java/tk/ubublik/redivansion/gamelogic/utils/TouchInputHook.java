package tk.ubublik.redivansion.gamelogic.utils;

import com.jme3.input.event.TouchEvent;

/**
 * Created by Bublik on 18-Oct-17.
 */

public interface TouchInputHook {

    boolean touchCaptured(TouchEvent touchEvent, float tfp);
}
