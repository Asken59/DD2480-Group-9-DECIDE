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
    @Test
    public void test_LIC4_true(){
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.QUADS = 1;
        Decide.PARAMETERS.Q_PTS = 3;
        Decide.X = new double[] {0,-1,1,-1,0};
        Decide.Y = new double[] {0,-1,-2,1,0};
        Assertions.assertTrue(Decide.LIC4());
    }

    @Test
    public void test_LIC4_false(){
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.QUADS = 1;
        Decide.PARAMETERS.Q_PTS = 2;
        Decide.X = new double[] {0,0,0,0,0};
        Decide.Y = new double[] {0,0,0,0,0};
        Assertions.assertFalse(Decide.LIC4());
    }
}
