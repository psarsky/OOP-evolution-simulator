package proj.presenter;

import proj.model.maps.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {
    private int count = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        System.out.println("---------------------------------------------\n");
        System.out.println("Map ID: " + worldMap.getID());
        System.out.println("Update " + ++count + ": " + message);
        System.out.println(worldMap);
    }
}
