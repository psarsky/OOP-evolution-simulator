package proj.util;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d position) {
        super("Position " + position + " is occupied.");
    }
}
