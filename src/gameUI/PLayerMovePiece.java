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

	@Override
	protected Integer call() throws Exception {
		int position = game.getPlayerPosition(player) + 1;
		System.out.println(game.currentPlayerName() + " on:" + position);
		if (position % 20 < 10) {
			System.out.println(game.currentPlayerName() + " on:" + position);
			playerImage.setTranslateX(playerImage.getTranslateX() + 60);
			System.out.println(playerImage.getTranslateX());
		} else if (position % 20 > 10) {
			System.out.println(game.currentPlayerName() + " on:" + position);
			playerImage.setTranslateX(playerImage.getTranslateX() - 60);
			System.out.println(playerImage.getTranslateX());
		} else if (position % 20 == 10 || game.currentPlayerPosition() % 20 == 0) {
			System.out.println(game.currentPlayerName() + " on:" + position);
			playerImage.setTranslateY(playerImage.getTranslateY() + 60);
			System.out.println(playerImage.getTranslateX());
		}
		Thread.sleep(10);
		return null;
	}

}
