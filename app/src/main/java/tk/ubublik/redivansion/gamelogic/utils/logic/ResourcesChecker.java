package tk.ubublik.redivansion.gamelogic.utils.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.PowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;

/**
 * Created by Bublik on 18-Oct-17.
 */

public class ResourcesChecker extends Checker implements Runnable{

    private Thread thread;
    private volatile boolean done = false;
    private WorldMap temporaryClone;

    enum LogicProperty{POWER, WATER, FIRE, POLLUTION, HAPPINESS, EDUCATION, CRIMINAL, HEALTH}

    public ResourcesChecker(WorldMap worldMap) {
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
    public boolean isDone() {
        if (done){
            done = false;
            return true;
        } else return false;
    }

    @Override
    public boolean isWorking() {
        return false;
    }

    @Override
    public void join() throws InterruptedException {

    }

    @Override
    public void run() {
        //power
        //find power plants
        List<Producer> powerPlants = new LinkedList<>();
        for (WorldObject worldObject: temporaryClone.getWorldObjects()){
            if (worldObject instanceof PowerPlant){
                powerPlants.add(new Producer(worldObject));
            }
        }
        //find consumers
        for (WorldObject worldObject: temporaryClone.getWorldObjects()){
            if (!(worldObject instanceof PowerPlant)) {
                // TODO: 26-Oct-17 calculate how much energy object needs
                //right here
                for (Producer producer : powerPlants) {
                    float distance = temporaryClone.getDistanceSqr(producer.producer, worldObject);
                    if (distance <= ((PowerPlant) producer.producer).radiusSqr) {
                        producer.add(worldObject, distance);
                    }
                }
            }
        }
        //sort by count
        Collections.sort(powerPlants, new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return Integer.compare(o1.consumers.size(), o2.consumers.size());
            }
        });
        //sort by distance
        for (Producer producer: powerPlants){
            Collections.sort(producer.consumers, new Comparator<Consumer>() {
                @Override
                public int compare(Consumer o1, Consumer o2) {
                    return Float.compare(o1.distance, o2.distance);
                }
            });
        }
    }

    private class Producer{
        WorldObject producer;
        List<Consumer> consumers = new LinkedList<>();

        Producer(WorldObject producer) {
            this.producer = producer;
        }

        public void add(WorldObject worldObject, float distance){
            consumers.add(new Consumer(worldObject, distance));
        }
    }

    private class Consumer{
        WorldObject consumer;
        float distance;

        Consumer(WorldObject consumer, float distance) {
            this.consumer = consumer;
            this.distance = distance;
        }
    }

    private void process(LogicProperty logicProperty, List<WorldObject> producers, List<WorldObject> consumers){

    }

}
