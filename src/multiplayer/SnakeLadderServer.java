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

public class SnakeLadderServer extends Game{

	private Server server;
	private Map<Connection,Player> connections = new HashMap<Connection,Player>();
	
	public SnakeLadderServer() throws IOException {
		super(2);
		server = new Server();
		
		server.getKryo().register(Player.class);
		server.getKryo().register(GameData.class);
		server.getKryo().register(PlayerData.class);
		
		server.getKryo().register(Board.class);
		server.getKryo().register(Piece.class);
		server.getKryo().register(Square.class);
		server.getKryo().register(Die.class);
		server.getKryo().register(BackwardSquare.class);
		server.getKryo().register(FreezeSquare.class);
		server.getKryo().register(Snake.class);
		server.getKryo().register(Ladder.class);
		server.getKryo().register(Game.class);


		
		server.addListener(new ServerListener());
		server.start();
		server.bind(50000);
		System.out.println("Snake Ladder Server started");
	}
	
	@Override
	public void start() {
		System.out.println("Running...");
	}
	
	public void onRolled(Connection c,int face) {
		String status = currentPlayerMovePiece(face);
		Player player = currentPlayer();
		GameData gameData = new GameData(player,status);
		c.sendTCP(gameData);
	}
		
	class ServerListener extends Listener {
		@Override
		public void connected(Connection arg0) {
			super.connected(arg0);
			int number = connections.size();
			String status = "Game Start";
			Player curPlayer = getPlayer(number);
			
			connections.put(arg0, curPlayer);
			
			arg0.sendTCP(new GameData(curPlayer, status));
			System.out.println(curPlayer.getName()+" connected.");
		}
		
		@Override
		public void disconnected(Connection arg0) {
			super.disconnected(arg0);
			Player player = connections.get(arg0);
			connections.remove(arg0);
			System.out.println(player.getName()+" disconnected.");
		}
		
		@Override
		public void received(Connection arg0, Object arg1) {
			super.received(arg0, arg1);
			PlayerData data = (PlayerData) arg1;
			int face = data.getFace();
			onRolled(arg0,face);
			System.out.println("Server received data.");
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
