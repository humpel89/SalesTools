package model_Hnefatafl;

import java.util.ArrayList;

public class GameBoard {
    public static final int EMPTY_SQUARE = 0;
    public static final int CORNER_SQUARE = 1;
    public static final int KING = 2;
    public static final int WHITE_PIECE = 3;
    public static final int BLACK_PIECE = 4;
    public static final int KING_START_SQUARE = 5;
    public static final int KING_DEAD = 6;
    public static final Coordinates KING_START_POSITION = new Coordinates(4,4);
    public static final Coordinates CORNER_TOP_LEFT = new Coordinates(0,8);
    public static final Coordinates CORNER_TOP_RIGHT = new Coordinates(8,8);
    public static final Coordinates CORNER_BOTTOM_LEFT = new Coordinates(0,0);
    public static final Coordinates CORNER_BOTTOM_RIGHT = new Coordinates(8,0);

    public static final char[] ALPHA_AXIS = {'A','B','C','D','E','F','G','H','I'};
    public static final int[] NUM_AXIS = {1,2,3,4,5,6,7,8,9};

    private String playersTurn = Rules.playerWhite;
    private String nextPlayersTurn = Rules.playerBlack;
    private boolean gameOver = false;
    private boolean kingIsDead = false;
    private Square[][] squares;

    public GameBoard(){
        setupBoard();
    }

    public void setupBoard(){
        squares = new Square[9][9];
        for(int alphaY = 0; alphaY < 9; alphaY++){
            for(int numX = 0; numX < 9; numX++){
                if((alphaY == 2 && numX == 4) || 
                        (alphaY == 3 && numX == 4) || 
                        (alphaY == 4 && (numX == 2 || numX == 3 || numX == 5 || numX == 6))||
                        (alphaY == 5 && numX == 4) ||
                        (alphaY == 6 && numX == 4))
                    squares[alphaY][numX] = new Square(WHITE_PIECE);

                else if((alphaY == 0 && (numX == 3 || numX == 4 || numX == 5) ||
                        (alphaY == 1 && numX == 4) ||
                        (alphaY == 3 && (numX == 8 || numX == 0))||
                        (alphaY == 4 && (numX == 8 || numX == 7 || numX == 1 || numX == 0))||
                        (alphaY == 5 && (numX == 8 || numX == 0)) ||
                        (alphaY == 7 && numX == 4) ||
                        (alphaY == 8 && (numX == 3 || numX == 4 || numX == 5))))
                    squares[alphaY][numX] = new Square(BLACK_PIECE);
                else
                    squares[alphaY][numX] = new Square(EMPTY_SQUARE);
            }
        }  
        setSquare(KING_START_POSITION, KING);
        setSquare(CORNER_TOP_RIGHT, CORNER_SQUARE);
        setSquare(CORNER_TOP_LEFT, CORNER_SQUARE);
        setSquare(CORNER_BOTTOM_RIGHT, CORNER_SQUARE);
        setSquare(CORNER_BOTTOM_LEFT, CORNER_SQUARE);
    }


