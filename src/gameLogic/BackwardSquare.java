package gameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BackwardSquare {
	private Map<Integer,Integer> backward = new HashMap<Integer,Integer>();
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
			backward.put(backPath,0);
			board.addSpecialPath(backPath);
			System.out.println("Backward path at "+backPath);
		}
	}
	
	public boolean isOnBackward(Board board,Piece piece) {
		int pos = board.getPiecePosition(piece);
		return backward.get(pos) != null;
	}
		
	public void moveBack(Board board,Piece piece,int back) {
		back *= -1;
		board.movePiece(piece, back);
		System.out.println(back);
	}
}
