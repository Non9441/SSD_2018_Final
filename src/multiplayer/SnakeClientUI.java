package multiplayer;

import java.util.concurrent.CountDownLatch;

import gameLogic.Player;
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
	Button rollButton;
	
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
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("../gameUI/SnakeAndLadderGameUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Snake and Ladder");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

}
