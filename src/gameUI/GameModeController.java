package gameUI;

import java.io.IOException;
import java.net.URL;

import gameLogic.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import multiplayer.SnakeLadderClient;

public class GameModeController {
	
	@FXML
    ImageView twoPlayerMode;

    @FXML
    ImageView threePlayerMode;

    @FXML
    ImageView fourPlayerMode;
    
    @FXML
    ImageView online;

	private Stage stage;
	MediaPlayer player;

	public void initialize() {
		
		URL bgm = getClass().getResource("bgm.wav");
		Media media = new Media(bgm.toString());
		player = new MediaPlayer(media);
		player.setVolume(0.1);
		player.setCycleCount((int)Double.POSITIVE_INFINITY);
		player.play();
		
		twoPlayerMode.setOnMouseEntered(this::onMouseOver2);
		threePlayerMode.setOnMouseEntered(this::onMouseOver3);
		fourPlayerMode.setOnMouseEntered(this::onMouseOver4);
		online.setOnMouseEntered(this::onMouseOverOnline);
		
		twoPlayerMode.setOnMouseExited(this::onMouseExited2);
		threePlayerMode.setOnMouseExited(this::onMouseExited3);
		fourPlayerMode.setOnMouseExited(this::onMouseExited4);
		online.setOnMouseExited(this::onMouseExitedOnline);
		
		twoPlayerMode.setOnMousePressed(this::onMousePressed2);
		threePlayerMode.setOnMousePressed(this::onMousePressed3);
		fourPlayerMode.setOnMousePressed(this::onMousePressed4);
		online.setOnMousePressed(this::onMousePressedOnline);
		
		twoPlayerMode.setOnMouseReleased(this::onMouseReleased2);
		threePlayerMode.setOnMouseReleased(this::onMouseReleased3);
		fourPlayerMode.setOnMouseReleased(this::onMouseReleased4);
		online.setOnMouseReleased(this::onMouseReleasedOnline);
		
		
		
	}
	
	@FXML
    public void onMouseOver2(MouseEvent event) {
		Image image = new Image(getClass().getResource("../res/button2.gif").toExternalForm());
		twoPlayerMode.setImage(image);
    }

    @FXML
    public void onMouseOver3(MouseEvent event) {
		Image image = new Image(getClass().getResource("../res/button3.gif").toExternalForm());
		threePlayerMode.setImage(image);
    }

    @FXML
    public void onMouseOver4(MouseEvent event) {
    	Image image = new Image(getClass().getResource("../res/button4.gif").toExternalForm());
		fourPlayerMode.setImage(image);
    }
    
    @FXML
    public void onMouseOverOnline(MouseEvent event) {
    	Image image = new Image(getClass().getResource("../res/OButton.gif").toExternalForm());
		online.setImage(image);
    }
    
    @FXML
    public void onMouseExited2(MouseEvent event) {
		Image image = new Image(getClass().getResource("../res/button2.png").toExternalForm());
		twoPlayerMode.setImage(image);
    }

    @FXML
    public void onMouseExited3(MouseEvent event) {
		Image image = new Image(getClass().getResource("../res/button3.png").toExternalForm());
		threePlayerMode.setImage(image);
    }

    @FXML
    public void onMouseExited4(MouseEvent event) {
		Image image = new Image(getClass().getResource("../res/button4.png").toExternalForm());
		fourPlayerMode.setImage(image);
    }
    
    @FXML
    public void onMouseExitedOnline(MouseEvent event) {
    	Image image = new Image(getClass().getResource("../res/OButton.png").toExternalForm());
		online.setImage(image);
    }
    
    @FXML
    public void onMousePressed2(MouseEvent event) {
		Image image = new Image(getClass().getResource("../res/button2P.png").toExternalForm());
		twoPlayerMode.setImage(image);
		
    }

    @FXML
    public void onMousePressed3(MouseEvent event) {
    	Image image = new Image(getClass().getResource("../res/button3P.png").toExternalForm());
		threePlayerMode.setImage(image);
		
    }

    @FXML
    public void onMousePressed4(MouseEvent event) {
    	Image image = new Image(getClass().getResource("../res/button4P.png").toExternalForm());
		fourPlayerMode.setImage(image);
		
    }
    
    @FXML
    public void onMousePressedOnline(MouseEvent event) {
    	Image image = new Image(getClass().getResource("../res/OButtonP.png").toExternalForm());
		online.setImage(image);
    }
    
    @FXML
    public void onMouseReleased2(MouseEvent event) {
    	stage = (Stage) twoPlayerMode.getScene().getWindow();
		try {

			int player = 2;
			
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			SnakeAndLadderController snakeAndLadder = chooseGameLoader.getController();
			snakeAndLadder.setGame(new Game(player));
			
			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
		player.stop();
    }

    @FXML
    public void onMouseReleased3(MouseEvent event) {
    	stage = (Stage) threePlayerMode.getScene().getWindow();
		try {

			int player = 3;
			
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			SnakeAndLadderController snakeAndLadder = chooseGameLoader.getController();
			snakeAndLadder.setGame(new Game(player));
			
			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
		player.stop();
    }

    @FXML
    public void onMouseReleased4(MouseEvent event) {
    	stage = (Stage) fourPlayerMode.getScene().getWindow();
		try {

			int player = 4;
			
			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
			Parent chooseGameRoot = chooseGameLoader.load();
			Scene chooseGameScene = new Scene(chooseGameRoot);

			SnakeAndLadderController snakeAndLadder = chooseGameLoader.getController();
			snakeAndLadder.setGame(new Game(player));
			
			stage.setTitle("Snake and Ladder");
			stage.setScene(chooseGameScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
		player.stop();
    }
    
    @FXML
    void onMouseReleasedOnline(MouseEvent event) {
//    	Image image = new Image(getClass().getResource("../res/OButton.png").toExternalForm());
//    	online.setImage(image);
    	try {
			new SnakeLadderClient((Stage) online.getScene().getWindow());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
