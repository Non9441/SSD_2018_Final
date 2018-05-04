package gameUI;

import java.util.Optional;

import gameLogic.Game;
import gameLogic.Player;
import javafx.animation.AnimationTimer;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	@FXML
	ImageView dieImage;
	@FXML
	ImageView player1Image;
    @FXML
    ImageView player2Image;

	private Game game;
	private Stage stage;
	private AnimationTimer timer;

	public void setGame(Game game) {
		this.game = game;
	}

	public void initialize() {
		rollButton.setOnAction(event -> {
			try {
				onRollButtonClicked(event);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		playerPosition.setText("Your position: " + 1);
	}

	public void onRollButtonClicked(ActionEvent event) throws InterruptedException {
		int face = game.currentPlayerRollDie();
		dieImage.setImage(new Image("/res/face" + face + ".png"));
		diceOutputNumberText.setText(face + "");
		
		ImageView curImg = null;
		
		Player cur = game.currentPlayer();
		int curPos = game.currentPlayerPosition() + 1;
		
		switch (cur.getName()) {
		case "Player1":
			curImg = player1Image;
			break;
		case "Player2":
			curImg = player2Image;
			break;
		default:
			curImg = player1Image;
			break;
		}
		
		timer = new MyAnimTimer(curImg, curPos, face);
		timer.start();

		game.currentPlayerOnMovePiece(face);
		
		int newPos = game.getPlayerPosition(cur) + 1;
		
		playerPosition.setText(cur.getName() + " " + curPos + "->" + newPos);
		game.switchPlayer();
		if (game.isEnded()) {
			gameEndAlert();
		}
	}

	public void gameEndAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog with Custom Actions");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Play again");
		ButtonType buttonTypeThree = new ButtonType("Replay!");
		ButtonType buttonTypeTwo = new ButtonType("Back to Home");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeThree, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			game = new Game(game.getNumPlayer());
			initialize();
		} else if (result.get() == buttonTypeThree) {
			alert.setHeaderText("Not finish!!");
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
	
	class MyAnimTimer extends AnimationTimer {
		
		private ImageView curImg;
		private int curPos;
		private int face;
		
		public MyAnimTimer(ImageView curImg, int curPos, int face) {
			this.curImg = curImg;
			this.curPos = curPos;
			this.face = face;
		}
		@Override
		public void handle(long now) {
			
			if(face == 0) stop();
			else {
				if (curPos % 20 == 0 || curPos % 20 == 10) {
					curImg.setTranslateY(curImg.getTranslateY() - 60);	
				} else if (curPos % 20 <= 10) {
					curImg.setTranslateX(curImg.getTranslateX() + 60);
				} else if (curPos % 20 >= 10) {
					curImg.setTranslateX(curImg.getTranslateX() - 60);
				}
				curPos++;
				face--;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
