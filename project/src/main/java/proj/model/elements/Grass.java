package proj.model.elements;

import proj.util.Vector2d;

public class Grass implements WorldElement {

    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Vector2d getPos() {
        return position;
    }
}
