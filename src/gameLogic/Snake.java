package gameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Snake {

	private Map<Integer, Integer> snake = new HashMap<>();
	private Random rn = new Random();
	
	private int numberOfPath = 62;
	private int maxSnake = 5;
	private int minSnake = 3;
	public Snake() {
		
		int numOfSnake = rn.nextInt(maxSnake - minSnake + 1) + minSnake;
		System.out.println("Number of snake : "+numOfSnake);
		for(int i = 0; i < numOfSnake;i++) {
			int tailOfSnake = rn.nextInt(numberOfPath);
			int headOfSnake = rn.nextInt(numberOfPath - tailOfSnake) + tailOfSnake + 1;
			snake.put(headOfSnake, tailOfSnake);
			System.out.println("Snake at "+headOfSnake+","+tailOfSnake);
		}

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
