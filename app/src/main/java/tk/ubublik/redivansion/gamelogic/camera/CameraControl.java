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

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 09-Sep-17.
 * Custom FlyByCamera class
 * @see com.jme3.input.FlyByCamera
 */
// TODO: 10-Sep-17 CLEAN!
public class CameraControl implements ActionListener, AnalogListener {

    private static String[] mappings = new String[]{
            CameraInput.FLYCAM_STRAFELEFT,
            CameraInput.FLYCAM_STRAFERIGHT,
            CameraInput.FLYCAM_FORWARD,
            CameraInput.FLYCAM_BACKWARD,

            CameraInput.FLYCAM_ZOOMIN,
            CameraInput.FLYCAM_ZOOMOUT,
            CameraInput.FLYCAM_ROTATEDRAG,

            CameraInput.FLYCAM_INVERTY
    };

    protected Camera cam;
    protected Vector3f initialUpVec;
    protected float rotationSpeed = 1f;
    protected float moveSpeed = 3f;
    protected float moveYSpeed = 2f;
    protected float moveSpeedScale = 550.f/MainActivity.getScreenDPI();
    protected float zoomSpeed = 1f;
    protected MotionAllowedListener motionAllowed = null;
    protected boolean enabled = true;
    protected boolean dragToRotate = false;
    protected boolean canRotate = false;
    protected boolean invertY = false;
    protected InputManager inputManager;

    /**
     * Creates a new FlyByCamera to control the given Camera object.
     * @param cam
     */
    public CameraControl(Camera cam, InputManager inputManager){
        this.cam = cam;
        initialUpVec = cam.getUp().clone();
        registerWithInput(inputManager);
        setDefaultPosition();
    }

    public void setDefaultPosition(){
        cam.setLocation(new Vector3f(10,20,10));
        cam.setFrustumPerspective(10f, 1.7777f, 1f, 50f);
        cam.lookAt(new Vector3f(0,0,0), Vector3f.UNIT_Y);
    }

    /**
     * Sets the up vector that should be used for the camera.
     * @param upVec
     */
    public void setUpVector(Vector3f upVec) {
        initialUpVec.set(upVec);
    }

    public void setMotionAllowedListener(MotionAllowedListener listener){
        this.motionAllowed = listener;
    }

