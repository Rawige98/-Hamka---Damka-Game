package View;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.MainPageController;
import Controller.PlayGameController;
import Model.Game;
import Model.Tile;
import Model.TimeForPlayer;
import Utils.Consts;
import Utils.MoveResult;
import Utils.MoveType;
import Utils.PieceType;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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
	private BorderPane rootBorderPane;

	private Group tileGroup;
	private Group pieceGroup;
	private TileView[][] boardView = new TileView[Consts.ROWS][Consts.COLS];
	
	private Game game;

	@FXML
	void closeWindow(ActionEvent event) {
		((Stage) player1.getScene().getWindow()).close();
	}

	@FXML
	public void back(ActionEvent event) throws Exception {
		((Stage) player1.getScene().getWindow()).close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
		Scene scene = new Scene(root, 581, 449);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka game");
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		player1.setText(MainPageController.getPlayer1().getUsername());
		player2.setText(MainPageController.getPlayer2().getUsername());
		point1.setText(Integer.toString(MainPageController.getPlayer1().getScore()));
		point2.setText(Integer.toString(MainPageController.getPlayer2().getScore()));
		tileGroup = new Group();
		pieceGroup = new Group();
		rootBorderPane.setCenter(createBoardView());

		PlayGameController.getInstance();
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

			MoveResult moveResult = tryMove(piece, newX, newY);

			int x0 = toBoard(piece.getOldX());
			int y0 = toBoard(piece.getOldY());

			switch (moveResult.getType()) {
			case NONE:
				piece.aboartMove();
				break;
			case NORMAL:
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);
				break;

			case KILL:
				piece.move(newX, newY);
				boardView[x0][y0].setPiece(null);
				boardView[newX][newY].setPiece(piece);

				Piece otherPiece = moveResult.getPiece();
				boardView[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				pieceGroup.getChildren().remove(otherPiece);
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

	
	private MoveResult tryMoveTest(Piece piece, int newX, int newY) {
		
		boolean move;
		Tile currentTile;
		Tile nextTile;
		if (newX < 0 || newY < 0 || newX >= Consts.ROWS || newY >= Consts.COLS)
			return new MoveResult(MoveType.NONE);
		
		int oldX = toBoard(piece.getOldX());
		int oldY = toBoard(piece.getOldY());
		
	//	game=PlayGameController.getInstance().getGame();
		currentTile= game.getBoard().getTile(oldX, oldY);

		move=game.move(oldX, oldY, newX, newY);
		
		if (Math.abs(newX - oldX) == 1 && newY - oldY == piece.getPieceType().moveDir) {
			return new MoveResult(MoveType.NORMAL);
		} else if (Math.abs(newX - oldX) == 2 && newY - oldY == piece.getPieceType().moveDir * 2) {

			int x1 = oldX + (newX - oldX) / 2;
			int y1 = oldY + (newY - oldY) / 2;

			if(game.getBoard().validateIDs(true, oldX, oldY, newX, newY, false))
				return new MoveResult(MoveType.KILL, boardView[x1][y1].getPiece());
			
			}
		

		return new MoveResult(MoveType.NONE);
	
		
	}
	
	
	private int toBoard(double pixel) {
		return (int) (pixel + Consts.TILE_SIZE / 2) / Consts.TILE_SIZE;
	}
}
