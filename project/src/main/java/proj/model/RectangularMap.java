package proj.model;

import proj.util.Boundary;

public class RectangularMap extends AbstractWorldMap {
    public RectangularMap(int width, int height) {
        super();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(lowerLeft, upperRight);
    }
}
