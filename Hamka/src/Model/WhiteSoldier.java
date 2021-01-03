package Model;

public class WhiteSoldier extends Tile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WhiteSoldier(int cols, int rows) {
		super(1,  javafx.scene.paint.Color.BLACK, cols, rows);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean upgradeToQueen() {

		if( MoveValidation.toIndex(getRows(), getCols()) >= 0 && MoveValidation.toIndex(getRows(), getCols()) < 4)
		{
			setValue(11);
			return true;
		}
			return false;
		}
	@Override
	public boolean isQueen() {
		if(getValue()==11)
			return true;
		return false;
	}
	@Override
	public Tile makeCopy(int cols,int rows) {
		WhiteSoldier w=null;
		try {
			w=(WhiteSoldier)super.clone();
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
		return "WhiteSoldier";
	}
}
