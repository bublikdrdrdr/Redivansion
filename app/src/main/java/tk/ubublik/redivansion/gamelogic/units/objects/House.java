package tk.ubublik.redivansion.gamelogic.units.objects;

import tk.ubublik.redivansion.gamelogic.graphics.GeometryManager;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class House extends Building {

    private int population;

    public House() {
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parseBytes() {

    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
        population = Math.max(population, getLevel().getParams().getPollution());
    }
}
