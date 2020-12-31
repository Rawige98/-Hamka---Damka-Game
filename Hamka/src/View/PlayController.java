package View;

import java.beans.Visibility;
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
import Model.Tile;
import Model.WhiteSoldier;
import Utils.Consts;
import Utils.Difficulty;
import Utils.MoveResult;
import Utils.MoveType;
import Utils.PieceType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	public Pane boardPane;

	@FXML
	private AnchorPane rootPane;
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
				}
				if (game.getBoard().getMyBoard()[x][y] instanceof WhiteSoldier) {
					piece = makePiece(PieceType.GREY, player_1.getColor(),  x, y);
				}
				if (piece != null) {
					tileView.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}
				tileView.setOnMousePressed(e -> {
					if(lastColor.equals(Color.BLUE)) {
						colorSuggesstedForBlueTile();
						double mouseX = e.getSceneX();
						double mouseY = e.getSceneY();
						int col = toBoard(mouseX);
						int row = toBoard(mouseY);
						Tile tile = PlayGameController.getInstance().getGame().getBoard().getMyBoard()[col][row];
						if(suggestedTileBlueMove().contains(tile)) {
							Piece newPiece = new Piece((currentPlayer.equals(player1)? PieceType.GREY : PieceType.WHITE), Color.AQUAMARINE, col, row, null);
							boardView[row][col].setPiece(newPiece);
						}
					}
				});
			}
		}
		return boardPane;
	}

	private void colorSuggesstedForBlueTile() {
		// TODO Auto-generated method stub
		for(Tile tile : suggestedTileBlueMove()) {
			boardView[tile.getRows()][tile.getCols()].setFill(Color.BEIGE);
		}
	}

	private Piece makePiece(PieceType type, Color color, int x, int y) {
		Piece piece = new Piece(type, color, x, y, "b.jpg");
		piece.setOnMouseReleased(e -> {
			int newX = toBoard(piece.getLayoutX());
			int newY = toBoard(piece.getLayoutY());
			int x0 = toBoard(piece.getOldX());
			int y0 = toBoard(piece.getOldY());
			// calling tryMoveTest instead of tryMove
			currentPlayer = PlayGameController.getInstance().getCurrentPlayer();
			MoveResult moveResult = tryMoveTest(piece, newX, newY);
			if (lastTile == null) {
				lastTile = boardView[x0][y0];
			}
			if (lastColor.equals(Color.RED)) {
				Piece lastPiece = lastTile.getPiece();
				if (!lastPiece.equals(piece)) {
					System.out.println("You must move the same soldier");
					moveResult.setType(MoveType.NONE);
				} else {
					System.out.println("GOOD");
				}
			}

			switch (moveResult.getType()) {
			case NONE:
				piece.aboartMove();
				break;
			case NORMAL:
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);
				// showYellowTiles();
				checkDestinationTile(boardView[newX][newY]);
				colorTiles();
				checkQueen(piece, newX, newY);
				break;

			case KILL:
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);
//				
				Piece otherPiece = moveResult.getPiece();
				boardView[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				pieceGroup.getChildren().remove(otherPiece);
				// showYellowTiles();
				checkDestinationTile(boardView[newX][newY]);
				colorTiles();
				checkQueen(piece, newX, newY);
				break;
			default:
				break;
			}

		});

		return piece;
	}

	private void checkQueen(Piece piece, int x, int y) {
		// TODO Auto-generated method stub
		if(PlayGameController.getInstance().checkIfQueen(x,y))
			piece.showCrown();
	}

	private void checkDestinationTile(TileView tileView) {
		lastTile = tileView;
		if (tileView.getFill().equals(Color.YELLOW)) {
			popQuestion();
			samePlayerTurn = true;
			lastColor = Color.YELLOW;
		} else if (tileView.getFill().equals(Color.RED)) {
			if (PlayGameController.getInstance().checkMovesAvailability(tileView.getX_value(), tileView.getY_value())) {
				samePlayerTurn = true;
				lastColor = Color.RED;
			} else {
				samePlayerTurn = false;
				lastColor = Color.BLACK;
			}
		} else if (tileView.getFill().equals(Color.GREEN)) {
			currentPlayer.updateScore(Consts.POINTS_FOR_GREEN_TILE);
			updateScore(currentPlayer);
			samePlayerTurn = false;
			lastColor = Color.GREEN;
		} else if (tileView.getFill().equals(Color.BLUE)) {
			lastColor = Color.BLUE;
			samePlayerTurn = true;
		} else {
			samePlayerTurn = false;
			lastColor = Color.BLACK;
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
			PlayGameController.getInstance().switchTurnNow();
			lastColor = Color.BLACK;
		}
	}

	private MoveResult tryMove(Piece piece, int newX, int newY) {

		if (newX < 0 || newY < 0 || newX >= Consts.ROWS || newY >= Consts.COLS)
			return new MoveResult(MoveType.NONE);

		if (boardView[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
			return new MoveResult(MoveType.NONE);
		}

		int x0 = toBoard(piece.getOldX());
		int y0 = toBoard(piece.getOldY());

		if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getPieceType().moveDir) {
			return new MoveResult(MoveType.NORMAL);
		} else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getPieceType().moveDir * 2) {

			int x1 = x0 + (newX - x0) / 2;
			int y1 = y0 + (newY - y0) / 2;

			if (boardView[x1][y1].hasPiece() && boardView[x1][y1].getPiece().getPieceType() != piece.getPieceType()) {
				return new MoveResult(MoveType.KILL, boardView[x1][y1].getPiece());
			}
		}

		return new MoveResult(MoveType.NONE);
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

		if (PlayGameController.getInstance().isQueen(newX, newX)) {

		}
		if(Game.isOwnKill()) {
			Game.setOwnKill(false);
			x1=Game.getKilledSoldier().getCols();
			y1=Game.getKilledSoldier().getRows();
			result=MoveType.KILL;
		}
		updateScore(player_1);
		updateScore(player_2);
		System.out.println(game.getGameState());
		return new MoveResult(result, boardView[x1][y1].getPiece());
	}
	private void colorTiles() {
		PlayGameController.getInstance().checkTilesToBeColored();
		refreshBoardTilesColors();
	}

	private void refreshBoardTilesColors() {
		for (int x = 0; x < Consts.COLS; x++) {
			for (int y = 0; y < Consts.COLS; y++) {
				Color color = game.getBoard().getMyBoard()[y][x].getColor();
				boardView[y][x].setFill(color);
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
		if (q.getAnswers().get(2).equals("")) {
			ans3.setVisible(false);
			ans4.setVisible(false);
		} else {
			ans3.setText(q.getAnswers().get(2));
			ans4.setText(q.getAnswers().get(3));

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
		questionPane.setVisible(false);
		Game.notFinished = true;
		Game.setP1Turn(true);
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
		player_1 = PlayGameController.getInstance().getPlayer1();
		player_2 = PlayGameController.getInstance().getPlayer2();
		currentPlayer = player_1;
		game = PlayGameController.getInstance().getGame();

		createBoardView();

		colorTiles();

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
			// System.out.println("aya");
			// System.out.println(Game.getIsP1Turn());
			reset();

			while (Game.getIsP1Turn() && Game.notFinished) {
				player1.setTextFill(Color.RED);
				player2.setTextFill(Color.BLACK);

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}

				if (second == 30 && mints == 0)
					handler.showColor(Color.GREEN);
				if (second == 30 && mints == 1)
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

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}

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
			System.out.println("true");
		} else {
			if (q.getDifficulty().equals(Difficulty.HARD)) {
				currentPlayer.updateScore(-50);
			} else if (q.getDifficulty().equals(Difficulty.MEDIUM)) {
				currentPlayer.updateScore(-100);

			} else if (q.getDifficulty().equals(Difficulty.EASY)) {
				currentPlayer.updateScore(-250);

			}
		}
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
	}

}
