package gameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	private Player[] players;
	private Die die;
	private Board board;
	private Ladder ladder;
	private Snake snake;
	private BackwardSquare backwards;
	private FreezeSquare freezes;
	
	private List<Integer> walkHistories;
	private Scanner sc = new Scanner(System.in);
	private int currentPlayerIndex;
	private int numPlayer;
	private boolean ended;
	private boolean isReplay = false;

	public Game(int numPlayer) {
		this.numPlayer = numPlayer;
		players = new Player[numPlayer];
		die = new Die();
		board = new Board();
		ladder = new Ladder(board);
		snake = new Snake(board);
		backwards = new BackwardSquare(board);
		freezes = new FreezeSquare(board);
		walkHistories = new ArrayList<>();

		for (int i = 0; i < numPlayer; i++) {
			players[i] = new Player("Player" + (i + 1));
			board.addPiece(players[i].getPiece(), 0);
		}

		ended = false;
	}
	
	public List<Integer> getHistories() {
		return walkHistories;
	}

	public boolean isEnded() {
		return ended;
	}

	public void end() {
		ended = true;
		for(Integer step : walkHistories) {
			System.out.print(step+",");
		}
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}
	
	public void resetGame() {
		currentPlayerIndex = 0;
		ended = false;
		board.resetBoard(players);
		for(Player p:players) {
			p.setBackStatus(false);
			p.setCanPlay(true);
		}
	}
	
	public void replay() {
		resetGame();
		isReplay = true;
		for(Integer step : walkHistories) {
			currentPlayerMovePiece(step);
			switchPlayer();
		}
		isReplay = false;
	}

	public void switchPlayer() {
		currentPlayerIndex += 1;
		if (currentPlayerIndex >= numPlayer) {
			currentPlayerIndex = 0;
		}
		if (!currentPlayer().isCanPlay()) {
			currentPlayer().setCanPlay(true);
			switchPlayer();
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
	
	public int getPlayerPosition(Player player) {
		return board.getPiecePosition(player.getPiece());
	}
	
	public Player getPlayer(int num) {
		return players[num];
	}
	
	public String currentPlayerMovePiece(int steps) {
		Player currentPlayer = currentPlayer();
		if(!isReplay) {
			walkHistories.add(steps);
		}
		if (currentPlayer.isBackStatus()) {
			backwards.moveBack(board, currentPlayer.getPiece(), steps);
			currentPlayer.setBackStatus(false);
		} else {
			currentPlayer.movePiece(board, steps);
		}

		String status = "normal";
		if (ladder.isOnLadder(board, currentPlayer.getPiece())) {
			ladder.moveUp(board, currentPlayer.getPiece());
			status = "Ladder";
		}
		// Found snake
		else if (snake.isOnSnake(board, currentPlayer.getPiece())) {
			snake.moveDown(board, currentPlayer.getPiece());
			status = "Snake";
		}
		// found freeze
		else if (freezes.isOnFreeze(board, currentPlayer.getPiece())) {
			currentPlayer.setCanPlay(false);
			status = "Freeze";
		} 
		
		else if (backwards.isOnBackward(board, currentPlayer.getPiece())) {
			status = "Backward";
			currentPlayer.setBackStatus(true);
			return status;
		}

		else if (board.pieceIsAtGoal(currentPlayer.getPiece())) {
			status = "Goal";
			end();
		}

//		for (Player p : players) {
//			System.out.println(p.getName() + " at " + board.getPiecePosition(p.getPiece()));
//		}
		System.out.println(currentPlayer.getName()+" move to "+board.getPiecePosition(currentPlayer.getPiece()));
//		switchPlayer();
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
//			System.out.println("----------------------------------------");
//			System.out.println("Current Player is " + currentPlayer.getName());
//
			System.out.println("Please hit enter to roll a die.");
			sc.nextLine();
			int face = currentPlayerRollDie();
//			System.out.println("The die is rolled! Face = " + face);
//			System.out.println("The piece is at " + board.getPiecePosition(currentPlayer.getPiece()));
			currentPlayerMovePiece(face);
//			System.out.println("The piece is moved to " + board.getPiecePosition(currentPlayer.getPiece()));
			switchPlayer();
		}
		replay();
	}
	
	public int getNumPlayer() {
		return numPlayer;
	}

	public static void main(String[] args) {
		Game game = new Game(4);
		game.start();
	}
}
