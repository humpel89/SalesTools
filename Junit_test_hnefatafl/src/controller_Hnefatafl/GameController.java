package controller_Hnefatafl;

import view_Hnefatafl.UserInterface;
import model_Hnefatafl.GameBoard;
import model_Hnefatafl.PlayerMove;
import model_Hnefatafl.Rules;

public class GameController {

    Algorithm algo = new Algorithm();
    GameBoard board;
    UserInterface ui = new UserInterface();
    Rules rules = new Rules();
    PlayerMove move;
    String humanColor;
    String cpuColor;
    String lastPlayer;

    public void runNewGame() {
        askUserToChooseColor();
        board = new GameBoard();
        nextTurn();
    }
    public void nextTurn(){
        printBoard();
        if(Rules.gameOver(board)){
            ui.printTextln("Game over");
            if (lastPlayer == Rules.playerWhite)
                ui.printTextln("White player won.");
            else
                ui.printTextln("Black player won.");
        }
        else { 
            if(lastPlayer == humanColor) {
                lastPlayer = cpuColor;
                computersTurn();
            }
            else{
                lastPlayer = humanColor;
                humanPlayerTurn();
            }
        }
    } 
    private void computersTurn(){
        move = algo.getBestMove(board);
        board.movePiece(move);
        nextTurn();
    }

    public void askUserToChooseColor(){
        ui.printText("Do you want to play as black or white? \n"
                + "Type 'black', or  'white'");
        //   String input = ui.getUserInput();
        String input = "WHITE"; // TEMP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        System.out.println(input);
        if (input.matches("WHITE")){
            humanColor = Rules.playerWhite;
            cpuColor = Rules.playerBlack;
        }
        else {
            if (input.matches("BLACK")){
                humanColor = Rules.playerBlack;
                cpuColor = Rules.playerWhite;
            }
            else askUserToChooseColor();
        }
    }

    public void humanPlayerTurn(){
        String input = ui.getUserInput();
        if(input.matches("MENU") || input.matches("HELP")){
            // TODO Skriv ut alternativ vad som kan skrivas in.
        }
        else {
            if(input.matches("QUIT") || input.matches("EXIT"))
                System.exit(0);
            else {
                if(input.matches("NEWGAME"))
                    runNewGame();
                else {
                    if (inputIsCoordinates(input)){
                        PlayerMove move = new PlayerMove(input);
                        if(rules.validMove(move, board)){
                            board.movePiece(move);
                            nextTurn();
                        }
                        else{
                            ui.printText("No matching action, try again\n");
                            humanPlayerTurn();
                        }
                    }
                    else{
                        ui.printText("No matching action, try again\n");
                        humanPlayerTurn();
                    }
                }
            }
        }
    }
    public boolean inputIsCoordinates(String input){
        int match = 0;
        for (int i = 0; i < 9; i++){
            if (input.charAt(0) == GameBoard.ALPHA_AXIS[i])
                match++;
            if ((Integer.parseInt(input.substring(1, 2))-1) == GameBoard.NUM_AXIS[i])
                match++;
            if (input.charAt(2) == GameBoard.ALPHA_AXIS[i])
                match++;   
            if ((Integer.parseInt(input.substring(3, 4))-1) == GameBoard.NUM_AXIS[i])
                match++;
        }
        if (match == 4)
            return true;
        else 
            return false;
    }

    private void printBoard(){
        ui.printText(board.toString());
    }
}