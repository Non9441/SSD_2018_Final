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
		

		String status = game.currentPlayerMovePiece(face);
		int newPos = game.currentPlayerPosition() + 1;
		
		switch (status) {
		case "normal":
			timer = new MyAnimTimer(curImg, curPos, face);
			timer.start();
			break;
		case "Backward":
			timer = new MyAnimTimer(curImg, curPos, -face);
			timer.start();
			break;
		case "Snake":
			timer = new MyAnimTimer(curImg, curPos, face);
			timer.start();
			while(timer.isActive()) {
				if(!timer.isActive()) break;
			}
			timer.setSteps(curPos-newPos);
			timer.start();
			
			break;
		case "Ladder":
			timer = new MyAnimTimer(curImg, curPos, face);
			timer.start();
			while(timer.isActive()) {
				if(!timer.isActive()) break;
			}
			timer.setSteps(newPos-curPos);
			timer.start();
			break;
		case "Freeze":
			break;
		default:
			break;
		}
		
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
		private int steps;
		private boolean active;
		
		
		
		public MyAnimTimer(ImageView curImg, int curPos, int steps) {
			this.curImg = curImg;
			this.curPos = curPos;
			this.steps = steps;
			active = false;
		}
		
		public void setSteps(int steps) {
			this.steps = steps;
		}
		
		public void setPosition(int curPos) {
			this.curPos = curPos;
		}
		
		public boolean isActive() {
			return active;
		}
		
		@Override
		public void handle(long now) {
			active = true;
			if(steps == 0) {stop(); active = false;}
			if(steps > 0) {
					if (curPos % 20 == 0 || curPos % 20 == 10) {
						curImg.setTranslateY(curImg.getTranslateY() - 60);	
					} else if (curPos % 20 <= 10) {
						curImg.setTranslateX(curImg.getTranslateX() + 60);
					} else if (curPos % 20 >= 10) {
						curImg.setTranslateX(curImg.getTranslateX() - 60);
					}
					curPos++;
					steps--;
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} 
			if (steps < 0){
				if (curPos % 20 == 1 || curPos % 20 == 11) {
					curImg.setTranslateY(curImg.getTranslateY() + 60);	
				} else if (curPos % 20 <= 10) {
					curImg.setTranslateX(curImg.getTranslateX() - 60);
				} else if (curPos % 20 >= 10) {
					curImg.setTranslateX(curImg.getTranslateX() + 60);
				}
				curPos--;
				steps++;
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
