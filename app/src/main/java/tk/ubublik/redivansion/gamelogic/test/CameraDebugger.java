package tk.ubublik.redivansion.gamelogic.test;

import android.graphics.Point;

import com.jme3.math.Vector3f;

/**
 * Created by Bublik on 24-Sep-17.
 */

public class CameraDebugger {

    private static long time = 0;
    public static boolean canPrint(){
        return  (System.currentTimeMillis()-time>=500);
    }

    public static void print(Vector3f cV, Point cP) {
        System.out.println("CAMERA DEBUG: center point " + cV + ", map point " + cP);
        time = System.currentTimeMillis();
    }
}
