package multiplayer;

import gameLogic.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeAndLadderUI extends Application{

	@Override
	public void start(Stage stage) throws Exception {

		try {

			
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("/multiplayer/SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			SnakeAndLadderController sal = chooseGameLoader.getController();
			sal.setPlayer(new Player("Player1"));
			
			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);
			stage.setResizable(false);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
