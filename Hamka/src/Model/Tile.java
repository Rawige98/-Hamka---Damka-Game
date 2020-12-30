package Model;

import java.io.Serializable;

import javafx.scene.paint.Color;


public abstract class Tile implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int value;
	private Color color;
	private int rows;
	private int cols;
	
	public Tile(int value, Color color,  int cols,int rows) {
		super();
		this.value = value;
		this.setColor(color);
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
	public Tile getClone(Tile t,int cols,int rows) {
		
		return t.makeCopy( cols, rows);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + cols;
		result = prime * result + rows;
		result = prime * result + value;
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
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (cols != other.cols)
			return false;
		if (rows != other.rows)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value+"";
	}

	/**
	 * in this method we upgrade the soldier to queen
	 * 
	 * @param TileView T
	 * @return true if the soldier upgraded successfully
	 */
	public abstract boolean upgradeToQueen() ;
	public abstract boolean isQueen();
	public abstract Tile makeCopy(int cols,int rows);
	public abstract String subClass();

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
}
