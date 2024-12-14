package proj.util;

import proj.model.Vector2d;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d position) {
        super("Position " + position + " is occupied.");
    }
}
