package tk.ubublik.redivansion.graphics;

import android.app.Instrumentation;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Random;
import static org.junit.Assert.*;

import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.PolyAnimation;
import tk.ubublik.redivansion.gamelogic.graphics.Polygon;

/**
 * Created by Bublik on 22-Aug-17.
 */

public class BytesParseUnitTest {

    private RandomObjects randomObjects = new RandomObjects();

    @Test
    public void polygonReParsingIsCorrect() throws Exception{
        Polygon polygon = randomObjects.getRandomPolygon();
        byte[] bytes = polygon.getBytes();
        Polygon newPolygon = new Polygon(bytes, 0);
        assertTrue("Parsed polygon from getBytes must be equal to source", polygon.equals(newPolygon));
    }


    @Test
    public void animationReParsingIsCorrect() throws Exception{
        PolyAnimation animation = randomObjects.getRandomPolyAnimation();
        byte[] bytes = animation.getBytes();
        PolyAnimation newAnimation = new PolyAnimation(bytes, 0);
        assertTrue("Parsed animation from getBytes must be equal to source", animation.equals(newAnimation));
    }

    @Test
    public void modelReParsingIsCorrect() throws Exception{
        Model model = randomObjects.getRandomModel();
        byte[] bytes = model.getBytes();
        Model newModel = new Model(bytes);
        assertTrue("Parsed animation from getBytes must be equal to source", model.equals(newModel));
    }
}
