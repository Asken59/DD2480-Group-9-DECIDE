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

    // Launch Interceptor Condition 1.
    public static Boolean LIC1() {

        for (int i = 0; i < (NUMPOINTS - 2); i++) {
            double x1 = X[i];
            double y1 = Y[i];
            double x2 = X[i + 1];
            double y2 = Y[i + 1];
            double x3 = X[i + 2];
            double y3 = Y[i + 2];

            // Calculates distances between data points
            double a = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
            double b = Math.sqrt(Math.pow((x1 - x3), 2) + Math.pow((y1 - y3), 2));
            double c = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));

            // Takes the largest distance
            double max_distance = Math.max(Math.max(a, b), c);

            // If the largest distance is twice the radius there is no chance to fit all points inside circle
            if ((max_distance / 2) > PARAMETERS.RADIUS1) {
                return true;
            }

            // Check if any of the angles are obtuse
            boolean obtuse = false;
            if (max_distance == a) {
                if (Math.toDegrees(Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b))) > 90) {
                    obtuse = true;
                }
            } else if (max_distance == b) {
                if (Math.toDegrees(Math.acos((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2 * a * c))) > 90) {
                    obtuse = true;
                }
            } else if (max_distance == c) {
                if (Math.toDegrees(Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2 * b * c))) > 90) {
                    obtuse = true;
                }
            }

            // If any angle is obtuse, all three points has to be on circle formed
            if (obtuse) {
                double s = (a * b * c) / 2;
                double radius = (a * b * c) / (4 * Math.sqrt(s * (s - a) * (s - b) * (s - c)));
                if (radius > PARAMETERS.RADIUS1) {
                    return true;
                }
            }
            // Otherwise the radius of the minimum circle created is half the largest distance between two points
            else {
                if ((max_distance / 2) > PARAMETERS.RADIUS1) {
                    return true;
                }
            }
        }
        return false;
    }

    // Launch Interceptor Condition 9. For further details, see documented requirements.
    public static Boolean LIC9(){

        // The condition cannot be met if:
        if( (NUMPOINTS < 5) || (PARAMETERS.C_PTS < 1) ||
            (PARAMETERS.D_PTS < 1) || (PARAMETERS.C_PTS + PARAMETERS.D_PTS > NUMPOINTS-3) )
            return false;

        // First point of the set of three points
        for(int i = 0; i < (NUMPOINTS-2-PARAMETERS.C_PTS-PARAMETERS.D_PTS); i++){

            // Second point of the set of three, preceded by C_PTS intervening points
            int j = i + PARAMETERS.C_PTS + 1;

            // Third point of the set of three, preceded by D_PTS intervening points
            int k = j + PARAMETERS.D_PTS + 1;

            // The first (i) or last point (k) must not coincide with the vertex point (j)
            if( (X[i]==X[j] && Y[i]==Y[j]) || (X[k]==X[j] && Y[k]==Y[j]) ) return false;

            // Compute the length of the sides of the triangle that the three points produce
            double ij = Math.sqrt( Math.pow(X[i]-X[j], 2) + Math.pow(Y[i]-Y[j], 2) );
            double ik = Math.sqrt( Math.pow(X[i]-X[k], 2) + Math.pow(Y[i]-Y[k], 2) );
            double jk = Math.sqrt( Math.pow(X[j]-X[k], 2) + Math.pow(Y[j]-Y[k], 2) );

            // Compute the angle contained between the sides ij and jk using the law of cosines
            double angle = Math.acos(( Math.pow(ij, 2) + Math.pow(jk, 2) - Math.pow(ik, 2) ) / (2 * ij * jk));

            // Evaluate condition
            if( angle < (Math.PI - PARAMETERS.EPSILON) || angle > (Math.PI + PARAMETERS.EPSILON) )
                return true;
        }
        return false;
    }

}