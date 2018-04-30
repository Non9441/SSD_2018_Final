package gameUI;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameModeController {

	@FXML
	Button twoPlayerModeButton;
	@FXML
	Button fourPlayerModeButton;

	private Stage stage;
	MediaPlayer player;

	public void initialize() {
		
		URL bgm = getClass().getResource("bgm.wav");
		Media media = new Media(bgm.toString());
		player = new MediaPlayer(media);
		player.setVolume(0.1);
		player.setCycleCount((int)Double.POSITIVE_INFINITY);
		player.play();		
		
		twoPlayerModeButton.setOnAction(this::onPlayerModeButtonClicked);
		fourPlayerModeButton.setOnAction(this::onPlayerModeButtonClicked);
		
	}

	public void onPlayerModeButtonClicked(ActionEvent event) {
		stage = (Stage) twoPlayerModeButton.getScene().getWindow();
		try {

			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
		player.pause();
	}

}
