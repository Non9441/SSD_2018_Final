package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int SIZE = 64;

	private Square[] squares;
	private List<Integer> specialPath = new ArrayList<Integer>();
	
	public Board() {
		squares = new Square[SIZE];
		for (int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
		squares[squares.length - 1].setGoal(true);
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void movePiece(Piece piece, int steps) {
		int pos = getPiecePosition(piece);
		squares[pos].removePiece(piece);
		int newPos = pos + steps;
		if (newPos >= squares.length) {
			newPos = squares.length - 1;
		}
		if(newPos < 0 ) {
			newPos = 0;
		}
		addPiece(piece, newPos);
	}

	public int getPiecePosition(Piece piece) {
		for (Square s : squares) {
			if (s.hasPiece(piece)) {
				return s.getNumber();
			}
		}
		return -1;
	}

	public boolean pieceIsAtGoal(Piece piece) {
		return squares[getPiecePosition(piece)].isGoal();
	}
	
	public void addSpecialPath(int path) {
		specialPath.add(path);
	}
	
	public boolean isSpecialPath(int path) {
		for(Integer spPath : specialPath) {
			if(path == spPath) {
				return true;
			}
		}
		return false;
	}

}
