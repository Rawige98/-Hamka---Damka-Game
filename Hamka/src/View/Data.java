package View;

import java.io.IOException;

import Controller.PlayGameController;
import Model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Data {

	@FXML
	private Label gameDate;

	@FXML
	private HBox hBox;

	@FXML
	private ImageView fstImg;

	@FXML
	private Label fstPlayer;

	@FXML
	private ImageView secImg;

	@FXML
	private Label secPlayer;

	@FXML
	private Button b1;
	
	private static Game game = null;
	private Game cellGame = null;

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Data.game = game;
	}
	
	public Button getB1() {
		return b1;
	}

	public void setB1(Button b1) {
		this.b1 = b1;
	}

	public Data() {
	
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/listCellItem.fxml"));
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setInfo(Game game) {
		fstPlayer.setText(game.getPlayer1().getUsername());
		secPlayer.setText(game.getPlayer2().getUsername());
		if (game.getWinner().equals(game.getPlayer1()))
			fstImg.setImage(new Image("/images/winner.png"));
		if (game.getWinner().equals(game.getPlayer2()))
			secImg.setImage(new Image("/images/winner.png"));
		gameDate.setText(game.getGameDate().toString());
		b1.setText("Show Board");
		cellGame = game;
		Data.game=cellGame;
	b1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				PlayGameController.getInstance().setGame(getCellGame());
				PlayGameController.getInstance().setPlayer1(getCellGame().getPlayer1());
				PlayGameController.getInstance().setPlayer2(getCellGame().getPlayer2());
				PlayGameController.getInstance().setHistory(true);
				WebCamPreviewController.profilePic=null;
				WebCamPreviewController.profilePic2=null;
				Stage primaryStage = new Stage();
				Parent root;
				try {
					((Stage) b1.getScene().getWindow()).close();
					root = FXMLLoader.load(getClass().getResource("/View/Play.fxml"));
					Scene scene = new Scene(root, 609, 637);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Play");
					primaryStage.show();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						}
		});
	}

	public HBox getBox() {
		return hBox;
	}

	@FXML
	public void showBoardPressed(ActionEvent event) {
		Data.game = this.getCellGame();
	}

	public Game getCellGame() {
		return cellGame;
	}

	public void setCellGame(Game cellGame) {
		this.cellGame = cellGame;
	}
}