package Model;

import javafx.scene.paint.Color;

public class WhiteTile extends Tile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WhiteTile(int cols, int rows) {
		super(0,  Color.WHITE, cols, rows);
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
		WhiteTile w=null;
		try {
			w=(WhiteTile)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}
	@Override
	public String subClass() {
		// TODO Auto-generated method stub
		return "WhiteTile";
	}

}
