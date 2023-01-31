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

    // All LICs: Test whether each LIC evaluates to false when there are no data points.
    //
    // We set the NUMPOINTS variable to 0 and initialize X and Y to empty arrays.
    //
    // Expected output: false
    @Test
    public void test_all_LICs_no_points_false(){
        Decide.NUMPOINTS = 0;
        Decide.X = new double[]{};
        Decide.Y = new double[]{};
        Assertions.assertFalse(
    Decide.LIC0() || Decide.LIC1() || Decide.LIC2() || Decide.LIC3() || Decide.LIC4()
            || Decide.LIC5() || Decide.LIC6() || Decide.LIC7() || Decide.LIC8() || Decide.LIC9()
            ||  Decide.LIC10() || Decide.LIC11() || Decide.LIC12() || Decide.LIC13() || Decide.LIC14()
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

    @Test
    public void test_LIC0_true() {
        Decide.NUMPOINTS = 2;
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.X = new double[]{1, 3};
        Decide.Y = new double[]{2, 5};
        Assertions.assertTrue(Decide.LIC0());
    }

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

    @Test
    public void test_LIC2_true() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{0, -1, 0, 1, 0};
        Decide.Y = new double[]{0, 1, 0, 1, 0};
        Decide.PARAMETERS.EPSILON = (Math.PI / 2) - 0.1; //PI - EPSILON ~ above 90 degrees
        Assertions.assertTrue(Decide.LIC2());
    }

    @Test
    public void test_LIC2_false() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{0, -1, 0, 1, 0};
        Decide.Y = new double[]{0, 1, 0, 1, 0};
        Decide.PARAMETERS.EPSILON = (Math.PI / 2) + 0.1; //PI - EPSILON ~ below 90 degrees
        Assertions.assertFalse(Decide.LIC2());
    }

    @Test
    public void test_LIC3_true() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{1, 0, 4, 4, 2};
        Decide.Y = new double[]{1, 0, 0, 4, 2};
        Decide.PARAMETERS.AREA1 = 7.9;
        Assertions.assertTrue(Decide.LIC3());
    }

    @Test
    public void test_LIC3_false() {
        Decide.NUMPOINTS = 5;
        Decide.X = new double[]{1, 0, 4, 4, 2};
        Decide.Y = new double[]{1, 0, 0, 4, 2};
        Decide.PARAMETERS.AREA1 = 8.1;
        Assertions.assertFalse(Decide.LIC3());
    }

    @Test
    public void test_LIC4_true() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.QUADS = 1;
        Decide.PARAMETERS.Q_PTS = 3;
        Decide.X = new double[]{0, -1, 1, -1, 0};
        Decide.Y = new double[]{0, -1, -2, 1, 0};
        Assertions.assertTrue(Decide.LIC4());
    }

    @Test
    public void test_LIC4_false() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.QUADS = 1;
        Decide.PARAMETERS.Q_PTS = 2;
        Decide.X = new double[]{0, 0, 0, 0, 0};
        Decide.Y = new double[]{0, 0, 0, 0, 0};
        Assertions.assertFalse(Decide.LIC4());
    }

    @Test
    public void test_LIC5_true() {
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{2, 1};
        Assertions.assertTrue(Decide.LIC5());
    }

    @Test
    public void test_LIC5_false() {
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{1, 2};
        Assertions.assertFalse(Decide.LIC5());
    }

    @Test
    public void test_LIC6_true(){
        Decide.NUMPOINTS = 4;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.X = new double[] {0, 3, 0,1};
        Decide.Y = new double[] {0, -6, 0,1};
        Assertions.assertTrue(Decide.LIC6());
    }

    @Test
    public void test_LIC6_false() {
        Decide.NUMPOINTS = 3;
        Decide.PARAMETERS.N_PTS = 3;
        Decide.X = new double[]{1, 2, 3};
        Decide.Y = new double[]{1, 2, 3};
        Assertions.assertFalse(Decide.LIC6());
    }

    @Test
    public void test_LIC7_true() {
        Decide.X = new double[]{1, 2, 1, 2, 5, 3};
        Decide.Y = new double[]{1, 1, 2, 2, 6, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Assertions.assertTrue(Decide.LIC7());
    }

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

    @Test
    public void test_LIC9_true() {
        // First, third and fifth points form an equilateral triangle. All angles are PI/3 radians (60 degrees).
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.PARAMETERS.EPSILON = Math.PI / 2; // PI/2 radians = 90 degrees
        Decide.X = new double[]{0, 1, 2, 3, 4};
        Decide.Y = new double[]{0, 1, 3, 1, 0};
        Assertions.assertTrue(Decide.LIC9());
    }

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

    @Test
    public void test_LIC9_false_2() {
        Decide.NUMPOINTS = 4;
        Assertions.assertFalse(Decide.LIC9());
    }

    @Test
    public void test_LIC9_false_3() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.C_PTS = 1;
        Decide.PARAMETERS.D_PTS = 1;
        Decide.X = new double[]{0, 1, 0, 2, 3};
        Decide.Y = new double[]{0, 2, 0, 3, 4};
        Assertions.assertFalse(Decide.LIC9());
    }

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

    @Test
    public void test_LIC10_false_2() {
        Decide.NUMPOINTS = 4;
        Assertions.assertFalse(Decide.LIC10());
    }

    @Test
    public void test_LIC10_false_3() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.E_PTS = 2;
        Decide.PARAMETERS.F_PTS = 2;
        Assertions.assertFalse(Decide.LIC10());
    }

    @Test
    public void test_LIC11_true(){
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.G_PTS = 2;
        Decide.X = new double[] {1, 3, 3, 3, 2};
        Decide.Y = new double[] {2, 5, 1, 1, 1};
        Assertions.assertTrue(Decide.LIC11());
    }

    @Test
    public void test_LIC11_false() {
        Decide.NUMPOINTS = 5;
        Decide.PARAMETERS.G_PTS = 2;
        Decide.X = new double[]{1, 3, 3, 2, 4};
        Decide.Y = new double[]{2, 5, 1, 1, 1};
        Assertions.assertFalse(Decide.LIC11());
    }

    @Test
    public void test_LIC12_true(){
        Decide.X = new double[] {1, 2, 1, 2, 5, 3};
        Decide.Y = new double[] {1, 1, 2, 2, 6, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 4;
        Decide.PARAMETERS.LENGTH2 = 10;
        Assertions.assertTrue(Decide.LIC12());
    }

    @Test
    public void test_LIC12_false(){
        Decide.X = new double[] {1, 2, 1, 2, 2, 3};
        Decide.Y = new double[] {1, 1, 2, 2, 2, 2};
        Decide.NUMPOINTS = 6;
        Decide.PARAMETERS.K_PTS = 3;
        Decide.PARAMETERS.LENGTH1 = 1;
        Decide.PARAMETERS.LENGTH2 = 1;
        Assertions.assertFalse(Decide.LIC12());
    }

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

    // Decide 1: Test whether the program prints Yes
    //
    // With the PUV vector including only false, the program should
    // print Yes
    //
    // Expected output: True
    @Test
    public void main_test_1() {
        Decide.PARAMETERS = new Parameters_t(
                10, 6, (Math.PI / 2) - 0.1, 10, 2,
                1, 3, 3, 2, 1, 1, 1, 1,
                1, 1, 1, 3, 3, 4);
        Decide.NUMPOINTS = 7;
        Decide.X = new double[]{-1, 4, 3, 2, 6, 4, 3};
        Decide.Y = new double[]{4, 2, 1, 6, 3, 2, 8};
        Decide.LCM = new Decide.Connectors[][]{
                {Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR},
                {Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR},
                {Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR},
                {Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD},
                {Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ANDD},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR},
                {Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ANDD},
                {Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED},
                {Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.ORR, Decide.Connectors.ANDD, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.ANDD, Decide.Connectors.ANDD}
        };
        Decide.PUV = new Boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        Decide.DECIDE();
        Assertions.assertTrue(Decide.LAUNCH);
    }

    // Decide 2: Test whether the program prints Yes
    //
    // LCM[0][0] set to ANDD and all other elements NOTUSED.
    // Knowing LIC0 will return True with the given parameters,
    // the program should then print Yes
    //
    // Expected output: True
    @Test
    public void main_test_2() {
        Decide.PARAMETERS = new Parameters_t(
                1, 6, (Math.PI / 2) - 0.1, 10, 2,
                1, 3, 3, 2, 1, 1, 1, 1,
                1, 1, 1, 3, 3, 4);
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{1, 3};
        Decide.Y = new double[]{2, 5};
        Decide.LCM = new Decide.Connectors[][]{
                {Decide.Connectors.ANDD, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
        };
        Decide.PUV = new Boolean[]{true, false, false, true, true, false, true, false, false, false, true, true, false, true, false};
        Decide.DECIDE();
        Assertions.assertTrue(Decide.LAUNCH);
    }

    // Decide 3: Test whether the program prints No
    //
    // LCM[0][1] set to ORR and all other elements to NOTUSED
    // Knowing LIC0 will return false with the given parameters,
    // the program should then print No
    //
    // Expected output: False
    @Test
    public void main_test_3() {
        Decide.PARAMETERS = new Parameters_t(
                10, 6, (Math.PI / 2) - 0.1, 10, 2,
                1, 3, 3, 2, 1, 1, 1, 1,
                1, 1, 1, 3, 3, 4);
        Decide.NUMPOINTS = 2;
        Decide.X = new double[]{1, 3};
        Decide.Y = new double[]{2, 5};
        Decide.LCM = new Decide.Connectors[][]{
                {Decide.Connectors.NOTUSED, Decide.Connectors.ORR, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
                {Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED, Decide.Connectors.NOTUSED},
        };
        Decide.PUV = new Boolean[]{true, false, false, true, true, false, true, false, false, false, true, true, false, true, false};
        Decide.DECIDE();
        Assertions.assertFalse(Decide.LAUNCH);
    }
}

    
