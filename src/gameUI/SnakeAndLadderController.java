package gameUI;

import java.util.Optional;

import gameLogic.Game;
import gameLogic.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

public class SnakeAndLadderController extends Application{

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
	private MyAnimTimer timer;

	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			SnakeAndLadderController snake = loader.getController();
			snake.setGame(new Game(2));
			primaryStage.setTitle("Snake&Ladder | ");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		timer = new MyAnimTimer();
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
		
		/*
		 * อาจจะบึ้ม some condition
		 */

		String status = game.currentPlayerMovePiece(face);
		int newPos = game.currentPlayerPosition() + 1;
		
		playerPosition.setText(cur.getName() + " " + curPos + "->" + newPos);
		
		switch (status) {
		case "normal":
			timer = new MyAnimTimer(curImg, curPos, newPos-curPos, status);
			timer.start();
			game.switchPlayer();
			break;
		case "Backward":
			System.out.println("backward");
			timer = new MyAnimTimer(curImg, curPos, face, status);
			timer.start();
			break;
		case "Snake":
			System.out.println("snake");
			timer = new MyAnimTimer(curImg, curPos, face, status);
			timer.setSsteps((curPos+face)-newPos);
			timer.start();
			game.switchPlayer();
			
			break;
		case "Ladder":
			System.out.println("ladder");
			timer = new MyAnimTimer(curImg, curPos, newPos-curPos, status);
			timer.start();
			game.switchPlayer();
			break;
		case "Freeze":
			System.out.println("freeze");
			timer = new MyAnimTimer(curImg, curPos, face, status);
			timer.start();
			game.switchPlayer();
			break;
		default:
			break;
		}
//		game.switchPlayer();
		
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

}
