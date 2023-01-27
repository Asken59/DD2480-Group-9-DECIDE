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

    public static Boolean LIC3(){ //Decide if the area of a triangle formed by 3 cons. points is greater than AREA1
        double triArea = 0;
        for(int i = 0; i < NUMPOINTS - 2; i++){ //Loop through consecutive points
            triArea = ((X[i+1] - X[i])*(Y[i+2] - Y[i]) - (X[i+3] - X[i])*(Y[i+1] - Y[i]))/2; //Shoelace formula
            triArea = Math.abs(triArea);
            if(PARAMETERS.AREA1 < triArea){
                return true;
            }
        }
        return false;
    }
}