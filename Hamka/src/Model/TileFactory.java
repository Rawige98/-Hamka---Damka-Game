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
}
