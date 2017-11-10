package tk.ubublik.redivansion;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import tk.ubublik.redivansion.gamelogic.units.Settings;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private static int screenDPI = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDPI();
        Settings.createInstance(this);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_main);
    }

    private void setDPI(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenDPI = displayMetrics.densityDpi;
    }

    public static int getScreenDPI(){
        return screenDPI;
    }

    public static float getScreenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static float getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
