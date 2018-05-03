package multiplayer;

import java.util.concurrent.CountDownLatch;

import gameLogic.Game;
import gameLogic.Player;
import gameUI.SnakeAndLadderController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SnakeClientUI extends Application{
	
	private Player player;
	private SnakeLadderClient slc; 
	
	private static SnakeClientUI scu;
	private final static CountDownLatch latch = new CountDownLatch(1);
	
	@FXML
	private Button rollButton;
	
	public void setSnakeClientUI(SnakeClientUI scu) {
		this.scu = scu;
		latch.countDown();
	}
	
	public SnakeClientUI() {
		setSnakeClientUI(this);
	}
	
	public static SnakeClientUI waitForLaunch() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return scu;
	}
	
	public void setClient(SnakeLadderClient slc) {
		this.slc = slc;
		player = slc.getPlayer();
	}
	
	public void disableRollButton() {
		rollButton.setDisable(true);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("../gameUI/SnakeAndLadderGameUI.fxml"));
			Parent root = chooseGameLoader.load();
			Scene scene = new Scene(root);
			
			SnakeAndLadderController snakeandladder = chooseGameLoader.getController();
			snakeandladder.setGame(new Game(2));
			
			primaryStage.setTitle("Snake&Ladder | "+player.getName());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
