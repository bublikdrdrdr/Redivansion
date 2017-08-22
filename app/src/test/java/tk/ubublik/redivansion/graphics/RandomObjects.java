package tk.ubublik.redivansion.graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.PolyAnimation;
import tk.ubublik.redivansion.gamelogic.graphics.Polygon;

/**
 * Created by Bublik on 22-Aug-17.
 */

public class RandomObjects {

    private Random random = new Random();

    private static final int NAME_MIN_SIZE = 3;
    private static final int NAME_MAX_SIZE = 20;
    private static final int MIN_POLYGONS_COUNT = 10;
    private static final int MAX_POLYGONS_COUNT = 40;
    private static final int MIN_ANIMATIONS_COUNT = 10;
    private static final int MAX_ANIMATIONS_COUNT = 40;

    public Model getRandomModel(){
        Model model = new Model();
        int count = MIN_ANIMATIONS_COUNT+random.nextInt(MAX_ANIMATIONS_COUNT-MIN_ANIMATIONS_COUNT);
        for (int i = 0; i < count; i++){
            model.addAnimation(getRandomPolyAnimation());
        }
        return model;
    }

    public PolyAnimation getRandomPolyAnimation(){
        PolyAnimation polyAnimation = new PolyAnimation(getRandomName());
        int count = MIN_POLYGONS_COUNT+random.nextInt(MAX_POLYGONS_COUNT-MIN_POLYGONS_COUNT);
        for (int i = 0; i < count; i++){
            polyAnimation.addPolygon(getRandomPolygon());
        }
        return polyAnimation;
    }

    public String getRandomName(){
        int length = random.nextInt(NAME_MAX_SIZE-NAME_MIN_SIZE);
        return RandomStringUtils.randomAlphabetic(NAME_MIN_SIZE+length);
    }

    public Polygon getRandomPolygon(){
        ColorRGBA c1 = getRandomColor();
        ColorRGBA c2 = getRandomColor();
        Vector3f[] v1 = new Vector3f[]{getRandomVector(), getRandomVector(), getRandomVector()};
        Vector3f[] v2 = new Vector3f[]{getRandomVector(), getRandomVector(), getRandomVector()};
        return new Polygon(c1, c2, v1, v2, random.nextFloat(), random.nextFloat());
    }

    public ColorRGBA getRandomColor(){
        return new ColorRGBA(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public Vector3f getRandomVector(){
        return new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }
}
