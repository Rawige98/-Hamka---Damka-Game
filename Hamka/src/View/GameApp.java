package View;

import Utils.Consts;
import Utils.MoveResult;
import Utils.MoveType;
import Utils.PieceType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameApp extends Pane{

	private Group tileGroup = new Group();
	private Group pieceGroup = new Group();
	private TileView[][] boardView = new TileView[Consts.ROWS][Consts.COLS];
	
	public GameApp gameApp() {
		return (GameApp) createContent();
	}

	private Parent createContent() {
		Pane rootPane = new Pane();
		rootPane.setPrefSize(Consts.COLS * Consts.TILE_SIZE , Consts.ROWS * Consts.TILE_SIZE);
		rootPane.getChildren().addAll(tileGroup, pieceGroup);
		for(int y=0 ; y<Consts.ROWS ; y++) {
			for(int x=0 ; x<Consts.COLS ;x++) {
				TileView tileView = new TileView((x+y)%2 == 0, x, y);
				boardView[x][y] = tileView;
				tileGroup.getChildren().add(tileView);

				Piece piece = null;
				if(y <= 2 && (x+y)%2 != 0) {
					piece = makePiece(PieceType.RED, x, y);
				}
				if(y >= 5 && (x+y)%2 != 0) {
					piece = makePiece(PieceType.BLUE, x, y);
				}
				if(piece != null) {
					tileView.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}
			}
		}
		return rootPane;
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		Scene scene = new Scene(createContent());
//		primaryStage.setTitle("Hamka Game");
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}
//
//	public static void main(String[] args) {
//		launch(args);
//	}

	private Piece makePiece(PieceType type , int x, int y) {
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

	private int toBoard(double pixel) {
		return (int)(pixel + Consts.TILE_SIZE / 2) / Consts.TILE_SIZE;
	}
}
