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
    // There exists at least one set of N PTS consecutive data points such that at least one of the
    // points lies a distance greater than DIST from the line joining the first and last of these N PTS
    // points. If the first and last points of these N PTS are identical, then the calculated distance
    // to compare with DIST will be the distance from the coincident point to all other points of
    // the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
    public static Boolean LIC6(){

        if(NUMPOINTS < 3) return false;
        if(NUMPOINTS > PARAMETERS.N_PTS) return false;

        for(int i = 0; i <= NUMPOINTS-PARAMETERS.N_PTS; i++){

            for(int j = i + 1 ; j < i + PARAMETERS.N_PTS; j ++){

                //distance of first and last point
                double dist = Math.sqrt((Math.pow(X[i + PARAMETERS.N_PTS-1],2)-Math.pow(X[i],2)) + (Math.pow(Y[i + PARAMETERS.N_PTS-1],2)-Math.pow(Y[i],2)));
                // special case if first and last N_PTS have identical coordinates
                if(dist == 0){
                    // do special distance by calculating X[j]Y[j] distance from the first of these points
                    double specialDistance = Math.sqrt((Math.pow(X[j],2)-Math.pow(X[i],2)) + (Math.pow(Y[j],2)-Math.pow(Y[i],2)));

                    if(specialDistance > 0) return true;
                }
                // standard distance from point X[j]Y[j] to the line
                else{

                    double distanceFromDist = Math.abs(((X[i+PARAMETERS.N_PTS-1]-X[i])*(Y[i]-Y[j])) - (X[i]-X[j])*(Y[i+PARAMETERS.N_PTS-1]-Y[i]))/dist;

                    if(distanceFromDist > dist)return true;
                }

            }
        }
        return false;
    }
}
