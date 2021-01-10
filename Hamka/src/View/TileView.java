package View;

import Utils.Consts;
import Utils.TileIconType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


/**
 * This class represents a board tile
 * @author rawix
 *
 */
public class TileView extends StackPane{
	
	private Piece piece;
	private int x_value;
	private int y_value;
	private Rectangle iconShape , baseShape;
	private RotateTransition rotateTransition;

	public boolean hasPiece() {
		return piece!=null;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public TileView(boolean isWhite , int x , int y) {
		setX_value(x);
		setY_value(y);
		baseShape = new Rectangle(Consts.TILE_SIZE, Consts.TILE_SIZE);
		relocate(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE);
		//changes in the colors
		baseShape.setFill(isWhite ? Color.WHITE : Color.BLACK);
		iconShape = new Rectangle(Consts.TILE_SIZE-Consts.TILE_SIZE/3,Consts.TILE_SIZE-Consts.TILE_SIZE/3);
		iconShape.relocate(x * Consts.TILE_SIZE - (Consts.TILE_SIZE /2) , y * Consts.TILE_SIZE - (Consts.TILE_SIZE /2) );
		iconShape.setStroke(Color.TRANSPARENT);
		rotateTransition = new RotateTransition(Duration.seconds(10), iconShape);
		setIcon(TileIconType.NONE);
		getChildren().addAll(baseShape, iconShape);
	}
	


	public Rectangle getBaseShape() {
		return baseShape;
	}
	
	public Rectangle getIconShape() {
		return iconShape;
	}
	
	public int getX_value() {
		return x_value;
	}

	public void setX_value(int x_value) {
		this.x_value = x_value;
	}

	public int getY_value() {
		return y_value;
	}

	public void setY_value(int y_value) {
		this.y_value = y_value;
	}

	
	
	/*
	 * public void ChangeColor(Color color , int x, int y) {
	 * setWidth(Consts.TILE_SIZE); setHeight(Consts.TILE_SIZE); relocate(x *
	 * Consts.TILE_SIZE, y * Consts.TILE_SIZE); setFill(color); }
	 */
	
	public void setIcon(TileIconType iconType) {
		Image iconImage;
		if(iconType.equals(TileIconType.QUESTION)) {
			 iconImage = new Image("/images/question.png", false);
		}else if(iconType.equals(TileIconType.REPLAY)) {
			iconImage = new Image("/images/replay.png", false);
		}else if(iconType.equals(TileIconType.HELP)) {
			iconImage = new Image("/images/help.png", false);
		}else if(iconType.equals(TileIconType.BACK_TO_LIFE)) {
			iconImage = new Image("/images/back_to_life.png", false);
		}
		else if(iconType.equals(TileIconType.MOREPOINTS)) {
			iconImage = new Image("/images/50pls.png", false);
		}else {
			iconShape.setFill(null);
			return;
		}
		
		ImagePattern pattern = new ImagePattern(iconImage);
		iconShape.setFill(pattern);
	
	
		
	}

}
