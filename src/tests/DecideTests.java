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
    public void test_LIC1_true(){
        Decide.X = new double[] {1, 5, 1, 2, 3, 5};
        Decide.Y = new double[] {1, 1, 5, 3, 1, 4};
        Decide.PARAMETERS.RADIUS1 = 2;
        Decide.NUMPOINTS = 6;
        Assertions.assertTrue(Decide.LIC1());
    }

    @Test
    public void test_LIC1_false(){
        Decide.X = new double[] {1, 2, 1, 2, 1, 3};
        Decide.Y = new double[] {1, 1, 2, 2, 3, 2};
        Decide.PARAMETERS.RADIUS1 = 3;
        Decide.NUMPOINTS = 6;
        Assertions.assertFalse(Decide.LIC1());
    }


    @Test
    public void test_LIC7_true(){
        Decide.X = new double[] {1, 2, 1, 2, 5, 3};
        Decide.Y = new double[] {1, 1, 2, 2, 6, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Assertions.assertTrue(Decide.LIC7());
    }

    @Test
    public void test_LIC7_false(){
        Decide.X = new double[] {1, 2, 1, 2, 2, 3};
        Decide.Y = new double[] {1, 1, 2, 2, 2, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Assertions.assertFalse(Decide.LIC7());
    }


}

    
