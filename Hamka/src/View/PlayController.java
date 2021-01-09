package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controller.PlayGameController;
import Controller.PopQ;
import Model.BlackSoldier;
import Model.Board;
import Model.ColorTilesHandler;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.SysData;
import Model.Tile;
import Model.WhiteSoldier;
import Utils.Consts;
import Utils.DataType;
import Utils.Difficulty;
import Utils.GameStatus;
import Utils.MoveResult;
import Utils.MoveType;
import Utils.PieceType;
import Utils.TileIconType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayController implements Initializable {

	@FXML
	private Circle player1image;

	@FXML
	private Label player1;

	@FXML
	private Circle player2image;

	@FXML
	private Pane questionPane;

	@FXML
	private Label player2;
	@FXML
	private Label point1;

	@FXML
	private Label playerTimer;
	
	@FXML
	private Label point2;

	@FXML
    private Label msgLabel;
	
	@FXML
	public Pane boardPane;

	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private FlowPane msgPane;
	
	@FXML
	private Label gameTimer;

	@FXML
	private AnchorPane rootBorderPane;

	@FXML
	private Button check;

	@FXML
	private Text question;

	@FXML
	private RadioButton ans1;

	@FXML
	private ToggleGroup answers;

	@FXML
	private RadioButton ans2;

	@FXML
	private RadioButton ans3;
	@FXML
	private ImageView p1Turn;

	@FXML
	private ImageView p2Turn;
	@FXML
	private ImageView hard;

	@FXML
	private ImageView medium;

	@FXML
	private ImageView easy;

	@FXML
	private RadioButton ans4;
	@FXML
	private Circle w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12;
	@FXML
	private Circle b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;

	PopQ p = new PopQ();
	int rightA;

	private Group tileGroup;
	private Group pieceGroup;
	private TileView[][] boardView = new TileView[Consts.ROWS][Consts.COLS];
	private Thread t = null;
	private Thread tp1 = null;
	// private Thread tp2 = null;
	private Game game;
	private Player player_1;
	private Player player_2;
	private boolean gameInProgress; // Is a game currently in progress?
	private Player currentPlayer; // Whose turn is it now? The possible values
	private TimerForGame timer;
	private TimerForPlayer1 PlayerTimer1;
	int count = 0;
	private Question q;
	private static TileView lastTile;
	private boolean samePlayerTurn = false;
	private static Color lastColor = Color.BLACK;
	private boolean flag;
	// private TimerForPlayer2 PlayerTimer2;

	@FXML
	void closeWindow(ActionEvent event) {

		// tp1.stop();
		Game.notFinished = false;
		t.stop();
		tp1.stop();
		((Stage) player1.getScene().getWindow()).close();
	}

	@FXML
	void PauseGame(ActionEvent event) {
		SysData.getInstance().saveGame(DataType.PAUSED_GAMES, game);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Pause Game");
		alert.setContentText("The Game has been Paused and Saved");
		alert.show();
	}

	@FXML
	public void back(ActionEvent event) throws Exception {
		Game.notFinished = false;
		t.stop();
		tp1.stop();
		((Stage) player1.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
		Scene scene = new Scene(root, 581, 449);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka game");
		primaryStage.show();
	}

	public Pane createBoardView() {
		// boardPane = new Pane();
		boardPane.setPrefSize(Consts.COLS * Consts.TILE_SIZE, Consts.ROWS * Consts.TILE_SIZE);
		boardPane.getChildren().addAll(tileGroup, pieceGroup);
		for (int y = 0; y < Consts.ROWS; y++) {
			for (int x = 0; x < Consts.COLS; x++) {
				TileView tileView = new TileView((x + y) % 2 == 0, x, y);
				boardView[x][y] = tileView;
				tileGroup.getChildren().add(tileView);
				Piece piece = null;
				// changes in (if)
				if (game.getBoard().getMyBoard()[x][y] instanceof BlackSoldier) {
					piece = makePiece(PieceType.WHITE, player_2.getColor(), x, y);
					if (game.getBoard().getMyBoard()[x][y].isQueen())
						piece.showCrown();
				}
				if (game.getBoard().getMyBoard()[x][y] instanceof WhiteSoldier) {
					piece = makePiece(PieceType.GREY, player_1.getColor(), x, y);
					if (game.getBoard().getMyBoard()[x][y].isQueen())
						piece.showCrown();
				}
				if (piece != null) {
					tileView.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}
				tileView.setOnMousePressed(e -> {
					if (lastColor.equals(Color.BLUE)) {
						// colorSuggesstedForBlueTile();
						double mouseX = e.getSceneX();
						double mouseY = e.getSceneY();
						int col = toBoard(mouseY);
						int row = toBoard(mouseX);
						Tile tile = PlayGameController.getInstance().getGame().getBoard().getMyBoard()[row - 1][col
						                                                                                        - 1];
						if (suggestedTileBlueMove().contains(tile)) {
							Piece newPiece = makePiece(
									(currentPlayer.equals(player_1) ? PieceType.GREY : PieceType.WHITE),
									currentPlayer.getColor(), row - 1, col - 1);
							checkQueen(newPiece, row - 1, col - 1);
							PlayGameController.getInstance().getBackSoldierToLife(row - 1, col - 1);
							TileView newTileView = boardView[row - 1][col - 1];
							newTileView.setPiece(newPiece);
							newPiece.toFront();
							pieceGroup.getChildren().add(newPiece);
							removeBlueStroke();
							refreshBoardTilesColors();
							PlayGameController.getInstance().switchTurnNow();
							// lastColor=Color.BLACK;
						}
					}
				});
			}
		}
		return boardPane;
	}

	private void removeBlueStroke() {
		for (int y = 0; y < Consts.ROWS; y++) {
			for (int x = 0; x < Consts.COLS; x++) {

				boardView[x][y].getBaseShape().setStroke(null);
			}
		}
	}

	private void colorSuggesstedForBlueTile() {

		for (Tile tile : suggestedTileBlueMove()) {

			if (!boardView[tile.getCols()][tile.getRows()].getBaseShape().getFill().equals(Color.WHITE)) {
				boardView[tile.getCols()][tile.getRows()].getBaseShape().setStroke(Color.BLUE);
				boardView[tile.getCols()][tile.getRows()].getBaseShape().setStrokeWidth(5);
				boardView[tile.getCols()][tile.getRows()].toFront();

			}
		}
	}

	private Piece makePiece(PieceType type, Color color, int x, int y) {
		Piece piece = new Piece(type, color, x, y);
		piece.setOnMouseReleased(e -> {
			int newX = toBoard(piece.getLayoutX());
			int newY = toBoard(piece.getLayoutY());
			int x0 = toBoard(piece.getOldX());
			int y0 = toBoard(piece.getOldY());
			currentPlayer = PlayGameController.getInstance().getCurrentPlayer();
			MoveResult moveResult = tryMoveTest(piece, newX, newY);
			if (lastTile == null) {
				lastTile = boardView[x0][y0];
			}
			if (lastColor.equals(Color.RED)) {
				Piece lastPiece = lastTile.getPiece();
				if (!lastPiece.equals(piece))
					moveResult.setType(MoveType.NONE);
			}

			switch (moveResult.getType()) {
			case NONE:
				String msg = "";
				piece.aboartMove();
				if(Game.getIsP1Turn() == true) {
					
				}
				showMsg("Bad move, Try again!");
				break;
			case NORMAL:
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);
				// showYellowTiles();
				// check indexes
				checkDestinationTile(boardView[newX][newY]);
				if (!lastColor.equals(Color.BLUE))
					colorTiles();
				checkQueen(piece, newX, newY);
				break;

			case KILL:
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);
				Piece otherPiece = moveResult.getPiece();
				checkDestinationTile(boardView[newX][newY]);

				if (!Game.isOwnKill()) {
					boardView[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
					pieceGroup.getChildren().remove(otherPiece);
					checkAnotherKill(newX, newY);
					showMsg("!! Great, nice move !!\nNOTE: If you have another kill... Do it with the same soldier!");
				} else {
					boardView[toBoard(Game.getKilledSoldier().getCols())][toBoard(Game.getKilledSoldier().getRows())]
							.setPiece(null);
					pieceGroup.getChildren().remove(
							boardView[Game.getKilledSoldier().getCols()][Game.getKilledSoldier().getRows()].getPiece());
					Game.setOwnKill(false);
					showMsg("You had to eat the soldier next to you!");
				}
				if (!lastColor.equals(Color.BLUE))
					colorTiles();
				checkQueen(piece, newX, newY);
				break;
			default:
				break;
			}

		});

		return piece;
	}

	private void checkAnotherKill(int newX, int newY) {
		// TODO Auto-generated method stub
		if (!(lastColor.equals(Color.YELLOW) && lastColor.equals(Color.RED)))
			PlayGameController.getInstance().switchTurnNow();
		if (PlayGameController.getInstance().haveAnotherKill(newX, newY)) {
			samePlayerTurn = true;
			lastColor = Color.RED;

		} else {
			PlayGameController.getInstance().switchTurnNow();
		}
	}

	private void checkQueen(Piece piece, int x, int y) {
		// TODO Auto-generated method stub
		if (PlayGameController.getInstance().checkIfQueen(x, y)) {
			if(piece.getCrownEllipse().getFill() == null)
				showMsg("Long live for the new Queen");
			piece.showCrown();
		}
	}
	private void checkDestinationTile(TileView tileView) {
		lastTile = tileView;
		//		System.out.println(PlayGameController.getInstance().getGame().getBoard().getMyBoard()[tileView.getX_value()][tileView.getY_value()].longString());
		if (tileView.getBaseShape().getFill().equals(Color.YELLOW)) {
			popQuestion();
			samePlayerTurn = true;
			lastColor = Color.YELLOW;
		} else if (tileView.getBaseShape().getFill().equals(Color.RED)) {
			if (PlayGameController.getInstance().checkMovesAvailability(tileView.getX_value(), tileView.getY_value())) {
				samePlayerTurn = true;
				lastColor = Color.RED;
				showMsg("You had earned a one more turn with the same soldier");
			} else {
				samePlayerTurn = false;
				lastColor = Color.BLACK;
				PlayGameController.getInstance().switchTurnNow();
			}
		} else if (tileView.getBaseShape().getFill().equals(Color.GREEN)) {
			showMsg("Congrats! You had earned "+ Consts.POINTS_FOR_GREEN_TILE+" ponits.");
			currentPlayer.updateScore(Consts.POINTS_FOR_GREEN_TILE);
			updateScore(currentPlayer);
			samePlayerTurn = false;
			lastColor = Color.GREEN;
			PlayGameController.getInstance().switchTurnNow();

		} else if (tileView.getBaseShape().getFill().equals(Color.BLUE)) {
			if (!suggestedTileBlueMove().isEmpty()) {
				showMsg("Good job! You had earned a chance to get back one soldier to life\nChooce a tile that marked with a blue border");
				colorSuggesstedForBlueTile();
				lastColor = Color.BLUE;
				samePlayerTurn = true;
			}
		} else {
			samePlayerTurn = false;
			lastColor = Color.BLACK;
			PlayGameController.getInstance().switchTurnNow();
		}
		if (!samePlayerTurn) {
			if (currentPlayer.equals(player_1)) {
				if (PlayerTimer1.getMints() < 1)
					player_1.setScore(60 - PlayerTimer1.getSecond());
				else
					player_1.setScore(60 - (PlayerTimer1.getSecond() + 60 * PlayerTimer1.getMints()));
			} else {
				if (PlayerTimer1.getMints() < 1)
					player_2.setScore(60 - PlayerTimer1.getSecond());
				else
					player_2.setScore(60 - (PlayerTimer1.getSecond() + 60 * PlayerTimer1.getMints()));

			}

			updateScore(player_1);
			updateScore(player_2);
			if (!samePlayerTurn && PlayGameController.getInstance().isLastMove()) {
				((Stage) player1.getScene().getWindow()).close();
				Stage primaryStage = new Stage();
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("/View/Winner.fxml"));
					Scene scene = new Scene(root, 439, 256);
					primaryStage.setScene(scene);
					primaryStage.setTitle("winner");
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
	}

	// move update(Model)
	private MoveResult tryMoveTest(Piece piece, int newX, int newY) {
		int oldX = toBoard(piece.getOldX());
		int oldY = toBoard(piece.getOldY());

		if (newX < 0 || newY < 0 || newX >= Consts.ROWS || newY >= Consts.COLS || oldX == newX || oldY == newY)
			return new MoveResult(MoveType.NONE);

		MoveType result = MoveType.NONE;

		if (currentPlayer.equals(player_1)) {
			if (piece.getPieceType().equals(PieceType.GREY)) {
				result = PlayGameController.getInstance().movePiece(oldY, oldX, newY, newX);
			}
		} else {
			if (piece.getPieceType().equals(PieceType.WHITE)) {
				result = PlayGameController.getInstance().movePiece(oldY, oldX, newY, newX);
			}
		}

		int x1, y1;
		if (result.equals(MoveType.KILL)) {
			x1 = Board.getSkipedTile().getCols();
			y1 = Board.getSkipedTile().getRows();
		} else {
			x1 = oldX + (newX - oldX) / 2;
			y1 = oldY + (newY - oldY) / 2;
		}
		if (Game.isOwnKill()) {
			x1 = Game.getKilledSoldier().getCols();
			y1 = Game.getKilledSoldier().getRows();
			result = MoveType.KILL;
		}
		updateScore(player_1);
		updateScore(player_2);
		return new MoveResult(result, boardView[x1][y1].getPiece());
	}

	private void colorTiles() {
		PlayGameController.getInstance().checkTilesToBeColored();
		refreshBoardTilesColors();
	}

	private void refreshBoardTilesColors() {
		TileIconType type;
		for (int x = 0; x < Consts.COLS; x++) {
			for (int y = 0; y < Consts.COLS; y++) {
				type = TileIconType.NONE;
				Color color = game.getBoard().getMyBoard()[y][x].getColor();
				if(color.equals(Color.YELLOW)) {
					type = TileIconType.QUESTION;
				}else if(color.equals(Color.GREEN) || color.equals(Color.ORANGE)) {
					type = TileIconType.HELP;
				}else if(color.equals(Color.RED)) {
					type = TileIconType.REPLAY;
				}else if(color.equals(Color.BLUE)) {
					type = TileIconType.BACK_TO_LIFE;
				}
				boardView[y][x].setIcon(type);
				boardView[y][x].getBaseShape().setFill(color);

				//				if(game.getBoard().getMyBoard()[y][x].getValue() == 0 && boardView[x][y].getPiece() != null) {
				//					boardView[y][x].setPiece(null);
				//					pieceGroup.getChildren().remove(boardView[y][x].getPiece());
				//				}
				// boardView[y][x].setStroke(null);
				//				if(game.getBoard().getMyBoard()[y][x].getValue() == 0) {
				//					boardView[x][y].setPiece(null);
				//				}
			}
		}
	}

	private ArrayList<Tile> suggestedTileBlueMove() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		if (currentPlayer.equals(player_1)) {
			tiles = PlayGameController.getInstance().blueMoveSuggestedTiles(true);
		}

		if (currentPlayer.equals(player_2)) {
			tiles = PlayGameController.getInstance().blueMoveSuggestedTiles(false);
		}

		return tiles;

	}

	public void popQuestion() {

		ans1.setSelected(false);
		ans2.setSelected(false);
		ans3.setSelected(false);
		ans4.setSelected(false);
		boardPane.setDisable(true);
		questionPane.setVisible(true);
		q = p.popQuestion();
		ans1.setVisible(true);
		ans2.setVisible(true);
		ans3.setVisible(true);
		ans4.setVisible(true);
		rightA = q.getRightAnswer();
		question.setText(q.getText());
		ans1.setText(q.getAnswers().get(0));
		ans2.setText(q.getAnswers().get(1));
		if (q.getAnswers().size() == 2) {
			ans3.setVisible(false);
			ans4.setVisible(false);
		} else if (q.getAnswers().size() == 3) {
			ans3.setText(q.getAnswers().get(2));
			ans3.setVisible(false);
			ans4.setVisible(false);
		} else if (q.getAnswers().size() == 4) {
			ans3.setText(q.getAnswers().get(2));
			ans4.setText(q.getAnswers().get(3));
			ans3.setVisible(true);
			ans4.setVisible(true);
		}

		if (q.getDifficulty().equals(Difficulty.EASY)) {
			easy.setVisible(true);
			medium.setVisible(false);
			hard.setVisible(false);
		} else if (q.getDifficulty().equals(Difficulty.MEDIUM)) {

			medium.setVisible(true);
			hard.setVisible(false);
			easy.setVisible(false);
		} else {
			hard.setVisible(true);
			medium.setVisible(false);
			easy.setVisible(false);
		}

	}

	public void updateScore(Player p) {
		if (p.equals(player_1)) {
			point1.setText(String.valueOf(player_1.getScore()));
		} else if (p.equals(player_2)) {
			point2.setText(String.valueOf(player_2.getScore()));

		}
	}

	private int toBoard(double pixel) {
		return (int) (pixel + Consts.TILE_SIZE / 2) / Consts.TILE_SIZE;
	}

	public ArrayList<Tile> getSuggestedTilesArray() {
		return PlayGameController.getInstance().getSuggestedTilesArrayForPlayer();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hard.setVisible(false);
		medium.setVisible(false);
		easy.setVisible(false);
		questionPane.setVisible(false);
		Game.notFinished = true;
		timer = new TimerForGame();
		t = new Thread(timer);
		t.start();
		////////////////////////////
		PlayerTimer1 = new TimerForPlayer1(new ColorTilesHandler() {
			@Override
			public void showColor(Color color) {
				// TODO Auto-generated method stub
				ArrayList<Tile> suggestedTiles = getSuggestedTilesArray();
				if (color.equals(Color.GREEN))
					PlayGameController.getInstance().colorRandomTile(suggestedTiles, color);
				else if (color.equals(Color.ORANGE))
					PlayGameController.getInstance().colorAllTiles(suggestedTiles, color);
				refreshBoardTilesColors();
			}
		});
		tp1 = new Thread(PlayerTimer1);
		tp1.start();
		player1.setText(PlayGameController.getInstance().getPlayer1().getUsername());
		player2.setText(PlayGameController.getInstance().getPlayer2().getUsername());
		point1.setText(Integer.toString(PlayGameController.getInstance().getPlayer1().getScore()));
		point2.setText(Integer.toString(PlayGameController.getInstance().getPlayer2().getScore()));
		tileGroup = new Group();
		pieceGroup = new Group();
		player_1 = PlayGameController.getInstance().getGame().getPlayer1();
		player_2 = PlayGameController.getInstance().getGame().getPlayer2();
		currentPlayer = player_1;
		game = PlayGameController.getInstance().getGame();
		Game.setP1Turn(game.isP1Turn());
		if(WebCamPreviewController.profilePic.getImage()!=null)
		{
			player1image.setFill(new ImagePattern(WebCamPreviewController.profilePic.getImage()));
			player1image.setStroke(player_1.getColor());
			player1image.setStrokeWidth(5);
		}
		else
		{
			player1image.setFill(player_1.getColor());
		}
		if(WebCamPreviewController.profilePic2.getImage()!=null)
		{
			player2image.setFill(new ImagePattern(WebCamPreviewController.profilePic2.getImage()));
			player2image.setStroke(player_2.getColor());
			player2image.setStrokeWidth(5);
		}
		else
		{
			player2image.setFill(player_2.getColor());
		}



		createBoardView();
		colorTiles();
		showMsg("Let's start the game");
	}

	public class TimerForPlayer1 implements Runnable {

		private int second;
		private int mints;
		private Label l;
		ColorTilesHandler handler;

		public Label getL() {
			return l;
		}

		public void setL(Label l) {
			this.l = l;
		}

		public int getSecond() {
			return second;
		}

		public int getMints() {
			return mints;
		}

		public void setMints(int mints) {
			this.mints = mints;
		}

		public void setSecond(int second) {
			this.second = second;
		}

		public void reset() {
			this.second = -1;
			this.mints = 0;
		}

		public TimerForPlayer1(ColorTilesHandler handler) {
			this.second = -1;
			this.mints = 0;
			this.handler = handler;
		}

		public void getLabel(Label text) {
			l = text;
		}

		@Override
		public void run() {
			reset();
			while (Game.getIsP1Turn() && Game.notFinished) {
				player1.setTextFill(Color.RED);
				player2.setTextFill(Color.BLACK);
				p2Turn.setVisible(false);
				p1Turn.setVisible(true);
				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}
				if (second == 10 && mints == 0)
					handler.showColor(Color.GREEN);
				if (second == 20 && mints == 0)
					handler.showColor(Color.ORANGE);
				Platform.runLater(() -> {
					if (mints < 10 && second < 10) {
						playerTimer.setText("0" + mints + " : 0" + second);
					} else if (mints < 10) {
						playerTimer.setText("0" + mints + " : " + second);
					} else if (second < 10) {
						playerTimer.setText(mints + " : 0" + second);
					} else {
						playerTimer.setText(mints + " : " + second);
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//			if (PlayerTimer1.getMints() < 1)
			//				player_1.setScore(60 - PlayerTimer1.getSecond());
			//			else
			//				player_1.setScore(60 - (PlayerTimer1.getSecond() + 60 * PlayerTimer1.getMints()));
			// updateScore(player_1);
			reset();

			while (!Game.getIsP1Turn() && Game.notFinished) {
				player2.setTextFill(Color.RED);
				player1.setTextFill(Color.BLACK);
				p1Turn.setVisible(false);
				p2Turn.setVisible(true);

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}
				if (second == 10 && mints == 0)
					handler.showColor(Color.GREEN);
				if (second == 20 && mints == 0)
					handler.showColor(Color.ORANGE);
				Platform.runLater(() -> {
					if (mints < 10 && second < 10) {
						playerTimer.setText("0" + mints + " : 0" + second);
					} else if (mints < 10) {
						playerTimer.setText("0" + mints + " : " + second);
					} else if (second < 10) {
						playerTimer.setText(mints + " : 0" + second);
					} else {
						playerTimer.setText(mints + " : " + second);
					}

				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//			if (PlayerTimer1.getMints() < 1)
			//				player_2.setScore(60 - PlayerTimer1.getSecond());
			//			else
			//				player_2.setScore(60 - (PlayerTimer1.getSecond() + 60 * PlayerTimer1.getMints()));
			if (Game.notFinished) {
				run();
			}

		}

	}

	public class TimerForGame implements Runnable {

		private int second;
		private int mints;
		private Label l;

		public Label getL() {
			return l;
		}

		public void setL(Label l) {
			this.l = l;
		}

		public int getSecond() {
			return second;
		}

		public int getMints() {
			return mints;
		}

		public void setMints(int mints) {
			this.mints = mints;
		}

		public void setSecond(int second) {
			this.second = second;
		}

		public void reset() {
			this.second = -1;
			this.mints = 0;
		}

		public TimerForGame() {
			this.second = -1;
			this.mints = 0;
		}

		public void getLabel(Label text) {
			l = text;
		}

		@Override
		public void run() {
			while (Game.notFinished) {

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}

				Platform.runLater(() -> {
					if (mints < 10 && second < 10) {
						gameTimer.setText("0" + mints + " : 0" + second);
					} else if (mints < 10) {
						gameTimer.setText("0" + mints + " : " + second);
					} else if (second < 10) {
						gameTimer.setText(mints + " : 0" + second);
					} else {
						gameTimer.setText(mints + " : " + second);
					}

				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public void check(ActionEvent event) {
		if ((ans1.isSelected() && rightA == 1) || (ans2.isSelected() && rightA == 2)
				|| (ans3.isSelected() && rightA == 3) || (ans4.isSelected() && rightA == 4)) {
			if (q.getDifficulty().equals(Difficulty.HARD)) {
				currentPlayer.updateScore(500);
			} else if (q.getDifficulty().equals(Difficulty.MEDIUM)) {
				currentPlayer.updateScore(200);

			} else if (q.getDifficulty().equals(Difficulty.EASY)) {
				currentPlayer.updateScore(100);

			}
		} else {
			if (q.getDifficulty().equals(Difficulty.HARD)) {
				currentPlayer.updateScore(-50);
			} else if (q.getDifficulty().equals(Difficulty.MEDIUM)) {
				currentPlayer.updateScore(-100);

			} else if (q.getDifficulty().equals(Difficulty.EASY)) {
				currentPlayer.updateScore(-250);

			}
		}
		if (!ans1.isSelected() && !ans2.isSelected() && !ans3.isSelected() && !ans4.isSelected()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No answer");
			alert.setContentText("You must choose an answer!");
			alert.show();
			return;
		} else {
			if (currentPlayer.equals(player_1)) {
				if (PlayerTimer1.getMints() < 1)
					player_1.setScore(60 - PlayerTimer1.getSecond());
				else
					player_1.setScore(60 - (PlayerTimer1.getSecond() + 60 * PlayerTimer1.getMints()));
			} else {
				if (PlayerTimer1.getMints() < 1)
					player_2.setScore(60 - PlayerTimer1.getSecond());
				else
					player_2.setScore(60 - (PlayerTimer1.getSecond() + 60 * PlayerTimer1.getMints()));

			}
			updateScore(player_1);
			updateScore(player_2);
			PlayGameController.getInstance().switchTurnNow();
			questionPane.setVisible(false);
			boardPane.setDisable(false);
			colorTiles();
		}

	}
	
	private void showMsg(String msg) {
		Color color = currentPlayer.getColor();
		if (color.equals(Color.BLACK)) {
			msgLabel.setTextFill(Color.WHITE);
		}else {
			msgLabel.setTextFill(Color.BLACK);
		}
		
		msgPane.setVisible(true);
		msgLabel.setText(msg);
		msgPane.setStyle("-fx-background-radius: 100; -fx-border-radius: 100; -fx-background-color: " + toHexString(color));
//		msgPane.setStyle("-fx-background-raduis: 100");

//		msgPane.setBackground(color);
//		msgPane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(2500), 
                        new KeyValue(msgPane.visibleProperty(), false)));
        timeline.play();
	}

	private String toHexString(Color color) {
		  int r = ((int) Math.round(color.getRed()     * 255)) << 24;
		  int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
		  int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
		  int a = ((int) Math.round(color.getOpacity() * 255));
		  return String.format("#%08X", (r + g + b + a));
		}
}
