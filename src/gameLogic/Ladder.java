package gameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ladder {

	private Map<Integer, Integer> ladder = new HashMap<>();
	private Random rn = new Random();
	
	private int numberOfPath = 62;
	private int maxLadder = 5;
	private int minLadder = 3;

	public Ladder() {
		
		int numOfLadder = rn.nextInt(maxLadder - minLadder + 1) + minLadder;
		System.out.println("Number of ladder : "+numOfLadder);
		for(int i = 0; i < numOfLadder;i++) {
			int headOfLadder = rn.nextInt(numberOfPath);
			int tailOfLadder = rn.nextInt(headOfLadder);
			ladder.put(tailOfLadder, headOfLadder);
			System.out.println("Ladder at "+tailOfLadder+","+headOfLadder);
		}
	}

	public boolean isOnLadder(Board board, Piece piece) {
		int pos = board.getPiecePosition(piece);
		return pos == 5 || pos == 13 || pos == 25 || pos == 40 || pos == 43;
	}

	public int getNewPosition(Board board, Piece piece) {
		if (isOnLadder(board, piece))
			return ladder.get(board.getPiecePosition(piece));
		return board.getPiecePosition(piece);
	}

	public void moveUp(Board board, Piece piece) {
		board.movePiece(piece, getNewPosition(board, piece) - board.getPiecePosition(piece));
	}
}
