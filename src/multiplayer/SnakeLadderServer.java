package multiplayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import gameLogic.Game;
import gameLogic.Player;

public class SnakeLadderServer extends Game{

	private Server server;
	private Map<Connection,Player> connections = new HashMap<Connection,Player>();
	
	public SnakeLadderServer() throws IOException {
		super(2);
		server = new Server();
		
		server.addListener(new ServerListener());
		server.start();
		server.bind(50000);
		System.out.println("Snake Ladder Server started");
	}
	
	@Override
	public void start() {
		System.out.println("Running...");
	}
	
	class ServerListener extends Listener {
		@Override
		public void connected(Connection arg0) {
			super.connected(arg0);
			int number = connections.size()+1;
			connections.put(arg0, new Player("Player"+number));
			System.out.println("Client connected.");
		}
		
		@Override
		public void disconnected(Connection arg0) {
			super.disconnected(arg0);
			connections.remove(arg0);
			System.out.println("Client disconnected.");
		}
		
		@Override
		public void received(Connection arg0, Object arg1) {
			super.received(arg0, arg1);
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
