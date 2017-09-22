package tk.ubublik.redivansion.gamelogic.utils;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryAnimationManager;
import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 21-Sep-17.
 */

public class WorldObjectFactory {

    public static WorldObject get(Class<? extends WorldObject> clazz){
        String name = clazz.getCanonicalName();
        if (name.equals(Office.class.getCanonicalName())) return getOffice();
        throw new IllegalArgumentException("Unknown class type: "+name);
    }

    private static WorldObject getOffice(){
        Office office = new Office();
        office.setSize(2);
        return office;
    }
}
