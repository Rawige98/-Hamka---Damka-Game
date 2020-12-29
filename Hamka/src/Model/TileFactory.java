package Model;

public class TileFactory {
	public Tile makeTile(int i,int j) {
		
		if (MoveValidation.toIndex(i, j) >= 0 && MoveValidation.toIndex(i, j) <= 11) {
			return new BlackSoldier(j, i);
		} else {
			if (MoveValidation.toIndex(i, j) >= 20 && MoveValidation.toIndex(i, j) <= 31) {
				return new WhiteSoldier(j, i);
			} else {
				if (MoveValidation.toIndex(i, j) == -1)
					return new WhiteTile(j,i);
				else		
					return new BlackTile(j, i);
					
			}
		}
	}
	public Tile getSubTile(int col,int row,int val) {
		if(val==1||val==11) {
			WhiteSoldier x=new WhiteSoldier(col, row);
			x.setValue(val);
			return x;
		}
		else if(val==2||val==22) {
			BlackSoldier x=new BlackSoldier(col, row);
			x.setValue(val);
			return x;
		}
		else 
			if((col+row)%2==0)
				return new WhiteTile(col,row);
			else
				return new BlackTile(col,row);
	}
	
}
