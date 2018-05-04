package multiplayer;

import java.util.Optional;

import gameLogic.Die;
import gameLogic.Player;
import gameUI.MyAnimTimer;
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
	@FXML
	ImageView player2Image;

	private Stage stage;
	private TranslateTransition transition;

	private Player player;
	private Player currentPlayer;

	private String moveDetail = "Starting path";
	private String status = "Waiting";
	private int curPos;
	private int newPos;
	private String posStatus = "";

	private Die die;
	private int face;

	private SnakeLadderClient salClient;
	private MyAnimTimer timer;

	public void setPlayer(Player player) {
		this.player = player;
		playerNameLabel.setText(player.getName());
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				playerTurnLabel.setText(currentPlayer.getName() + "'s turn");
				specialBlockLabel.setText("Game start!!");
				if (currentPlayer.getName().equals(player.getName())) {
					rollButton.setDisable(false);
				} else {
					rollButton.setDisable(true);
				}
			}
		});
	}

	public void setSalClient(SnakeLadderClient salClient) {
		this.salClient = salClient;
	}

	public void setStatusAndMoveDetail(String status, String moveDetail, int curPos, int newPos) {
		this.status = status;
		this.moveDetail = moveDetail;
		this.curPos = curPos;
		this.newPos = newPos;
		String[] text = status.split(" ");
		if (text.length > 4) {
			this.posStatus= text[3];
		}
		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				moveImage(posStatus, curPos, newPos);
				playerPosition.setText(moveDetail);
				specialBlockLabel.setText(status);
			}
		});
	}

	public void moveImage(String posStatus, int curPos, int newPos) {

		ImageView curImg = null;

		System.out.println("-----------------------------------------");
		System.out.println(player.getName());
		System.out.println(posStatus);
		System.out.println("-----------------------------------------");

		switch (player.getName()) {
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

		switch (posStatus) {
		case "normal":
			System.out.println(newPos+""+curPos);
			System.out.println("555555555555");
			timer.setUp(curImg, curPos, newPos - curPos);
			timer.start();
			break;
		case "Backward":
			System.out.println("backward");
			timer.setUp(curImg, curPos, face);
			timer.start();
			break;
		case "Snake":
			System.out.println("snake");
			timer.setUp(curImg, curPos, face);
			timer.start();
			timer.setSteps(newPos - (curPos + face));
			timer.start();

			break;
		case "Ladder":
			System.out.println("ladder");
			timer.setUp(curImg, curPos, face);
			timer.start();
			timer.setSteps(newPos - curPos);
			timer.start();
			break;
		case "Freeze":
			System.out.println("freeze");
			timer.setUp(curImg, curPos, face);
			timer.start();
			break;
		default:
			break;
		}

	}

	public void initialize() {
		die = new Die();
		rollButton.setOnAction(event -> {
			try {
				onRollButtonClicked(event);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		playerPosition.setText("Your position: " + 1);
		specialBlockLabel.setText("Waiting...");
		transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(1));
		rollButton.setDisable(true);

		timer = new MyAnimTimer();
	}

	public void onRollButtonClicked(ActionEvent event) throws InterruptedException {
		face = player.roll(die);
		dieImage.setImage(new Image("/res/face" + face + ".png"));
		diceOutputNumberText.setText(face + "");

		salClient.sendRollResult(face);

		specialBlockLabel.setText("Playing....");
		playerPosition.setText(moveDetail);
	}

	public void setButtomDisable() {
		stage = (Stage) rollButton.getScene().getWindow();
		String stagePlayerName = stage.getTitle().substring(stage.getTitle().lastIndexOf(" ") + 1);
		if (stagePlayerName.equals(player.getName())) {
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

}
