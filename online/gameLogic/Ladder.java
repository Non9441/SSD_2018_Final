package gameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ladder {

	private Map<Integer, Integer> ladder = new HashMap<>();
//	private Random rn = new Random();
//	
//	private int numberOfPath = 62;
//	private int maxLadder = 5;
//	private int minLadder = 3;

	public Ladder(Board board) {
		
//		int numOfLadder = rn.nextInt(maxLadder - minLadder + 1) + minLadder;
//		System.out.println("Number of ladder : "+numOfLadder);
//		for(int i = 0; i < numOfLadder;i++) {
//			int tailOfLadder = 0;
//			int headOfLadder = 0;
//			boolean randomSuccess = false;
//			while(!randomSuccess) {
//				headOfLadder = rn.nextInt(numberOfPath - 2) + 2;
//				tailOfLadder = rn.nextInt(headOfLadder - 1) + 1;
//				if(!board.isSpecialPath(tailOfLadder) && !board.isSpecialPath(headOfLadder)) {
//					randomSuccess = true;
//				}
//			}
//			ladder.put(tailOfLadder, headOfLadder);
//			board.addSpecialPath(headOfLadder);
//			board.addSpecialPath(tailOfLadder);
//			System.out.println("Ladder at "+tailOfLadder+","+headOfLadder);
//		}
		ladder.put(3, 13);
		ladder.put(8, 30);
		ladder.put(18, 37);
		ladder.put(20, 41);
		ladder.put(27, 83);
		ladder.put(50, 66);
		ladder.put(71, 90);
		ladder.put(79, 98);


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
		System.out.println(board.getPiecePosition(piece));
	}
}
