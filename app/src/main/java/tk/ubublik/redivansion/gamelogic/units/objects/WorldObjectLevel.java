package tk.ubublik.redivansion.gamelogic.units.objects;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class WorldObjectLevel {
    private final int upgradeCost;
    private final int number;
    private final Params params;

    public WorldObjectLevel(int upgradeCost, int number, Params params) {
        this.upgradeCost = upgradeCost;
        this.number = number;
        this.params = params;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public int getNumber() {
        return number;
    }

    public Params getParams() {
        return params;
    }
}
