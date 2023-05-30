import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TesteCoverage extends MetrikObserver{
    CoverageCalc cc;
    int[][] ergebnis;


    public TesteCoverage() {
        observers.add(this);
        cc = new CoverageCalc();
    }

@Test
    public void test(){
        update(this, Metrik.MCDC, new int[][]{{0,0,0,1},{0,0,1,0},{0,1,0,0},{0,1,1,1},{1,0,0,0},{1,0,1,1},{1,1,0,1},{1,1,1,1}});
    }

    @Override
    public void update(MetrikObserver vonWem, Metrik metrik, int[][] aufgabe) {
        if(metrik == Metrik.MCDC && vonWem != this)
        {

            ergebnis = aufgabe;
        }

    }

    @Test
    public void testErfolgreich()
    {
        System.out.println(ergebnis == null);
        assertArrayEquals(ergebnis, ergebnis);
    }
}
