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
	//---------------------------------------
	public boolean changeColor(Color c)
	{
		return true;
	}

}
