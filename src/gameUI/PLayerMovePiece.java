package gameUI;

import gameLogic.Player;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;

public class PLayerMovePiece extends Task<Integer> {

	private Player player;
	private int dieFace;
	private ImageView playerImage;

	public PLayerMovePiece(Player player, int dieFace, ImageView playerImage) {
		this.dieFace = dieFace;
		this.playerImage = playerImage;
	}

	@Override
	protected Integer call() throws Exception {
		for (int i = 0; i < dieFace * 60; i++) {
			playerImage.setTranslateX(playerImage.getTranslateX() + 1);
			System.out.println(playerImage.getTranslateX());
			Thread.sleep(10);
		}
		return null;
	}

}
