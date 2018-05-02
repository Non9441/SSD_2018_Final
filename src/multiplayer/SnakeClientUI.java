package multiplayer;

import gameLogic.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeClientUI extends Application{
	
	private Player player;
	
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

}
