package gameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FreezeSquare {
	
	private List<Integer> freeze = new ArrayList<>();
//	private Random rn = new Random();
//	
//	private int numberOfPath = 62;
//	private int maxFreeze = 4;
//	private int minFreeze = 2;
	
	public FreezeSquare(Board board) {
		
//		int numOfFreeze = rn.nextInt(maxFreeze - minFreeze + 1) + minFreeze;
//		System.out.println("Number of freeze : "+numOfFreeze);
//		for(int i = 0; i < numOfFreeze;i++) {
//			boolean randomSuccess = false;
//			int freezePath = 0;
//			while(!randomSuccess) {
//				freezePath = rn.nextInt(numberOfPath - 1) + 1;
//				if(!board.isSpecialPath(freezePath)) {
//					randomSuccess = true;
//				}
//
//			}
//			freeze.add(freezePath);
//			board.addSpecialPath(freezePath);
//			System.out.println("Freeze path at "+freezePath);
//		}
		
		freeze.add(7);
		freeze.add(31);
		freeze.add(96);
	}
	
	public boolean isOnFreeze(Board board,Piece piece) {
		int pos = board.getPiecePosition(piece);
		for(int freezePath : freeze) {
			if(pos == freezePath) {
				return true;
			}
		}
		return false;
	}
}
