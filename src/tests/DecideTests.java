package tests;

import main.Decide;
import main.Parameters_t;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DecideTests {

    @BeforeEach
    public void initParams(){
        Decide.PARAMETERS = new Parameters_t(
                0,0,0,0,0,0,
                0,0,0,0,0,0,
                0,0,0,0,0,0,0
        );
    }

    @Test
    public void testLIC0(){
        Assertions.assertTrue(true);
    }

    @Test
    public void test_LIC3_true(){
        Decide.NUMPOINTS = 5;
        Decide.X = new double[] {1, 0, 4, 4, 2};
        Decide.Y = new double[] {1, 0, 0, 4, 2};
        Decide.PARAMETERS.AREA1 = 7.9;
        Assertions.assertTrue(Decide.LIC3());
    }

    @Test
    public void test_LIC3_false(){
        Decide.NUMPOINTS = 5;
        Decide.X = new double[] {1, 0, 4, 4, 2};
        Decide.Y = new double[] {1, 0, 0, 4, 2};
        Decide.PARAMETERS.AREA1 = 8.1;
        Assertions.assertFalse(Decide.LIC3());
    }
}
