package tk.ubublik.redivansion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The "activity_main" layout includes the reference
        // to the fragment that contains the GLSurfaceView
        // that will be used to display the jME content.
        setContentView(R.layout.activity_main);
    }
}
