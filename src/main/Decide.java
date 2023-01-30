package main;

import java.util.Arrays;

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
    // There exists at least one set of Q PTS consecutive data points that lie in more than QUADS
    // quadrants. Where there is ambiguity as to which quadrant contains a given point, priority
    // of decision will be by quadrant number, i.e., I, II, III, IV. For example, the data point (0,0)
    // is in quadrant I, the point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the point
    // (0,1) is in quadrant I and the point (1,0) is in quadrant I.
    public static Boolean LIC4(){

        // return false if PARAMETER input values are invalid
        if(PARAMETERS.Q_PTS < 2 || PARAMETERS.Q_PTS > NUMPOINTS)return false;
        if(PARAMETERS.QUADS < 1 || PARAMETERS.QUADS > 3)return false;

        // iterate up to the number of points - Q_PTS -1
        for(int i = 0; i < NUMPOINTS-PARAMETERS.Q_PTS-1; i++) {

            int[] unique = new int[PARAMETERS.Q_PTS];
            for (int j = 0; j < PARAMETERS.Q_PTS; j++) {

                // check if point is in I
                if (X[i + j] >= 0 && Y[i + j] >= 0) {
                    unique[j] = 1;
                }
                // check if point is in II
                else if (X[i + j] < 0 && Y[i + j] >= 0) {
                    unique[j] = 2;
                }
                // check if point is in III
                else if (X[i + j] <= 0 && Y[i + j] < 0) {
                    unique[j] = 3;
                }
                // check if point is in IV
                else{
                    unique[j] = 4;

                }
            }
            // sort unique array and count distinct elements
            // return true if the number of distinct elements is greater than QUAD quadrants
            int uniqueQuadrants = 0;
            Arrays.sort(unique);
            for (int x = 1; x < unique.length; x++) {
                if (unique[x - 1] != unique[x]) uniqueQuadrants++;
            }
            if (uniqueQuadrants > PARAMETERS.QUADS) return true;
        }
        return false;
    }

}