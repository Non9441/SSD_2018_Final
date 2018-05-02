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

	public void moveUp() throws InterruptedException {
		for (int i = 0; i < 60; i++) {
			playerImage.setTranslateY(playerImage.getTranslateY() + 1);
			Thread.sleep(10);
		}
	}

	public void moveLeft() throws InterruptedException {
		for (int i = 0; i < 60; i++) {
			playerImage.setTranslateX(playerImage.getTranslateX() + 1);
			Thread.sleep(10);
		}
	}

	public void moveRight() throws InterruptedException {
		for (int i = 0; i < 60; i++) {
			playerImage.setTranslateX(playerImage.getTranslateX() - 1);
			Thread.sleep(10);
		}
	}

	@Override
	protected Integer call() throws Exception {
		int position = game.getPlayerPosition(player) + 1;
		System.out.println(game.currentPlayerName() + " on:" + position);
		if (position % 20 < 10) {
			moveLeft();
		} else if (position % 20 > 10) {
			moveRight();
		} else if (position % 20 == 10 || game.currentPlayerPosition() % 20 == 0) {
			moveUp();
		}
		return null;
	}

}
