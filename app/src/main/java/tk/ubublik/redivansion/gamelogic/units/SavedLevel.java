package tk.ubublik.redivansion.gamelogic.units;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.exceptions.ParseLevelException;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

import static tk.ubublik.redivansion.gamelogic.utils.ByteSettings.ByteConverter.*;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class SavedLevel {

    public List<WorldObject> worldObjects;
    public long time;
    public int money;
    public int level;

    public SavedLevel(List<WorldObject> worldObjects, long time, int money, int level) {
        this.worldObjects = worldObjects;
        this.time = time;
        this.money = money;
        this.level = level;
    }

    public SavedLevel(byte[] bytes) throws ParseLevelException {
        try {
            worldObjects = new LinkedList<>();
            int index = 0;
            int count = getInt(bytes, index);
            index += INT_SIZE;
            for (int i = 0; i < count; i++) {
                int size = getInt(bytes, index);
                index += INT_SIZE;
                worldObjects.add(WorldObject.parseFromBytes(bytes, index));
                index += size;
            }
            time = getLong(bytes, index);
            index += LONG_SIZE;
            money = getInt(bytes, index);
            index += INT_SIZE;
            level = getInt(bytes, index);
        } catch (InstantiationException | IllegalAccessException | UnsupportedOperationException|NoSuchMethodException|InvocationTargetException e) {
            throw new ParseLevelException("Can't parse level", e);
        }
    }

    private static final int INT_SIZE = 4;
    private static final int LONG_SIZE = 8;
    public byte[] getBytes() {
        LinkedList<byte[]> bytesList = new LinkedList<>();
        int size = 0;
        bytesList.add(getArray(worldObjects.size()));
        size+=INT_SIZE;
        for (WorldObject worldObject: worldObjects){
            byte[] worldObjectBytes = worldObject.toBytes();
            bytesList.add(getArray(worldObjectBytes.length));
            size+=INT_SIZE;
            bytesList.add(worldObjectBytes);
            size+=worldObjectBytes.length;
        }
        bytesList.add(getArray(time)); size+=LONG_SIZE;
        bytesList.add(getArray(money)); size+=INT_SIZE;
        bytesList.add(getArray(level)); size+=INT_SIZE;
        byte[] bytes = new byte[size];
        int index = 0;
        for(byte[] tempBytes: bytesList){
            insertArray(bytes, tempBytes, index);
            index+=tempBytes.length;
        }
        return bytes;
    }
}
