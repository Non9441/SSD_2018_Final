package gameLogic;

import java.util.HashMap;
import java.util.Map;

public class Ladder {

	private Map<Integer, Integer> ladder = new HashMap<>();

	public Ladder() {
		ladder.put(5, 11);
		ladder.put(13, 30);
		ladder.put(25, 32);
		ladder.put(40, 62);
		ladder.put(43, 45);
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
