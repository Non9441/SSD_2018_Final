package gameLogic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BackwardSquare {
	private Set<Integer> backward = new HashSet<Integer>();
	private Random rn = new Random();
	
	private int numberOfPath = 62;
	private int maxBack = 4;
	private int minBack = 2;
	
	public BackwardSquare(Board board) {
		
		int numOfBack = rn.nextInt(maxBack - minBack + 1) + minBack;
		System.out.println("Number of backward : "+numOfBack);
		for(int i = 0; i < numOfBack;i++) {
			boolean randomSuccess = false;
			int backPath = 0;
			while(!randomSuccess) {
				backPath = rn.nextInt(numberOfPath -1 ) + 1;
				if(!board.isSpecialPath(backPath)) {
					randomSuccess = true;
				}

			}
			backward.add(backPath);
			board.addSpecialPath(backPath);
			System.out.println("Backward path at "+backPath);
		}
	}
	
	public boolean isOnBackward(Board board,Piece piece) {
		int pos = board.getPiecePosition(piece);
		return backward.contains(pos);
	}
		
	public void moveBack(Board board,Piece piece,int back) {
		back *= -1;
		board.movePiece(piece, back);
		System.out.println(back);
	}
}
