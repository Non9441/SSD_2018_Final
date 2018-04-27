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

	public Ladder(Board board) {
		
		int numOfLadder = rn.nextInt(maxLadder - minLadder + 1) + minLadder;
		System.out.println("Number of ladder : "+numOfLadder);
		for(int i = 0; i < numOfLadder;i++) {
			int tailOfLadder = 0;
			int headOfLadder = 0;
			boolean randomSuccess = false;
			while(!randomSuccess) {
				headOfLadder = rn.nextInt(numberOfPath - 2) + 2;
				tailOfLadder = rn.nextInt(headOfLadder - 1) + 1;
				if(!board.isSpecialPath(tailOfLadder) && !board.isSpecialPath(headOfLadder)) {
					randomSuccess = true;
				}
			}
			ladder.put(tailOfLadder, headOfLadder);
			board.addSpecialPath(headOfLadder);
			board.addSpecialPath(tailOfLadder);
			System.out.println("Ladder at "+tailOfLadder+","+headOfLadder);
		}
	}

	public boolean isOnLadder(Board board, Piece piece) {
		int pos = board.getPiecePosition(piece);
		return ladder.get(pos) != null;
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
