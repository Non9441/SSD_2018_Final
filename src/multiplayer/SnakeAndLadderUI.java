package multiplayer;

import java.util.concurrent.CountDownLatch;

import gameLogic.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeAndLadderUI extends Application{

	private SnakeAndLadderController sal;
	private SnakeLadderClient slc;
	private static SnakeAndLadderUI ui;
	private final static CountDownLatch latch = new CountDownLatch(1);
	private Player player;
	private Player curPlayer;
	
	public void setSnakeAndLadderUI(SnakeAndLadderUI ui) {
		this.ui = ui;
		latch.countDown();
	}
	
	public SnakeAndLadderUI() {
		setSnakeAndLadderUI(this);
	}
	
	public static SnakeAndLadderUI waitForLaunch() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ui;
	}
	
	public void setClient(SnakeLadderClient slc) {
		this.slc = slc;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		System.out.println(this.player.getName());
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		try {
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("/multiplayer/SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			sal = chooseGameLoader.getController();
			stage.setTitle("Snake and Ladder | ");
			stage.setScene(chooseGameScene);
			stage.setResizable(false);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public int getFace() {
		return 0;
	}

}
