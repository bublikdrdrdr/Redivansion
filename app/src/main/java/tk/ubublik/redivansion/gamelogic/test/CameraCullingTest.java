package tk.ubublik.redivansion.gamelogic.test;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bublik on 06-Oct-17.
 */

public class CameraCullingTest {

    int width = 2560;
    int height = 1440;
    float aspect = width/height;
    float near = 0.1f;
    float far = 9000f;

    Camera wide90 = new Camera(width, height);
    Camera wide45 = new Camera(width, height);
    Camera narrow90 = new Camera(width, height);
    Camera narrow45 = new Camera(width, height);
    Camera orto90 = new Camera(width, height);
    Camera orto45 = new Camera(width, height);

    List<Spatial> spatials = new LinkedList<>();

    public CameraCullingTest() {
        setCamerasParams();
        createSpatials();
    }

    private void setCamerasParams(){
        wide90.setFrustumPerspective(60, aspect, near, far);
        wide45.setFrustumPerspective(60, aspect, near, far);
        narrow90.setFrustumPerspective(5, aspect, near, far);
        narrow45.setFrustumPerspective(5, aspect, near, far);
        orto90.setFrustum(near, far, narrow45.getFrustumLeft(), narrow45.getFrustumRight(), narrow45.getFrustumTop(), narrow45.getFrustumBottom());
        orto45.setFrustum(near, far, narrow45.getFrustumLeft(), narrow45.getFrustumRight(), narrow45.getFrustumTop(), narrow45.getFrustumBottom());

        Vector3f location90 = new Vector3f(10,10,0);
        Vector3f location45 = new Vector3f(10,10,10);

        wide90.setLocation(location90);
        wide45.setLocation(location45);
        narrow90.setLocation(location90);
        narrow45.setLocation(location45);
        orto90.setLocation(location90);
        orto45.setLocation(location45);

        wide90.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        wide45.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        narrow90.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        narrow45.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        orto90.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        orto45.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    private void createSpatials(){
        Material material = new Material();
        for (int i = -100; i < 100; i+=2){
            for (int j = -100; j < 100; j+=2){
                Box box = new Box(1,1,1);
                Geometry geometry = new Geometry("box", box);
                geometry.setMaterial(material);
                geometry.setLocalTranslation(i, 0, j);
                spatials.add(geometry);
            }
        }
    }

    private void checkCamera(Camera camera){
        for (Spatial spatial: spatials){
            camera.contains(spatial.getWorldBound());
        }
    }

    long lastTime = System.currentTimeMillis();
    public void onUpdate(){
        if (System.currentTimeMillis()-lastTime>=2000){
            lastTime = System.currentTimeMillis();
            test();
        }
    }

    private void test(){
        long res;
        startTimer();
        checkCamera(wide90);
        res = getTime();
        System.out.println("W90: "+res);

        startTimer();
        checkCamera(wide45);
        res = getTime();
        System.out.println("W45: "+res);

        startTimer();
        checkCamera(narrow90);
        res = getTime();
        System.out.println("N90: "+res);

        startTimer();
        checkCamera(narrow45);
        res = getTime();
        System.out.println("N45: "+res);

        startTimer();
        checkCamera(orto90);
        res = getTime();
        System.out.println("O90: "+res);

        startTimer();
        checkCamera(orto45);
        res = getTime();
        System.out.println("O45: "+res);
    }

    long nanos = 0;
    private void startTimer(){
        nanos = System.nanoTime();
    }

    private long getTime(){
        return System.nanoTime()-nanos;
    }
}
