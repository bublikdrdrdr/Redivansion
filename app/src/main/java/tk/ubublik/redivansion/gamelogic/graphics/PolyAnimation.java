package tk.ubublik.redivansion.gamelogic.graphics;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.utils.ByteSettings.ByteConverter;

/**
 * Created by Bublik on 22-Aug-17.
 */

/**
 * One-step model animation.
 * For example: some building must be created, upgraded and destroyed.
 * Every process has own animation and this class - it is.
 */
public class PolyAnimation implements Cloneable{

    private List<Polygon> polygons;
    private String name;

    public PolyAnimation(String name, List<Polygon> polygons){
        this.name = name;
        this.polygons = polygons;
    }

    public PolyAnimation(byte[] bytes, int index){
        try{
            int stringSize = ByteConverter.getInt(bytes, index);
            index+=4;
            name = ByteConverter.getString(bytes, index, stringSize);
            index+=stringSize;
            this.polygons = parseBytes(bytes, index);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't parse model from bytes", e);
        }

    }

    private List<Polygon> parseBytes(byte[] bytes, int index){
        try {
            List<Polygon> list = new ArrayList<>();
            int count = ByteConverter.getInt(bytes, index);
            if (count < 0) throw new IllegalArgumentException("Count number is negative");
            index+=4;
            for (int i = 0; i < count; i++){
                Polygon polygon = new Polygon(bytes, index);
                index+=Polygon.POLYGON_SIZE;
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
        byte[] nameStringBytes = ByteConverter.getArray(name);
        int polygonsCount = polygons.size();
        int nameStringSize = nameStringBytes.length;
        int polygonsSize = polygonsCount * Polygon.POLYGON_SIZE;
        
        int size = 4 + nameStringSize + 4 + polygonsCount * 4 + polygonsSize;
        byte[] bytes = new byte[size];
        int index = 0;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(nameStringSize), index);
        index+=4;
        ByteConverter.insertArray(bytes, nameStringBytes, index);
        index+=nameStringSize;
        ByteConverter.insertArray(bytes, ByteConverter.getArray(polygonsCount), index);
        index+=4;
        for (Polygon polygon:polygons) {
            ByteConverter.insertArray(bytes, polygon.getBytes(), index);
            index+=Polygon.POLYGON_SIZE;
        }
        return bytes;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public String getName() {
        return name;
    }

    public void addPolygon(Polygon polygon){
        this.polygons.add(polygon);
        resetCount();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            if (obj instanceof PolyAnimation) {
                PolyAnimation anotherPolyAnimation = (PolyAnimation) obj;
                if (!anotherPolyAnimation.name.equals(getName())) return false;
                if (anotherPolyAnimation.getPolygons().size()==getPolygons().size()){
                    Iterator<Polygon> anotherPolygonsIterator = anotherPolyAnimation.getPolygons().iterator();
                    Polygon anotherPolygon;
                    for (Polygon polygon: getPolygons()){
                        anotherPolygon = anotherPolygonsIterator.next();
                        if (!anotherPolygon.equals(polygon)) return false;
                    }
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e){
            return false;
        }
    }

    private void resetCount(){
        count = -1;
    }

    private int count = -1;
    public int polygonCount(){
        if (count==-1) {
            count = this.polygons.size();
        }
        return count;
    }

    @Override
    public PolyAnimation clone() {
        List<Polygon> polygons = new LinkedList<>();
        for (Polygon polygon: getPolygons()){
            polygons.add(polygon.clone());
        }
        return new PolyAnimation(getName(), polygons);
    }
}