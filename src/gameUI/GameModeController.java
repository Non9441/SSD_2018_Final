package gameUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameModeController {

	@FXML
	Button twoPlayerModeButton;
	@FXML
	Button fourPlayerModeButton;

	private Stage stage;

	public void initialize() {
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
	}

}
