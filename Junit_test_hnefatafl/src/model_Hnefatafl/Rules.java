package model_Hnefatafl;

import java.util.ArrayList;

/**
 * This class keeps track on rules, it does not keep any sort of records,
 * therefor can be created and used by any class, it simply views and checks
 * game states on demand and returns Ex. true if a valid move can be done.
 * 
 * @author ntn13hcm
 *
 */
public class Rules {

    public static enum PlayerType {
        PLAYER, COMPUTER
    };

    public static PlayerType currentPlayer = PlayerType.PLAYER;

    public static final String playerWhite = "WHITE";
    public static final String playerBlack = "BLACK";
    public static final int ONESQUARE = 1;
    public boolean validMove(PlayerMove move, GameBoard board) {
        // int moveDistance = move.getMoveDistance();
        String direction = move.getDirection();
        /*
         * Check if proper move by the King.
         */
        if (board.getSquare(move.getStart()) == GameBoard.KING) { // Add: &&
            // moveDistance
            // == 1 if
            // King shall
            // only move 1
            // step.
            if (board.getSquare(move.getTarget()) == GameBoard.EMPTY_SQUARE
                    || board.getSquare(move.getTarget()) == GameBoard.CORNER_SQUARE)
                ;
            return true;
        }
        /*
         * Check if proper move by a peasant/tower.
         */
        else {
            if (direction == PlayerMove.RIGHT) {
                for (int x = move.getStart().getAlphaY() + 1; x <= move.getTarget()
                        .getAlphaY(); x++) {
                    if (board.getSquare(new Coordinates(x, move.getTarget()
                            .getNumX())) != GameBoard.EMPTY_SQUARE)
                        return false;
                }
            } else {
                if (direction == PlayerMove.LEFT) {
                    for (int x = move.getStart().getAlphaY() - 1; x >= move
                            .getTarget().getAlphaY(); x--) {
                        if (board.getSquare(new Coordinates(x, move.getTarget()
                                .getNumX())) != GameBoard.EMPTY_SQUARE)
                            return false;
                    }
                } else {
                    if (direction == PlayerMove.UP) {
                        for (int y = move.getStart().getNumX() + 1; y <= move
                                .getTarget().getNumX(); y++) {
                            if (board.getSquare(new Coordinates(move
                                    .getTarget().getAlphaY(), y)) != GameBoard.EMPTY_SQUARE)
                                return false;
                        }
                    } else {
                        if (direction == PlayerMove.DOWN) {
                            for (int y = move.getStart().getNumX() - 1; y >= move
                                    .getTarget().getNumX(); y--) {
                                if (board.getSquare(new Coordinates(move
                                        .getTarget().getAlphaY(), y)) != GameBoard.EMPTY_SQUARE)
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Finds all empty locations the selected piece can move to on the board.
     * 
     * @param start
     * @param board
     * @return
     */

    public ArrayList<PlayerMove> getAllPossiblePlayerMoves(GameBoard boardState) {

        ArrayList<PlayerMove> allPlayerMoves = new ArrayList<PlayerMove>();
        ArrayList<Coordinates> playerPieces = new ArrayList<Coordinates>();
        ArrayList<Coordinates> possibleMoveLocations = new ArrayList<Coordinates>();
        Coordinates start;
        Coordinates target;
        PlayerMove move;

        if (boardState.getCurrentPlayersTurn().matches(playerWhite)){
            playerPieces = boardState.getWhitePieces();
        } else {
            playerPieces = boardState.getBlackPieces();
        }

        int indexOuterLoop = 0;
        int indexInnerLoop;

        while(indexOuterLoop < playerPieces.size()){
            start = playerPieces.get(indexOuterLoop);
            possibleMoveLocations.addAll(getPossibleMoveLocations(start, boardState));

            indexInnerLoop = 0;
            while (indexInnerLoop < possibleMoveLocations.size()) {
                target = possibleMoveLocations.get(indexInnerLoop);
                if(start.getAlphaY() == target.getAlphaY() || start.getNumX() == target.getNumX()){
                    move = new PlayerMove(start, target);
                    allPlayerMoves.add(move);
                }
                indexInnerLoop++;
            }
            indexOuterLoop++;
        }
        return allPlayerMoves;
    }

    private ArrayList<Coordinates> getPossibleMoveLocations(Coordinates start,
            GameBoard board) {

        ArrayList<Coordinates> possibleMoveLocations = new ArrayList<Coordinates>();
        for (int alphaY = start.getAlphaY()+ONESQUARE; alphaY < 9; alphaY++){
            if (board.getSquare(alphaY, start.getNumX()) == GameBoard.EMPTY_SQUARE){
                possibleMoveLocations.add(new Coordinates(alphaY, start.getNumX()));
            }
            else{
                break;
            }
        }
        for (int alphaY = start.getAlphaY()-ONESQUARE; alphaY >= 0; alphaY--){
            if (board.getSquare(alphaY, start.getNumX()) == GameBoard.EMPTY_SQUARE){
                possibleMoveLocations.add(new Coordinates(alphaY, start.getNumX()));
            }
            else{
                break;
            }
        }
        for (int numX = start.getNumX()+ONESQUARE; numX < 9; numX++){
            if (board.getSquare(start.getAlphaY(), numX) == GameBoard.EMPTY_SQUARE){
                possibleMoveLocations.add(new Coordinates(start.getAlphaY(), numX));
            }
            else{
                break;
            }
        }
        for (int numX = start.getNumX()-ONESQUARE; numX >= 0; numX--){
            if (board.getSquare(start.getAlphaY(), numX) == GameBoard.EMPTY_SQUARE){
                possibleMoveLocations.add(new Coordinates(start.getAlphaY(), numX));
            }
            else{
                break;
            }
        }
        return possibleMoveLocations;
    }    
    
    public GameBoard createBoardCopy(GameBoard originalBoard) {
        GameBoard boardCopy = new GameBoard();
        boardCopy.copyAndSetBoardStateFrom(originalBoard);
        return boardCopy;
    }
    
    public static boolean gameOver(GameBoard board) {
        return (board.gameOver());

    }
}
/*
    public ArrayList<Coordinates> checkForPiecesToBeRemoved(Coordinates newPieceLocation,
            GameBoard board) {
        ArrayList<Coordinates> removeThese = new ArrayList<Coordinates>();

        int x = newPieceLocation.getAlphaY();
        int y = newPieceLocation.getNumX();
        int right = x + 1;
        int left = x - 1;
        int up = y + 1;
        int down = y - 1;
        int opponent;
        if (board.getCurrentPlayersTurn() == Rules.playerWhite)
            opponent = GameBoard.BLACK_PIECE;
        else
            opponent = GameBoard.WHITE_PIECE;
        if (board.getCurrentPlayersTurn() == Rules.playerBlack){
            if (right < 9 && board.getSquare(right, y) == GameBoard.KING){
                checkIfKingDies(new Coordinates(right, y), board);
            }
            if (left >= 0 && board.getSquare(left, y) == GameBoard.KING){
                checkIfKingDies(new Coordinates(left, y), board);
            }
            if (up < 9 && board.getSquare(x, up)  == GameBoard.KING){
                checkIfKingDies(new Coordinates(x, up), board);
            }
            if (down >= 0 && board.getSquare(x, down) == GameBoard.KING){
                checkIfKingDies(new Coordinates(x, down), board);
            }
        }

        if (right < 8 && board.getSquare(right, y) == opponent){
            if(otherSideOfPieceIsFriendly(new Coordinates(right+1, y),opponent, board));
            removeThese.add(new Coordinates(right, y));
        }
        if (left > 0 && board.getSquare(left, y) == opponent){
            if(otherSideOfPieceIsFriendly(new Coordinates(left-1, y),opponent, board));
            removeThese.add(new Coordinates(left, y));
        }
        if (up < 8 && board.getSquare(x, up) == opponent){
            if(otherSideOfPieceIsFriendly(new Coordinates(x, up+1),opponent, board));
            removeThese.add(new Coordinates(x, up));
        }
        if (down > 0 && board.getSquare(x, down) == opponent){
            if(otherSideOfPieceIsFriendly(new Coordinates(x, down-1),opponent, board));
            removeThese.add(new Coordinates(x, down));
        }


    }

    private boolean checkIfKingDies(Coordinates king, GameBoard board) {
        int fourOrThreeIfAtWall = 0;
        if(king.getAlphaY() == 0 || king.getAlphaY() == 8 || king.getNumX() == 0 || king.getNumX() == 8){
            fourOrThreeIfAtWall = 3;
        }
        else 
            fourOrThreeIfAtWall = 4;
        int piecesAroundKing = 0;
        if(king.getAlphaY() < 9)
            if(board.getSquare(king.getAlphaY()+1, king.getNumX()) == GameBoard.BLACK_PIECE){
                piecesAroundKing++;
            }
        if(king.getAlphaY() >= 0)
            if(board.getSquare(king.getAlphaY()-1, king.getNumX()) == GameBoard.BLACK_PIECE){
                piecesAroundKing++;
            }
        if(king.getNumX() < 9)
            if(board.getSquare(king.getAlphaY(), king.getNumX()+1) == GameBoard.BLACK_PIECE){
                piecesAroundKing++;
            }
        if(king.getNumX() >= 0)
            if(board.getSquare(king.getAlphaY(), king.getNumX()-1) == GameBoard.BLACK_PIECE){
                piecesAroundKing++;
            }
        if(piecesAroundKing == fourOrThreeIfAtWall)
            return true;
        else
            return false;
    }

    private boolean otherSideOfPieceIsFriendly(Coordinates isThisFriendly, int opponentPiece, GameBoard board) {
        if (board.getCurrentPlayersTurn() == Rules.playerWhite){
            if (board.getSquare(isThisFriendly) == GameBoard.WHITE_PIECE)
                return true;
        }
        else
            if (board.getSquare(isThisFriendly) == GameBoard.BLACK_PIECE)
                return true;
        return false;
    }


 */

