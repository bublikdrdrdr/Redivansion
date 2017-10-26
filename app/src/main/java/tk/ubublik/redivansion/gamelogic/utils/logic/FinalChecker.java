package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;

/**
 * Created by Bublik on 25-Oct-17.
 */


//blaha muha, ne mozu nazwy prydumaty, roma help
public class FinalChecker extends Checker{

    public FinalChecker(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean isWorking() {
        return false;
    }

    @Override
    public void join() throws InterruptedException {

    }

}
