package gameLogic;

public class Player {

	private String name;
	private Piece piece;
	private boolean canPlay = true;;
	
	public Player(String name) {
		this.name = name;
		piece = new Piece();
	}
	
	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}
	
	public String getName() {
		return name;
	}
	
	public void movePiece(Board board, int steps) {
		board.movePiece(piece, steps);
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}
	
	public boolean isCanPlay() {
		return canPlay;
	}
}
