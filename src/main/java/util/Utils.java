package util;

public class Utils {

    public final static int SPARE_SCORE = 10;
    public final static int INCREMENT_BY_ONE = 1;
    public final static int INCREMENT_BY_TWO = 2;
    public final static int INCREMENT_BY_THREE = 3;
    public final static int DECREASE_BY_ONE = 1;
    public final static int START_INCLUSIVE = 0;
    public final static int EIGHTH_FRAME = 8;
    public final static int NINTH_FRAME = 9;
    public final static int NUMBER_OF_LOOP = 3;
    public final static String MISSED_BALL = "-";
    public final static String STRING_ZERO = "0";
    public final static String STRIKE = "X";

    public final static String FRAME_BOUNDARY_PIPE = "|";
    public final static int STRIKE_SCORE = 10;
    public final static String SPARE = "/";

    public static boolean isNotFrameBoundary(String score){
        return !FRAME_BOUNDARY_PIPE.equals(score) ? true : false;
    }

    public static boolean isFrameBoundary(String score){
        return FRAME_BOUNDARY_PIPE.equals(score) ? true : false;
    }

    public static boolean isStrike(String score){
        return inString(STRIKE_SCORE).equals(score) ? true : false;
    }

    public static boolean isNotStrike(String score){
        return !inString(STRIKE_SCORE).equals(score) ? true : false;
    }

    public static boolean isSpare(String score){
        return SPARE.equals(score) ? true : false;
    }

    public static boolean isNotSpare(String score){
        return !SPARE.equals(score) ? true : false;
    }

    public static int inInteger(String number){
        return Integer.parseInt(number);
    }

    public static String inString(int number){
        return String.valueOf(number);
    }
}
