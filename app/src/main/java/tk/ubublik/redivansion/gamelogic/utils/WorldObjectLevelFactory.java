package tk.ubublik.redivansion.gamelogic.utils;

import java.util.ArrayList;

import tk.ubublik.redivansion.gamelogic.units.Level;
import tk.ubublik.redivansion.gamelogic.units.objects.Office;
import tk.ubublik.redivansion.gamelogic.units.objects.Params;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObjectLevel;

import static tk.ubublik.redivansion.gamelogic.utils.GameParams.*;

/**
 * Created by Bublik on 31-Aug-17.
 *
 * Contains information and returns levels with params for all world object types
 */
public final class WorldObjectLevelFactory {

    private static ArrayList<CachedWorldObjectLevel> cachedWorldObjectLevels = new ArrayList<>();

    public static WorldObjectLevel getDefaultLevel(Class<WorldObject> worldObjectClass) {
        final String objectClassName = worldObjectClass.getName();
        if (objectClassName.equals(Office.class.getName())) {
            return new WorldObjectLevel(0, 0,
                    new Params(OFFICE_DEFAULT_MONTH_COST,
                            OFFICE_DEFAULT_POWER,
                            OFFICE_DEFAULT_FIRE,
                            OFFICE_DEFAULT_WATER,
                            OFFICE_DEFAULT_POLLUTION,
                            OFFICE_DEFAULT_MONEY,
                            OFFICE_DEFAULT_CRIMINAL,
                            OFFICE_DEFAULT_HEALTH));
        }
        return null;
        //throw new UnsupportedClassVersionError("No default level for "+objectClassName);
    }

    public static void initializeLevels(){
        // TODO: 28-Oct-17 create levels
    }

    public static void destroyLevels(){
        cachedWorldObjectLevels.clear();
    }

    public static WorldObjectLevel getObjectLevel(Class<WorldObject> objectClass, int level){
        String objectClassName = objectClass.getName();
        for (CachedWorldObjectLevel cachedWorldObjectLevel: cachedWorldObjectLevels){
            if (cachedWorldObjectLevel.levelNumber==level && cachedWorldObjectLevel.objectClassName.equals(objectClassName)){
                return cachedWorldObjectLevel.worldObjectLevel;
            }
        }
        throw new IllegalArgumentException("No level with current params: "+objectClassName+" - level "+Integer.toString(level));
    }

    private class CachedWorldObjectLevel{
        WorldObjectLevel worldObjectLevel;
        String objectClassName;
        int levelNumber;
    }
}
