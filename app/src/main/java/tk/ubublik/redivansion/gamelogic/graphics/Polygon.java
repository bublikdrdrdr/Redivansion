package tk.ubublik.redivansion.gamelogic.graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.utils.ByteSettings.ByteConverter;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Polygon {

    private ColorRGBA startColor;
    private ColorRGBA endColor;

    private Vector3f[] startPoints;
    private Vector3f[] endPoints;

    private float duration;//animation duration
    private float delay;//delay before animation start

    private void setValues(ColorRGBA startColor, ColorRGBA endColor, Vector3f[] startPoints, Vector3f[] endPoints, float duration, float delay){
        if (startPoints.length !=VECTOR_ARRAY_COUNT || endPoints.length != VECTOR_ARRAY_COUNT)
            throw new IllegalArgumentException("Point array length must be equal "+Integer.toString(VECTOR_ARRAY_COUNT));
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
    public static final int POLYGON_SIZE = FLOAT_SIZE*2 + COLOR_SIZE*2 + VECTOR_ARRAY_SIZE*2;
    public Polygon(byte[] bytes, int index) {
        try{
            float duration = ByteConverter.getFloat(bytes, index);
            index+=FLOAT_SIZE;
            float delay = ByteConverter.getFloat(bytes, index);
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
        float x = ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float y = ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float z = ByteConverter.getFloat(bytes, index);
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
        float r = ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float g = ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float b = ByteConverter.getFloat(bytes, index);
        index+=FLOAT_SIZE;
        float a = ByteConverter.getFloat(bytes, index);
        return new ColorRGBA(r,g,b,a);
    }

    public byte[] getBytes(){
        byte[] bytes = new byte[POLYGON_SIZE];
        int index = 0;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(duration), index);
        index+=FLOAT_SIZE;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(delay), index);
        index+=FLOAT_SIZE;

        ByteConverter.insertArray(bytes, getVectorArrayBytes(startPoints), index);
        index+=VECTOR_ARRAY_SIZE;
        ByteConverter.insertArray(bytes, getVectorArrayBytes(endPoints), index);
        index+=VECTOR_ARRAY_SIZE;

        ByteConverter.insertArray(bytes, getColorBytes(startColor), index);
        index+=COLOR_SIZE;
        ByteConverter.insertArray(bytes, getColorBytes(endColor), index);
        return bytes;
    }

    private byte[] getColorBytes(ColorRGBA color){
        byte[] bytes = new byte[COLOR_SIZE];
        int index = 0;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(color.getRed()), index);
        index+=FLOAT_SIZE;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(color.getGreen()), index);
        index+=FLOAT_SIZE;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(color.getBlue()), index);
        index+=FLOAT_SIZE;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(color.getAlpha()), index);
        return bytes;
    }

    //do not get single vector bytes, cause of time wasting
    private byte[] getVectorArrayBytes(Vector3f[] vectorArray){
        byte[] bytes = new byte[VECTOR_ARRAY_SIZE];
        int index = 0;
        for (int i = 0; i < VECTOR_ARRAY_COUNT; i++){
            ByteConverter.insertArray(bytes, ByteConverter.getArray(vectorArray[i].getX()), index);
            index+=FLOAT_SIZE;
            ByteConverter.insertArray(bytes, ByteConverter.getArray(vectorArray[i].getY()), index);
            index+=FLOAT_SIZE;
            ByteConverter.insertArray(bytes, ByteConverter.getArray(vectorArray[i].getZ()), index);
            index+=FLOAT_SIZE;
        }
        return bytes;
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
