package tk.ubublik.redivansion.gamelogic.graphics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.utils.ByteSettings.ByteConverter;

/**
 * Created by Bublik on 20-Aug-17.
 */

public class Model {

    private List<PolyAnimation> animations;

    //нам це треба?
    private int id;
    private String name;

    public Model(){
        this(new LinkedList<PolyAnimation>());
    }

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
            int count = ByteConverter.getInt(bytes, 0);
            if (count < 0) throw new IllegalArgumentException("Count number is negative");
            int index = 4;//current position in array, 4 - size of int
            for (int i = 0; i < count; i++){
                int size = ByteConverter.getInt(bytes, index);
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

    public byte[] getBytes(){
        int size = 0;
        LinkedList<byte[]> byteArraysList = new LinkedList<>();
        int animationsCount = animations.size();
        byteArraysList.add(ByteConverter.getArray(animationsCount));
        size+=4;
        for(PolyAnimation polyAnimation: animations){
            byte[] animationBytes = polyAnimation.getBytes();
            byteArraysList.add(ByteConverter.getArray(animationBytes.length));
            size+=4;
            byteArraysList.add(animationBytes);
            size+=animationBytes.length;
        }

        byte[] bytes = new byte[size];
        int index = 0;
        for (byte[] byteArray:byteArraysList) {
            ByteConverter.insertArray(bytes, byteArray, index);
            index+=byteArray.length;
        }
        return bytes;
    }

    public List<PolyAnimation> getAnimations() {
        return animations;
    }

    public void addAnimation(PolyAnimation polyAnimation){
        animations.add(polyAnimation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            if (obj instanceof Model) {
                Model anotherModel = (Model) obj;
                if (anotherModel.getAnimations().size()==getAnimations().size()){
                    Iterator<PolyAnimation> anotherAnimationsIterator = anotherModel.getAnimations().iterator();
                    PolyAnimation anotherAnimation;
                    for (PolyAnimation polyAnimation: getAnimations()){
                        anotherAnimation = anotherAnimationsIterator.next();
                        if (!anotherAnimation.equals(polyAnimation)) return false;
                    }
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e){
            return false;
        }
    }
}
