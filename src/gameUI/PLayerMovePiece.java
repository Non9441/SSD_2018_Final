package gameUI;

import gameLogic.Game;
import gameLogic.Player;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;

public class PLayerMovePiece extends Task<Integer> {

	private Game game;
	private Player player;
	private ImageView playerImage;

	public PLayerMovePiece(Game game, Player player, ImageView playerImage) {
		this.game = game;
		this.player = player;
		this.playerImage = playerImage;
	}


	public void moveUp() {
		playerImage.setTranslateY(playerImage.getTranslateY() - 60);
	}

	public void moveLeft() {
		playerImage.setTranslateX(playerImage.getTranslateX() - 60);
	}

	public void moveRight() {
		playerImage.setTranslateX(playerImage.getTranslateX() + 60);
	}

	@Override
	protected Integer call() throws InterruptedException {
		int position = game.getPlayerPosition(player) ;
		System.out.println("Player on: " + position);
		if (position % 20 == 0 || position % 20 == 10) {
			moveUp();
		} else if (position % 20 <= 10) {
			moveRight();
		} else if (position % 20 >= 10) {
			moveLeft();
		}
		Thread.sleep(20);
		return null;
	}



}
