package tests;

import main.Decide;
import main.Parameters_t;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Decide;

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
        Decide.NUMPOINTS = 2;

        // Expected output: true
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.X = new double[] {1, 3};
        Decide.Y = new double[] {2, 5};
        Assertions.assertTrue(Decide.LIC0());

        // Expected output: false
        Decide.PARAMETERS.LENGTH1 = 10;
        Assertions.assertFalse(Decide.LIC0());
    }
}
