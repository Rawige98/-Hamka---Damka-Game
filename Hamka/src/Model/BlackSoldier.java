package Model;

import javafx.scene.paint.Color;

public class BlackSoldier extends Tile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BlackSoldier(int cols, int rows) {
		super(2, Color.BLACK, cols, rows);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean upgradeToQueen() {
	if( MoveValidation.toIndex(getRows(), getCols()) >= 28 && MoveValidation.toIndex(getRows(), getCols()) < 32)
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
	public Tile makeCopy(int cols,int rows) {
		BlackSoldier w=null;
		try {
			w=(BlackSoldier)super.clone();
			w.setCols(cols);
			w.setRows(rows);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}

	@Override
	public String subClass() {
		// TODO Auto-generated method stub
		return "BlackSoldier";
	}
	
}
