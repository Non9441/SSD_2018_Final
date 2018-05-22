package multiplayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import gameLogic.Die;
import gameLogic.Player;
import gameUI.MyAnimTimer;
import javafx.application.Platform;
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
	@FXML
	ImageView player3Image;
	@FXML
	ImageView player4Image;

	private Stage stage;

	private Player player;
	private Player currentPlayer;

	private String moveDetail = "Starting path";
	private String status = "Waiting";
	private int curPos;
	private int newPos;
	private String posStatus = "";
	private int faceFromServer;

	private Die die;
	private int face;
	private String playerWin = "";

	private SnakeLadderClient salClient;
	private MyAnimTimer timer;

	private double x;
	private double y;
	private List<MyAnimTimer> history;
	private List<MyAnimTimer> historytemp;

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
		rollButton.setDisable(true);

		x = player1Image.getX();
		y = player1Image.getY();

		history = new ArrayList<MyAnimTimer>();
		historytemp = new ArrayList<>();

		timer = new MyAnimTimer();
	}

	public void setPlayer(Player player) {
		this.player = player;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				playerNameLabel.setText(player.getName());
			}
		});
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

	public void setStatusAndMoveDetail(String player, String status, String moveDetail, int curPos, int newPos,
			int faceFromServer) {
		this.status = status;
		this.moveDetail = moveDetail;
		this.curPos = curPos;
		this.newPos = newPos;
		this.faceFromServer = faceFromServer;
		String[] text = status.split(" ");
		if (text.length > 4) {
			this.posStatus = text[3];
		}
		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (status.contains("Goal")) {
					rollButton.setDisable(true);
					specialBlockLabel.setText(status);
					moveImage(player, posStatus, curPos, newPos);
				} else if (status.equals("Full")) {
					rollButton.setDisable(true);
					playerNameLabel.setText("Fail to connected");
					playerPosition.setText(player);
					specialBlockLabel.setText(moveDetail);
				} else {
					moveImage(player, posStatus, curPos, newPos);
					playerPosition.setText(moveDetail);
					specialBlockLabel.setText(status);
				}
			}
		});
	}

	public void enableRollButton(WorkerStateEvent event) {
		if (currentPlayer != null) {
			if (player.getName().equals(currentPlayer.getName())) {
				rollButton.setDisable(false);
			}
		}
	}

	public void moveImage(String player, String posStatus, int curPos, int newPos) {

		ImageView curImg = null;
		boolean isEnd = false;
		System.out.println(posStatus);
		rollButton.setDisable(true);

		switch (player) {
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

		switch (posStatus) {
		case "normal":
			timer = new MyAnimTimer(curImg, curPos, newPos - curPos, 0, posStatus);
			history.add(new MyAnimTimer(curImg, curPos, newPos - curPos, 0, posStatus));
			timer.start();
			break;
		case "Backward":
			System.out.println("backward");
			timer = new MyAnimTimer(curImg, curPos, faceFromServer, 0, posStatus);
			history.add(new MyAnimTimer(curImg, curPos, faceFromServer, 0, posStatus));
			timer.start();
			break;
		case "Snake":
			System.out.println("snake");
			timer = new MyAnimTimer(curImg, curPos, faceFromServer, newPos - (curPos + faceFromServer), posStatus);
			history.add(new MyAnimTimer(curImg, curPos, faceFromServer, newPos - (curPos + faceFromServer), posStatus));
			timer.start();
			break;
		case "Ladder":
			System.out.println("ladder");
			timer = new MyAnimTimer(curImg, curPos, newPos - curPos, 0, posStatus);
			history.add(new MyAnimTimer(curImg, curPos, newPos - curPos, 0, posStatus));
			timer.start();
			break;
		case "Freeze":
			System.out.println("freeze");
			timer = new MyAnimTimer(curImg, curPos, faceFromServer, 0, posStatus);
			history.add(new MyAnimTimer(curImg, curPos, faceFromServer, 0, posStatus));
			System.out.println(player + " at " + curPos + " walk " + faceFromServer);
			timer.start();
			break;
		case "Goal":
			isEnd = true;
			rollButton.setDisable(true);
			playerWin = player;
			timer = new MyAnimTimer(curImg, curPos, newPos - curPos, 0, status);
			history.add(new MyAnimTimer(curImg, curPos, newPos - curPos, 0, status));
			timer.start();
			Task<Void> move = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while (timer.isActive()) {
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
		if (!isEnd) {
			Task<Void> move = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while (timer.isActive()) {
						System.out.print("");
					}
					return null;
				}
			};
			move.setOnSucceeded(this::enableRollButton);
			new Thread(move).start();
		}
	}

	public void onRollButtonClicked(ActionEvent event) throws InterruptedException {
		rollButton.setDisable(false);
		face = player.roll(die);
		dieImage.setImage(new Image("/res/face" + face + ".png"));
		diceOutputNumberText.setText(face + "");

		face = 50;
		salClient.sendRollResult(face);

		specialBlockLabel.setText("Playing....");
		playerPosition.setText(moveDetail);
	}

	public void setButtonDisable() {
		stage = (Stage) rollButton.getScene().getWindow();
		String stagePlayerName = stage.getTitle().substring(stage.getTitle().lastIndexOf(" ") + 1);
		if (stagePlayerName.equals(player.getName())) {
			rollButton.setDisable(false);
		} else {
			rollButton.setDisable(true);
		}
	}

	public void gameIsEnd(WorkerStateEvent event) {
		gameEndAlert();
	}

	public void gameEndAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Game end!!");
		alert.setHeaderText(playerWin + " Win >.<");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Replay!");
		ButtonType buttonTypeTwo = new ButtonType("Back to Home");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			replayAction();
		} else if (result.get() == buttonTypeTwo) {
			backToHome();
		}
	}

	public void replayAction() {
		player1Image.setTranslateX(x - player1Image.getX());
		player1Image.setTranslateY(y - player1Image.getY());

		player2Image.setTranslateX(x - player1Image.getX());
		player2Image.setTranslateY(y - player1Image.getY());

		player3Image.setTranslateX(x - player1Image.getX());
		player3Image.setTranslateY(y - player1Image.getY());

		player4Image.setTranslateX(x - player1Image.getX());
		player4Image.setTranslateY(y - player1Image.getY());

		rollButton.setDisable(true);

		Iterator<MyAnimTimer> iter = history.iterator();
		MyAnimTimer timer = iter.next();

		replay(timer, iter);
	}

	private void replay(MyAnimTimer hTimer, Iterator<MyAnimTimer> iter) {
		hTimer.start();
		historytemp.add(new MyAnimTimer(hTimer.getImg(), hTimer.getCurPos(), hTimer.getSteps(), hTimer.getSSteps(),
				hTimer.getStatus()));
		if (!iter.hasNext()) {
			history.clear();
			history.addAll(historytemp);
			historytemp.clear();
			gameEndAlert();
			return;
		}

		Thread t = new Thread(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				while (hTimer.isActive()) {
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

	public void backToHome() {
		stage = (Stage) playerNameLabel.getScene().getWindow();
		try {
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("/gameUI/GameModeUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			stage.setTitle("Choose Game Mode");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
