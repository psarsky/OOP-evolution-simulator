package agh.ics.oop;

import proj.OptionsParser;
import proj.Simulation;
import proj.model.*;
import proj.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GrassFieldIntegrationTest {

    @Test
    public void grassSpawnAmount() {

        List<Vector2d> positions = new ArrayList<>();
        List<MoveDirection> moves = new ArrayList<>();

        Simulation simulation = new Simulation(positions,moves,new GrassField(20));
        simulation.run();

        int result=0;
        for(char c : simulation.toString().toCharArray()) {
            if(c=='*') result++;
        }

        assertEquals(20, result);
    }

    @Test
    public void visualPriority() {

        List<Vector2d> positions = new ArrayList<>();
        List<MoveDirection> moves = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                positions.add(new Vector2d(i,j));
            }
        }

        Simulation simulation = new Simulation(positions,moves,new GrassField(20));
        simulation.run();

        int result=0;
        for(char c : simulation.toString().toCharArray()) {
            if (c == '*') result++;
        }

        assertEquals(0, result);
    }

    @Test
    public void mapExtendsUponPlacement() {

        List<Vector2d> positions = new ArrayList<>();
        List<MoveDirection> moves = new ArrayList<>();
        GrassField map = new GrassField(20);
        positions.add(new Vector2d(20,15));

        Simulation simulation = new Simulation(positions, moves, map);
        simulation.run();

        assertEquals(new Vector2d(20,15), map.getCurrentBounds().upperRight());
    }

    @Test
    public void mapExtendsUponMovement() {

        List<Vector2d> positions = new ArrayList<>();
        List<MoveDirection> moves = OptionsParser.parse(new String[]{"r", "f", "f", "f", "l", "f"});
        GrassField map = new GrassField(20);
        positions.add(new Vector2d(15,15));

        Simulation simulation = new Simulation(positions, moves, map);
        simulation.run();

        assertEquals(new Vector2d(18,16), map.getCurrentBounds().upperRight());
    }
    @Test
    public void mapShrinks() {
        List<Vector2d> positions = new ArrayList<>();
        List<MoveDirection> moves = OptionsParser.parse(new String[]{"l", "f", "f"});
        GrassField map = new GrassField(20);
        positions.add(new Vector2d(20,15));

        Simulation simulation = new Simulation(positions,moves,map);

        simulation.run();

        assertEquals(new Vector2d(18,15), map.getCurrentBounds().upperRight());
    }

    @Test
    public void overlappingPlacement() {

        GrassField map = new GrassField(10);
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

        GrassField map = new GrassField(10);
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
    public void emptyMoves() {

        GrassField map = new GrassField(10);
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
        // overlapping placement, movement collisions
        GrassField map = new GrassField(10);
        List<Vector2d> initialPositions = List.of(new Vector2d(0, 0), new Vector2d(0, 0), new Vector2d(2, 3), new Vector2d(2, 3));
        List<MoveDirection> moves = OptionsParser.parse(new String[]{"f", "l", "r", "f", "f", "l", "l", "f", "f", "f", "l", "r", "f", "r", "f", "f", "f", "f", "l", "f", "f", "f", "f", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});

        Vector2d resultAnimal1 = new Vector2d(-2, -7);
        Vector2d resultAnimal2 = new Vector2d(7, 6);

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