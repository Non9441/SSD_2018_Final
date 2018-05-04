package multiplayer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import gameLogic.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeAndLadderUI extends Application {

	private SnakeAndLadderController salController;
	private SnakeLadderClient salClient;
	private static SnakeAndLadderUI ui;
	private final static CountDownLatch latch = new CountDownLatch(1);
	private Player player;
	private Player currentPlayer;
	private String status = "";
	
	private FXMLLoader chooseGameLoader;
	private Parent chooseGameRoot;
	private Scene chooseGameScene;

	public void setSnakeAndLadderUI(SnakeAndLadderUI ui) {
		this.ui = ui;
		
		chooseGameLoader = new FXMLLoader(
				getClass().getResource("/multiplayer/SnakeAndLadderGameUI.fxml"));
		try {
			chooseGameRoot = chooseGameLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		chooseGameScene = new Scene(chooseGameRoot);

		salController = chooseGameLoader.getController();
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
		this.salClient = slc;
		salController.setSalClient(salClient);
	}

	public void setPlayer(Player player) {
		this.player = player;
		salController.setPlayer(player);
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		salController.setCurrentPlayer(currentPlayer);
	}
	
	public void setStatusAndMoveDetail(String status,String moveDetail) {
		salController.setStatusAndMoveDetail(status,moveDetail);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		try {
			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);
			stage.setResizable(false);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
