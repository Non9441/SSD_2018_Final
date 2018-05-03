package gameUI;

import java.util.Optional;

import gameLogic.Game;
import gameLogic.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

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

	private Game game;
	private Stage stage;
	private AnimationTimer timer;
	private PLayerMovePiece playerMove;
	private TranslateTransition transition;

	public void setGame(Game game) {
		this.game = game;
	}

	public void initialize() {

		rollButton.setOnAction(event -> {
			try {
				onRollButtonClicked(event);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		playerPosition.setText("Your position: " + 1);
		
		transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(1));
	}

	public void onRollButtonClicked(ActionEvent event) throws InterruptedException {
		int face = game.currentPlayerRollDie();
		dieImage.setImage(new Image("/res/face" + face + ".png"));
		diceOutputNumberText.setText(face + "");
		Player cur = game.currentPlayer();
		int curPos = game.currentPlayerPosition() + 1;

		transition.setNode(player1Image);

		for (int i = 0; i < face; i++) {

			// transition.stop();
			//// if (face - i == 1) {
			//// System.out.println("5555");
			//// String status = game.currentPlayerMovePiece(1);
			//// specialBlockLabel.setText(status);
			////
			//// } else {
			//// System.out.println("1111");
			//// }
			// game.currentPlayerOnMovePiece(1);
			// curPos = game.currentPlayerPosition();
			//
			// if (curPos % 20 < 10) {
			// transition.setByX(60);
			// } else if (curPos % 20 > 10) {
			// transition.setByX(-60);
			// } else if (curPos % 20 == 10 || game.currentPlayerPosition() % 20 == 0) {
			// transition.setByY(60);

			// if (face - i == 1) {
			// System.out.println("5555");
			// String status = game.currentPlayerMovePiece(1);
			// specialBlockLabel.setText(status);
			//
			// } else {

			// }
			// transition.play();
			// transition.jumpTo(Duration.seconds(1));
			// Thread.sleep(100);
			game.currentPlayerOnMovePiece(1);

			if (game.currentPlayer().getName().equals("Player1")) {
				playerMove = new PLayerMovePiece(game, cur, player1Image);
				new Thread(playerMove).start();
				Thread.sleep(40);
			}

			System.out.println("-----------\n" + game.currentPlayerName() + "\n----------");
		}

		int newPos = game.getPlayerPosition(cur) + 1;

		// if (game.currentPlayer().getName().equals("Player1")) {
		//// playerMove = new PLayerMovePiece(game, cur, player1Image);
		//// new Thread(playerMove).start();
		// transition.setNode(player1Image);
		// if(newPos > curPos) {
		// if (newPos % 20 <= 10) {
		//// if(curPos % 20 < 10) {
		//// transition.setByX(60*(newPos-curPos));
		//// transition.play();
		//// }
		//// if(curPos % 20 == 10) {
		//// transition.setByX((((int) Math.ceil((double)curPos/10))-curPos)*60);
		//// transition.play();
		//// transition.setByY(60);
		//// transition.play();
		//// }
		// transition.setByX(60*(newPos-curPos));
		// transition.play();
		// } else if (newPos % 20 > 10 || newPos % 20 == 0) {
		// if(curPos % 20 < 10) {
		// transition.setToX((((int) Math.ceil((double)curPos/10))-curPos)*60);
		// transition.play();
		// transition.setToY(60);
		// transition.play();
		// transition.setToX(-((newPos - ((int) Math.floor((double)newPos/10)))*60));
		// transition.play();
		// }
		// }
		//// else if (newPos % 20 == 10 || game.currentPlayerPosition() % 20 == 0) {
		//// transition.setToY(+60);
		//// }
		// } else if(newPos < curPos) {
		//
		// }
		// }

		playerPosition.setText(cur.getName() + " " + curPos + "->" + newPos);
		game.switchPlayer();
		setButtomDisable();
		if (game.isEnded()) {
			gameEndAlert();
		}
	}

	public void setButtomDisable() {
		stage = (Stage) rollButton.getScene().getWindow();
		String stagePlayerName = stage.getTitle().substring(stage.getTitle().lastIndexOf(" ") + 1);
		if (stagePlayerName.equals(game.currentPlayer().getName())) {
			rollButton.setDisable(false);
		} else {
			rollButton.setDisable(true);
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

	public void playerMove() {
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				player1Image.setTranslateX(player1Image.getTranslateX() + 5);
				player1Image.setY(player1Image.getY());
				System.out.println(player1Image.getTranslateX());
			}
		};
		timer.start();
	}
}
