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

    // Launch Interceptor Condition 0. For further details, see documented requirements.
    public static Boolean LIC0(){

        for(int i = 0; i < NUMPOINTS-1; i++){
            // Euclidean distance
            double distance = Math.sqrt( Math.pow(X[i]-X[i+1], 2) + Math.pow(Y[i]-Y[i+1], 2) );
            if(distance > PARAMETERS.LENGTH1)
                return true;
        }
        return false;
    }
}