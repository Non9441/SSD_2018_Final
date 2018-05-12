package gameUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import gameLogic.Game;
import gameLogic.Player;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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

public class SnakeAndLadderController extends Application {

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
	@FXML
    ImageView player3Image;
    @FXML
    ImageView player4Image;

	private Game game;
	private Stage stage;
	private MyAnimTimer timer;
	private List<MyAnimTimer> history;
	private List<MyAnimTimer> historytemp;
	private double x;
	private double y;

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
			snake.setGame(new Game(game.getNumPlayer()));
			primaryStage.setTitle("Snake&Ladder | ");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
//			switch (game.getNumPlayer()) {
//			case 2:
//				player3Image.setVisible(false);
//				player4Image.setVisible(false);
//				break;
//			case 3:
//				player4Image.setVisible(false);
//				break;
//			default:
//				break;
//			}
			
		} catch (Exception e) {
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
		x = player1Image.getX();
		y = player1Image.getY();
		playerPosition.setText("Your position: " + 1);
		timer = new MyAnimTimer();
		history = new ArrayList<MyAnimTimer>();
		historytemp = new ArrayList<>();
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
		case "Player3":
			curImg = player3Image;
			break;
		case "Player4":
			curImg = player4Image;
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
			timer = new MyAnimTimer(curImg, curPos, newPos-curPos, 0, status);
			history.add(new MyAnimTimer(curImg, curPos, newPos-curPos, 0, status));
			timer.start();
			game.switchPlayer();
			break;
		case "Backward":
			System.out.println("backward");
			timer = new MyAnimTimer(curImg, curPos, face, 0, status);
			history.add(new MyAnimTimer(curImg, curPos, face, 0, status));
			timer.start();
			break;
		case "Snake":
			System.out.println("snake");
			timer = new MyAnimTimer(curImg, curPos, face, newPos-(curPos+face), status);
			history.add(new MyAnimTimer(curImg, curPos, face, newPos-(curPos+face), status));
			timer.start();
			game.switchPlayer();

			break;
		case "Ladder":
			System.out.println("ladder");
			timer = new MyAnimTimer(curImg, curPos, newPos-curPos, 0, status);
			history.add(new MyAnimTimer(curImg, curPos, newPos-curPos, 0, status));
			timer.start();
			game.switchPlayer();
			break;
		case "Freeze":
			System.out.println("freeze");
			timer = new MyAnimTimer(curImg, curPos, face, 0,status);
			history.add(new MyAnimTimer(curImg, curPos, face, 0,status));
			timer.start();
			game.switchPlayer();
			break;
		case "Goal":
			System.out.println("Goal");
			rollButton.setDisable(true);
			timer = new MyAnimTimer(curImg, curPos, newPos - curPos, 0, status);
			history.add(new MyAnimTimer(curImg, curPos, newPos - curPos, 0, status));
			timer.start();
			
			Task<Void> move = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					while(timer.isActive()) {
						System.out.print("");
					}
					return null;
				}
			};
			move.setOnSucceeded(this::gameIsEnd);
			new Thread(move).start();		
			break;
		default:
			break;
		}
		specialBlockLabel.setText(cur.getName()+" hit on \n"+status+" path.");

	}
	
	public void gameIsEnd(WorkerStateEvent event) {
		gameEndAlert();
		
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
			playAgain(null);
		}
		if (result.get() == buttonTypeThree) {
			replayAction();
		}
		if (result.get() == buttonTypeTwo) {
			backToHome();
		}
	}

	public void playAgain(List<MyAnimTimer> history) {

		stage = (Stage) playerNameLabel.getScene().getWindow();
		try {

			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			SnakeAndLadderController snake = chooseGameLoader.getController();
			snake.setGame(new Game(game.getNumPlayer()));
			snake.setHistory(history);
			stage.setTitle("Snake&Ladder | ");
			stage.setScene(chooseGameScene);
			stage.setResizable(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setHistory(List<MyAnimTimer> history) {
		this.history = history;
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
	
	public void replay(MyAnimTimer timer, Iterator<MyAnimTimer> iter) {
		timer.start();
		historytemp.add(new MyAnimTimer(timer.getImg(), timer.getCurPos(), timer.getSteps(), timer.getSSteps(), timer.getStatus()));
		if(!iter.hasNext()) {
			history.clear();
			history.addAll(historytemp);
			historytemp.clear();
			gameEndAlert();
			return;
		}
		
		Thread t = new Thread(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				while(timer.isActive()) {
					System.out.print("");
				}
				return null;
			}
			
			@Override
			protected void succeeded() {
				replay(iter.next(), iter);
				super.succeeded();
			}
			
		});
		
		t.start();
	}

	public void replayAction() {
		player1Image.setTranslateX(x-player1Image.getX());
		player1Image.setTranslateY(y-player1Image.getY());
		
		player2Image.setTranslateX(x-player1Image.getX());
		player2Image.setTranslateY(y-player1Image.getY());
		
		player3Image.setTranslateX(x-player1Image.getX());
		player3Image.setTranslateY(y-player1Image.getY());
		
		player4Image.setTranslateX(x-player1Image.getX());
		player4Image.setTranslateY(y-player1Image.getY());
		
		rollButton.setDisable(true);
		Iterator<MyAnimTimer> iter = history.iterator();
		MyAnimTimer timer = iter.next();
		
		replay(timer, iter);
	}

}
