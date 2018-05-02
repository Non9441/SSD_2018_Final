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
		// for (int i = 0; i < 60; i++) {
		playerImage.setLayoutY(playerImage.getLayoutY() - 60);
		// playerImage.setTranslateX(playerImage.getTranslateX());
		// playerImage.setTranslateY(playerImage.getTranslateY() + 1);
		// }
	}

	public void moveRight() throws InterruptedException {
		// for (int i = 0; i < 60; i++) {
		playerImage.setLayoutX(playerImage.getLayoutX() + 60);
		// playerImage.setTranslateX(playerImage.getTranslateX() + 1);
		// playerImage.setTranslateY(playerImage.getTranslateY());
		// }
	}

	public void moveLeft() throws InterruptedException {
		// for (int i = 0; i < 60; i++) {
		playerImage.setLayoutX(playerImage.getLayoutX() - 60);
		// playerImage.setTranslateX(playerImage.getTranslateX() - 1);
		// playerImage.setTranslateY(playerImage.getTranslateY());
		// }
	}

	@Override
	protected Integer call() throws Exception {
		int position = game.getPlayerPosition(player) ;
		System.out.println("Player on: " + position);
		if (position % 20 == 0 || position % 20 == 10) {
			System.out.println("Move Up!!");
			moveUp();
		} else if (position % 20 <= 10) {
			moveRight();
		} else if (position % 20 >= 10) {
			moveLeft();
		}
		return null;
	}

}
