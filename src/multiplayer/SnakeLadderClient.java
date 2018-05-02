package multiplayer;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

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

public class SnakeLadderClient {

	private Client client;
	
	public SnakeLadderClient() throws IOException {
		client = new Client();
		
		client.getKryo().register(Player.class);
		client.getKryo().register(GameData.class);
		client.getKryo().register(PlayerData.class);
		

		client.getKryo().register(Board.class);
		client.getKryo().register(Piece.class);
		client.getKryo().register(Square.class);
		client.getKryo().register(Die.class);
		client.getKryo().register(BackwardSquare.class);
		client.getKryo().register(FreezeSquare.class);
		client.getKryo().register(Snake.class);
		client.getKryo().register(Ladder.class);
		client.getKryo().register(Game.class);
		
		client.start();
		client.connect(500000, "127.0.0.1", 50000);
	}
	
	public static void main(String[] args) {
		try {
			SnakeLadderClient snc = new SnakeLadderClient();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


