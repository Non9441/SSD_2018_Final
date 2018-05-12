package multiplayer;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import gameLogic.BackwardSquare;
import gameLogic.Board;
import gameLogic.Die;
import gameLogic.FreezeSquare;
import gameLogic.Ladder;
import gameLogic.Piece;
import gameLogic.Player;
import gameLogic.Snake;
import gameLogic.Square;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeLadderClient {

	private Client client;
	private Player player;
	private Player currentPlayer;
	private String status;
	private String moveDetail;

	private int curPos;
	private int newPos;

//	private SnakeAndLadderUI scu;
	multiplayer.SnakeAndLadderController scu;

	public SnakeLadderClient(Stage stage) throws IOException {
		client = new Client();

		client.getKryo().register(Board.class);
		client.getKryo().register(Piece.class);
		client.getKryo().register(Square.class);
		client.getKryo().register(Die.class);
		client.getKryo().register(BackwardSquare.class);
		client.getKryo().register(FreezeSquare.class);
		client.getKryo().register(Snake.class);
		client.getKryo().register(Ladder.class);
		client.getKryo().register(Player.class);
		client.getKryo().register(GameData.class);

		client.addListener(new clientListener());
		
		try {
			
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			scu = chooseGameLoader.getController();
			scu.setSalClient(this);
			System.out.println(scu);
			
			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}

		client.start();
		// client.connect(50000, "35.198.204.2", 50000);
		client.connect(50000, "127.0.0.1", 50000);
	}

	public Player getPlayer() {
		return player;
	}

	public String getStatus() {
		return status;
	}

	public Client getClient() {
		return client;
	}

	public void sendRollResult(int face) {
		client.sendTCP(face);
	}

	class clientListener extends Listener {
		@Override
		public void connected(Connection arg0) {
			super.connected(arg0);
		}

		@Override
		public void disconnected(Connection arg0) {
			super.disconnected(arg0);
		}

		@Override
		public void received(Connection arg0, Object arg1) {
			super.received(arg0, arg1);
			if (arg1 instanceof GameData) {
				GameData data = (GameData) arg1;
				if (data.getStatus().equals("Waiting...")) {
					player = data.getCurrentPlayer();
					status = data.getStatus();
					moveDetail = data.getMoveDetail();
					curPos = data.getCurPos();
					newPos = data.getNewPos();

					scu.setPlayer(player);
					scu.setStatusAndMoveDetail(player.getName(), status, moveDetail, curPos, newPos);
				} else {
					currentPlayer = data.getCurrentPlayer();
					status = (String) data.getStatus();
					moveDetail = data.getMoveDetail();
					curPos = data.getCurPos();
					newPos = data.getNewPos();

					scu.setCurrentPlayer(currentPlayer);
					scu.setStatusAndMoveDetail(currentPlayer.getName(), status, moveDetail, curPos, newPos);
				}
			} else if (arg1 instanceof String) {
				scu.setStatusAndMoveDetail("Connection Fail", "Room is full", "", 0, 0);
			}
		}
	}

	public static void main(String[] args) {
//		try {
//			SnakeLadderClient snc = new SnakeLadderClient();
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Cant connect");
//		}
	}
}
