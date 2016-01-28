package controller_Hnefatafl;

import java.util.ArrayList;

import exceptions.NoMoveSetException;
import model_Hnefatafl.Coordinates;
import model_Hnefatafl.GameBoard;
import model_Hnefatafl.MoveValue;
import model_Hnefatafl.PlayerMove;
import model_Hnefatafl.Rules;

public class Algorithm {

    Rules rules = new Rules();
    //  private String computer;

    private GameBoard originalBoardCopy;
    int level = 5;

    public PlayerMove getBestMove(GameBoard boardCopy) {
        //   this.computer = computer;
        originalBoardCopy = rules.createBoardCopy(boardCopy);
        MoveValue bestValue = alphabetaMiniMax(level, boardCopy,
                Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        PlayerMove bestMove = null;
        try {
            bestMove = bestValue.getMove();
        } catch (NoMoveSetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bestMove;
    }

    private MoveValue alphabetaMiniMax(int level, GameBoard boardState,
            int alpha, int beta, boolean maximizingComputer) {
        if (level == 0 || Rules.gameOver(boardState)) {
            int value = evaluate(boardState, maximizingComputer);
            MoveValue moveValue = new MoveValue(value);
            return moveValue;
        }
        if (maximizingComputer) { // MAXimizing computer
            ArrayList<PlayerMove> playerMoves = rules.getAllPossiblePlayerMoves(boardState);
            MoveValue bestValue = new MoveValue(alpha);
            for (int i = 0; i < playerMoves.size(); i++) {
                GameBoard boardStateCopy = rules.createBoardCopy(boardState);
                boardStateCopy.movePiece(playerMoves.get(i));
                MoveValue testValue = alphabetaMiniMax(level - 1, boardStateCopy, alpha, beta, false);
                if (testValue.value() > alpha) {
                    alpha = testValue.value();
                    bestValue.setMove(playerMoves.get(i));
                }
                if (alpha >= beta)
                    break;
            }
            return bestValue;
        } else { // MINimizing player
            ArrayList<PlayerMove> playerMoves = rules.getAllPossiblePlayerMoves(boardState);
            MoveValue bestValue = new MoveValue(beta);
            for (int i = 0; i < playerMoves.size(); i++) {
                GameBoard boardStateCopy = rules.createBoardCopy(boardState);
                boardStateCopy.movePiece(playerMoves.get(i));
                MoveValue testValue = alphabetaMiniMax(level - 1, boardStateCopy, alpha, beta, true);
                if (testValue.value() < beta) {
                    beta = testValue.value();
                    bestValue.setMove(playerMoves.get(i));
                }
                if (beta <= alpha)
                    break;
            }
            return bestValue;
        }
    }

    private int evaluate(GameBoard boardState, boolean maximizingComputer) {
        int blackValue = 0;
        int whiteValue = 0;
        if (boardState.isKingInCorner())
            whiteValue = Integer.MAX_VALUE;
        else
            if (boardState.kingIsDead())
                blackValue = Integer.MAX_VALUE;
            else {
                int whitePiecesLost = originalBoardCopy.getWhitePieces().size() - boardState.getWhitePieces().size();
                int blackPiecesLost = originalBoardCopy.getBlackPieces().size() - boardState.getBlackPieces().size();
                whiteValue = whitePiecesLost*(-10);
                blackValue = blackPiecesLost*(-5);
                /*
                 * Checking if any white pieces has made it outside of the middle area
                 * and gives a small bonus to white if so.
                 */
                for (int i = 0; i < boardState.getWhitePieces().size(); i++) {
                    if (whiteAtOuterSquare(boardState.getWhitePieces().get(i))) {
                        whiteValue++;
                    }
                }
                /*
                 * Checking if any black pieces has made it into the middle area
                 * and gives a small bonus to black if so.
                 */
                for (int i = 0; i < boardState.getBlackPieces().size(); i++) {
                    if (BlackAtInnerSquare(boardState.getBlackPieces().get(i))) {
                        blackValue++;
                    }
                } 
            } 
        return (whiteValue - blackValue);
    }

    private boolean BlackAtInnerSquare(Coordinates c) {
        int midMin = 2;
        int midMax = 6;
        int x = c.getNumX();
        int y = c.getAlphaY();
        if ((midMin < x && x < midMax) && (midMin < y && y < midMax))
            return true;
        else
            return false;
    }

    private boolean whiteAtOuterSquare(Coordinates c) {
        int midMin = 2;
        int midMax = 6;
        int x = c.getNumX();
        int y = c.getAlphaY();
        if ((midMax < x && x < midMin) && (midMax < y && y < midMin))
            return true;
        else
            return false;
    }
}
