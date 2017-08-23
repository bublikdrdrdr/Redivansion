package tk.ubublik.redivansion.graphics;

import org.junit.Test;

import tk.ubublik.redivansion.gamelogic.graphics.Model;
import tk.ubublik.redivansion.gamelogic.graphics.PolyAnimation;
import tk.ubublik.redivansion.gamelogic.graphics.Polygon;

import static org.junit.Assert.assertTrue;

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
        assertTrue("Parsed model from getBytes must be equal to source", model.equals(newModel));
    }
}
