package proj.model;

import proj.util.Boundary;
import proj.util.RandomPositionGenerator;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {

    private final Map<Vector2d, Grass> grass;

    public GrassField(int grassCount) {
        super();
        this.grass = new HashMap<>();

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator((int) sqrt(grassCount * 10), (int) sqrt(grassCount * 10), grassCount);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grass.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (!isOccupied(position)) {
            return grass.get(position);
        }
        return super.objectAt(position);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> worldElements = super.getElements();
        worldElements.addAll(grass.values());
        return worldElements;
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2d ur = new Vector2d(lowerLeft.getX(), lowerLeft.getY());
        Vector2d ll = new Vector2d(upperRight.getX(), upperRight.getY());

        for (WorldElement we : getElements()) {
            ur = ur.upperRight(we.getPos());
            ll = ll.lowerLeft(we.getPos());
        }
        return new Boundary(ll, ur);
    }
}
