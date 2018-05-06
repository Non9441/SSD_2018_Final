package gameLogic;

import gameLogic.Player;

public class GameData {
	private Player currentPlayer;
	private String status;
	private String moveDetail;
	private int curPos;
	private int newPos;

	public GameData() {

	}

	public void setMoveDetail(String moveDetail) {
		this.moveDetail = moveDetail;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public String getMoveDetail() {
		return moveDetail;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public String getStatus() {
		return status;
	}

	public void setCurPos(int curPos) {
		this.curPos = curPos;
	}

	public int getCurPos() {
		return curPos;
	}

	public void setNewPos(int newPos) {
		this.newPos = newPos;
	}

	public int getNewPos() {
		return newPos;
	}

}
