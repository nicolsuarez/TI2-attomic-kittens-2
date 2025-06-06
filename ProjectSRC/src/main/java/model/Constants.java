package model;

public class Constants {
    /*
    The value of the max score is given by the weight of all the edges,
    which is used assuming that the player moved through the whole map twice
    Total weight = 227 approx. 230
    Total weight * 2  = 460
    And I don't like the number six so the max score will be 450 :p
    */
    public static final int MAX_SCORE = 450;

    // Traps and Clues
    public static final int TRAPS = 10;
    public static final int CLUES = 10;
}
