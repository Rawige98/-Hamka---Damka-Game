package Model;

import java.awt.Color;

public class Tile {
	private int value;
	private java.awt.Color color;
	private int rows;
	private int cols;
	
	public Tile(int value, java.awt.Color color,  int cols,int rows) {
		super();
		this.value = value;
		this.color = color;
		this.rows = rows;
		this.cols = cols;
	}

	//-------------------Getters and Setters-----------
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public java.awt.Color getColor() {
		return color;
	}
	public void setColor(java.awt.Color color) {
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

	//------------------------------------
	/**
	 * in this method we upgrade the soldier to queen
	 * @param Tile T
	 * @return true if the soldier upgraded successfully
	 */
	public boolean upgradeToQueen() {
		if(this.getValue()==1&&Board.toIndex(rows, cols)>=0&&Board.toIndex(rows, cols)<4) {
			this.setValue(11);
			return true;
		}
		if(this.getValue()==2&&Board.toIndex(rows, cols)>=29&&Board.toIndex(rows, cols)<33) {
			this.setValue(22);
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		String colorTxt;
		if(this.color.equals(Color.yellow))
			colorTxt = "YELLOW";
		else if(this.color.equals(Color.red))
			colorTxt = "RED";
		else if(this.color.equals(Color.blue))
			colorTxt = "BLUE";
		else if(this.color.equals(Color.orange))
			colorTxt = "ORANGE" ;
		else if(this.color.equals(Color.green))
			colorTxt = "GREEN";
		else if(this.color.equals(Color.white)) {
			colorTxt = "WHITE";
		}
		else
			colorTxt = "BLACK";
		
		
		return  value+" - "+colorTxt;
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

	
	
}
