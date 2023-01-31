package tests;

import main.Decide;
import main.Parameters_t;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DecideTests {

    @BeforeEach
    public void initParams() {
        Decide.PARAMETERS = new Parameters_t(
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0
        );
    }

    @Test
    public void test_generatePUM() {
        Decide.CMV = new Boolean[]{true, true, false, true, true, true, true, true, true, true, true, true, true, true, true};
        Decide.LCM = new Decide.Connectors[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Decide.LCM[i][j] = Decide.Connectors.NOTUSED;
            }
        }
        Decide.LCM[0][2] = Decide.Connectors.ORR;
        Decide.LCM[1][2] = Decide.Connectors.ANDD;
        Decide.generatePUM();
        Assertions.assertTrue(Decide.PUM[0][2]);
        Assertions.assertTrue(Decide.PUM[11][3]);
        Assertions.assertFalse(Decide.PUM[1][2]);
    }

    // LIC0: Test whether the condition evaluates to true when there exists
    // at least one  set of two consecutive data points that are a distance
    // greater than LENGTH1 apart.
    //
    // We add two points with a distance of approx. 2.24 length units apart,
    // and set the parameter LENGTH1 to 1. Thus, the condition should evaluate to true.
    //
    // Expected output: true
    @Test
    public void test_LIC0_true() {
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.X = new double[]{1, 3};
        Decide.Y = new double[]{2, 5};
        Assertions.assertTrue(Decide.LIC0());
    }

    // LIC0: Test whether the condition evaluates to false when there exists
    // NO set of two consecutive data points that are a distance
    // greater than LENGTH1 apart.
    //
    // We add two points with a distance of approx. 2.24 length units apart,
    // and set the parameter LENGTH1 to 10. Thus, the condition should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC0_false() {
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.LENGTH1 = 10;
        Decide.X = new double[]{1, 3};
        Decide.Y = new double[]{2, 5};
        Assertions.assertFalse(Decide.LIC0());
    }

    @Test
    public void test_LIC1_true() {
        Decide.X = new double[]{1, 5, 1, 2, 3, 5};
        Decide.Y = new double[]{1, 1, 5, 3, 1, 4};
        Decide.PARAMETERS.RADIUS1 = 2;
        Decide.NUMPOINTS = 6;
        Assertions.assertTrue(Decide.LIC1());
    }

    @Test
    public void test_LIC1_false() {
        Decide.X = new double[]{1, 2, 1, 2, 1, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 3, 2};
        Decide.PARAMETERS.RADIUS1 = 3;
        Decide.NUMPOINTS = 6;
        Assertions.assertFalse(Decide.LIC1());
    }

    // LIC2 Positive test
    // The parameters NUMPOINTS, X, and Y are set up to form an angle of PI/2 radians at (-1,1) (0,0) (1,1)
    // EPSILON is set to slightly less than (PI/2) radians
    // This should result in the angle < PI - EPSILON
    // Expected method from method call: true
    @Test
    public void test_LIC2_true() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{0, -1, 0, 1, 0};
        Decide.Y = new double[]{0, 1, 0, 1, 0};
        Decide.PARAMETERS.EPSILON = (Math.PI / 2) - 0.1; //PI - EPSILON ~ above 90 degrees
        Assertions.assertTrue(Decide.LIC2());
    }

    // LIC2 Negative test
    // The parameters NUMPOINTS, X, and Y are set up to form an angle of PI/2 radians at (-1,1) (0,0) (1,1)
    // EPSILON is set to slightly more than (PI/2) radians
    // This should result in the angle > PI - EPSILON
    // Expected method from method call: false
    @Test
    public void test_LIC2_false() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{0, -1, 0, 1, 0};
        Decide.Y = new double[]{0, 1, 0, 1, 0};
        Decide.PARAMETERS.EPSILON = (Math.PI / 2) + 0.1; //PI - EPSILON ~ below 90 degrees
        Assertions.assertFalse(Decide.LIC2());
    }

    // LIC3 Positive test
    // The parameters NUMPOINTS, X, and Y are set up to form a triangle at (0,0) (4,0) (4,4) with area 8
    // AREA1 is set to slightly less than 8
    // This should result in the area > AREA1
    // Expected method from method call: true
    @Test
    public void test_LIC3_true() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{1, 0, 4, 4, 2};
        Decide.Y = new double[]{1, 0, 0, 4, 2};
        Decide.PARAMETERS.AREA1 = 7.9;
        Assertions.assertTrue(Decide.LIC3());
    }

    // LIC3 Negative test
    // The parameters NUMPOINTS, X, and Y are set up to form a triangle at (0,0) (4,0) (4,4) with area 8
    // AREA1 is set to slightly more than 8
    // This should result in the area > AREA1
    // Expected method from method call: false
    @Test
    public void test_LIC3_false() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{1, 0, 4, 4, 2};
        Decide.Y = new double[]{1, 0, 0, 4, 2};
        Decide.PARAMETERS.AREA1 = 8.1;
        Assertions.assertFalse(Decide.LIC3());
    }

    /*
       LIC4 positive test
       The parameters are NUMPOINTS, QUADS, Q_PTS, X, and Y
       NUMPOINTS are the number of points tested. QUADS are the number of QUAD quadrant that need to be less than
       the number of consecutive points in distinct quadrants. The X, and Y represent coordinates for each of the NUMPOINTS points.
       The test contains four points in five different quadrants, and the number of QUADS is 1, the Q_PTS is set to 3.
       Expected result true
    */
    @Test
    public void test_LIC4_true() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.QUADS = 1;
        Decide.PARAMETERS.Q_PTS = 3;
        Decide.X = new double[]{0, -1, 1, -1, 0};
        Decide.Y = new double[]{0, -1, -2, 1, 0};
        Assertions.assertTrue(Decide.LIC4());
    }

    /*
       LIC4 negative test
       The parameters are NUMPOINTS, QUADS, Q_PTS, X, and Y
       NUMPOINTS are the number of points tested. QUADS are the number of QUAD quadrant that need to be less than
       the number of consecutive points in distinct quadrants. The X, and Y represent coordinates for each of the NUMPOINTS points
       The test contains five identical points, and the number of QUADS is 1, the Q_PTS is set to 1.
       Expected result false
     */
    @Test
    public void test_LIC4_false() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.QUADS = 1;
        Decide.PARAMETERS.Q_PTS = 2;
        Decide.X = new double[]{0, 0, 0, 0, 0};
        Decide.Y = new double[]{0, 0, 0, 0, 0};
        Assertions.assertFalse(Decide.LIC4());
    }

    /*
       LIC5 positive test
       The Parameters are NUMPOINTS, and X
       NUMPOINTS are the number of points tested, and X represent the X coordinates for each of the NUMPOINTS points.
       The test contains two different points where the first is greater than the second.
       Expected result true
     */
    @Test
    public void test_LIC5_true() {
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{2, 1};
        Assertions.assertTrue(Decide.LIC5());
    }

    /*
        LIC5 negative test
        The Parameters are NUMPOINTS, and X
        NUMPOINTS are the number of points tested, and X represent the X coordinates for each of the NUMPOINTS points.
        The test contains two different points where the second is greater than the first.
        Expected result false
     */
    @Test
    public void test_LIC5_false() {
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{1, 2};
        Assertions.assertFalse(Decide.LIC5());
    }
    /*
        LIC6 positive test
        The Parameters are NUMPOINTS, N_PTS, X, and Y
        NUMPOINTS are the number of points tested, N_PTS are the number of consecutive poitns tested and X represent the X coordinates for each of the NUMPOINTS points.
        The test contains four different points where there are at least N_PTS consecutive points where the line distance of the first
        and last of these is lesser than the distance of at least one of the points to this between these two points
        Expected result true
     */
    @Test
    public void test_LIC6_true() {
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.X = new double[]{0, 3, 0, 1};
        Decide.Y = new double[]{0, -6, 0, 1};
        Assertions.assertTrue(Decide.LIC6());
    }
    /*
        LIC6 negative test
        The Parameters are NUMPOINTS, N_PTS, X, and Y
        NUMPOINTS are the number of points tested, N_PTS are the number of consecutive poitns tested and X represent the X coordinates for each of the NUMPOINTS points.
        The test contains three different points where there are at least N_PTS consecutive points where the line distance of the first
        and last of these is not lesser than the distance of at least one of the points to this between these two points.
        Furthermore the NUMPOINTS is not greater than the N_PTS
        Expected result false
     */
    @Test
    public void test_LIC6_false() {
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.X = new double[]{1, 2, 3};
        Decide.Y = new double[]{1, 2, 3};
        Assertions.assertFalse(Decide.LIC6());
    }

    // LIC 7 positive test
    // Sets two points to values that result in the distance between a set of them separated by
    // exact K_PTS consecutive points being greater than LENGTH1.
    // According to the requirements specification this should make the condition evaluate to false.
    // Excepted result from method call: false
    @Test
    public void test_LIC7_true() {
        Decide.X = new double[]{1, 2, 1, 2, 5, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 6, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Assertions.assertTrue(Decide.LIC7());
    }

    // LIC 7 negative test
    // Sets all points to values that result in no distance between a set of them separated by
    // exact K_PTS consecutive points being greater than LENGTH1.
    // According to the requirements specification this should make the condition evaluate to false.
    // Excepted result from method call: False
    @Test
    public void test_LIC7_false() {
        Decide.X = new double[]{1, 2, 1, 2, 2, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 2, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Assertions.assertFalse(Decide.LIC7());
    }

    @Test
    public void test_LIC8_true() {
        Decide.X = new double[]{6, 3, 2, -1, 1};
        Decide.Y = new double[]{2, 4, 8, 7, 2};
        Decide.PARAMETERS.RADIUS1 = 3;
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Assertions.assertTrue(Decide.LIC8());
    }

    @Test
    public void test_LIC8_false() {
        Decide.X = new double[]{6, 3, 2, -1, 1};
        Decide.Y = new double[]{2, 4, 2.5, 7, 2};
        Decide.PARAMETERS.RADIUS1 = 3;
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Assertions.assertFalse(Decide.LIC8());
    }

    // LIC9: Test whether the condition evaluates to true when there exists at least
    // one set of three points separated by exactly C_PTS and D_PTS consecutive
    // intervening points, respectively, and forms an angle lesser than (PI - EPSILON)
    // OR greater than (PI + EPSILON).
    //
    // We add five points, and set both parameters C_PTS and D_PTS to 1.
    // First, third and fifth points form an equilateral triangle.
    // All angles are therefore PI/3 radians (60 degrees) and we set EPSILON to PI/2 (90 degrees)
    // so that the condition should evaluate to true.
    //
    // Expected output: true
    @Test
    public void test_LIC9_true() {
        //
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.PARAMETERS.EPSILON = Math.PI / 2; // PI/2 radians = 90 degrees
        Decide.X = new double[]{0, 1, 2, 3, 4};
        Decide.Y = new double[]{0, 1, 3, 1, 0};
        Assertions.assertTrue(Decide.LIC9());
    }

    // LIC9: Test whether the condition evaluates to false when there exists NO
    // set of three points separated by... (see description above for LIC9 positive test)
    //
    // We add five points, and set both parameters C_PTS and D_PTS to 1.
    // First, third and fifth points form an equilateral triangle.
    // All angles are therefore PI/3 radians (60 degrees) and we set EPSILON to 5PI/6 (150 degrees)
    // so that the condition should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC9_false() {
        // First, third and fifth points form an equilateral triangle. All angles are PI/3 radians (60 degrees).
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.PARAMETERS.EPSILON = (5 * Math.PI) / 6; // 5PI/6 radians = 150 degrees
        Decide.X = new double[]{0, 1, 2, 3, 4};
        Decide.Y = new double[]{0, 1, 3, 1, 0};
        Assertions.assertFalse(Decide.LIC9());
    }

    // LIC9: Test whether the condition evaluates to false when passed invalid input.
    //
    // The condition cannot be fulfilled if NUMPOINTS is less than 5. Thus,
    // we set the parameter NUMPOINTS to 4 so that the condition should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC9_false_2() {
        Decide.NUMPOINTS = 4;
        Assertions.assertFalse(Decide.LIC9());
    }

    // LIC9: Test whether the condition evaluates to false when passed invalid input.
    //
    // The condition cannot be fulfilled if any of the two other points of the triangle
    // (see comment for LIC9 positive test) coincide with the vertex point.
    // Thus, we set the first point to coincide with the vertex point so that the condition
    // should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC9_false_3() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.X = new double[]{0, 1, 0, 2, 3};
        Decide.Y = new double[]{0, 2, 0, 3, 4};
        Assertions.assertFalse(Decide.LIC9());
    }

    // LIC10: There exists at least one set of three data points separated by
    // exactly E_PTS and F_PTS consecutive intervening points, respectively,
    // that are the vertices of a triangle with area greater than AREA1.
    //
    // We form an equilateral triangle with an area of 6 area units, and we set
    // the parameter AREA1 to 4, so that the condition should evaluate to true.
    //
    // Expected output: true
    @Test
    public void test_LIC10_true() {
        // Equilateral triangle with an area of 6 area units
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 4;
        Decide.X = new double[]{0, 1, 2, 3, 4};
        Decide.Y = new double[]{0, 1, 3, 1, 0};
        Assertions.assertTrue(Decide.LIC10());
    }

    // LIC10: There exists NO set of three data points separated
    // by... (see comment above for LIC10 positive test case)
    //
    // We form an equilateral triangle with an area of 6 area units, and we set
    // the parameter AREA1 to 10, so that the condition should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC10_false_1() {
        // Equilateral triangle with an area of 6 area units
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.PARAMETERS.AREA1 = 10;
        Decide.X = new double[]{0, 1, 2, 3, 4};
        Decide.Y = new double[]{0, 1, 3, 1, 0};
        Assertions.assertFalse(Decide.LIC10());
    }

    // LIC10: Test whether the condition evaluates to false when passed invalid input.
    //
    // The condition cannot be met if the number of points are less than 5. Thus,
    // we set the parameter NUMPOINTS to 4 so that the condition should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC10_false_2() {
        Decide.NUMPOINTS = 4;
        Assertions.assertFalse(Decide.LIC10());
    }

    // LIC10: Test whether the condition evaluates to false when passed invalid input.
    //
    // The condition cannot be met if the number of consecutive intervening points (E_PTS+F_PTS)
    // spare less than 3 points for the triangle (E_PTS+F_PTS > NUMPOINTS-3).
    // We set the parameter NUMPOINTS to 5 (valid) and both E_PTS and F_PTS to 4 so that the
    // condition should evaluate to false.
    //
    // Expected output: false
    @Test
    public void test_LIC10_false_3() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 2;
        Decide.PARAMETERS.F_PTS = 2;
        Assertions.assertFalse(Decide.LIC10());
    }

    // LIC11 Positive test
    // The parameters NUMPOINTS, X, and Y are set up so that X[1] > X[3]
    // G_PTS is set to 2 so that X[1] and X[3] is the correct distance
    // This should result in the requirement being fulfilled
    // Expected method from method call: true
    @Test
    public void test_LIC11_true() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.G_PTS = 2;
        Decide.X = new double[]{1, 3, 3, 3, 2};
        Decide.Y = new double[]{2, 5, 1, 1, 1};
        Assertions.assertTrue(Decide.LIC11());
    }

    // LIC11 Negative test
    // The parameters NUMPOINTS, X, and Y are set up so that X[1] > X[2]
    // G_PTS is set to 2 so that X[1] and X[2] is not the correct distance
    // This should result in the requirement not being fulfilled
    // Expected method from method call: false
    @Test
    public void test_LIC11_false() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.G_PTS = 2;
        Decide.X = new double[]{1, 3, 3, 2, 4};
        Decide.Y = new double[]{2, 5, 1, 1, 1};
        Assertions.assertFalse(Decide.LIC11());
    }

    // LIC 12 positive test
    // Sets two points to values that result in the distance between a set of them separated by
    // exact K_PTS consecutive points being greater than LENGTH1 and less than LENGTH2.
    // According to the requirements specification this should make the condition evaluate to true.
    // Excepted result from method call: true
    @Test
    public void test_LIC12_true() {
        Decide.X = new double[]{1, 2, 1, 2, 5, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 6, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Decide.PARAMETERS.LENGTH2 = 10;
        Assertions.assertTrue(Decide.LIC12());
    }

    // LIC 12 negative test
    // Sets all points to values that result in the distance between a set of them separated by
    // exact K_PTS consecutive points being greater than LENGTH1 but not less than LENGTH2.
    // According to the requirements specification this should make the condition evaluate to false.
    // Excepted result from method call: false
    @Test
    public void test_LIC12_false() {
        Decide.X = new double[]{1, 2, 1, 2, 2, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 2, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.PARAMETERS.LENGTH2 = 1;
        Assertions.assertFalse(Decide.LIC12());
    }

    // LIC 13 positive test
    // Sets three points to values separated by exact A_PTS and B_PTS that result in them not being
    // contained in a circle of radius RADIUS1 while also being contained in a circle of radius RADIUS2.
    // According to the requirements specification this should make the condition evaluate to true.
    // Excepted result from method call: true;
    @Test
    public void test_LIC13_true() {
        Decide.X = new double[]{1, 5, 1, 2, 3, 5};
        Decide.Y = new double[]{1, 1, 5, 3, 1, 4};
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.PARAMETERS.RADIUS1 = 2;
        Decide.PARAMETERS.RADIUS2 = 8;
        Decide.NUMPOINTS = 6;
        Assertions.assertTrue(Decide.LIC13());
    }

    // LIC 13 false test
    // Sets three points to values separated by exact A_PTS and B_PTS that result in them not being
    // contained in a circle of radius RADIUS1 while also not being contained in a circle of radius RADIUS2.
    // According to the requirements specification this should make the condition evaluate to false.
    // Excepted result from method call: false;
    @Test
    public void test_LIC13_false() {
        Decide.X = new double[]{1, 2, 1, 2, 1, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 3, 2};
        Decide.PARAMETERS.A_PTS = 1;
        Decide.PARAMETERS.B_PTS = 1;
        Decide.PARAMETERS.RADIUS1 = 8;
        Decide.PARAMETERS.RADIUS2 = 1;
        Decide.NUMPOINTS = 6;
        Assertions.assertFalse(Decide.LIC13());
    }

    @Test
    public void test_LIC14_true() {
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.X = new double[]{-1, 0, -2, -2, 1, 1};
        Decide.Y = new double[]{4, -4, 2, 2, 4, 4};
        Decide.PARAMETERS.AREA1 = 7;
        Decide.PARAMETERS.AREA2 = 4;
        Assertions.assertTrue(Decide.LIC14());
    }

    @Test
    public void test_LIC14_false_1() {
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.E_PTS = 1;
        Decide.PARAMETERS.F_PTS = 1;
        Decide.X = new double[]{-1, 0, -2, -2, 1, 1};
        Decide.Y = new double[]{4, 1, 2, 2, 4, 4};
        Decide.PARAMETERS.AREA1 = 4;
        Decide.PARAMETERS.AREA2 = 4;
        Assertions.assertFalse(Decide.LIC14());
    }

    @Test
    public void test_LIC14_false_2() {
        Decide.NUMPOINTS = 4;
        Assertions.assertFalse(Decide.LIC14());
    }
}

    
