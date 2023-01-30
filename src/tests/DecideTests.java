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
    public void test_LIC2_true(){
        Decide.NUMPOINTS = 5;
        Decide.X = new double[] {0, -1, 0, 1, 0};
        Decide.Y = new double[] {0, 1, 0, 1, 0};
        Decide.PARAMETERS.EPSILON = (Math.PI/2) - 0.1; //PI - EPSILON ~ above 90 degrees
        Assertions.assertTrue(Decide.LIC2());
    }

    @Test
    public void test_LIC2_false(){
        Decide.NUMPOINTS = 5;
        Decide.X = new double[] {0, -1, 0, 1, 0};
        Decide.Y = new double[] {0, 1, 0, 1, 0};
        Decide.PARAMETERS.EPSILON = (Math.PI/2) + 0.1; //PI - EPSILON ~ below 90 degrees
        Assertions.assertFalse(Decide.LIC2());
    }
}
