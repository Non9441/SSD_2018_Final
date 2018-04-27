package gameLogic;

import java.util.HashMap;
import java.util.Map;

public class Snake {

	private Map<Integer, Integer> snake = new HashMap<>();

	public Snake() {
		snake.put(61, 10);
		snake.put(50, 45);
		snake.put(31, 15);
		snake.put(55, 1);
		snake.put(10, 8);

	}

	public boolean isOnSnake(Board board, Piece piece) {
		int pos = board.getPiecePosition(piece);
		return pos == 61 || pos == 50 || pos == 31 || pos == 55 || pos == 10;
	}

	public int getNewPosition(Board board, Piece piece) {
		if (isOnSnake(board, piece))
			return snake.get(board.getPiecePosition(piece));
		return board.getPiecePosition(piece);
	}

	public void moveDown(Board board, Piece piece) {
		board.movePiece(piece, getNewPosition(board, piece) - board.getPiecePosition(piece));
	}
}
