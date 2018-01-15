package tk.ubublik.redivansion.gamelogic.utils.logic;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tk.ubublik.redivansion.gamelogic.units.WorldMap;
import tk.ubublik.redivansion.gamelogic.units.objects.PowerPlant;
import tk.ubublik.redivansion.gamelogic.units.objects.Tree;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject;
import tk.ubublik.redivansion.gamelogic.units.objects.WorldObject.*;

/**
 * Created by Bublik on 18-Oct-17.
 */

public class ResourcesChecker extends Checker implements Runnable{

    private WorldMap temporaryClone;

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
    public void run() {
        try {
            v2();
            done = true;
        } catch (InterruptedException ie){
            done = false;
            temporaryClone = null;
        }
    }

    private void v2() throws InterruptedException {
        HashMap<WorldObjectTypeKey, List<WorldObject>> hashMap = recalculateAndSeparateByType(temporaryClone.getWorldObjects());
        ResourceType[] resourceTypes = ResourceType.values();
        for (ResourceType resourceType: resourceTypes){
            checkInterrupted();
            List<WorldObject> producers = hashMap.get(new WorldObjectTypeKey(resourceType, true));
            List<WorldObject> consumers = hashMap.get(new WorldObjectTypeKey(resourceType, false));
            processResourceType(resourceType, producers, consumers);
        }
    }

    private HashMap<WorldObjectTypeKey, List<WorldObject>> recalculateAndSeparateByType(List<WorldObject> list) throws InterruptedException {
        HashMap<WorldObjectTypeKey, List<WorldObject>> hashMap = new HashMap<>();
        ResourceType[] resourceTypes = ResourceType.values();
        for (ResourceType resourceType:resourceTypes){
            hashMap.put(new WorldObjectTypeKey(resourceType, false), new LinkedList<WorldObject>());
            hashMap.put(new WorldObjectTypeKey(resourceType, true), new LinkedList<WorldObject>());
        }
        for (WorldObject worldObject: list) {
            checkInterrupted();
            worldObject.recalculateParams();
            if (!worldObject.roadConnected && !(worldObject instanceof Tree)) continue;
            if (worldObject.power > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.POWER, true)).add(worldObject);
            else if (worldObject.power < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.POWER, false)).add(worldObject);

            if (worldObject.fire > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.FIRE, true)).add(worldObject);
            else if (worldObject.fire < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.FIRE, false)).add(worldObject);

            if (worldObject.water > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.WATER, true)).add(worldObject);
            else if (worldObject.water < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.WATER, false)).add(worldObject);

            if (worldObject.pollution > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.POLLUTION, true)).add(worldObject);
            else if (worldObject.pollution < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.POLLUTION, false)).add(worldObject);

            if (worldObject.criminal > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.CRIMINAL, true)).add(worldObject);
            else if (worldObject.criminal < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.CRIMINAL, false)).add(worldObject);

            if (worldObject.health > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.HEALTH, true)).add(worldObject);
            else if (worldObject.health < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.HEALTH, false)).add(worldObject);

            if (worldObject.work > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.WORK, true)).add(worldObject);
            else if (worldObject.work < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.WORK, false)).add(worldObject);

            if (worldObject.happiness > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.HAPPINESS, true)).add(worldObject);
            else if (worldObject.happiness < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.HAPPINESS, false)).add(worldObject);

            if (worldObject.education > 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.EDUCATION, true)).add(worldObject);
            else if (worldObject.education < 0)
                hashMap.get(new WorldObjectTypeKey(ResourceType.EDUCATION, false)).add(worldObject);
        }
        return hashMap;
    }

    private void processResourceType(ResourceType resourceType, List<WorldObject> producers, List<WorldObject> consumers) throws InterruptedException {
        List<Producer> list = getSortedProducerList(producers, consumers);
        for (Producer producer : list) {
            int producerResourceValue = producer.producer.getResourceValue(resourceType);
            while (producerResourceValue > 0) {
                boolean stillNeeded = false;
                if (producer.consumers.size()==0) break;
                int part = Math.max(producerResourceValue / producer.consumers.size(), 1);
                Iterator<Consumer> consumerIterator = producer.consumers.iterator();
                while (consumerIterator.hasNext()){
                    Consumer consumer = consumerIterator.next();
                    int consumerValue = consumer.consumer.getResourceValue(resourceType);
                    if (consumerValue + part >= 0) {
                        producerResourceValue+=consumerValue;
                        consumer.consumer.setResourceValue(resourceType, 0);
                        consumerIterator.remove();
                    } else {
                        producerResourceValue-=part;
                        consumer.consumer.setResourceValue(resourceType, consumerValue+part);
                        stillNeeded = true;
                    }
                    if (producerResourceValue<=0) {
                        stillNeeded = false;
                        break;
                    }
                }
                if (!stillNeeded) break;
            }
            producer.producer.setResourceValue(resourceType, producerResourceValue);
        }
    }

    private List<Producer> getSortedProducerList(List<WorldObject> producers, List<WorldObject> consumers) throws InterruptedException {
        List<Producer> list = new LinkedList<>();
        for (WorldObject producerObject: producers){
            checkInterrupted();
            Producer currentProducer = new Producer(producerObject);
            for (WorldObject consumerObject: consumers){
                float distance = temporaryClone.getDistanceSqr(producerObject, consumerObject);
                if (distance <= producerObject.radiusSqr) {
                    currentProducer.add(consumerObject, distance);
                }
            }
            Collections.sort(currentProducer.consumers, new Comparator<Consumer>() {
                @Override
                public int compare(Consumer o1, Consumer o2) {
                    return Float.compare(o1.distance, o2.distance);
                }
            });
            list.add(currentProducer);
        }
        Collections.sort(list, new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return Integer.compare(o1.consumers.size(), o2.consumers.size());
            }
        });
        return list;
    }

    private class WorldObjectTypeKey{
        public ResourceType resourceType;
        public boolean producer;

        public WorldObjectTypeKey(ResourceType resourceType, boolean producer) {
            this.resourceType = resourceType;
            this.producer = producer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WorldObjectTypeKey)) return false;
            WorldObjectTypeKey key = (WorldObjectTypeKey) o;
            return resourceType.equals(key.resourceType) && producer==key.producer;
        }

        @Override
        public int hashCode() {
            return resourceType.ordinal()*(producer?-1:1);
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

}
