package proj.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {

    private final List<Vector2d> positions = new ArrayList<>();

    public RandomPositionGenerator(int maxWidth, int maxHeight, int count) {

        int[] indices = new int[maxWidth * maxHeight];

        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        Random rand = new Random();

        for (int i = indices.length - 1; i > indices.length - count - 2; i--) {
            int j = rand.nextInt(i);
            int tmp = indices[i];
            indices[i] = indices[j];
            indices[j] = tmp;
        }

        for (int i = indices.length - 1; i > indices.length - count - 2; i--) {
            positions.add(new Vector2d(indices[i] % maxWidth, indices[i] / maxWidth));
        }
    }

    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator(this);
    }

    public Vector2d getHead() {
        return positions.getFirst();
    }

    public Vector2d getTail() {
        return positions.getLast();
    }

    public int getIndex(Vector2d pos) {
        return positions.indexOf(pos);
    }

    public Vector2d getPos(int index) {
        return positions.get(index);
    }
}

class RandomPositionIterator implements Iterator<Vector2d> {

    private final RandomPositionGenerator generator;
    private Vector2d current;

    RandomPositionIterator(RandomPositionGenerator generator) {
        current = generator.getHead();
        this.generator = generator;
    }

    public boolean hasNext() {
        return !current.equals(generator.getTail());
    }

    public Vector2d next() {
        Vector2d data = current;
        int index = generator.getIndex(current) + 1;
        current = generator.getPos(index);
        return data;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
