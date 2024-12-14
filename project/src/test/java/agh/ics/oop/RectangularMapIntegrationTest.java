package agh.ics.oop;

import proj.OptionsParser;
import proj.Simulation;
import proj.model.*;

import java.util.List;
import java.util.ArrayList;

import proj.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangularMapIntegrationTest {

    @Test
    public void outOfBoundsPlacement() {

        RectangularMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(new Vector2d(5, 5));

        assertThrows(IncorrectPositionException.class, () -> map.place(animal));
    }

    @Test
    public void overlappingPlacement() {

        RectangularMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 2));

        try {
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));

        WorldElement placedAnimal = map.objectAt(new Vector2d(2, 2));

        assertEquals(animal1, placedAnimal);
    }

    @Test
    public void validMoves() {

        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> initialPositions = List.of(new Vector2d(2, 2), new Vector2d(1, 1));
        List<MoveDirection> moves = OptionsParser.parse(new String[]{"f", "r", "f", "l", "b", "f", "r", "l"});

        Vector2d resultAnimal1 = new Vector2d(2, 3);
        Vector2d resultAnimal2 = new Vector2d(1, 2);

        Simulation simulation = new Simulation(initialPositions, moves, map);
        simulation.run();

        Animal animal1 = simulation.getAnimal(0);
        Animal animal2 = simulation.getAnimal(1);

        assertEquals(resultAnimal1, animal1.getPos());
        assertEquals(resultAnimal2, animal2.getPos());

        assertEquals(MapDirection.EAST, animal1.getDir());
        assertEquals(MapDirection.WEST, animal2.getDir());
    }

    @Test
    public void boundaryConditions() {

        RectangularMap map = new RectangularMap(4, 4);
        List<Vector2d> initialPositions = List.of(new Vector2d(0, 0), new Vector2d(3, 3));
        List<MoveDirection> moves = OptionsParser.parse(new String[]{"b", "f", "l", "r", "f", "f"});

        Vector2d resultAnimal1 = new Vector2d(0, 0);
        Vector2d resultAnimal2 = new Vector2d(3, 3);

        Simulation simulation = new Simulation(initialPositions, moves, map);
        simulation.run();

        Animal animal1 = simulation.getAnimal(0);
        Animal animal2 = simulation.getAnimal(1);

        assertEquals(resultAnimal1, animal1.getPos());
        assertEquals(resultAnimal2, animal2.getPos());
    }

    @Test
    public void emptyMoves() {

        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> initialPositions = List.of(new Vector2d(1, 1), new Vector2d(2, 3));

        Vector2d resultAnimal1 = new Vector2d(1, 1);
        Vector2d resultAnimal2 = new Vector2d(2, 3);

        Simulation simulation = new Simulation(initialPositions, new ArrayList<>(), map);
        simulation.run();

        Animal animal1 = simulation.getAnimal(0);
        Animal animal2 = simulation.getAnimal(1);

        assertEquals(resultAnimal1, animal1.getPos());
        assertEquals(resultAnimal2, animal2.getPos());

        assertEquals(MapDirection.NORTH, animal1.getDir());
        assertEquals(MapDirection.NORTH, animal2.getDir());
    }

    @Test
    public void allInOne() {
        // overlapping placement, OOB placement, OOB movement, movement collisions
        RectangularMap map = new RectangularMap(6, 6);
        List<Vector2d> initialPositions = List.of(new Vector2d(0, 0), new Vector2d(0, 0), new Vector2d(2, 3), new Vector2d(6, 6), new Vector2d(2, 3));
        List<MoveDirection> moves = OptionsParser.parse(new String[]{"f", "l", "r", "f", "f", "l", "l", "f", "f", "f", "l", "r", "f", "r", "f", "f", "f", "f", "l", "f", "f", "f", "f", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});

        Vector2d resultAnimal1 = new Vector2d(0, 0);
        Vector2d resultAnimal2 = new Vector2d(5, 5);

        Simulation simulation = new Simulation(initialPositions, moves, map);
        simulation.run();

        Animal animal1 = simulation.getAnimal(0);
        Animal animal2 = simulation.getAnimal(1);

        assertEquals(resultAnimal1, animal1.getPos());
        assertEquals(resultAnimal2, animal2.getPos());

        assertEquals(MapDirection.SOUTH, animal1.getDir());
        assertEquals(MapDirection.EAST, animal2.getDir());
    }
}
