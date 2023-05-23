import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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


    @Test
    public void testReadMarkdownFile() {
        try {
            int[][] result = IOWriter.readMarkdownFile("exercise1.md");
            int[][] expected = {
                    {0, 0, 0, 1},
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {1, 1, 0, 0},
                    {0, 0, 1, 1},
                    {1, 0, 1, 0},
                    {0, 1, 1, 1},
                    {1, 1, 1, 0}
            };

            assertArrayEquals(expected, result);
        } catch (IOException e) {
            fail("File not found.");
        }
    }

    @Test
    public void testCountOccurrences() {
        int result = IOWriter.countOccurrences("hello world", 'l');
        assertEquals(3, result);
    }

    @Test
    public void testWrite() {
        // This test will be difficult to implement because the write() function does not return anything
        // and it has side effects. You would have to read the file it outputs and compare it to an expected output file.
    }

    @Test
    public void testReadMarkdownTable() {
        try {
            String[][] result = IOWriter.readMarkdownTable("exercise2.md");
            String[][] expected =   {{"A0", "A1", "A2", "B"},
            {"--", "--", "--", "--"},
            {"0", "0", "0", "0"},
            {"1", "0", "0", "0"},
            {"0", "1", "0", "0"},
            {"1", "1", "0", "0"},
            {"0", "0", "1", "0"},
            {"1", "0", "1", "1"},
            {"0", "1", "1", "1"},
            {"1", "1", "1", "0"}};  // Set your expected result
            assertArrayEquals(expected, result);
        } catch (Exception e) {
            fail("Error reading the file.");
        }
    }
}

