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
	public Snake(Board board) {
		
		int numOfSnake = rn.nextInt(maxSnake - minSnake + 1) + minSnake;
		System.out.println("Number of snake : "+numOfSnake);
		for(int i = 0; i < numOfSnake;i++) {
			boolean randomSuccess = false;
			int tailOfSnake = 0;
			int headOfSnake = 0;
			while(!randomSuccess) {
				tailOfSnake = rn.nextInt(numberOfPath - 1) + 1;
				headOfSnake = rn.nextInt(numberOfPath - tailOfSnake) + tailOfSnake;
				if(!board.isSpecialPath(tailOfSnake) && !board.isSpecialPath(headOfSnake)) {
					randomSuccess = true;
				}
			}
			snake.put(headOfSnake, tailOfSnake);
			board.addSpecialPath(headOfSnake);
			board.addSpecialPath(tailOfSnake);
			System.out.println("Snake at "+headOfSnake+","+tailOfSnake);
		}

	}

	public boolean isOnSnake(Board board, Piece piece) {
		int pos = board.getPiecePosition(piece);
		return snake.get(pos) != null;
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