    private void setSquare(Coordinates coord, int piece){
        squares[coord.getAlphaY()][coord.getNumX()].setPiece(piece);
    }
    public int getSquare(Coordinates coord){
        return squares[coord.getAlphaY()][coord.getNumX()].getPiece();
    }
    public int getSquare(int alphaY, int numX){
        return getSquare(new Coordinates(alphaY, numX));
    }
    /**
     * Moves a piece on the board, must be checked as an allowed move
     * before executing.
     * @param move sends the information needed to make a move on the board.
     */
    public void movePiece(PlayerMove move){
        if (getSquare(move.getStart()) == KING){
            setSquare(move.getTarget(), getSquare(move.getStart()));
            if(move.getStart() == KING_START_POSITION){
                setSquare(move.getStart(), KING_START_SQUARE);
            }
            else

                setSquare(move.getStart(), EMPTY_SQUARE);
            isKingInCorner();
        }
        else{
            setSquare(move.getTarget(), getSquare(move.getStart()));
            setSquare(move.getStart(), EMPTY_SQUARE);
        }
        removeTakenPieces(move.getTarget());
        setNewPlayersTurn();
    }
    private void removeTakenPieces(Coordinates newPieceLocation) {
        int numX = newPieceLocation.getNumX();
        int alphaY =  newPieceLocation.getAlphaY();
        int right = numX + 1;
        int left = numX - 1;
        int up = alphaY - 1;
        int down = alphaY + 1;
        int opponent;
        if (getCurrentPlayersTurn() == Rules.playerWhite)
            opponent = GameBoard.BLACK_PIECE;
        else
            opponent = GameBoard.WHITE_PIECE;
        if (getCurrentPlayersTurn() == Rules.playerBlack){
            if (right < 9 && (getSquare(alphaY, right) == GameBoard.KING)){
                checkIfKingDies(new Coordinates(alphaY,right));
            }
            if (left >= 0 && (getSquare(alphaY, left) == GameBoard.KING)){
                checkIfKingDies(new Coordinates(alphaY, left));
            }
            if (up >= 0 && (getSquare(up, numX)  == GameBoard.KING)){
                checkIfKingDies(new Coordinates(up, numX));
            }
            if (down < 9 && (getSquare(down, numX) == GameBoard.KING)){
                checkIfKingDies(new Coordinates(down, numX));
            }
        }

        if (right < 8 && (getSquare(alphaY, right) == opponent)){
            if(otherSideOfPieceIsFriendly(new Coordinates(alphaY, right+1),opponent))
                removePiece(new Coordinates(alphaY, right));
        }
        if (left > 0 && (getSquare(alphaY, left) == opponent)){
            if(otherSideOfPieceIsFriendly(new Coordinates(alphaY, left-1),opponent))
                removePiece(new Coordinates(alphaY, left));
        }
        if (up > 0 && (getSquare(up, numX) == opponent)){
            if(otherSideOfPieceIsFriendly(new Coordinates(up-1, numX),opponent))
                removePiece(new Coordinates(up, numX));
        }
        if (down < 8 && (getSquare(down, numX) == opponent)){
            if(otherSideOfPieceIsFriendly(new Coordinates(down+1, numX),opponent))
                removePiece(new Coordinates(down, numX));
        }


    }

    private boolean otherSideOfPieceIsFriendly(Coordinates isThisFriendly, int opponentPiece) {
        if (getCurrentPlayersTurn() == Rules.playerWhite){
            if (getSquare(isThisFriendly) == WHITE_PIECE || getSquare(isThisFriendly) == KING)
                return true;
        }
        else
            if (getSquare(isThisFriendly) == BLACK_PIECE)
                return true;
        return false;
    }

    public GameBoard createBoardCopy(GameBoard originalBoard) {
        GameBoard boardCopy = new GameBoard();
        boardCopy.copyAndSetBoardStateFrom(originalBoard);
        return boardCopy;
    }


    private void setNewPlayersTurn(){
        if (playersTurn == Rules.playerWhite){
            playersTurn = Rules.playerBlack;
            nextPlayersTurn = Rules.playerWhite;
        }
        else{
            playersTurn = Rules.playerWhite;
            nextPlayersTurn = Rules.playerBlack;
        }
    }
    public String getCurrentPlayersTurn(){
        return playersTurn;
    }
    public String getNextPlayersTurn(){
        return nextPlayersTurn;
    }


    public void removePiece(Coordinates coord){
        setSquare(coord, EMPTY_SQUARE);
    }

    public void copyAndSetBoardStateFrom(GameBoard board){
        for (int alphaY = 0; alphaY < 9; alphaY++){
            for (int numX = 0; numX < 9; numX++){
                int pieceId =  board.getSquare(alphaY, numX);
                squares[alphaY][numX].setPiece(pieceId);
            }
        }
        playersTurn = board.getCurrentPlayersTurn();
        nextPlayersTurn = board.getNextPlayersTurn();
    }


