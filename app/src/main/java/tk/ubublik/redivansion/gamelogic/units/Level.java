package tk.ubublik.redivansion.gamelogic.units;

import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Level {
    LevelStatus levelStatus;
    List<WorldObject> worldObjects;
    long time;
    int money;
    LevelGoal levelGoal;
}
