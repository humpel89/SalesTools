package model_Hnefatafl;

import exceptions.NoMoveSetException;

public class MoveValue {
	private PlayerMove move;
	private int value;

	public MoveValue(int value){
		this.value = value;
	}
	public int value(){
		return value;
	}
	public void setMove(PlayerMove move){
		this.move = move;
	}
	public PlayerMove getMove() throws NoMoveSetException{
		if (move == null){
			throw new NoMoveSetException("No move set for the specific moveValue");
		}
		return move;
	}
	
}
