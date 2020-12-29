package Model;

import javafx.scene.paint.Color;

public class BlackSoldier extends Tile {

	public BlackSoldier(int cols, int rows) {
		super(2, Color.BLACK, cols, rows);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean upgradeToQueen() {
	if( MoveValidation.toIndex(getRows(), getCols()) >= 29 && MoveValidation.toIndex(getRows(), getCols()) < 33)
	{
		setValue(22);
		return true;
	}
		return false;
	}

	@Override
	public boolean isQueen() {
		if(getValue()==22)
			return true;
		return false;
	}
	@Override
	public Tile makeCopy() {
		BlackSoldier w=null;
		try {
			w=(BlackSoldier)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}
	
}
