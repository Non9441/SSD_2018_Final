package gameUI;

import java.util.Optional;

import gameLogic.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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

	private Game game;

	public void initialize() {
		game = new Game();
		rollButton.setOnAction(this::onRollButtonClicked);
	}

	public void onRollButtonClicked(ActionEvent event) {
		int face = game.currentPlayerRollDie();
		diceOutputNumberText.setText(face + "");
		game.currentPlayerMovePiece(face);
		playerPosition.setText("Your position: " + game.currentPlayerPosition());
		if (game.currentPlayerPosition() == 63) {
			gameEndAlert();
		}
	}

	public void gameEndAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog with Custom Actions");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Play this again");
		ButtonType buttonTypeTwo = new ButtonType("Back to Home");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			// ... user chose "One"
		} else if (result.get() == buttonTypeTwo) {

		}
	}
}
