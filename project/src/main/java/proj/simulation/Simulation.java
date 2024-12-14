package proj.simulation;

import proj.model.elements.Animal;
import proj.util.MoveDirection;
import proj.util.Vector2d;
import proj.model.maps.WorldMap;
import proj.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap map;

    public Simulation(List<Vector2d> initialPositions, List<MoveDirection> moves, WorldMap map) {
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();

        for (Vector2d pos : initialPositions) {
            Animal animal = new Animal(pos);
            try {
                this.map.place(animal);
            } catch (IncorrectPositionException e) {
                System.out.println("IncorrectPositionException: " + e.getMessage());
                continue;
            }
            this.animals.add(animal);
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public void run() {
        for(int i = 0; i < moves.size(); i++){
            map.move(animals.get(i % animals.size()), moves.get(i));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Animal getAnimal(int index) {
        return animals.get(index);
    }
}
