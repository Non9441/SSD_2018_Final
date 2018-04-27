package gameUI;

import gameLogic.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
	}
}
