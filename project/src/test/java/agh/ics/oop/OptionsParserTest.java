package agh.ics.oop;

import proj.OptionsParser;
import proj.model.MoveDirection;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class OptionsParserTest {

    @Test
    void parseCorrectInput() {
        // given
        String[] input = {"f", "b", "r", "l"};
        MoveDirection[] expectedResult = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT};

        // when
        List<MoveDirection> result = OptionsParser.parse(input);

        // then
        assertArrayEquals(expectedResult, result.toArray());
    }

    @Test
    void parseIncorrectInput() {
        String[] input = {"fdhbfv", "eeee", "", "uwuwuewue"};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(input));
    }

    @Test
    void parseMixedInput() {
        String[] input = {"f", "fdhbfv", "b", "eeee", "r", "", "l", "uwuwuewue"};
        MoveDirection[] expectedResult = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT};

        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(input));
    }

    @Test
    void parseEmptyInput() {
        // given
        String[] input = {};
        MoveDirection[] expectedResult = {};

        // when
        List<MoveDirection> result = OptionsParser.parse(input);

        // then
        assertArrayEquals(expectedResult, result.toArray());
    }
}
