package gameUI;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameModeUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			URL bgm = getClass().getResource("bgm.wav");
			Media media = new Media(bgm.toString());
			MediaPlayer player = new MediaPlayer(media);
			player.setVolume(0.1);
			player.setCycleCount((int)Double.POSITIVE_INFINITY);
			player.play();
			
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