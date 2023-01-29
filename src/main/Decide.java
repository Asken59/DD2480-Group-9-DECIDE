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

    // Launch Interceptor Condition 10. For further details, see documented requirements.
    public static Boolean LIC10(){

        // The condition is not met if:
        if( (NUMPOINTS < 5) || (PARAMETERS.E_PTS < 1) || (PARAMETERS.F_PTS < 1) ||
            (PARAMETERS.E_PTS+PARAMETERS.F_PTS <= NUMPOINTS-3) )
            return false;

        // First point
        for(int i = 0; i < (NUMPOINTS-2-PARAMETERS.E_PTS-PARAMETERS.F_PTS); i++){

            // Second point, preceded by E_PTS consecutive intervening points
            int j = i + PARAMETERS.E_PTS + 1;

            // Third point, preceded by F_PTS consecutive intervening points
            int k = j + PARAMETERS.F_PTS + 1;

            // Compute the sides of the triangle formed by the three points
            double ij = Math.sqrt( Math.pow(X[i]-X[j], 2) + Math.pow(Y[i]-Y[j], 2) );
            double ik = Math.sqrt( Math.pow(X[i]-X[k], 2) + Math.pow(Y[i]-Y[k], 2) );
            double jk = Math.sqrt( Math.pow(X[j]-X[k], 2) + Math.pow(Y[j]-Y[k], 2) );

            // Compute the area of the triangle using Heron's formula
            double s = 0.5 * (ij + ik + jk);
            double area = Math.sqrt( s * (s-ij) * (s-ik) * (s-jk) );

            // Evaluate
            if(area > PARAMETERS.AREA1) return true;
        }
        return false;
    }
}