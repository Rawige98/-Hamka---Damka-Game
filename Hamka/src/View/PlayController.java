package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import Controller.PlayGameController;
import Model.Game;
import Model.Player;
import Model.Tile;
import Model.TimeForPlayer;
import Utils.Consts;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
	private Label player2;
	@FXML
	private Label point1;

	@FXML
	private Label playerTimer;
	@FXML
	private Label point2;

	@FXML
	public static Pane boardPane;

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Label gameTimer;

	@FXML
	private BorderPane rootBorderPane;

	private Group tileGroup;
	private Group pieceGroup;
	private TileView[][] boardView = new TileView[Consts.ROWS][Consts.COLS];
	private Thread t = null;
	private Thread tp1 = null;
//	private Thread tp2 = null;
	private Game game;
	private Player player_1;
	private Player player_2;
	private boolean gameInProgress; // Is a game currently in progress?
	private Player currentPlayer; // Whose turn is it now? The possible values
	private TimerForGame timer;
	private TimerForPlayer1 PlayerTimer1;
	int count = 0;
	//private TimerForPlayer2 PlayerTimer2;
	@FXML
	void closeWindow(ActionEvent event) {


		//tp1.stop();
		Game.notFinished=false;
		t.stop();
		tp1.stop();
		((Stage) player1.getScene().getWindow()).close();
	}

	@FXML
	public void back(ActionEvent event) throws Exception {
		Game.notFinished=false;
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
		boardPane = new Pane();
		boardPane.setPrefSize(Consts.COLS * Consts.TILE_SIZE, Consts.ROWS * Consts.TILE_SIZE);
		boardPane.getChildren().addAll(tileGroup, pieceGroup);

		for (int y = 0; y < Consts.ROWS; y++) {
			for (int x = 0; x < Consts.COLS; x++) {

				TileView tileView = new TileView((x + y) % 2 == 0, x, y);
				boardView[x][y] = tileView;
				tileGroup.getChildren().add(tileView);

				Piece piece = null;
				// changes in (if)
				if (y <= 2 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.RED, x, y);
				}
				if (y >= 5 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.BLUE, x, y);
				}
				if (piece != null) {
					tileView.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}
			}
		}
		return boardPane;
	}

	private Piece makePiece(PieceType type, int x, int y) {
		Piece piece = new Piece(type, x, y, "b.jpg");
		piece.setOnMouseReleased(e -> {
			int newX = toBoard(piece.getLayoutX());
			int newY = toBoard(piece.getLayoutY());
			// calling tryMoveTest instead of tryMove
			MoveResult moveResult = tryMoveTest(piece, newX, newY);

			int x0 = toBoard(piece.getOldX());
			int y0 = toBoard(piece.getOldY());
			if (boardView[newX][newY].getFill().equals(Color.YELLOW)) {
				try {
					popQuestion();
					updateScore(player_1);
					updateScore(player_2);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("************** YELLOW TILE *******************");
			}
			switch (moveResult.getType()) {
			case NONE:
				piece.aboartMove();
				break;
			case NORMAL:
				turnOffAllColors();
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);
				showYellowTiles();
				break;

			case KILL:
				turnOffAllColors();
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);

				Piece otherPiece = moveResult.getPiece();
				System.out.println("the other piece is:" + otherPiece);
				boardView[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				pieceGroup.getChildren().remove(otherPiece);
				showYellowTiles();
				break;
			default:
				break;
			}

			
		});
		
		return piece;
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

		if (newX < 0 || newY < 0 || newX >= Consts.ROWS || newY >= Consts.COLS)
			return new MoveResult(MoveType.NONE);

		MoveType result = MoveType.NONE;
		int oldX = toBoard(piece.getOldX());
		int oldY = toBoard(piece.getOldY());
		int x1 = oldX + (newX - oldX) / 2;
		int y1 = oldY + (newY - oldY) / 2;

		if (currentPlayer.equals(player_1)) {
			if (piece.getPieceType().equals(PieceType.BLUE)) {
				result = PlayGameController.getInstance().movePiece(oldY, oldX, newY, newX, player_1, true);
				PlayGameController.getInstance().getGame();
				Game.setP1Turn(!Game.getIsP1Turn());
				currentPlayer = player_2;

			}
		} else {
			if (piece.getPieceType().equals(PieceType.RED)) {
				result = PlayGameController.getInstance().movePiece(oldY, oldX, newY, newX, player_2, false);

				PlayGameController.getInstance().getGame();
				Game.setP1Turn(!PlayGameController.getInstance().getGame().isP1Turn());
				currentPlayer = player_1;

			}

		}
		
		

		updateScore(player_1);
		updateScore(player_2);

		return new MoveResult(result, boardView[x1][y1].getPiece());
	}




	private void showYellowTiles() {
		ArrayList<Tile> yellowTiles = PlayGameController.getInstance().returnYellowTiles();
		
		for (Tile tile : yellowTiles) {
			int x = tile.getRows();
			int y = tile.getCols();
			boardView[y][x].setFill(Color.YELLOW);


	}

}


	private void turnOffAllColors() {
		Color color;
		for (int x = 0; x < Consts.COLS; x++) {
			for (int y = 0; y < Consts.ROWS; y++) {
				color = ((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
				boardView[x][y].setFill(color);
			}
		}
	}

	private void popQuestion() throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/PopQuestion.fxml"));
		Scene scene = new Scene(root, 473, 310);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("questions");
		primaryStage.show();

	}

	public  void updateScore(Player p) {
		if (p.equals(player_1)) {
			point1.setText(String.valueOf(player_1.getScore()));
		} else if (p.equals(player_2)) {
			point2.setText(String.valueOf(player_2.getScore()));

		}
	}

	private int toBoard(double pixel) {
		return (int) (pixel + Consts.TILE_SIZE / 2) / Consts.TILE_SIZE;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Game.notFinished=true;
		Game.setP1Turn(true);
		timer = new TimerForGame();
		t = new Thread(timer);
		t.start();
		////////////////////////////
		PlayerTimer1 = new TimerForPlayer1();
		tp1 =  new Thread(PlayerTimer1);
		tp1.start();

		player1.setText(MainPageController.getPlayer1().getUsername());
		player2.setText(MainPageController.getPlayer2().getUsername());
		point1.setText(Integer.toString(MainPageController.getPlayer1().getScore()));
		point2.setText(Integer.toString(MainPageController.getPlayer2().getScore()));
		tileGroup = new Group();
		pieceGroup = new Group();
		rootBorderPane.setCenter(createBoardView());

		player_1 = MainPageController.getPlayer1();
		player_2 = MainPageController.getPlayer2();
		currentPlayer = player_1;
		PlayGameController.getInstance().startGame(player_1, player_2);
		showYellowTiles();

	}

	public class TimerForPlayer1 implements Runnable {

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
		public TimerForPlayer1() {
			this.second = -1;
			this.mints = 0;
		}


		public void getLabel(Label text) {
		   l=text;
		}

		@Override
		public void run() {
			//System.out.println("aya");
			//System.out.println(Game.getIsP1Turn());
			reset();

			while (Game.getIsP1Turn()&&Game.notFinished) {

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}


				Platform.runLater(()->{

					if(mints<10 && second <10)
					{
						playerTimer.setText("0"+mints+" : 0"+second);
					}
					else if(mints<10)
					{
						playerTimer.setText("0"+mints+" : "+second);
					}
					else if(second<10)
					{
						playerTimer.setText(mints+" : 0"+second);
					}
					else
					{
						playerTimer.setText(mints+" : "+second);
					}

				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reset();

			while (!Game.getIsP1Turn()&&Game.notFinished) {

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}

				Platform.runLater(()->{
					if(mints<10 && second <10)
					{
						playerTimer.setText("0"+mints+" : 0"+second);
					}
					else if(mints<10)
					{
						playerTimer.setText("0"+mints+" : "+second);
					}
					else if(second<10)
					{
						playerTimer.setText(mints+" : 0"+second);
					}
					else
					{
						playerTimer.setText(mints+" : "+second);
					}

				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
           if(Game.notFinished){
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
		   l=text;
		}

		@Override
		public void run() {
			System.out.println("aya");

			while (Game.notFinished) {

				second++;
				if (second >= 60) {
					second = 0;
					mints++;
				}

				Platform.runLater(()->{
					if(mints<10 && second <10)
					{
						gameTimer.setText("0"+mints+" : 0"+second);
					}
					else if(mints<10)
					{
						gameTimer.setText("0"+mints+" : "+second);
					}
					else if(second<10)
					{
						gameTimer.setText(mints+" : 0"+second);
					}
					else
					{
						gameTimer.setText(mints+" : "+second);
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




}
