package tk.ubublik.redivansion.gamelogic.graphics;


import java.util.ArrayList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.utils.ByteSettings;

/**
 * One-step model animation.
 * For example: some building must be created, upgraded and destroyed.
 * Every process has own animation and this class - it is.
 */
public class PolyAnimation {

    //list of single polygons animation
    private List<Polygon> polygons;
    //animation name ("build", "upgrade", "destroy")
    private String name;

    public PolyAnimation(String name, List<Polygon> polygons){
        this.name = name;
        this.polygons = polygons;
    }

    public PolyAnimation(byte[] bytes, int index){
        try{
            int stringSize = ByteSettings.ByteConverter.getInt(bytes, index);
            index+=4;
            String name = ByteSettings.ByteConverter.getString(bytes, index, stringSize);
            index+=stringSize;
            this.polygons = parseBytes(bytes, index);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't parse model from bytes", e);
        }

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

    public PolyAnimation(String name){
        this(name, new ArrayList<Polygon>());
    }
    
    public byte[] getBytes(){
        return null;
        // TODO: 21-Aug-17  
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public String getName() {
        return name;
    }
}