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

    // Launch Interceptor Condition 1.
    public static Boolean LIC1() {

        for (int i=0; i < (NUMPOINTS - 2); i++) {
            double x1 = X[i];
            double y1 = Y[i];
            double x2 = X[i+1];
            double y2 = Y[i+1];
            double x3 = X[i+2];
            double y3 = Y[i+2];

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
            }
            else if (max_distance == b) {
                if (Math.toDegrees(Math.acos((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2 * a * c))) > 90) {
                    obtuse = true;
                }
            }
            else if (max_distance == c) {
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
}