    public ArrayList<Coordinates> getWhitePieces() {
        ArrayList<Coordinates> whitePieces = new ArrayList<Coordinates>();
        for (int alphaY = 0; alphaY < 9; alphaY++){
            for (int numX = 0; numX < 9; numX++){
                if (squares[alphaY][numX].getPiece() == KING || squares[alphaY][numX].getPiece() == WHITE_PIECE){
                    whitePieces.add(new Coordinates(alphaY, numX));
                }
            }
        }
        return whitePieces;
    }
    public ArrayList<Coordinates> getBlackPieces() {
        ArrayList<Coordinates> blackPieces = new ArrayList<Coordinates>();
        for (int alphaY = 0; alphaY < 9; alphaY++){
            for (int numX = 0; numX < 9; numX++){
                if (squares[alphaY][numX].getPiece() == BLACK_PIECE){
                    blackPieces.add(new Coordinates(alphaY, numX));
                }
            }
        }
        return blackPieces;
    }

    private void checkIfKingDies(Coordinates king) {
        int fourOrThreeIfAtWall = 0;
        if(king.getAlphaY() == 0 || king.getAlphaY() == 8 || king.getNumX() == 0 || king.getNumX() == 8){
            fourOrThreeIfAtWall = 3;
        }
        else 
            fourOrThreeIfAtWall = 4;
        int piecesAroundKing = 0;
        if(king.getAlphaY() < 8)
            if(getSquare(king.getAlphaY()+1, king.getNumX()) == BLACK_PIECE){
                piecesAroundKing++;
            }
        if(king.getAlphaY() > 0)
            if(getSquare(king.getAlphaY()-1, king.getNumX()) == BLACK_PIECE){
                piecesAroundKing++;
            }
        if(king.getNumX() < 8)
            if(getSquare(king.getAlphaY(), king.getNumX()+1) == BLACK_PIECE){
                piecesAroundKing++;
            }
        if(king.getNumX() > 0)
            if(getSquare(king.getAlphaY(), king.getNumX()-1) == BLACK_PIECE){
                piecesAroundKing++;
            }
        if(piecesAroundKing == fourOrThreeIfAtWall){
            kingIsDead = true;
            gameOver = true;
        }
    }
    public boolean isKingInCorner() {
        if (getSquare(CORNER_TOP_LEFT) == KING ||
                getSquare(CORNER_TOP_RIGHT) == KING ||
                getSquare(CORNER_BOTTOM_LEFT) == KING ||
                getSquare(CORNER_BOTTOM_RIGHT) == KING){
            gameOver = true;
            return true;
        }
        else
            return false;
    }

    public boolean kingIsDead(){
        return kingIsDead;
    }
    public boolean gameOver() {
        return gameOver;
    }

    public String toString(){
        String gbString = "\n";
        gbString = gbString +("  ");
        for(int i=0; i<9;i++){
            gbString = gbString +(" " + NUM_AXIS[i] + " ");
        }
        gbString = gbString +("\n");
        for(int i=0;i<9;i++){
            gbString = gbString + ALPHA_AXIS[i]+" ";
            for(int j=0;j<9;j++){
                gbString = gbString + "[" + squares[i][j].toString() + "]";
            }
            gbString = gbString + " " + ALPHA_AXIS[i]+" ";
            gbString = gbString +("\n");
        }
        gbString = gbString +("  ");
        for(int i=0; i<9;i++){
            gbString = gbString +(" " + NUM_AXIS[i] + " ");
        }
        gbString = gbString +("\n");
        return gbString;
    }


    /**
     * This class handles the squares on the GameBoard, they provide
     * easy access to check what kind of piece is on a square or
     * if the square is empty, a corner square, or the kings start position.
     * @author Humpel
     *
     */
    class Square {
        private int piece = 0;
        private String[] stringPieceLetters= {" ", "X", "K", "W", "B", "X"};
        Square(int piece){
            this.piece = piece;
        }
        public int getPiece(){
            return piece;
        }
        public void setPiece(int piece){
            this.piece = piece;
        }
        public String toString(){
            return stringPieceLetters[piece];
        }
    }



}