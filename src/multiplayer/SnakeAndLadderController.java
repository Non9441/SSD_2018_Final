package multiplayer;

import java.util.Optional;

import gameLogic.Die;
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

	private Stage stage;
	private AnimationTimer timer;
	private TranslateTransition transition;
	private Player player;
	private Player currentPlayer;
	private String status = "Waiting";
	private Die die;
	private int face;

	private SnakeLadderClient salClient;

	public void setPlayer(Player player) {
		this.player = player;
		playerNameLabel.setText(player.getName());
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		specialBlockLabel.setText("Ready for play");
	}
	
	public void setSalClient(SnakeLadderClient salClient) {
		this.salClient = salClient;
	}
	
	public void setStatus(String status) {
		this.status = status;
		System.out.println(status);
//		specialBlockLabel.setText(this.status+"");
	}
	
	public int getFace() {
		return face;
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
		specialBlockLabel.setText(status);
		transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(1));
	}

	public void onRollButtonClicked(ActionEvent event) throws InterruptedException {
		if(currentPlayer == null) {
			specialBlockLabel.setText("Wait for other player");
			return;
		}
		System.out.println("I am "+player.getName()+", It's"+currentPlayer.getName()+"turn");
		if(!currentPlayer.getName().equals(player.getName())) {
			specialBlockLabel.setText("It's not your turn");
			return;
		}
		face = player.roll(die);
		salClient.sendRollResult(face);
		dieImage.setImage(new Image("/res/face" + face + ".png"));
		diceOutputNumberText.setText(face + "");

		playerPosition.setText(player.getName() + " ");
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
