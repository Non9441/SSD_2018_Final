package multiplayer;

import gameLogic.Player;

public class GameData {
	private Player currentPlayer;
	private String playerWhoRollName;
	private String status;
	private String moveDetail;
	private int curPos;
	private int newPos;
	private int faceFromServer;

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
	public void setPlayerWhoRollName(String playerWhoRollName) {
		this.playerWhoRollName = playerWhoRollName;
	}
	public String getPlayerWhoRollName() {
		return playerWhoRollName;
	}
	public void setFaceFromServer(int faceFromServer) {
		this.faceFromServer = faceFromServer;
	}
	public int getFaceFromServer() {
		return faceFromServer;
	}

}
