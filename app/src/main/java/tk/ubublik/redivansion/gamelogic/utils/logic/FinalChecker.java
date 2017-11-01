package tk.ubublik.redivansion.gamelogic.utils.logic;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 25-Oct-17.
 */


//blaha muha, ne mozu nazwy prydumaty, roma help
public class FinalChecker extends Checker implements Runnable{

    private WorldMap temporaryClone;

    public FinalChecker(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public void refresh() {
        if (thread!=null && thread.isAlive()){
            thread.interrupt();
        }
        done = false;
        thread = new Thread(this);
        temporaryClone = worldMap.clone();
        thread.start();
    }

    @Override
    public void run() {
        try{
            for(WorldObject worldObject: temporaryClone.getWorldObjects()){
                checkInterrupted();

            }
        } catch (InterruptedException ignored){

        }
    }
}
