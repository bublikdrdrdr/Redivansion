package tk.ubublik.redivansion.gamelogic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Bublik on 20-Feb-16.
 */

public class ByteSettings {

    private class DataStruct {
        int tag;
        int size;
        int type; // {0,1,2... = boolean, byte, short, char, int, long, float, double, byte[], int[], double[], String, Bitmap
        //                         0       1       2     3    4     5     6       7      8      9        10       11      12
        Object object;
    }

    public static class ByteConverter {
        public static void insertArray(byte[] main_array, byte[] small_array, int position) {
            int to = small_array.length;
            if (small_array.length + position > main_array.length)
                to = main_array.length - position;

            for (int i = 0; i < to; i++) {
                main_array[i + position] = small_array[i];
            }
        }

        public static byte[] getArray(boolean b) {
            byte[] TR = new byte[1];
            if (b) TR[0] = 1;
            else TR[0] = 0;
            return TR;
        }

        public static byte[] getArray(byte b) {
            byte[] TR = new byte[1];
            TR[0] = b;
            return TR;
        }

        public static byte[] getArray(short s) {
            return ByteBuffer.allocate(2).putShort(s).array();
        }

        public static byte[] getArray(char c) {
            return ByteBuffer.allocate(2).putChar(c).array();
        }

        public static byte[] getArray(int i) {
            return ByteBuffer.allocate(4).putInt(i).array();
        }

        public static byte[] getArray(long l) {
            return ByteBuffer.allocate(8).putLong(l).array();
        }

        public static byte[] getArray(float f) {
            return ByteBuffer.allocate(4).putFloat(f).array();
        }

        public static byte[] getArray(double d) {
            return ByteBuffer.allocate(8).putDouble(d).array();
        }

        public static byte[] getArray(byte[] b) {
            return b;
        }

        public static byte[] getArray(int[] i) {
            byte[] TR = new byte[i.length * 4];
            byte[] target;
            for (int j = 0; j < i.length; j++) {
                target = getArray(i[j]);
                insertArray(TR, target, j * 4);
            }
            return TR;
        }

        public static byte[] getArray(double[] d) {
            byte[] TR = new byte[d.length * 8];
            byte[] target;
            for (int j = 0; j < d.length; j++) {
                target = getArray(d[j]);
                insertArray(TR, target, j * 4);
            }
            return TR;
        }

        public static byte[] getArray(String s) {
            return s.getBytes();
        }

        public static byte[] getArray(Bitmap b) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        public static boolean getBoolean(byte[] bytes, int index) {
            if (bytes[index] == 1) return true;
            else return false;
        }

        public static byte getByte(byte[] bytes, int index) {
            return bytes[index];
        }

        public static short getShort(byte[] bytes, int index) {
            ByteBuffer bb = ByteBuffer.wrap(bytes, index, 2);
            return bb.getShort();
        }

        public static char getChar(byte[] bytes, int index) {
            ByteBuffer bb = ByteBuffer.wrap(bytes, index, 2);
            return bb.getChar();
        }

        public static int getInt(byte[] bytes, int index) {
            ByteBuffer bb = ByteBuffer.wrap(bytes, index, 4);
            return bb.getInt();
        }

        public static long getLong(byte[] bytes, int index) {
            ByteBuffer bb = ByteBuffer.wrap(bytes, index, 8);
            return bb.getLong();
        }

        public static float getFloat(byte[] bytes, int index) {
            ByteBuffer bb = ByteBuffer.wrap(bytes, index, 4);
            return bb.getFloat();
        }

        public static double getDouble(byte[] bytes, int index) {
            ByteBuffer bb = ByteBuffer.wrap(bytes, index, 8);
            return bb.getDouble();
        }

        public static int[] getIntArray(byte[] bytes, int index, int byteArraySize) {
            int[] TR = new int[byteArraySize / 4];
            for (int i = 0; i < TR.length; i++) {
                TR[i] = getInt(bytes, index + i * 4);
            }
            return TR;
        }

        public static double[] getDoubleArray(byte[] bytes, int index, int byteArraySize) {
            double[] TR = new double[byteArraySize / 8];
            for (int i = 0; i < TR.length; i++) {
                TR[i] = getDouble(bytes, index + i * 8);
            }
            return TR;
        }

        public static String getString(byte[] bytes, int index, int byteArraySize) {
            byte[] subArray = Arrays.copyOfRange(bytes, index, index + byteArraySize);
            return new String(subArray);
        }

        public static Bitmap getBitmap(byte[] bytes, int index, int byteArraySize) {
            byte[] subArray = Arrays.copyOfRange(bytes, index, index + byteArraySize);
            return BitmapFactory.decodeByteArray(subArray, 0, subArray.length);
        }
    }

    public String ERROR_TEXT = "";
    public boolean ERROR = false;

    private String key = "bublik_key";
    private LinkedList<DataStruct> list;
    private int VERSION = 2;
    public boolean Encode = false;

    private String FILE_NAME;


    public ByteSettings(String filename) {
        if (!CheckFile(filename)) {
            ERROR = true;
            return;
        }
        byte[] fileByteArray = ReadFile(filename);
        if (Encode) {
            fileByteArray = encode(fileByteArray, key);
        }
        Parse(fileByteArray, list);
        FILE_NAME = filename;
    }

    private static byte[] encode(byte[] source, String code_key) {
        return source;
    }

