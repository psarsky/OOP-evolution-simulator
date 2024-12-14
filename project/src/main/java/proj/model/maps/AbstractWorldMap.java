package proj.model.maps;

import proj.model.elements.Animal;
import proj.model.elements.WorldElement;
import proj.presenter.MapChangeListener;
import proj.util.IncorrectPositionException;
import proj.util.MapVisualizer;
import proj.util.MoveDirection;
import proj.util.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected Vector2d lowerLeft = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    protected Vector2d upperRight = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    protected final HashMap<Vector2d, Animal> animals;
    protected final MapVisualizer mapVisualizer;
    protected final List<MapChangeListener> observers;
    protected final UUID id;

    public AbstractWorldMap() {
        this.animals = new HashMap<>();
        this.mapVisualizer = new MapVisualizer(this);
        this.observers = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return withinBoundaries(position) && !isOccupied(position);
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPos())) {
            animals.put(animal.getPos(), animal);
            notifyObservers("New animal placed at " + animal.getPos() + ".");
        }
        else {
            throw new IncorrectPositionException(animal.getPos());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if (Objects.equals(objectAt(animal.getPos()), animal)) {
            Vector2d oldPos = animal.getPos();
            animals.remove(animal.getPos());
            animal.move(direction, this);
            animals.put(animal.getPos(), animal);
            notifyObservers("Animal moved from " + oldPos + " to " + animal.getPos() + ".");
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public boolean withinBoundaries(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    public UUID getID() {
        return id;
    }
}
