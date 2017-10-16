package tk.ubublik.redivansion.gamelogic.camera;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.CameraInput;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.Joystick;
import com.jme3.input.JoystickAxis;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.system.SystemListener;

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 09-Sep-17.
 * Custom FlyByCamera class
 * @see com.jme3.input.FlyByCamera
 */
// TODO: 10-Sep-17 CLEAN!
public class CameraControl implements ActionListener, AnalogListener, TouchListener {

    public static final float minFov = 10f;
    public static final float maxFov = 30f;
    private float currentFoV = 10f;

    public static final String TOUCH_MAPPING = "Mapping touch";
    private static String[] mappings = new String[]{
            CameraInput.FLYCAM_STRAFELEFT,
            CameraInput.FLYCAM_STRAFERIGHT,
            CameraInput.FLYCAM_FORWARD,
            CameraInput.FLYCAM_BACKWARD,
            TOUCH_MAPPING
    };

    protected Camera cam;
    protected Vector3f initialUpVec;
    protected float moveSpeed = 3f;
    protected float moveYSpeed = 2f;
    protected float moveSpeedScale = (550.f / MainActivity.getScreenDPI());
    protected MotionAllowedListener motionAllowed = null;
    protected boolean enabled = true;
    protected boolean dragToRotate = false;
    protected boolean canRotate = false;
    protected InputManager inputManager;

    private float screenAspect = 1f;

    /**
     * Creates a new FlyByCamera to control the given Camera object.
     *
     * @param cam
     */
    public CameraControl(Camera cam, InputManager inputManager) {
        this.cam = cam;
        screenAspect = cam.getWidth() / (float) cam.getHeight();
        initialUpVec = cam.getUp().clone();
        registerWithInput(inputManager);
        setDefaultPosition();
    }

    public void setDefaultPosition() {
        cam.setLocation(new Vector3f(10, 20, 10));
        setFov();
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
    }


    public void onUpdate() {

    }

    /**
     * Sets the up vector that should be used for the camera.
     *
     * @param upVec
     */
    public void setUpVector(Vector3f upVec) {
        initialUpVec.set(upVec);
    }

    public void setMotionAllowedListener(MotionAllowedListener listener) {
        this.motionAllowed = listener;
    }

    /**
     * Sets the move speed. The speed is given in world units per second.
     *
     * @param moveSpeed
     */
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Gets the move speed. The speed is given in world units per second.
     *
     * @return moveSpeed
     */
    public float getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * @param enable If false, the camera will ignore input.
     */
    public void setEnabled(boolean enable) {
        if (enabled && !enable) {
            if (inputManager != null && (!dragToRotate || (dragToRotate && canRotate))) {
                inputManager.setCursorVisible(true);
            }
        }
        enabled = enable;
    }

    /**
     * @return If enabled
     * @see FlyByCamera#setEnabled(boolean)
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Registers the FlyByCamera to receive input events from the provided
     * Dispatcher.
     *
     * @param inputManager
     */
    public void registerWithInput(InputManager inputManager) {
        this.inputManager = inputManager;
        inputManager.addMapping(CameraInput.FLYCAM_STRAFELEFT, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(CameraInput.FLYCAM_STRAFERIGHT, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(CameraInput.FLYCAM_FORWARD, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping(CameraInput.FLYCAM_BACKWARD, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(TOUCH_MAPPING, new TouchTrigger(TouchInput.ALL));

        inputManager.addListener(this, mappings);
        inputManager.setCursorVisible(dragToRotate || !isEnabled());
    }

    /**
     * Unregisters the FlyByCamera from the event Dispatcher.
     */
    public void unregisterInput() {

        if (inputManager == null) {
            return;
        }

        for (String s : mappings) {
            if (inputManager.hasMapping(s)) {
                inputManager.deleteMapping(s);
            }
        }

        inputManager.removeListener(this);
        inputManager.setCursorVisible(!dragToRotate);

        Joystick[] joysticks = inputManager.getJoysticks();
        if (joysticks != null && joysticks.length > 0) {
            Joystick joystick = joysticks[0];

            // No way to unassing axis
        }
    }

    protected void moveCamera(float value, boolean sideways) {
        Vector3f vel = new Vector3f();
        Vector3f pos = cam.getLocation().clone();

        if (sideways) {
            cam.getLeft(vel);
        } else {
            cam.getDirection(vel);
            vel.setY(0);
            vel.normalize();
            vel.multLocal(moveYSpeed);
        }
        vel.multLocal(value * moveSpeed * moveSpeedScale);

        if (motionAllowed != null)
            motionAllowed.checkMotionAllowed(pos, vel);
        else
            pos.addLocal(vel);

        cam.setLocation(pos);
    }

    @Override
    public void onAction(String name, boolean value, float tpf) {

    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!enabled)
            return;

        if (name.equals(CameraInput.FLYCAM_FORWARD)) {
            moveCamera(value, false);
        } else if (name.equals(CameraInput.FLYCAM_BACKWARD)) {
            moveCamera(-value, false);
        } else if (name.equals(CameraInput.FLYCAM_STRAFELEFT)) {
            moveCamera(-value, true);
        } else if (name.equals(CameraInput.FLYCAM_STRAFERIGHT)) {
            moveCamera(value, true);
        }
    }

    public Vector3f getCameraCenterPoint() {
        return getCameraCenterPoint(cam);
    }

    @Deprecated
    public static Vector3f getCameraCenterPointV1(Camera camera) {
        CollisionResults collisionResults = new CollisionResults();
        Ray ray = new Ray(camera.getLocation(), camera.getDirection());
        Quad quad = new Quad(5000, 5000);
        Geometry geometry = new Geometry("q", quad);
        geometry.rotate(-FastMath.HALF_PI, 0, 0);
        geometry.center().move(Vector3f.ZERO);
        geometry.collideWith(ray, collisionResults);
        if (collisionResults.size() > 0) {
            return collisionResults.getCollision(0).getContactPoint();
        }
        return Vector3f.ZERO;
    }

    public static Vector3f getCameraCenterPoint(Camera camera) {
        float delta = camera.getLocation().getY() / (camera.getDirection().getY() * -1f);
        Vector3f fullDirection = camera.getDirection().mult(delta);
        return camera.getLocation().add(fullDirection);
    }

    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        if (event.getType() == TouchEvent.Type.SCALE_MOVE) {
            currentFoV -= event.getDeltaScaleSpan()/25;
            if(currentFoV < minFov){
                currentFoV = minFov;
            }
            else if(currentFoV > maxFov){
                currentFoV = maxFov;
            }
            setFov();
        }
    }

    private void setFov(){
        moveSpeedScale = (550.f / MainActivity.getScreenDPI()) * (currentFoV / 10);
        cam.setFrustumPerspective(currentFoV, screenAspect, 1f, 50f);
    }
}