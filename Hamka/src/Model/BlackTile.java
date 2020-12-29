package Model;

import java.awt.Color;

public class BlackTile extends Tile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlackTile( int cols, int rows) {
		super(0, Color.black, cols, rows);
		// TODO Auto-generated constructor stub
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
		BlackTile w=null;
		try {
			w=(BlackTile)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}

	@Override
	public String subClass() {
		// TODO Auto-generated method stub
		return "BlackTile";
	}
}
