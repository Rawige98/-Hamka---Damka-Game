package View;

import java.io.IOException;

import Model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
		b1.setText("Show Board");
		gameDate.setText(game.getGameDate().toString());
		cellGame = game;
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