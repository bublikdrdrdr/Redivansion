package tk.ubublik.redivansion.gamelogic.test;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.PolyAnimation;
import tk.ubublik.redivansion.gamelogic.graphics.Polygon;

/**
 * Created by Bublik on 25-Aug-17.
 */

public class ExampleModel extends Model{
    public ExampleModel() {
        PolyAnimation polyAnimation1 = new PolyAnimation("build");
        polyAnimation1.addPolygon(new Polygon(ColorRGBA.Red, ColorRGBA.Pink, new Vector3f[]{new Vector3f(0,0,0), new Vector3f(1,0,0), new Vector3f(1,1,0)},new Vector3f[]{new Vector3f(-1,0,0), new Vector3f(1,0,0), new Vector3f(1,1,0)}, 1500,2000));
        polyAnimation1.addPolygon(new Polygon(ColorRGBA.Blue, ColorRGBA.Cyan, new Vector3f[]{new Vector3f(0,0,2), new Vector3f(1,0,2), new Vector3f(1,1,2)},new Vector3f[]{new Vector3f(-1,0,2), new Vector3f(1,0,2), new Vector3f(1,1,2)}, 1500,4000));

        PolyAnimation polyAnimation2 = new PolyAnimation("change");
        polyAnimation2.addPolygon(new Polygon(ColorRGBA.Yellow, ColorRGBA.White, new Vector3f[]{new Vector3f(0,0,2), new Vector3f(1,0,2), new Vector3f(1,1,2)},new Vector3f[]{new Vector3f(-1,0,2), new Vector3f(1,0,2), new Vector3f(1,1,2)}, 1500,4000));
        this.addAnimation(polyAnimation1);
        this.addAnimation(polyAnimation2);
    }
}
