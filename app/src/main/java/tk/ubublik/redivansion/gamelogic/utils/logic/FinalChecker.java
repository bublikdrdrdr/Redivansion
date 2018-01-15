package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.House;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 25-Oct-17.
 */

public class FinalChecker extends Checker implements Runnable{

    private WorldMap temporaryClone;
    private Result result;

    public FinalChecker(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public void refresh() {
        if (thread!=null && thread.isAlive()){
            thread.interrupt();
        }
        done = false;
        result = null;
        thread = new Thread(this);
        temporaryClone = worldMap.clone();
        thread.start();
    }

    public Result getResult(){
        return result;
    }

    @Override
    public void run() {
        try{
            int population = 0;
            int moneyDelta = 0;
            for(WorldObject worldObject: temporaryClone.getWorldObjects()){
                checkInterrupted();
                if (worldObject instanceof House){
                    population += checkPopulation((House)worldObject);
                }
                moneyDelta += checkMoney(worldObject);
            }
            result = new Result(population, moneyDelta);
            done = true;
        } catch (InterruptedException ignored){
            done = false;
        }
    }

    private int checkPopulation(House house){
        house.checkPopulation();
        return house.getPopulation();
    }

    private double checkMoney(WorldObject worldObject){
        return worldObject.getMoneyDelta();
    }

    public class Result{
        public int population;
        public int moneyDelta;

        public Result(int population, int moneyDelta) {
            this.population = population;
            this.moneyDelta = moneyDelta;
        }
    }
}
