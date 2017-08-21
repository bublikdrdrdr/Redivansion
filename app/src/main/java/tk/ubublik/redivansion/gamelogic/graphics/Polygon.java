package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.utils.ByteSettings;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Polygon {

    private static final int POLYGON_VECTOR_ARRAY_SIZE = 3;

    private ColorRGBA startColor;
    private ColorRGBA endColor;

    private Vector3f[] startPoints;
    private Vector3f[] endPoints;

    private float duration;//animation duration
    private float delay;//delay before animation start

    private void setValues(ColorRGBA startColor, ColorRGBA endColor, Vector3f[] startPoints, Vector3f[] endPoints, float duration, float delay){
        if (startPoints.length !=POLYGON_VECTOR_ARRAY_SIZE || endPoints.length != POLYGON_VECTOR_ARRAY_SIZE)
            throw new IllegalArgumentException("Point array length must be equal "+Integer.toString(POLYGON_VECTOR_ARRAY_SIZE));
        this.startColor = startColor;
        this.endColor = endColor;
        this.startPoints = startPoints;
        this.endPoints = endPoints;
        this.duration = duration;
        this.delay = delay;
    }

    public Polygon(ColorRGBA startColor, ColorRGBA endColor, Vector3f[] startPoints, Vector3f[] endPoints, float duration, float delay) {
        setValues(startColor, endColor, startPoints, endPoints, duration, delay);
    }

    private static final int FLOAT_SIZE = 4;
    private static final int VECTOR_SIZE = FLOAT_SIZE*3;
    private static final int VECTOR_ARRAY_COUNT = 3;
    private static final int VECTOR_ARRAY_SIZE = VECTOR_SIZE*VECTOR_ARRAY_COUNT;
    private static final int COLOR_SIZE = FLOAT_SIZE*4;
    public Polygon(byte[] bytes, int index) {
        try{
            float duration = ByteSettings.ByteConverter.getFloat(bytes, index);
            index+=FLOAT_SIZE;
            float delay = ByteSettings.ByteConverter.getFloat(bytes, index);
            index+=FLOAT_SIZE;

            Vector3f[] startPoints = parseVectorArray(bytes, index);
            index+=VECTOR_ARRAY_SIZE;
            Vector3f[] endPoints = parseVectorArray(bytes, index);
            index+=VECTOR_ARRAY_SIZE;

            ColorRGBA startColor = parseColor(bytes, index);
            index+=COLOR_SIZE;
            ColorRGBA endColor = parseColor(bytes, index);

            setValues(startColor, endColor, startPoints, endPoints, duration, delay);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't parse model from bytes", e);
        }
    }

    private Vector3f parseVector3f(byte[] bytes, int index){
        float x = ByteSettings.ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float y = ByteSettings.ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float z = ByteSettings.ByteConverter.getFloat(bytes, index);
        return new Vector3f(x,y,z);
    }

    private Vector3f[] parseVectorArray(byte[] bytes, int index) {
        Vector3f[] vectors = new Vector3f[VECTOR_ARRAY_COUNT];
        for (int i = 0; i < VECTOR_ARRAY_COUNT; i++){
            vectors[i] = parseVector3f(bytes, index);
            index+=VECTOR_SIZE;
        }
        return vectors;
    }

    private ColorRGBA parseColor(byte[] bytes, int index){
        float r = ByteSettings.ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float g = ByteSettings.ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float b = ByteSettings.ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float a = ByteSettings.ByteConverter.getFloat(bytes, index);
        return new ColorRGBA(r,g,b,a);
    }


    private List<Polygon> parseBytes(byte[] bytes, int index){
        try {
            List<Polygon> list = new ArrayList<>();
            int count = ByteSettings.ByteConverter.getInt(bytes, index);
            if (count < 0) throw new IllegalArgumentException("Count number is negative");
            index+=4;
            for (int i = 0; i < count; i++){
                int size = ByteSettings.ByteConverter.getInt(bytes, index);
                index+=4;
                Polygon polygon = new Polygon(bytes, index);
                index+=size;
                list.add(polygon);
            }
            return list;
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't parse model from bytes", e);
        }
    }

    public byte[] getBytes(){
        // TODO: 21-Aug-17
        return null;
    }

    public static int getPolygonVectorArraySize() {
        return POLYGON_VECTOR_ARRAY_SIZE;
    }

    public ColorRGBA getStartColor() {
        return startColor;
    }

    public void setStartColor(ColorRGBA startColor) {
        this.startColor = startColor;
    }

    public ColorRGBA getEndColor() {
        return endColor;
    }

    public void setEndColor(ColorRGBA endColor) {
        this.endColor = endColor;
    }

    public Vector3f[] getStartPoints() {
        return startPoints;
    }

    public void setStartPoints(Vector3f[] startPoints) {
        this.startPoints = startPoints;
    }

    public Vector3f[] getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(Vector3f[] endPoints) {
        this.endPoints = endPoints;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }
}
