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
}
