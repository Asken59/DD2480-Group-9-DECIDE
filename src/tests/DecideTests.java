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
    public void test_LIC0_true(){
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.X = new double[] {1, 3};
        Decide.Y = new double[] {2, 5};
        Assertions.assertTrue(Decide.LIC0());
    }

    @Test
    public void test_LIC0_false(){
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.LENGTH1 = 10;
        Decide.X = new double[] {1, 3};
        Decide.Y = new double[] {2, 5};
        Assertions.assertFalse(Decide.LIC0());
    }
}
