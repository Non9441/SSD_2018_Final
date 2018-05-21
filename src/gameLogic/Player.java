package gameLogic;

public class Player {

	private String name;
	private Piece piece;
	private boolean canPlay = true;;
	private boolean isBackStatus = false;
	
	public Player() {

	}
	public Player(String name) {
		this.name = name;
		piece = new Piece();
	}
	
	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}
	
	public void setName(String name) {
		this.name = name;
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
	public void setBackStatus(boolean isBackStatus) {
		this.isBackStatus = isBackStatus;
	}
	public boolean isBackStatus() {
		return isBackStatus;
	}
}