    private boolean CheckFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            return true;
        } else {
            try {
                file.createNewFile();
                if (!WriteFile(filename, ByteConverter.getArray(VERSION))) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                ERROR_TEXT = e.getMessage();
                return false;
            }
        }
    }

    private byte[] ReadFile(String filename) {
        try {
            InputStream is = new FileInputStream(filename);
            byte[] input = new byte[is.available()];
            is.read(input, 0, is.available());
            is.close();
            return input;
        } catch (Exception e) {
            ERROR_TEXT = e.getMessage();
            ERROR = true;
            return null;
        }
    }

    private boolean WriteFile(String filename, byte[] bytes) {
        try {
            OutputStream os = new FileOutputStream(filename);
            os.write(bytes, 0, bytes.length);
            os.close();
            return true;
        } catch (Exception e) {
            ERROR = false;
            ERROR_TEXT = e.getMessage();
            return false;
        }
    }

    private static int getListSize(LinkedList<DataStruct> inputList) {
        int TR = 0;
        Iterator<DataStruct> iterator = inputList.listIterator();
        while (iterator.hasNext()) {
            DataStruct dataStruct = iterator.next();
            int type = dataStruct.type;
            switch (type) {
                case 0:
                    dataStruct.size = 1;
                    break;
                case 1:
                    dataStruct.size = 1;
                    break;
                case 2:
                    dataStruct.size = 2;
                    break;
                case 3:
                    dataStruct.size = 2;
                    break;
                case 4:
                    dataStruct.size = 4;
                    break;
                case 5:
                    dataStruct.size = 8;
                    break;
                case 6:
                    dataStruct.size = 4;
                    break;
                case 7:
                    dataStruct.size = 8;
                    break;
                case 8:
                    dataStruct.size = ((byte[]) dataStruct.object).length;
                    break;
                case 9:
                    dataStruct.size = ((int[]) dataStruct.object).length * 4;
                    break;
                case 10:
                    dataStruct.size = ((double[]) dataStruct.object).length * 8;
                    break;
                case 11:
                    dataStruct.size = ByteConverter.getArray((String) dataStruct.object).length;
                    break;
                case 12:
                    dataStruct.size = ByteConverter.getArray((Bitmap) dataStruct.object).length;
                    break;
            }
            TR += dataStruct.size; //size of object
            TR += 12; // int*3 tag, type, size
        }
        return TR;
    }

    private void Parse(byte[] bytes, LinkedList<DataStruct> outputList) {
        int i = 0;
        if (!CheckArraySizeWithError(bytes, i, 4)) return;
        int ver = ByteConverter.getInt(bytes, i);
        i += 4;
        if (ver != VERSION) {
            ERROR = true;
            ERROR_TEXT = "Invalid file version (current is " + VERSION + ", but file version is " + ver + ")";
            return;
        }
        while (i < bytes.length) {
            if (!CheckArraySizeWithError(bytes, i, 12)) return;
            int tag = ByteConverter.getInt(bytes, i);
            i += 4;
            int size = ByteConverter.getInt(bytes, i);
            i += 4;
            int type = ByteConverter.getInt(bytes, i);
            i += 4;
            if (!CheckArraySizeWithError(bytes, i, size)) return;
            Object current = null;
            switch (type) {
                case 0:
                    current = ByteConverter.getBoolean(bytes, i);
                    break;
                case 1:
                    current = ByteConverter.getByte(bytes, i);
                    break;
                case 2:
                    current = ByteConverter.getShort(bytes, i);
                    break;
                case 3:
                    current = ByteConverter.getChar(bytes, i);
                    break;
                case 4:
                    current = ByteConverter.getInt(bytes, i);
                    break;
                case 5:
                    current = ByteConverter.getLong(bytes, i);
                    break;
                case 6:
                    current = ByteConverter.getFloat(bytes, i);
                    break;
                case 7:
                    current = ByteConverter.getDouble(bytes, i);
                    break;
                case 8:
                    byte[] bt = Arrays.copyOfRange(bytes, i, i + size);
                    current = bt;
                    break;
                case 9:
                    current = ByteConverter.getIntArray(bytes, i, size);
                    break;
                case 10:
                    current = ByteConverter.getDoubleArray(bytes, i, size);
                    break;
                case 11:
                    current = ByteConverter.getString(bytes, i, size);
                    break;
                case 12:
                    current = ByteConverter.getBitmap(bytes, i, size);
                    break;
            }
            i += size;
            DataStruct dataStruct = new DataStruct();
            dataStruct.tag = tag;
            dataStruct.type = type;
            dataStruct.size = size;
            dataStruct.object = current;
            outputList.add(dataStruct);
        }
    }


    private boolean CheckArraySizeWithError(byte[] bytes, int pos, int value_size) {
        if (bytes.length >= pos + value_size) return true;
        else {
            ERROR = true;
            ERROR_TEXT = "File Parse Error: the expected size of the array must be greater";
            return false;
        }
    }

    public Object get(int tag) {
        Iterator<DataStruct> iterator = list.listIterator();
        while (iterator.hasNext()) {
            DataStruct dataStruct = iterator.next();
            if (dataStruct.tag == tag) {
                return dataStruct.object;
            }
        }
        return null;
    }

    public void remove(int tag) {
        Iterator<DataStruct> iterator = list.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            DataStruct dataStruct = iterator.next();
            if (dataStruct.tag == tag) {
                list.remove(index);
                return;
            }
            index++;
        }
    }
}