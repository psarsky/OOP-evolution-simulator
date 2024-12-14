package proj.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void equalsSameVectors() {
        // when
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);

        // then
        assertEquals(v1, v2);
    }

    @Test
    void equalsDifferentVectors() {
        // when
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, -2);

        // then
        assertNotEquals(v1, v2);
    }

    @Test
    void equalsOneIsNull() {
        // when
        Vector2d v = new Vector2d(1, 2);

        // then
        assertNotEquals(v, null);
    }

    @Test
    void toStringTest() {
        // given
        Vector2d v1 = new Vector2d(1, 2);

        // when
        String v2 = v1.toString();

        // then
        assertEquals("(1, 2)", v2);
    }

    @Test
    void precedesSameVectors() {
        // given
        Vector2d v1 = new Vector2d(2, 5);

        // when
        Vector2d v2 = new Vector2d(2, 5);

        // then
        assertTrue(v1.precedes(v2));
    }

    @Test
    void precedesDifferentVectorsCorrect() {
        // given
        Vector2d v1 = new Vector2d(1, 3);

        // when
        Vector2d v2 = new Vector2d(2, 5);

        // then
        assertTrue(v1.precedes(v2));
    }

    @Test
    void precedesDifferentVectorsIncorrect() {
        // given
        Vector2d v1 = new Vector2d(2, 5);

        // when
        Vector2d v2 = new Vector2d(1, 3);

        // then
        assertFalse(v1.precedes(v2));
    }

    @Test
    void precedesNeitherVectorPrecedes() {
        // given
        Vector2d v1 = new Vector2d(1, 3);

        // when
        Vector2d v2 = new Vector2d(0, 5);

        // then
        assertFalse(v1.precedes(v2));
    }

    @Test
    void followsSameVectors() {
        // given
        Vector2d v1 = new Vector2d(2, 5);

        // when
        Vector2d v2 = new Vector2d(2, 5);

        // then
        assertTrue(v1.follows(v2));
    }

    @Test
    void followsDifferentVectorsCorrect() {
        // given
        Vector2d v1 = new Vector2d(2, 5);

        // when
        Vector2d v2 = new Vector2d(1, 3);

        // then
        assertTrue(v1.follows(v2));
    }

    @Test
    void followsDifferentVectorsIncorrect() {
        // given
        Vector2d v1 = new Vector2d(1, 3);

        // when
        Vector2d v2 = new Vector2d(2, 5);

        // then
        assertFalse(v1.follows(v2));
    }

    @Test
    void followsNeitherVectorFollows() {
        // given
        Vector2d v1 = new Vector2d(1, 3);

        // when
        Vector2d v2 = new Vector2d(0, 5);

        // then
        assertFalse(v1.follows(v2));
    }

    @Test
    void upperRightIsV1() {
        // given
        Vector2d v1 = new Vector2d(2, 5);
        Vector2d v2 = new Vector2d(1, -3);
        Vector2d expectedResult = new Vector2d(2, 5);

        // when
        Vector2d result = v1.upperRight(v2);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void upperRightIsCombination() {
        // given
        Vector2d v1 = new Vector2d(2, 5);
        Vector2d v2 = new Vector2d(1, 10);
        Vector2d expectedResult = new Vector2d(2, 10);

        // when
        Vector2d result = v1.upperRight(v2);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void lowerLeftIsV1() {
        // given
        Vector2d v1 = new Vector2d(1, -3);
        Vector2d v2 = new Vector2d(2, 5);
        Vector2d expectedResult = new Vector2d(1, -3);

        // when
        Vector2d result = v1.lowerLeft(v2);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void lowerLeftIsCombination() {
        // given
        Vector2d v1 = new Vector2d(2, 5);
        Vector2d v2 = new Vector2d(1, 10);
        Vector2d expectedResult = new Vector2d(1, 5);

        // when
        Vector2d result = v1.lowerLeft(v2);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void add() {
        // given
        Vector2d v1 = new Vector2d(2, 5);
        Vector2d v2 = new Vector2d(1, -3);
        Vector2d expectedResult = new Vector2d(3, 2);

        // when
        Vector2d result = v1.add(v2);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void subtract() {
        // given
        Vector2d v1 = new Vector2d(2, 5);
        Vector2d v2 = new Vector2d(1, -3);
        Vector2d expectedResult = new Vector2d(1, 8);

        // when
        Vector2d result = v1.subtract(v2);

        // then
        assertEquals(expectedResult, result);
    }
    
    @Test
    void opposite() {
        // given
        Vector2d v = new Vector2d(2, 5);
        Vector2d expectedResult = new Vector2d(-2, -5);

        // when
        Vector2d result = v.opposite();

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    void oppositeZeroVector() {
        // given
        Vector2d v = new Vector2d(0, 0);
        Vector2d expectedResult = new Vector2d(0, 0);

        // when
        Vector2d result = v.opposite();

        // then
        assertEquals(expectedResult, result);
    }
}
