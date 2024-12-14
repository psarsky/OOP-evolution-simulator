package proj;

import proj.model.maps.AbstractWorldMap;
import proj.model.maps.GrassField;
import proj.model.maps.RectangularMap;
import proj.presenter.ConsoleMapDisplay;
import proj.simulation.Simulation;
import proj.simulation.SimulationEngine;
import proj.util.MoveDirection;
import proj.util.Vector2d;

import java.util.ArrayList;
import java.util.List;

import static proj.util.OptionsParser.parse;

public class World {
    public static void main(String[] args) {
        System.out.println("START");
        try {
            List<MoveDirection> directions = parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
            SimulationEngine engine = getSimulationEngine(positions, directions);
            engine.runAsyncInThreadPool();
            engine.awaitSimulationsEnd();
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
            return;
        }
        System.out.println("STOP");
    }

    static SimulationEngine getSimulationEngine(List<Vector2d> positions, List<MoveDirection> directions) {
        List<Simulation> simulations = new ArrayList<>();
        ConsoleMapDisplay observer = new ConsoleMapDisplay();
        for (int i = 0; i < 1000; i++) {
            AbstractWorldMap map = i % 2 == 0 ? new GrassField(10) : new RectangularMap(8, 8);
            map.addObserver(observer);
            Simulation simulation = new Simulation(positions, directions, map);
            simulations.add(simulation);
        }
        return new SimulationEngine(simulations);
    }
}
