import static org.junit.Assert.*;
import model_Hnefatafl.GameBoard;
import model_Hnefatafl.PlayerMove;
import model_Hnefatafl.Rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view_Hnefatafl.UserInterface;
import controller_Hnefatafl.Algorithm;
import controller_Hnefatafl.GameController;


public class Junit_test {
	
	GameController gc = new GameController();
	UserInterface ui = new UserInterface();
	GameBoard board = new GameBoard(); 
	PlayerMove move;
	Rules rules = new Rules();
	Algorithm algo = new Algorithm();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void inputIsCoordinatesTest(){
		// checks if the coordinates is valid. seem to work.
		assertTrue("should be true but isn´t", gc.inputIsCoordinates("A3C3"));
	}
	@Test
	public void playerMoveTest(){
		// checks if the move is a square on the board. seem to work
		move = new PlayerMove("A3C3");
		assertTrue("should be a zero but isn´t", move.getStart().getAlphaY()==0);
		assertTrue("should be zero but insn´t", move.getStart().getNumX()==2);
		assertTrue("should be 2 but isn´t", move.getTarget().getAlphaY()==2);
		assertTrue("should be 2 but isn´t", move.getTarget().getNumX()==2);

	}
	@Test
	public void validateMoveTest(){
		move = new PlayerMove("A4D4");
		assertTrue("valid move but says it isn´t",rules.validMove(move, board));
		move = new PlayerMove("D1D5");
		assertFalse("invalid move but says it isn´t", rules.validMove(move, board));
		
		//should not work because it´s an empty square but does anyway... does not work
		move = new PlayerMove("H1H3");
		assertFalse("should not move an empty square!!", rules.validMove(move, board));
	}
	@Test
	public void movePieceTest(){
		// checks the move method, seem to work
		move = new PlayerMove("A4D4");
		board.movePiece(move);
		assertTrue("piece in wrong position", board.getSquare(move.getTarget()) == GameBoard.BLACK_PIECE );
		assertFalse("is black_piece but says it´s white", board.getSquare(move.getTarget())==GameBoard.WHITE_PIECE);
	}
	@Test
	public void getBestMoveTest(){
		// checks if the cpu can make a move and in the same time checks if the spu moves the other players piece
		// seem to work!!
		
		move = new PlayerMove("C5C7");
		board.movePiece(move);
           
		move = algo.getBestMove(board);
        board.movePiece(move);
        
        assertTrue("cpu moved wrong piece", board.getSquare(move.getTarget())==GameBoard.BLACK_PIECE);
        move = new PlayerMove("D5D8");
        board.movePiece(move);
           
        move = algo.getBestMove(board);
        board.movePiece(move);
        
        assertTrue("cpu moved wrong piece", board.getSquare(move.getTarget())==GameBoard.BLACK_PIECE);

	}
	@Test
	public void takeAnotherPieceTest(){
		// checks if the human player can take a piece from the cpu. Seem to work
		move = new PlayerMove("D5D8");
		board.movePiece(move);
		      
		move = algo.getBestMove(board);
	    board.movePiece(move);
	    
	    move = new PlayerMove("F5F8");
		board.movePiece(move);
		
		assertTrue("piece is not taken", board.getSquare(4, 7)==GameBoard.EMPTY_SQUARE);
	}
	@Test
	public void kingInKornerTest(){
		// checks if the game is over if the king is in the corner. Seem to work.
	    move = new PlayerMove("E5A9");
	    board.movePiece(move);
	    assertTrue("king is in corner but not game over",Rules.gameOver(board));
	    
	    

	}
	@Test
	public void canKingTakeItsOnPiecesTest(){
		move = new PlayerMove("D5D4");
	    board.movePiece(move);
	    ui.printText(board.toString());
	    
	    move = new PlayerMove("E5D5");
	    board.movePiece(move);
	    ui.printText(board.toString());
	    
	    // the king takes it´s own pieces, acts like he´s a black player. Does not work
	    assertFalse("king should not take own piece",board.getSquare(2, 4)==GameBoard.EMPTY_SQUARE );
	    
	    
	}
	@Test
	public void canKingBeTakenTest(){
		move = new PlayerMove("A4D4");
	    board.movePiece(move);
	    ui.printText(board.toString());
	   
        move = new PlayerMove("I4F4");
	    board.movePiece(move);
	    ui.printText(board.toString());
	    
	    move = new PlayerMove("D9D6");
	    board.movePiece(move);
	    ui.printText(board.toString());
	    
	    move = new PlayerMove("I6F6");
	    board.movePiece(move);
	    ui.printText(board.toString());
	    
	    // why does not D5 get taken???
	    // don´t think the king can be taken!!
	    move = new PlayerMove("D4D5");
	    board.movePiece(move);
	    move = new PlayerMove("D6E6");
	    board.movePiece(move);
	    move = new PlayerMove("F6F5");
	    board.movePiece(move);
	    move = new PlayerMove("F4E4");
	    board.movePiece(move);
	    ui.printText(board.toString());
	    assertTrue("king should be taken",Rules.gameOver(board));
	}

}
