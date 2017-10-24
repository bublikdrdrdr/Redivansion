package tk.ubublik.redivansion.gamelogic.units.objects;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Params {
    private final int monthCost;
    private final int power;
    private final int fire;
    private final int water;
    private final int pollution;
    private final int money;
    private final int criminal;
    private final int health;

    public Params(int monthCost, int power, int fire, int water, int pollution, int money, int criminal, int health) {
        this.monthCost = monthCost;
        this.power = power;
        this.fire = fire;
        this.water = water;
        this.pollution = pollution;
        this.money = money;
        this.criminal = criminal;
        this.health = health;
    }

    public int getMonthCost() {
        return monthCost;
    }

    public int getPower() {
        return power;
    }

    public int getFire() {
        return fire;
    }

    public int getWater() {
        return water;
    }

    public int getPollution() {
        return pollution;
    }

    public int getMoney() {
        return money;
    }

    public int getCriminal() {
        return criminal;
    }

    public int getHealth() {
        return health;
    }
}
