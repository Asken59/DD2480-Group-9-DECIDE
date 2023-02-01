package main;

import java.util.Arrays;

public class Decide {
    public enum Connectors {NOTUSED, ORR, ANDD};

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

    public static void DECIDE() {
        // Evaluate LICS and generate the CMV
        generateCMV();

        //Generate the PUM using the CMV and LCM
        generatePUM();

        //Generate the FUV using the PUM and PUV
        generateFUV();

        //Evaluate LAUNCH and print
        evaluateFUV();
        
        if (LAUNCH) System.out.println("YES");
        else System.out.println("NO");
    }

    public static void generateCMV() {

        CMV = new Boolean[15];

        CMV[0] = LIC0();
        CMV[1] = LIC1();
        CMV[2] = LIC2();
        CMV[3] = LIC3();
        CMV[4] = LIC4();
        CMV[5] = LIC5();
        CMV[6] = LIC6();
        CMV[7] = LIC7();
        CMV[8] = LIC8();
        CMV[9] = LIC9();
        CMV[10] = LIC10();
        CMV[11] = LIC11();
        CMV[12] = LIC12();
        CMV[13] = LIC13();
        CMV[14] = LIC14();
    }
    
    public static void generatePUM() {
        // PUM is a 15 x 15 matrix
        PUM = new Boolean[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                switch (LCM[i][j]) {
                    case NOTUSED:
                        PUM[i][j] = true;
                        break;
                    case ORR:
                        PUM[i][j] = (CMV[i] || CMV[j]) ? true: false;
                        break;
                    case ANDD:
                        PUM[i][j] = (CMV[i] && CMV[j]) ? true: false;
                        break;
                }
            }
        }
    }


    public static void generateFUV() {
        FUV = new Boolean[15];

        for (int i = 0; i < 15; i++) {
            if (!PUV[i]) FUV[i] = true; 
            else {
                boolean row = true;
                for (int j = 0; j < 15; j++) {
                    if (!PUM[i][j]) row = false;
                }
                FUV[i] = row;
            }
        }

    }

    public static void evaluateFUV() {
        LAUNCH = true;
        for (Boolean b: FUV)
            if (!b) LAUNCH = false;
    }


    // Launch Interceptor Condition 0. For further details, see documented requirements.
    public static Boolean LIC0() {

        // Enforce non-negative LENGTH1 parameter
        if(PARAMETERS.LENGTH1 < 0) return false;

        for (int i = 0; i < NUMPOINTS - 1; i++) {
            // Euclidean distance
            double distance = Math.sqrt(Math.pow(X[i] - X[i + 1], 2) + Math.pow(Y[i] - Y[i + 1], 2));
            if (distance > PARAMETERS.LENGTH1)
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

    public static Boolean LIC2() { //Decide if 3 cons. points satisfies an angle < (PI - EPSILON) or > (PI + EPSILON)
        if(PARAMETERS.EPSILON < 0 || PARAMETERS.EPSILON > Math.PI) //Invalid input checking
            return false;

        double angle = 0;
        double sideA = 0;
        double sideSqrtA = 0;
        double sideB = 0;
        double sideC = 0;
        double sideSqrtC = 0;

        for (int i = 0; i < NUMPOINTS - 2; i++) {
            if (!((X[i] == X[i + 1] && Y[i] == Y[i + 1]) //They don't coincide
                    || (X[i] == X[i + 2] && Y[i] == Y[i + 2])
                    || (X[i + 1] == X[i + 2] && Y[i + 1] == Y[i + 2]))) {

                //Form triangle and calculate sides
                sideA = Math.pow(X[i + 1] - X[i + 2], 2) + Math.pow(Y[i + 1] - Y[i + 2], 2);
                sideB = Math.pow(X[i] - X[i + 2], 2) + Math.pow(Y[i] - Y[i + 2], 2);
                sideC = Math.pow(X[i] - X[i + 1], 2) + Math.pow(Y[i] - Y[i + 1], 2);
                sideSqrtA = Math.sqrt(sideA);
                sideSqrtC = Math.sqrt(sideC);

                //Calculate angle using law of cosine
                angle = Math.acos((sideA + sideC - sideB) / (2 * sideSqrtA * sideSqrtC));

                if (angle < Math.PI - PARAMETERS.EPSILON || angle > Math.PI + PARAMETERS.EPSILON) {
                    return true;
                }

            }
        }
        return false;
    }


    public static Boolean LIC3() { //Decide if the area of a triangle formed by 3 cons. points is greater than AREA1
        if(PARAMETERS.AREA1 < 0) //Invalid input
            return false;

        double triArea = 0;
        for (int i = 0; i < NUMPOINTS - 2; i++) { //Loop through consecutive points
            triArea = ((X[i + 1] - X[i]) * (Y[i + 2] - Y[i]) - (X[i + 2] - X[i]) * (Y[i + 1] - Y[i])) / 2; //Shoelace formula
            triArea = Math.abs(triArea);
            if (PARAMETERS.AREA1 < triArea) {

                return true;
            }
        }
        return false;
    }
    
    // There exists at least one set of Q PTS consecutive data points that lie in more than QUADS
    // quadrants. Where there is ambiguity as to which quadrant contains a given point, priority
    // of decision will be by quadrant number, i.e., I, II, III, IV. For example, the data point (0,0)
    // is in quadrant I, the point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the point
    // (0,1) is in quadrant I and the point (1,0) is in quadrant I.
    public static Boolean LIC4() {

        // return false if PARAMETER input values are invalid
        if (PARAMETERS.Q_PTS < 2 || PARAMETERS.Q_PTS > NUMPOINTS) return false;
        if (PARAMETERS.QUADS < 1 || PARAMETERS.QUADS > 3) return false;

        // iterate up to the number of points - Q_PTS -1
        for (int i = 0; i < NUMPOINTS - PARAMETERS.Q_PTS - 1; i++) {

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
                else {
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

    // There exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), such
    // that X[j] - X[i] < 0. (where i = j-1)
    public static Boolean LIC5() {

        for (int i = 0; i < NUMPOINTS - 1; i++) {

            if ((X[i + 1] - X[i]) < 0) return true;

        }
        return false;
    }

    // There exists at least one set of N PTS consecutive data points such that at least one of the
    // points lies a distance greater than DIST from the line joining the first and last of these N PTS
    // points. If the first and last points of these N PTS are identical, then the calculated distance
    // to compare with DIST will be the distance from the coincident point to all other points of
    // the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
    public static Boolean LIC6() {

        if (NUMPOINTS < 3) return false;
        if (NUMPOINTS < PARAMETERS.N_PTS) return false;

        for (int i = 0; i <= NUMPOINTS - PARAMETERS.N_PTS; i++) {

            for (int j = i + 1; j < i + PARAMETERS.N_PTS; j++) {

                //distance of first and last point
                double dist = Math.sqrt((Math.pow(X[i + PARAMETERS.N_PTS - 1], 2) - Math.pow(X[i], 2)) + (Math.pow(Y[i + PARAMETERS.N_PTS - 1], 2) - Math.pow(Y[i], 2)));
                // special case if first and last N_PTS have identical coordinates
                if (dist == 0) {
                    // do special distance by calculating X[j]Y[j] distance from the first of these points
                    double specialDistance = Math.sqrt((Math.pow(X[j], 2) - Math.pow(X[i], 2)) + (Math.pow(Y[j], 2) - Math.pow(Y[i], 2)));

                    if (specialDistance > 0) return true;
                }
                // standard distance from point X[j]Y[j] to the line
                else {

                    double distanceFromDist = Math.abs(((X[i + PARAMETERS.N_PTS - 1] - X[i]) * (Y[i] - Y[j])) - (X[i] - X[j]) * (Y[i + PARAMETERS.N_PTS - 1] - Y[i])) / dist;

                    if (distanceFromDist > dist) return true;
                }

            }
        }
        return false;
    }

    // Launch Interceptor Condition 7.
    public static Boolean LIC7() {

        // Check so that NUMBPOINTS is >= 3 as per specification, also checks that a set could possibly exist for the
        // given K_PTS parameter value
        if (NUMPOINTS >= 3 && NUMPOINTS >= (PARAMETERS.K_PTS + 2)) {
            for (int i = 0; i < (NUMPOINTS - PARAMETERS.K_PTS - 1); i++) {

                // Calculate distance between points
                double distance = Math.sqrt(Math.pow((X[i] - X[i + PARAMETERS.K_PTS + 1]), 2) + Math.pow((Y[i] - Y[i + PARAMETERS.K_PTS + 1]), 2));

                if (distance > PARAMETERS.LENGTH1) return true;

            }
        }
        return false;

    }

    public static Boolean LIC8() {

        if (NUMPOINTS < 5 || PARAMETERS.A_PTS < 1 || PARAMETERS.B_PTS < 1 ||
                PARAMETERS.A_PTS + PARAMETERS.B_PTS > NUMPOINTS - 3) {
            return false;
        }

        for (int i = 0; i < NUMPOINTS - 2 - PARAMETERS.A_PTS - PARAMETERS.B_PTS; i++) {

            double x1 = X[i];
            double y1 = Y[i];
            double x2 = X[i + PARAMETERS.A_PTS + 1];
            double y2 = Y[i + PARAMETERS.A_PTS + 1];
            double x3 = X[i + PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2];
            double y3 = Y[i + PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2];

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
    public static Boolean LIC9() {

        // The condition cannot be met if:
        if ((NUMPOINTS < 5) || (PARAMETERS.C_PTS < 1) ||
                (PARAMETERS.D_PTS < 1) || (PARAMETERS.C_PTS + PARAMETERS.D_PTS > NUMPOINTS - 3))
            return false;

        // First point of the set of three points
        for (int i = 0; i < (NUMPOINTS - 2 - PARAMETERS.C_PTS - PARAMETERS.D_PTS); i++) {

            // Second point of the set of three, preceded by C_PTS intervening points
            int j = i + PARAMETERS.C_PTS + 1;

            // Third point of the set of three, preceded by D_PTS intervening points
            int k = j + PARAMETERS.D_PTS + 1;

            // The first (i) or last point (k) must not coincide with the vertex point (j)
            if ((X[i] == X[j] && Y[i] == Y[j]) || (X[k] == X[j] && Y[k] == Y[j])) return false;

            // Compute the length of the sides of the triangle that the three points produce
            double ij = Math.sqrt(Math.pow(X[i] - X[j], 2) + Math.pow(Y[i] - Y[j], 2));
            double ik = Math.sqrt(Math.pow(X[i] - X[k], 2) + Math.pow(Y[i] - Y[k], 2));
            double jk = Math.sqrt(Math.pow(X[j] - X[k], 2) + Math.pow(Y[j] - Y[k], 2));

            // Compute the angle contained between the sides ij and jk using the law of cosines
            double angle = Math.acos((Math.pow(ij, 2) + Math.pow(jk, 2) - Math.pow(ik, 2)) / (2 * ij * jk));

            // Evaluate condition
            if (angle < (Math.PI - PARAMETERS.EPSILON) || angle > (Math.PI + PARAMETERS.EPSILON))
                return true;
        }
        return false;
    }


    // Launch Interceptor Condition 10. For further details, see documented requirements.
    public static Boolean LIC10() {

        // The condition is not met if:
        if ((NUMPOINTS < 5) || (PARAMETERS.E_PTS < 1) || (PARAMETERS.F_PTS < 1) ||
                (PARAMETERS.E_PTS + PARAMETERS.F_PTS > NUMPOINTS - 3))
            return false;

        // First point
        for (int i = 0; i < (NUMPOINTS - 2 - PARAMETERS.E_PTS - PARAMETERS.F_PTS); i++) {

            // Second point, preceded by E_PTS consecutive intervening points
            int j = i + PARAMETERS.E_PTS + 1;

            // Third point, preceded by F_PTS consecutive intervening points
            int k = j + PARAMETERS.F_PTS + 1;

            // Compute the sides of the triangle formed by the three points
            double ij = Math.sqrt(Math.pow(X[i] - X[j], 2) + Math.pow(Y[i] - Y[j], 2));
            double ik = Math.sqrt(Math.pow(X[i] - X[k], 2) + Math.pow(Y[i] - Y[k], 2));
            double jk = Math.sqrt(Math.pow(X[j] - X[k], 2) + Math.pow(Y[j] - Y[k], 2));

            // Compute the area of the triangle using Heron's formula
            double s = 0.5 * (ij + ik + jk);
            double area = Math.sqrt(s * (s - ij) * (s - ik) * (s - jk));

            // Evaluate
            if (area > PARAMETERS.AREA1) return true;
        }
        return false;
    }

    // Launch Interceptor Condition 11. For further details, see documented requirements.
    public static Boolean LIC11() {
        for (int i = 0; i < NUMPOINTS - PARAMETERS.G_PTS - 1; i++) {
            if (X[i + PARAMETERS.G_PTS + 1] - X[i] < 0) {
                return true;
            }
        }
        return false;
    }

    // Launch Interceptor Condition 12.
    public static Boolean LIC12() {

        // Check so that NUMBPOINTS is >= 3 as per specification, also checks that a set could possibly exist for the
        // given K_PTS parameter value
        if (NUMPOINTS >= 3 && NUMPOINTS >= (PARAMETERS.K_PTS + 2)) {
            boolean greaterThen = false;
            boolean lessThen = false;

            for (int i = 0; i < (NUMPOINTS - PARAMETERS.K_PTS - 1); i++) {

                // Calculate distance between points
                double distance = Math.sqrt(Math.pow((X[i] - X[i + PARAMETERS.K_PTS + 1]), 2) + Math.pow((Y[i] - Y[i + PARAMETERS.K_PTS +1]), 2));

                // Check greater
                if (distance > PARAMETERS.LENGTH1) greaterThen = true;

                // Check lesser
                if (distance < PARAMETERS.LENGTH2) lessThen = true;

                // If both are true then the condition is met
                if (lessThen && greaterThen) return true;

            }
        }

        return false;
    }

    // Launch Interceptor Condition 13.
    public static Boolean LIC13() {

        // Check so that NUMBPOINTS is >= 5 as per specification, also checks that a set could possibly exist for the
        // given A_PTS and B_PTS parameter values
        if (NUMPOINTS >= 5 && NUMPOINTS >= (PARAMETERS.A_PTS + PARAMETERS.B_PTS + 3)) {
            boolean contain1 = false;
            boolean contain2 = false;

            // Loop through all possible sets
            for (int i = 0; i < (NUMPOINTS - (PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2)); i++) {

                // Calculate the radius of the minimum enclosing circle for the set
                // Two senarios, one for obtuse triangles and one for acute and right triangles

                // The points in the set
                double x1 = X[i];
                double y1 = Y[i];
                double x2 = X[i + PARAMETERS.A_PTS + 1];
                double y2 = Y[i + PARAMETERS.A_PTS + 1];
                double x3 = X[i + PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2];
                double y3 = Y[i + PARAMETERS.A_PTS + PARAMETERS.B_PTS + 2];

                // Calculates distances between data points
                double a = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
                double b = Math.sqrt(Math.pow((x1 - x3), 2) + Math.pow((y1 - y3), 2));
                double c = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));

                // Calculates the largest distance
                double maxDist= Math.max(Math.max(a, b), c);

                // If the triangle is obruse then the obtruse angle will be oposite the largest distance
                // Check if the angle opposite the largest distance is obtrues with the cosine rule (C = arccos((a^2 + b^2 - c^2) / 2ab))
                boolean obtuse = false;
                if (maxDist == a) {
                    if (Math.toDegrees(Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b))) > 90) {
                        obtuse = true;
                    }
                } else if (maxDist == b) {
                    if (Math.toDegrees(Math.acos((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2 * a * c))) > 90) {
                        obtuse = true;
                    }
                } else if (maxDist == c) {
                    if (Math.toDegrees(Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2 * b * c))) > 90) {
                        obtuse = true;
                    }
                }

                // Calculate radius
                double minRadius = 0;
                if (obtuse) {
                    // The minimum enclosing circle is the one where all points lie on the circumference
                    double s = (a * b * c) / 2;
                    minRadius = (a * b * c) / (4 * Math.sqrt(s * (s - a) * (s - b) * (s - c)));
                } else {
                    // The minimun radius is half of the max distance
                    minRadius = maxDist/2;
                }

                // Check if set can not be contained within RADIUS1
                if (minRadius > PARAMETERS.RADIUS1) contain1 = true;

                // Check if set can be contained within or on RADIUS2
                if (minRadius <= PARAMETERS.RADIUS2) contain2 = true;
            }


            // If both are true then the contiditon is satisfied
            if (contain1 && contain2) return true;

        }

        return false;
    }

    // Launch Interceptor Condition 14. For further details, see documented requirements.
    public static Boolean LIC14() {

        boolean largeArea = false;
        boolean smallArea = false;

        // Conditions which should be met
        if (NUMPOINTS < 5 || PARAMETERS.AREA2 < 2) {
            return false;
        }

        // Looping through data points
        for (int i=0; i < NUMPOINTS-PARAMETERS.E_PTS-PARAMETERS.F_PTS-2; i++) {

            // Second point, preceded by E_PTS consecutive points
            int j = i + PARAMETERS.E_PTS + 1;

            // Third point, preceded by F_PTS consecutive points
            int k = j + PARAMETERS.F_PTS + 1;

            // Calculates area between the three points
            double area = 0.5 * Math.abs((X[i]*(Y[j] - Y[k])) + (X[j]*(Y[k] - Y[i])) + (X[k]*(Y[i] - Y[j])));

            // Evaluate area conditions
            if (area > PARAMETERS.AREA1) {
                largeArea = true;
            }

            if (area < PARAMETERS.AREA2) {
                smallArea = true;
            }

            // Check if both conditions are met
            if (largeArea && smallArea) {
                return true;
            }
        }

        return false;
    }
}
