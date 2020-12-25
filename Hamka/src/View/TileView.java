package View;

import Utils.Consts;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class represents a board tile
 * @author rawix
 *
 */
public class TileView extends Rectangle{
	
	private Piece piece;
	
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
		setWidth(Consts.TILE_SIZE);
		setHeight(Consts.TILE_SIZE);
		relocate(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE);
		//changes in the colors
		setFill(isWhite ? Color.BLACK : Color.WHITE);
	}

}
