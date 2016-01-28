package model_Hnefatafl;

import exceptions.CoordinateOutOfBoundsException;

/**
 * This class creates a coordinate of the gameboard in Hnefatafl, in the 9x9 format.
 * Trying to create a coordinate which is not on the board will result in an error.
 * @author Humpel
 *
 */

public class Coordinates {
    
    private int alphaY;
    private int numX;
    private char[] alphaAxis = {'A','B','C','D','E', 'F','G','H','I'};
    private int[] numAxis = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public Coordinates(int alphaY, int numX){
        if (alphaY > 8 || alphaY < 0){
            throw new CoordinateOutOfBoundsException("Alpha-Coord must be A-I.");
        }
        if (numX > 8 || numX < 0){
            throw new CoordinateOutOfBoundsException("Num-Coord must be 1-9.");
        }
        this.alphaY = alphaY;
        this.numX = numX;
    }
    /**
     * @return alphaY.
     */
    public int getAlphaY(){
        return alphaY;
    }
    /**
     * @return numX.
     */
    public int getNumX(){
        return numX;
    }
    /**
     * Set and change the X value of this coordinate.
     * @param alphaY
     */
    public void setAlphaY(int alphaY){
        this.alphaY = alphaY;
    }
    /**
     * Set and change the Y value of this coordinate.
     * @param NumX
     */
    public void setNumX(int NumX){
        this.numX = NumX;
    }
    /**
     * Returns a string value of the coordinate.
     * Ex: "A1" 
     */
    public String toString(){
        return  "[" + alphaAxis[alphaY] + Integer.toString(numAxis[numX]) +"]";
    }//TODO
}
