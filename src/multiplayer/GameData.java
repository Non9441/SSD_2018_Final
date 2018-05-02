package multiplayer;

import gameLogic.Player;

public class GameData {
	private Player currentPlayer;
	private String status;
	
	public GameData(Player currentPlayer,String status) {
		this.currentPlayer = currentPlayer;
		this.status = status;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public String getStatus() {
		return status;
	}

}
