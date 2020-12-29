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
	private int x_value;
	private int y_value;
	
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
		setWidth(Consts.TILE_SIZE);
		setHeight(Consts.TILE_SIZE);
		relocate(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE);
		//changes in the colors
		setFill(isWhite ? Color.WHITE : Color.BLACK);
		
	}
	
	public TileView(Color color , int x, int y)
	{
		setX_value(x);
		setY_value(y);
		setWidth(Consts.TILE_SIZE);
		setHeight(Consts.TILE_SIZE);
		relocate(x * Consts.TILE_SIZE, y * Consts.TILE_SIZE);
		//changes in the colors
		setFill(color);
		
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
	
	
	

}
