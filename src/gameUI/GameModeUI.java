package gameUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameModeUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("GameModeUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Choose Game Mode");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}