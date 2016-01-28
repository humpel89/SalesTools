package model_Hnefatafl;

import exceptions.CoordinateOutOfBoundsException;
import exceptions.WrongCoordFormatException;

public class PlayerMove {
    private Coordinates start;
    private Coordinates target;
  //  private int moveValue = 0;

    public static final String RIGHT = "RIGHT";
    public static final String LEFT = "LEFT";
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    
    public PlayerMove(String move){
        String start = move.substring(0, 2);
        String target = move.substring((move.length()-2), move.length());
        setStartCoord(createCoordFromString(start));
        setTargetCoord(createCoordFromString(target));
    }
    public PlayerMove(Coordinates start, Coordinates target){
        this.start = start;
        this.target = target;
    }

    private Coordinates createCoordFromString(String coords){
        int alphaY;
        int numX;
        if (coords.length() != 2){
            throw new WrongCoordFormatException("Coords must be 2 letters.");
        }
        coords = coords.toUpperCase();

        switch (coords.charAt(0)){
        case 'A': alphaY = 0; break;
        case 'B': alphaY = 1; break;
        case 'C': alphaY = 2; break;
        case 'D': alphaY = 3; break;
        case 'E': alphaY = 4; break;
        case 'F': alphaY = 5; break;
        case 'G': alphaY = 6; break;
        case 'H': alphaY = 7; break;
        case 'I': alphaY = 8; break;
        default: throw new CoordinateOutOfBoundsException("Alpha-Coord must be A-I from user input.");
        }

        numX = (Integer.parseInt((coords.substring(1, 2))) - 1);
        if (numX > 8 || numX < 0){
            throw new CoordinateOutOfBoundsException("num-Coord must be 1-9 from user input.");
        }
        return new Coordinates(alphaY, numX);
    }
    /*
    public int value(){
        return moveValue;
    }
    public void setMoveValue(int points){
        moveValue = points;
    }
    */
    public Coordinates getStart(){
        return start;
    }
    public Coordinates getTarget(){
        return target;
    }
    public void setStartCoord(Coordinates start){
        this.start = start;
    }
    public void setTargetCoord(Coordinates target){
        this.target = target;
    }
    public String toString(){        
        return start.toString() + target.toString();
    }
    public int getMoveDistance() {
        int distance = 0;
        if (getDirection().matches(RIGHT) || getDirection().matches(LEFT))
            distance = target.getAlphaY() - start.getAlphaY(); 
        else
            distance = target.getNumX() - start.getNumX();
        return Math.abs(distance);
    }
    public String getDirection() {
        if (target.getAlphaY() - start.getAlphaY() > 0)
            return RIGHT;
        else
            if (target.getAlphaY() - start.getAlphaY() < 0)
                return LEFT;
            else
                if (target.getNumX() - start.getNumX() > 0)
                    return UP;
                else
                    return DOWN;
    }

}
