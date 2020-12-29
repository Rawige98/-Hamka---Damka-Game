package Model;

import javafx.scene.paint.Color;

public class BlackTile extends Tile{
	
	public BlackTile( int cols, int rows) {
		super(0, Color.BLACK, cols, rows);
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
}
