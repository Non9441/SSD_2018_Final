package gameUI;

import java.util.Optional;

import gameLogic.Game;
import gameLogic.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SnakeAndLadderController {

	@FXML
	Label playerNameLabel;
	@FXML
	Label playerTurnLabel;
	@FXML
	Button rollButton;
	@FXML
	TextField diceOutputNumberText;
	@FXML
	Label playerPosition;
	@FXML
	Label specialBlockLabel;

	private Game game;
	private Stage stage;
	
	public void setGame(Game game) {
		this.game = game;
	}

	public void initialize() {
		rollButton.setOnAction(this::onRollButtonClicked);
//		playerPosition.setText("Your position: " + (game.currentPlayerPosition() + 1));
	}

	public void onRollButtonClicked(ActionEvent event) {
		int face = game.currentPlayerRollDie();
		Player cur = game.currentPlayer();
		int curPos = game.currentPlayerPosition() + 1;
		diceOutputNumberText.setText(face + "");
		String status = game.currentPlayerMovePiece(face);
		int newPos = game.getPlayerPosition(cur) + 1;
		playerPosition.setText(cur.getName()+" "+curPos+"-"+newPos);
		specialBlockLabel.setText(status);
		if (game.currentPlayerPosition() == 63) {
			gameEndAlert();
		}
	}

	public void gameEndAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog with Custom Actions");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Play again");
		ButtonType buttonTypeTwo = new ButtonType("Back to Home");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			initialize();
		} else if (result.get() == buttonTypeTwo) {
			backToHome();
		}
	}
	
	public void backToHome() {
		
		stage = (Stage) playerNameLabel.getScene().getWindow();
		try {

			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("GameModeUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			stage.setTitle("Choose Game Mode");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
