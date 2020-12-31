package View;

import Utils.Consts;
import Utils.PieceType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

	public PieceType pieceType;
	public String iconPath;
	private double mouseX, mouseY, oldX, oldY;
	Ellipse bgEllipse , ellipse, crownEllipse;
	Color color;
	
	public Piece(PieceType pieceType, Color color, int x, int y ,String iconPath) {
		this.pieceType = pieceType;
		oldX = x * Consts.TILE_SIZE;	
		oldY = y * Consts.TILE_SIZE;
		this.iconPath = iconPath;
		this.color = color;

		relocate(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE	);

		bgEllipse = new Ellipse(Consts.TILE_SIZE*0.3125 , Consts.TILE_SIZE*0.26);
		bgEllipse.setFill(Color.WHITE);

		bgEllipse.setStroke(Color.BLACK);
		bgEllipse.setStrokeWidth(Consts.TILE_SIZE * 0.03);

		bgEllipse.setTranslateX((Consts.TILE_SIZE - Consts.TILE_SIZE * 0.3125 * 2) / 2);
		bgEllipse.setTranslateY((Consts.TILE_SIZE - Consts.TILE_SIZE * 0.26 * 2) / 2 + Consts.TILE_SIZE * 0.07);

		ellipse = new Ellipse(Consts.TILE_SIZE*0.3125 , Consts.TILE_SIZE*0.26);
//		ellipse.setFill(pieceType == PieceType.WHITE ? Color.WHITE : Color.GREY);
		ellipse.setFill(color);
		
		ellipse.setStroke(Color.BLACK);
		ellipse.setStrokeWidth(Consts.TILE_SIZE * 0.03);

		ellipse.setTranslateX((Consts.TILE_SIZE - Consts.TILE_SIZE * 0.3125 * 2) / 2);
		ellipse.setTranslateY((Consts.TILE_SIZE - Consts.TILE_SIZE * 0.26 * 2) / 2);


		crownEllipse = new Ellipse(Consts.TILE_SIZE*0.2 , Consts.TILE_SIZE*0.15);
		crownEllipse.setFill(Color.TRANSPARENT);

		crownEllipse.setStroke(Color.TRANSPARENT);
		crownEllipse.setStrokeWidth(Consts.TILE_SIZE * 0.02);

		crownEllipse.setTranslateX((Consts.TILE_SIZE - Consts.TILE_SIZE * 0.3125 * 2) / 2);
		crownEllipse.setTranslateY((Consts.TILE_SIZE - Consts.TILE_SIZE * 0.26 * 2) / 2);
		
		
		getChildren().addAll(bgEllipse , ellipse, crownEllipse);

		setOnMousePressed(e -> {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
		});

		setOnMouseDragged(e ->{
			relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
		});
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	public void move(int x, int y) {
		oldX = x*Consts.TILE_SIZE;
		oldY = y*Consts.TILE_SIZE;
		relocate(oldX, oldY);
	}

	public double getOldX() {
		return oldX;
	}
	public double getOldY() {
		return oldY;
	}

	public void aboartMove() {
		relocate(oldX, oldY);
	}

	public void showCrown() {
		Image greyCrown = new Image("/images/ic_grey_crown.png", false);
		Image whiteCrown = new Image("/images/ic_white_crown.png", false);		
		Image goldCrown = new Image("/images/ic_crown.png", false);

//		Image blackCrown = new Image("/images/ic_black_crown.png", false);
//		ImagePattern pattern = (pieceType == PieceType.WHITE) ? new ImagePattern(whiteCrown) : new ImagePattern(greyCrown);
		ImagePattern pattern = new ImagePattern(goldCrown);

//		ellipse.setFill(pattern);
		crownEllipse.setFill(pattern);
		bgEllipse.setFill(Color.GOLD);

	}
}
