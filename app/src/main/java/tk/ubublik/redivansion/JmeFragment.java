package tk.ubublik.redivansion;

import android.app.AlertDialog;

import com.jme3.app.AndroidHarnessFragment;
import com.jme3.app.SimpleApplication;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;

/**
 * Created by Bublik on 20-Aug-17.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class JmeFragment extends AndroidHarnessFragment {



    public JmeFragment() {
        // Set main project class (fully qualified path)
        appClass = "tk.ubublik.redivansion.gamelogic.Main";

        // Set the desired EGL configuration
        eglBitsPerPixel = 24;
        eglAlphaBits = 0;
        eglDepthBits = 16;
        eglSamples = 1;//anti-aliasing
        eglStencilBits = 0;

        // Set the maximum framerate
        // (default = -1 for unlimited)
        frameRate = -1;

        // Set the maximum resolution dimension
        // (the smaller side, height or width, is set automatically
        // to maintain the original device screen aspect ratio)
        // (default = -1 to match device screen resolution)
        maxResolutionDimension = -1;

        // Set input configuration settings
        joystickEventsEnabled = false;
        keyEventsEnabled = true;
        mouseEventsEnabled = true;

        // Set application exit settings
        finishOnAppStop = true;
        //disable exit message
        // TODO: use gui back press handling and confirmation
        handleExitHook = false;
        //exitDialogTitle = "Confirm exit";
        //exitDialogMessage = "You really wanna close this game? Бездушная скотина";

        // Set splash screen resource id, if used
        // (default = 0, no splash screen)
        // For example, if the image file name is "splash"...
        //     splashPicID = R.drawable.splash;
        splashPicID = 0;
    }


    // TODO: 03-Sep-17 find a better place to put next code
    public static final String BACK_PRESS_EVENT = "BackPressEvent";
    @Override
    public void initialize() {
        super.initialize();
        super.getJmeApplication().getInputManager().addMapping(BACK_PRESS_EVENT, new TouchTrigger(TouchInput.KEYCODE_BACK));
    }
}
