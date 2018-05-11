package gameUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gameLogic.Game;
import gameLogic.Player;
import javafx.application.Application;
import javafx.application.Platform;
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
	private List<MyAnimTimer> history;

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
		history = new ArrayList<MyAnimTimer>();
		
		player1Image.setLayoutX(14);
		player1Image.setLayoutY(551);
		player2Image.setLayoutX(14);
		player2Image.setLayoutY(551);
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
		 * เดินเกินร้อยยังไม่ถอยหลัง backward ยังไม่สมบูรณ์
		 */

		String status = game.currentPlayerMovePiece(face);
		int newPos = game.currentPlayerPosition() + 1;
		
		playerPosition.setText(cur.getName() + " " + curPos + "->" + newPos);
		
		switch (status) {
		case "normal":
			timer = new MyAnimTimer(curImg, curPos, newPos-curPos, status);
			history.add(timer);
			timer.start();
			game.switchPlayer();
			break;
		case "Backward":
			System.out.println("backward");
			timer = new MyAnimTimer(curImg, curPos, face, status);
			history.add(timer);
			timer.start();
			break;
		case "Snake":
			System.out.println("snake");
			timer = new MyAnimTimer(curImg, curPos, face, status);
			timer.setSsteps(newPos-(curPos+face));
			history.add(timer);
			timer.start();
			game.switchPlayer();
			
			break;
		case "Ladder":
			System.out.println("ladder");
			timer = new MyAnimTimer(curImg, curPos, newPos-curPos, status);
			history.add(timer);
			timer.start();
			game.switchPlayer();
			break;
		case "Freeze":
			System.out.println("freeze");
			timer = new MyAnimTimer(curImg, curPos, face, status);
			history.add(timer);
			timer.start();
			game.switchPlayer();
			break;
		default:
			break;
		}
		
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
			playAgain();
		} else if (result.get() == buttonTypeThree) {
			replayAction();
		} else if (result.get() == buttonTypeTwo) {
			backToHome();
		}
	}

	public void playAgain() {

		stage = (Stage) playerNameLabel.getScene().getWindow();
		try {

			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			
			SnakeAndLadderController snake = chooseGameLoader.getController();
			snake.setGame(new Game(2));
			stage.setTitle("Snake&Ladder | ");
			stage.setScene(chooseGameScene);
			stage.setResizable(false);

		} catch (Exception e) {
			e.printStackTrace();
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
	
	public void replayAction() {
		player1Image.setX(14);
		player1Image.setY(551);
		
		player2Image.setX(14);
		player2Image.setY(551);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				for(MyAnimTimer timer : history) {
					timer.start();
					while(timer.isActive()) {
						if(!timer.isActive()) break;
					}
				}
			}
		});
	}

}
