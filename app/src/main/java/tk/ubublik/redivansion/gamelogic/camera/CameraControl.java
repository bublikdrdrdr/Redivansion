package tk.ubublik.redivansion.gamelogic.camera;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.CameraInput;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.Joystick;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 * Created by Bublik on 09-Sep-17.
 * Custom FlyByCamera class
 * @see com.jme3.input.ChaseCamera
 */
// TODO: 09-Sep-17 set this instead FlyByCamera
public class CameraControl extends ChaseCamera {

    private static String[] mappings = new String[]{
            CameraInput.FLYCAM_LEFT,
            CameraInput.FLYCAM_RIGHT,
            CameraInput.FLYCAM_UP,
            CameraInput.FLYCAM_DOWN,

            CameraInput.FLYCAM_STRAFELEFT,
            CameraInput.FLYCAM_STRAFERIGHT,
            CameraInput.FLYCAM_FORWARD,
            CameraInput.FLYCAM_BACKWARD,

            CameraInput.FLYCAM_ZOOMIN,
            CameraInput.FLYCAM_ZOOMOUT,
            CameraInput.FLYCAM_ROTATEDRAG,

            CameraInput.FLYCAM_RISE,
            CameraInput.FLYCAM_LOWER,

            CameraInput.FLYCAM_INVERTY
    };


    public CameraControl(Camera cam, Spatial target, InputManager inputManager) {
        super(cam, target, inputManager);
    }
}
