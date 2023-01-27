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

    public static Boolean LIC2(){ //Decide if 3 cons. points satisfies an angle < (PI - EPSILON) or > (PI + EPSILON)
        double angle = 0;
        double tempPI = Math.PI; //Need to add the constant to the class?
        double sideA = 0;
        double sideSqrtA = 0;
        double sideB = 0;
        double sideC = 0;
        double sideSqrtC = 0;

        for(int i = 0; i < NUMPOINTS - 2; i++){
            if(!(X[i] == X[i+1] && Y[i] == Y[i+1]) || (X[i] == X[i+2] && Y[i] == Y[i+2])){ //They don't coincide
                //Form triangle and calculate sides
                sideA = Math.pow(X[i+1] - X[i+2], 2) + Math.pow(Y[i+1] - Y[i+2], 2);
                sideB = Math.pow(X[i] - X[i+2], 2) + Math.pow(Y[i] - Y[i+2], 2);
                sideC = Math.pow(X[i] - X[i+1], 2) + Math.pow(Y[i] - Y[i+1], 2);
                sideSqrtA = Math.sqrt(sideA);
                sideSqrtC = Math.sqrt(sideC);

                //Calculate angle using law of cosine
                angle = Math.acos((sideA + sideC - sideB)/(2*sideSqrtA*sideSqrtC));

                if(angle < tempPI - PARAMETERS.EPSILON || angle > tempPI + PARAMETERS.EPSILON){
                    return true;
                }
            }
        }
        return false;
    }
}