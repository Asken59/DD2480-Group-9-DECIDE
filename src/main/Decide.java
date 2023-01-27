package main;

public class Decide {
    enum Connectors{NOTUSED, ORR, ANDD};

    //All "global" variables
    static Parameters_t PARAMETERS;

    static double[] X;

    static double[] Y;

    static int NUMPOINTS;

    static Connectors[][] LCM;

    static Boolean[][] PUM;

    static Boolean[] PUV;

    static Boolean[] CMV;

    static Boolean[] FUV;

    static Boolean LAUNCH;

    public static void DECIDE(){
        //TODO: Evaluate LICS and generate the CMV
        //TODO: Generate the PUM using the CMV and LCM
        //TODO: Generate the FUV using the PUM and PUV
        //TODO: Evaluate LAUNCH and print
    }

    public static Boolean LIC0(){
        return false;
    }
}