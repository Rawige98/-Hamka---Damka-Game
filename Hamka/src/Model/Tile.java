package Model;

import javafx.scene.paint.Color;

public class Tile {
	private int value;
	private java.awt.Color color;
	private int x;
	private int y;
	public Tile(int value, java.awt.Color color, int x, int y) {
		super();
		this.value = value;
		this.setColor(color);
		this.x = x;
		this.y = y;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	//------------------------------------
	/**
	 * in this method we upgrade the soldier to queen
	 * @param Tile T
	 * @return true if the soldier upgraded successfully
	 */
	public boolean upgradeToQueen() {
		if(this.getValue()==1) {
			this.setValue(11);
			return true;
		}
		if(this.getValue()==2) {
			this.setValue(22);
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return   value+"";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


	
	
}