    /**
     * Sets the move speed. The speed is given in world units per second.
     * @param moveSpeed
     */
    public void setMoveSpeed(float moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    /**
     * Gets the move speed. The speed is given in world units per second.
     * @return moveSpeed
     */
    public float getMoveSpeed(){
        return moveSpeed;
    }

    /**
     * Sets the rotation speed.
     * @param rotationSpeed
     */
    public void setRotationSpeed(float rotationSpeed){
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * Gets the move speed. The speed is given in world units per second.
     * @return rotationSpeed
     */
    public float getRotationSpeed(){
        return rotationSpeed;
    }

    /**
     * Sets the zoom speed.
     * @param zoomSpeed
     */
    public void setZoomSpeed(float zoomSpeed) {
        this.zoomSpeed = zoomSpeed;
    }

    /**
     * Gets the zoom speed.  The speed is a multiplier to increase/decrease
     * the zoom rate.
     * @return zoomSpeed
     */
    public float getZoomSpeed() {
        return zoomSpeed;
    }

    /**
     * @param enable If false, the camera will ignore input.
     */
    public void setEnabled(boolean enable){
        if (enabled && !enable){
            if (inputManager!= null && (!dragToRotate || (dragToRotate && canRotate))){
                inputManager.setCursorVisible(true);
            }
        }
        enabled = enable;
    }

    /**
     * @return If enabled
     * @see FlyByCamera#setEnabled(boolean)
     */
    public boolean isEnabled(){
        return enabled;
    }

    /**
     * @return If drag to rotate feature is enabled.
     *
     * @see FlyByCamera#setDragToRotate(boolean)
     */
    public boolean isDragToRotate() {
        return dragToRotate;
    }

    /**
     * Set if drag to rotate mode is enabled.
     *
     * When true, the user must hold the mouse button
     * and drag over the screen to rotate the camera, and the cursor is
     * visible until dragged. Otherwise, the cursor is invisible at all times
     * and holding the mouse button is not needed to rotate the camera.
     * This feature is disabled by default.
     *
     * @param dragToRotate True if drag to rotate mode is enabled.
     */
    public void setDragToRotate(boolean dragToRotate) {
        this.dragToRotate = dragToRotate;
        if (inputManager != null) {
            inputManager.setCursorVisible(dragToRotate);
        }
    }

    /**
     * Registers the FlyByCamera to receive input events from the provided
     * Dispatcher.
     * @param inputManager
     */
    public void registerWithInput(InputManager inputManager){
        this.inputManager = inputManager;
        // mouse only - zoom in/out with wheel, and rotate drag
        inputManager.addMapping(CameraInput.FLYCAM_ZOOMIN, new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping(CameraInput.FLYCAM_ZOOMOUT, new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping(CameraInput.FLYCAM_ROTATEDRAG, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addMapping(CameraInput.FLYCAM_STRAFELEFT, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(CameraInput.FLYCAM_STRAFERIGHT, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(CameraInput.FLYCAM_FORWARD, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping(CameraInput.FLYCAM_BACKWARD, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(CameraInput.FLYCAM_RISE, new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping(CameraInput.FLYCAM_LOWER, new KeyTrigger(KeyInput.KEY_Z));

        inputManager.addListener(this, mappings);
        inputManager.setCursorVisible(dragToRotate || !isEnabled());
    }

    /**
     * Unregisters the FlyByCamera from the event Dispatcher.
     */
    public void unregisterInput(){

        if (inputManager == null) {
            return;
        }

        for (String s : mappings) {
            if (inputManager.hasMapping(s)) {
                inputManager.deleteMapping( s );
            }
        }

        inputManager.removeListener(this);
        inputManager.setCursorVisible(!dragToRotate);

        Joystick[] joysticks = inputManager.getJoysticks();
        if (joysticks != null && joysticks.length > 0){
            Joystick joystick = joysticks[0];

            // No way to unassing axis
        }
    }

    protected void rotateCamera(float value, Vector3f axis){
        if (dragToRotate){
            if (canRotate){
//                value = -value;
            }else{
                return;
            }
        }

        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(rotationSpeed * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        cam.setAxes(q);
    }

    protected void zoomCamera(float value){
        // derive fovY value
        float h = cam.getFrustumTop();
        float w = cam.getFrustumRight();
        float aspect = w / h;

        float near = cam.getFrustumNear();

        float fovY = FastMath.atan(h / near)
                / (FastMath.DEG_TO_RAD * .5f);
        float newFovY = fovY + value * 0.1f * zoomSpeed;
        if (newFovY > 0f) {
            // Don't let the FOV go zero or negative.
            fovY = newFovY;
        }

        h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;
        w = h * aspect;

        cam.setFrustumTop(h);
        cam.setFrustumBottom(-h);
        cam.setFrustumLeft(-w);
        cam.setFrustumRight(w);
    }

    protected void riseCamera(float value){
        Vector3f vel = new Vector3f(0, value * moveSpeed, 0);
        Vector3f pos = cam.getLocation().clone();

        if (motionAllowed != null)
            motionAllowed.checkMotionAllowed(pos, vel);
        else
            pos.addLocal(vel);

        cam.setLocation(pos);
    }

    protected void moveCamera(float value, boolean sideways){
        Vector3f vel = new Vector3f();
        Vector3f pos = cam.getLocation().clone();

        if (sideways){
            cam.getLeft(vel);
        }else{
            cam.getDirection(vel);
            vel.setY(0);
            vel.normalize();
            vel.multLocal(moveYSpeed);
        }
        vel.multLocal(value * moveSpeed*moveSpeedScale);

        if (motionAllowed != null)
            motionAllowed.checkMotionAllowed(pos, vel);
        else
            pos.addLocal(vel);

        cam.setLocation(pos);
    }
    @Override
    public void onAction(String name, boolean value, float tpf) {
        if (!enabled)
            return;

        if (name.equals(CameraInput.FLYCAM_ROTATEDRAG) && dragToRotate){
            canRotate = value;
            inputManager.setCursorVisible(!value);
        } else if (name.equals(CameraInput.FLYCAM_INVERTY)) {
            // Toggle on the up.
            if( !value ) {
                invertY = !invertY;
            }
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!enabled)
            return;

        if (name.equals(CameraInput.FLYCAM_LEFT)){
            rotateCamera(value, initialUpVec);
        }else if (name.equals(CameraInput.FLYCAM_RIGHT)){
            rotateCamera(-value, initialUpVec);
        }else if (name.equals(CameraInput.FLYCAM_UP)){
            rotateCamera(-value * (invertY ? -1 : 1), cam.getLeft());
        }else if (name.equals(CameraInput.FLYCAM_DOWN)){
            rotateCamera(value * (invertY ? -1 : 1), cam.getLeft());
        }else if (name.equals(CameraInput.FLYCAM_FORWARD)){
            moveCamera(value, false);
        }else if (name.equals(CameraInput.FLYCAM_BACKWARD)){
            moveCamera(-value, false);
        }else if (name.equals(CameraInput.FLYCAM_STRAFELEFT)){
            moveCamera(-value, true);
        }else if (name.equals(CameraInput.FLYCAM_STRAFERIGHT)){
            moveCamera(value, true);
        }else if (name.equals(CameraInput.FLYCAM_RISE)){
            riseCamera(value);
        }else if (name.equals(CameraInput.FLYCAM_LOWER)){
            riseCamera(-value);
        }else if (name.equals(CameraInput.FLYCAM_ZOOMIN)){
            zoomCamera(value);
        }else if (name.equals(CameraInput.FLYCAM_ZOOMOUT)){
            zoomCamera(-value);
        }
    }

    public Vector3f getCameraCenterPoint(){
        // FIXME: 21-Sep-17 get camera ray and horizontal plane collision point
        CollisionResults collisionResults = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        Quad quad = new Quad(5000,5000);
        Geometry geometry = new Geometry("q", quad);
        geometry.rotate(-FastMath.HALF_PI, 0,0);
        geometry.center().move(Vector3f.ZERO);
        geometry.collideWith(ray, collisionResults);
        if (collisionResults.size()>0){
            return collisionResults.getCollision(0).getContactPoint();
        }
        return Vector3f.ZERO;
    }
}
