import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private ErmittleSigVar esv;

    @BeforeEach
    void setUp() {
        esv = new ErmittleSigVar();
    }

    @Test
    void testErmittleSignifikanteVariablen() {
        int[][] aufgabe = {
                {0, 0, 0, 1},
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 1},
                {1, 0, 0, 0},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 1}
        };
        int[][] expected = {
                //1 1 1
                //2 2 1
                //3 1 2
                //0 2 2
                //1 3 3
                //2 0 3
                //3 3 0
                //0 0 0
                // Insert the expected results here

                {1, 1, 1},
                {2, 2, 1},
                {3, 1, 2},
                {0, 2, 2},
                {1, 3, 3},
                {2, 0, 3},
                {3, 3, 0}, {0, 0, 0}
        };
        assertArrayEquals(expected, esv.ermittleSignifikanteVariablen(aufgabe));
    }

    @Test
    void testFindMatch() {
        int[][] table = {
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0},
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 1}
        };
        int[] target = {0, 0, 0};
        assertEquals(0, esv.findMatch(table, target));

        target = new int[]{1, 1, 1};
        assertEquals(7, esv.findMatch(table, target));

        target = new int[]{2, 2, 2};
        assertEquals(-1, esv.findMatch(table, target));
    }

    @Test
    void testArrayErweitern() {
        CoverageCalc coverageCalc = new CoverageCalc();
        int[][] array = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        int[][] expectedArray = {{0, 0, 0}, {1, 0, 0}, {0, 1, 0}, {1, 1, 0}};

        array = CoverageCalc.ArrayErweitern(array);

        assertArrayEquals(expectedArray, array);
    }
}

