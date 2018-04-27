package gameLogic;

public class Game {

	private Player[] players;
	private Die die;
	private Board board;
	private Ladder ladder;
	private Snake snake;
	private BackwardSquare backwards;
	private FreezeSquare freezes;

	private int currentPlayerIndex;
	private boolean ended;

	public Game() {
		players = new Player[2];
		players[0] = new Player("P1");
		players[1] = new Player("P2");

		die = new Die();
		board = new Board();
		ladder = new Ladder(board);
		snake = new Snake(board);
		backwards = new BackwardSquare(board);
		freezes = new FreezeSquare(board);
		
		ended = false;
		
		board.addPiece(players[0].getPiece(), 0);
		board.addPiece(players[1].getPiece(), 0);

	}

	public boolean isEnded() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int currentPlayerPosition() {
		return board.getPiecePosition(currentPlayer().getPiece());
	}

	public int currentPlayerRollDie() {
		return currentPlayer().roll(die);
	}

	public void currentPlayerMovePiece(int steps) {
		currentPlayer().movePiece(board, steps);
	}

	public boolean cuurentPlayerWin() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

//	public void start() {
//		board.addPiece(players[0].getPiece(), 0);
//		board.addPiece(players[1].getPiece(), 0);
//
//		currentPlayerIndex = 0;
//
//		while (!ended) {
//			Player currentPlayer = players[currentPlayerIndex];
//			System.out.println("----------------------------------------");
//			System.out.println("Current Player is " + currentPlayer.getName());
//
//			int face = currentPlayer.roll(die);
//
//			System.out.println("The die is rolled! Face = " + face);
//			currentPlayer.movePiece(board, face);
//			System.out.println("The piece is moved to " + board.getPiecePosition(currentPlayer.getPiece()));
//
//			// homework
//			// Found ladder
//			if (ladder.isOnLadder(board, currentPlayer.getPiece())) {
//				ladder.moveUp(board, currentPlayer.getPiece());
//				System.out.println(currentPlayer.getName() + " found Ladder!!");
//				System.out.println(
//						currentPlayer.getName() + " move to " + ladder.getNewPosition(board, currentPlayer.getPiece()));
//			}
//			// Found snake
//			if (snake.isOnSnake(board, currentPlayer.getPiece())) {
//				snake.moveDown(board, currentPlayer.getPiece());
//				System.out.println(currentPlayer.getName() + " found Snake!!");
//				System.out.println(
//						currentPlayer.getName() + " move to " + snake.getNewPosition(board, currentPlayer.getPiece()));
//			}
//
//			if (board.pieceIsAtGoal(currentPlayer.getPiece())) {
//				System.out.println("===============================================");
//				System.out.println(currentPlayer.getName() + " Win!!");
//				ended = true;
//			}
//			currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
//		}
//	}

//	public static void main(String[] args) {
//		Game game = new Game();
//		game.start();
//	}
}
