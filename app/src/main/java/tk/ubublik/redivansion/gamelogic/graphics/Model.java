package tk.ubublik.redivansion.gamelogic.graphics;

import java.util.ArrayList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.utils.ByteSettings;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Model {

    private List<PolyAnimation> animations;

    //нам це треба?
    private int id;
    private String name;

    public Model(List<PolyAnimation> animations){
        this.animations = animations;
    }

    public Model(byte[] bytes){
        this.animations = parseBytes(bytes);
    }

    /*
    Parse byte array to model (from file/memory)
    JSON is bad idea cause of performance and memory size
     */
    private List<PolyAnimation> parseBytes(byte[] bytes) {
        try {
            List<PolyAnimation> list = new ArrayList<>();
            int count = ByteSettings.ByteConverter.getInt(bytes, 0);
            if (count < 0) throw new IllegalArgumentException("Count number is negative");
            int index = 4;//current position in array, 4 - size of int
            for (int i = 0; i < count; i++){
                int size = ByteSettings.ByteConverter.getInt(bytes, index);
                index+=4;
                PolyAnimation polyAnimation = new PolyAnimation(bytes, index);
                index+=size;
                list.add(polyAnimation);
            }
            return list;
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't parse model from bytes", e);
        }
    }

    private byte[] getBytes(List<PolyAnimation> list){
        return null;
        // TODO: 21-Aug-17
    }

}
