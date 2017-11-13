package tk.ubublik.redivansion.gamelogic.camera;

import com.jme3.input.CameraInput;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import tk.ubublik.redivansion.MainActivity;
import tk.ubublik.redivansion.gamelogic.utils.TouchInputHook;

/**
 * Created by Bublik on 09-Sep-17.
 * Custom FlyByCamera class
 * @see com.jme3.input.FlyByCamera
 */
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

    private Camera cam;
    private static final float moveSpeed = 3f;
    private static final float moveYSpeed = 2f;
    private float moveSpeedScale = (550.f / MainActivity.getScreenDPI());
    private boolean enabled = true;
    private InputManager inputManager;
    private final float screenAspect;
    private TouchInputHook touchInputHook;
    private boolean areaLimitRound = false;
    private float areaLimit = 20f;

    public CameraControl(Camera cam, InputManager inputManager) {
        this.cam = cam;
        screenAspect = cam.getWidth() / (float) cam.getHeight();
        registerWithInput(inputManager);
        setDefaultPosition();
    }

    public void setDefaultPosition() {
        cam.setLocation(new Vector3f(10, 20, 10));
        setFov();
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
    }

    public void setTouchInputHook(TouchInputHook touchInputHook){
        this.touchInputHook = touchInputHook;
    }

    public void onUpdate() {

    }

    public void setEnabled(boolean enable) {
        if (enabled && !enable) {
            if (inputManager != null) {
                inputManager.setCursorVisible(true);
            }
        }
        enabled = enable;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void registerWithInput(InputManager inputManager) {
        this.inputManager = inputManager;
        inputManager.addMapping(CameraInput.FLYCAM_STRAFELEFT, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(CameraInput.FLYCAM_STRAFERIGHT, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(CameraInput.FLYCAM_FORWARD, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping(CameraInput.FLYCAM_BACKWARD, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(TOUCH_MAPPING, new TouchTrigger(TouchInput.ALL));

        inputManager.addListener(this, mappings);
        inputManager.setCursorVisible(!isEnabled());
    }

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
    }

    public Camera getCamera(){
        return cam;
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
        pos.addLocal(vel);
        pos = limitPosition(pos);
        cam.setLocation(pos);
    }

    private Vector3f limitPosition(Vector3f position){
        Vector3f center = getCameraCenterPoint();
        if (areaLimitRound){
            System.out.println("Center: "+center);
            System.out.println("Area limit: "+areaLimit);
            System.out.println("Area limit2: "+areaLimit*areaLimit);
            System.out.println("Distance: "+center.distanceSquared(Vector3f.ZERO));
            if (center.distanceSquared(Vector3f.ZERO)>areaLimit*areaLimit){
                Vector3f difference = position.subtract(center);
                Vector3f centerClone = center.normalize();
                centerClone.multLocal(areaLimit);// FIXME: find faster solution
                position = centerClone.add(difference);
            }
        } else {
            if (FastMath.abs(center.x)>areaLimit) position.x += -center.x +(center.x>0?areaLimit:-areaLimit);
            if (FastMath.abs(center.z)>areaLimit) position.z += -center.z +(center.z>0?areaLimit:-areaLimit);
        }
        return position;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {

    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!enabled)
            return;

        switch (name) {
            case CameraInput.FLYCAM_FORWARD: moveCamera(value, false); break;
            case CameraInput.FLYCAM_BACKWARD: moveCamera(-value, false); break;
            case CameraInput.FLYCAM_STRAFELEFT: moveCamera(-value, true); break;
            case CameraInput.FLYCAM_STRAFERIGHT: moveCamera(value, true); break;
        }
    }

    public Vector3f getCameraCenterPoint() {
        return getCameraCenterPoint(cam);
    }

    public static Vector3f getCameraCenterPoint(Camera camera) {
        float delta = camera.getLocation().getY() / (camera.getDirection().getY() * -1f);
        Vector3f fullDirection = camera.getDirection().mult(delta);
        return camera.getLocation().add(fullDirection);
    }

    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        if (touchInputHook!=null && touchInputHook.touchCaptured(event, tpf)){
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
            checkZoomGesture(event);
        }
    }

    private void checkZoomGesture(TouchEvent event){
        if (event.getType() == TouchEvent.Type.SCALE_MOVE) {
            currentFoV -= event.getDeltaScaleSpan() / 25;
            if (currentFoV < minFov) {
                currentFoV = minFov;
            } else if (currentFoV > maxFov) {
                currentFoV = maxFov;
            }
            setFov();
        }
    }

    private void setFov(){
        moveSpeedScale = (550.f / MainActivity.getScreenDPI()) * (currentFoV / 10);
        cam.setFrustumPerspective(currentFoV, screenAspect, 1f, 50f);
    }

    public boolean isAreaLimitRound() {
        return areaLimitRound;
    }

    public void setAreaLimitRound(boolean areaLimitRound) {
        this.areaLimitRound = areaLimitRound;
    }

    public float getAreaLimit() {
        return areaLimit;
    }

    public void setAreaLimit(float areaLimit) {
        this.areaLimit = areaLimit;
    }
}