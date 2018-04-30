package gameLogic;

import java.util.Scanner;

public class Game {

	private Player[] players;
	private Die die;
	private Board board;
	private Ladder ladder;
	private Snake snake;
	private BackwardSquare backwards;
	private FreezeSquare freezes;

	private Scanner sc = new Scanner(System.in);
	private int currentPlayerIndex;
	private int numPlayer;
	private boolean ended;

	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		players = new Player[numPlayer];
		die = new Die();
		board = new Board();
		ladder = new Ladder(board);
		snake = new Snake(board);
		backwards = new BackwardSquare(board);
		freezes = new FreezeSquare(board);
		
		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player("Player" + (i+1));
			board.addPiece(players[i].getPiece(), 0);
		}

		ended = false;
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
		currentPlayerIndex+=1;
		if(currentPlayerIndex >= numPlayer) {
			currentPlayerIndex = 0;
		}
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

	public String currentPlayerMovePiece(int steps) {
		Player currentPlayer = currentPlayer();
		currentPlayer.movePiece(board, steps);
		String status = currentPlayerName()+" move "+steps+" steps.";
		
		if (ladder.isOnLadder(board, currentPlayer.getPiece())) {
			ladder.moveUp(board, currentPlayer.getPiece());
			System.out.println(currentPlayer.getName() + " found Ladder!!");
			System.out.println(
					currentPlayer.getName() + " move to " + ladder.getNewPosition(board, currentPlayer.getPiece()));
			status = currentPlayerName() + " hit the snake block.";
		}
		// Found snake
		if (snake.isOnSnake(board, currentPlayer.getPiece())) {
			snake.moveDown(board, currentPlayer.getPiece());
			System.out.println(currentPlayer.getName() + " found Snake!!");
			System.out.println(
					currentPlayer.getName() + " move to " + snake.getNewPosition(board, currentPlayer.getPiece()));
			status = currentPlayerName() + " hit the snake block.";
		}
		// found freeze
		if (freezes.isOnFreeze(board, currentPlayer.getPiece())) {
			System.out.println(currentPlayer.getName() + " Freeze pass 1 turn");
			currentPlayer.setCanPlay(false);
			status = currentPlayerName() + " hit the freeze block.";
		}
		if (backwards.isOnBackward(board, currentPlayer.getPiece())) {
			System.out.println(currentPlayer.getName() + " found Backward!!");
			System.out.println("Please hit enter to roll a die.");
			sc.nextLine();
			int face = currentPlayer.roll(die);
			backwards.moveBack(board, currentPlayer.getPiece(), face);
			System.out.println("Die face " + face);
			System.out
					.println(currentPlayer.getName() + " move to " + board.getPiecePosition(currentPlayer.getPiece()));
			status = currentPlayerName() + " hit the backwards block.";
		}

		if (board.pieceIsAtGoal(currentPlayer.getPiece())) {
			System.out.println("===============================================");
			System.out.println(currentPlayer.getName() + " Win!!");
			status = currentPlayerName()+" reach the goal!!!";
			end();
		}
		
		return status;
	}

	public boolean currentPlayerWin() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

	public void start() {
		currentPlayerIndex = 0;

		while (!ended) {
			Player currentPlayer = currentPlayer();
			if (!currentPlayer.isCanPlay()) {
				currentPlayer.setCanPlay(true);
				System.out.println(currentPlayer.getName() + " skip");
				currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
				continue;
			}
			System.out.println("----------------------------------------");
			System.out.println("Current Player is " + currentPlayer.getName());

			System.out.println("Please hit enter to roll a die.");
			sc.nextLine();
			int face = currentPlayerRollDie();
			System.out.println("The die is rolled! Face = " + face);
			System.out.println("The piece is at " + board.getPiecePosition(currentPlayer.getPiece()));
			currentPlayerMovePiece(face);
			System.out.println("The piece is moved to " + board.getPiecePosition(currentPlayer.getPiece()));
			switchPlayer();
		}
	}

	public static void main(String[] args) {
		Game game = new Game(4);
		game.start();
	}
}
