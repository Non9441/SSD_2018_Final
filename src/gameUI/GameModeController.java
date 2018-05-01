package gameUI;

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

public class GameModeController {

	@FXML
	Button twoPlayerModeButton;
	@FXML
	Button threePlayerModeButton;
	@FXML
	Button fourPlayerModeButton;
	
	@FXML
    ImageView twoPlayerMode;

    @FXML
    ImageView threePlayerMode;

    @FXML
    ImageView fourPlayerMode;


	private Stage stage;
	MediaPlayer player;

	public void initialize() {
		
		URL bgm = getClass().getResource("bgm.wav");
		Media media = new Media(bgm.toString());
		player = new MediaPlayer(media);
		player.setVolume(0.1);
		player.setCycleCount((int)Double.POSITIVE_INFINITY);
		player.play();		
		
//		twoPlayerModeButton.setOnAction(this::onPlayerModeButtonClicked);
//		threePlayerModeButton.setOnAction(this::onPlayerModeButtonClicked);
//		fourPlayerModeButton.setOnAction(this::onPlayerModeButtonClicked);
		
		twoPlayerMode.setOnMouseEntered(this::onMouseOver2);
		threePlayerMode.setOnMouseEntered(this::onMouseOver3);
		fourPlayerMode.setOnMouseEntered(this::onMouseOver4);
		
		twoPlayerMode.setOnMouseExited(this::onMouseExited2);
		threePlayerMode.setOnMouseExited(this::onMouseExited3);
		fourPlayerMode.setOnMouseExited(this::onMouseExited4);
		
		twoPlayerMode.setOnMousePressed(this::onMousePressed2);
		threePlayerMode.setOnMousePressed(this::onMousePressed3);
		fourPlayerMode.setOnMousePressed(this::onMousePressed4);
		
		twoPlayerMode.setOnMouseReleased(this::onMouseReleased2);
		threePlayerMode.setOnMouseReleased(this::onMouseReleased3);
		fourPlayerMode.setOnMouseReleased(this::onMouseReleased4);
		
	}
	
	@FXML
    public void onMouseOver2(MouseEvent event) {
		System.out.println("over");
		Image image = new Image(getClass().getResource("../res/button2.gif").toExternalForm());
		twoPlayerMode.setImage(image);
    }

    @FXML
    public void onMouseOver3(MouseEvent event) {
    	System.out.println("over");
		Image image = new Image(getClass().getResource("../res/button3.gif").toExternalForm());
		threePlayerMode.setImage(image);
    }

    @FXML
    public void onMouseOver4(MouseEvent event) {
    	System.out.println("over");
		Image image = new Image(getClass().getResource("../res/button4.gif").toExternalForm());
		fourPlayerMode.setImage(image);
    }
    
    @FXML
    public void onMouseExited2(MouseEvent event) {
    	System.out.println("out");
		Image image = new Image(getClass().getResource("../res/button2.png").toExternalForm());
		twoPlayerMode.setImage(image);
    }

    @FXML
    public void onMouseExited3(MouseEvent event) {
    	System.out.println("out");
		Image image = new Image(getClass().getResource("../res/button3.png").toExternalForm());
		threePlayerMode.setImage(image);
    }

    @FXML
    public void onMouseExited4(MouseEvent event) {
    	System.out.println("out");
		Image image = new Image(getClass().getResource("../res/button4.png").toExternalForm());
		fourPlayerMode.setImage(image);
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
    public void onMouseReleased2(MouseEvent event) {
    	stage = (Stage) twoPlayerMode.getScene().getWindow();
		try {

			int player = 2;
			System.out.println(player);
			
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
			System.out.println(player);
			
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
			System.out.println(player);
			
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


//	public void onPlayerModeButtonClicked(ActionEvent event) {
//		stage = (Stage) twoPlayerMode.getScene().getWindow();
//		try {
//
//			Button b = (Button)event.getSource();
//			int player = Integer.parseInt(b.getText().charAt(0)+"");
//			System.out.println(player);
//			
//			FXMLLoader chooseGameLoader = new FXMLLoader(getClass().getResource("SnakeAndLadderGameUI.fxml"));
//			Parent chooseGameRoot = chooseGameLoader.load();
//			Scene chooseGameScene = new Scene(chooseGameRoot);
//
//			SnakeAndLadderController snakeAndLadder = chooseGameLoader.getController();
//			snakeAndLadder.setGame(new Game(player));
//			
//			stage.setTitle("Snake and Ladder");
//			stage.setScene(chooseGameScene);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		player.stop();
//	}

}
