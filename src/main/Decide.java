package main;

public class Decide {
    public enum Connectors{NOTUSED, ORR, ANDD};

    //All "global" variables
    public static Parameters_t PARAMETERS;

    public static double[] X;

    public static double[] Y;

    public static int NUMPOINTS;

    public static Connectors[][] LCM;

    public static Boolean[][] PUM;

    public static Boolean[] PUV;

    public static Boolean[] CMV;

    public static Boolean[] FUV;

    public static Boolean LAUNCH;

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