package multiplayer;

import gameLogic.Player;

public class GameData {
	private Player currentPlayer;
	private String status;
	
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public String getStatus() {
		return status;
	}

}
