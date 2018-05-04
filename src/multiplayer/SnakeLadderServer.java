package multiplayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

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

public class SnakeLadderServer {

	private Server server;
	private Game game;
	private static int numPlayer = 2;
	private Map<Connection, Player> connections = new HashMap<Connection, Player>();

	public SnakeLadderServer() throws IOException {
		server = new Server();
		game = new Game(numPlayer);

		server.getKryo().register(Board.class);
		server.getKryo().register(Piece.class);
		server.getKryo().register(Square.class);
		server.getKryo().register(Die.class);
		server.getKryo().register(BackwardSquare.class);
		server.getKryo().register(FreezeSquare.class);
		server.getKryo().register(Snake.class);
		server.getKryo().register(Ladder.class);

		server.getKryo().register(Player.class);
		server.getKryo().register(GameData.class);
		server.getKryo().register(RollData.class);

		server.addListener(new ServerListener());
		server.start();
		server.bind(50000);
		System.out.println("Snake Ladder Server started");
	}

	public void onRolled(int face) {
		System.out.println(game.currentPlayer() + " move " + face);
		int fromPosi = game.currentPlayerPosition();
		String status = game.currentPlayerMovePiece(face);
		String newStatus = game.currentPlayer().getName()+" hit on "+status+" path";
		int afterPosi = game.currentPlayerPosition();
		String moveDetail = game.currentPlayer().getName() + " " + fromPosi + " to " + afterPosi;

		game.switchPlayer();
		Player currentPlayer = game.currentPlayer();
		GameData gameData = new GameData();
		gameData.setCurrentPlayer(currentPlayer);
		gameData.setStatus(newStatus);
		gameData.setMoveDetail(moveDetail);
		System.out.println(currentPlayer.getName() + " turn");

		for (Connection c : connections.keySet()) {
			c.sendTCP(gameData);
		}
	}

	public void setFirstPlay() {
		Player player = game.currentPlayer();
		String status = "Playing...";
		GameData gameData = new GameData();
		gameData.setCurrentPlayer(player);
		gameData.setStatus(status);
		gameData.setMoveDetail("Let's start");
		for (Connection c : connections.keySet()) {
			c.sendTCP(gameData);
		}
	}

	class ServerListener extends Listener {
		@Override
		public void connected(Connection arg0) {
			super.connected(arg0);
			int number = connections.size();
			String status = "Waiting...";
			Player curPlayer = game.getPlayer(number);

			connections.put(arg0, curPlayer);

			GameData data = new GameData();
			data.setCurrentPlayer(curPlayer);
			data.setStatus(status);
			data.setMoveDetail("Connected!!!");
			arg0.sendTCP(data);
			System.out.println(curPlayer.getName() + " connected.");

			if (connections.size() == numPlayer) {
				setFirstPlay();
			}
		}

		@Override
		public void disconnected(Connection arg0) {
			super.disconnected(arg0);
			Player player = connections.get(arg0);
			connections.remove(arg0);
			System.out.println(player.getName() + " disconnected.");
		}

		@Override
		public void received(Connection arg0, Object arg1) {
			super.received(arg0, arg1);
			if (arg1 instanceof Integer) {
				int face = (Integer) arg1;
				onRolled(face);
				System.out.println("Server received data.");
			}
		}
	}

	public static void main(String[] args) {
		try {
			SnakeLadderServer server = new SnakeLadderServer();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
