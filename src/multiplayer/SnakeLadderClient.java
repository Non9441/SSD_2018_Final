package multiplayer;

import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import gameLogic.BackwardSquare;
import gameLogic.Board;
import gameLogic.Die;
import gameLogic.FreezeSquare;
import gameLogic.Game;
import gameLogic.Ladder;
import gameLogic.Piece;
import gameLogic.Player;
import gameLogic.Snake;
import gameLogic.Square;
import javafx.stage.Stage;

public class SnakeLadderClient {

	private Client client;
	private Player player;
	private Player currentPlayer;

	private static Scanner sc = new Scanner(System.in);

	public SnakeLadderClient() throws IOException {
		client = new Client();

		client.getKryo().register(Board.class);
		client.getKryo().register(Piece.class);
		client.getKryo().register(Square.class);
		client.getKryo().register(Die.class);
		client.getKryo().register(BackwardSquare.class);
		client.getKryo().register(FreezeSquare.class);
		client.getKryo().register(Snake.class);
		client.getKryo().register(Ladder.class);
		client.getKryo().register(Game.class);

		client.getKryo().register(Player.class);
		client.getKryo().register(GameData.class);
		client.getKryo().register(PlayerData.class);

		client.start();
		client.connect(5000, "127.0.0.1", 50000);
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
				if (data.getStatus().equals("Game Start")) {
					player = data.getCurrentPlayer();
					System.out.println(player);
				} else {
					currentPlayer = data.getCurrentPlayer();
					System.out.println(currentPlayer);
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			SnakeLadderClient snc = new SnakeLadderClient();
			new Thread() {
				@Override
				public void run() {
					javafx.application.Application.launch(SnakeClientUI.class);
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
