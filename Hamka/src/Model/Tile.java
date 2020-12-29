package Model;

import javafx.scene.paint.Color;

public class Tile implements Cloneable,TileOperations{
	private int value;
	private Color color;
	private int rows;
	private int cols;
	
	public Tile(int value, Color color,  int cols,int rows) {
		super();
		this.value = value;
		this.color = color;
		this.rows = rows;
		this.cols = cols;
	}

	// -------------------Getters and Setters-----------
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	// ------------------------------------

	//Clone Factory
	public Tile getClone(Tile t) {
		
		return t.makeCopy();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + rows;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (cols != other.cols)
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "row: " + rows + " col: " + cols + " color: "+color;
	}

	@Override
	public boolean upgradeToQueen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isQueen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tile makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
